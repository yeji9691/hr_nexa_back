package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexacro17.xapi.data.PlatformData;

import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.service.EmpInfoService;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.FamilyInfoTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.LicenseInfoTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.WorkInfoTO;
import kr.co.seoulit.insa.sys.mapper.DatasetBeanMapper;



@RequestMapping("/empinfomgmt/*")
@RestController
public class EmpDetailController {


	private final EmpInfoService empInfoService;

	private final DatasetBeanMapper datasetBeanMapper;

	public EmpDetailController(EmpInfoService empInfoService, DatasetBeanMapper datasetBeanMapper) {
		this.empInfoService = empInfoService;
		this.datasetBeanMapper = datasetBeanMapper;
	}

	@RequestMapping("/empdetail/all")
	public void findAllEmployeeInfo(@RequestAttribute("reqData") PlatformData reqData,
								@RequestAttribute("resData") PlatformData resData) throws Exception{


		System.out.println("=============🤢사원 조회 Cell클릭 컨트롤러🤢===============");
		long start1 = System.currentTimeMillis();
		String empCode = reqData.getVariable("empCode").getString();

			EmpTO empTO=empInfoService.findAllEmpInfo(empCode);
			ArrayList<WorkInfoTO> workInfoTO = empTO.getWorkInfo();
			empTO.setHobong(workInfoTO.get(0).getHobong());
			ArrayList<LicenseInfoTO> licenseInfoTO = empTO.getLicenseInfoList();
			ArrayList<FamilyInfoTO> familyInfoTO = empTO.getFamilyInfoList();

			datasetBeanMapper.beanToDataset(resData,empTO,EmpTO.class);
			datasetBeanMapper.beansToDataset(resData,workInfoTO,WorkInfoTO.class);
			datasetBeanMapper.beansToDataset(resData,familyInfoTO,FamilyInfoTO.class);
			datasetBeanMapper.beansToDataset(resData,licenseInfoTO,LicenseInfoTO.class);
		long end1 = System.currentTimeMillis();
		System.out.println("걸린 시간⏰⌚⏱️🕰️" + (end1 - start1));
		System.out.println("=============🤢사원 조회 Cell클릭 컨트롤러 종료🤢===============");

	}

	//✔️사원조회 - 사원정보 [저장]
	@RequestMapping("/empdetail/empcode")
	public void modifyEmployee(@RequestAttribute("reqData") PlatformData reqData,
			@RequestAttribute("resData") PlatformData resData) throws Exception{

		System.out.println("<<<<<<<<<<<<<<<<<<<<사원상세조회 저장 컨트롤러 진입 >>>>>>>>>>>>>>>>>>>>");
		EmpTO emp = datasetBeanMapper.datasetToBean(reqData, EmpTO.class);
		System.out.println("//////////////////////////////"+emp.toString());
		/*ArrayList<WorkInfoTO> workinfo = (ArrayList<WorkInfoTO>) datasetBeanMapper.datasetToBeans(reqData, WorkInfoTO.class);
		ArrayList<FamilyInfoTO> familyinfo = (ArrayList<FamilyInfoTO>) datasetBeanMapper.datasetToBeans(reqData, FamilyInfoTO.class);
		ArrayList<LicenseInfoTO> licenseinfo = (ArrayList<LicenseInfoTO>) datasetBeanMapper.datasetToBeans(reqData, LicenseInfoTO.class);


		emp.setWorkInfo(workinfo);
		emp.setFamilyInfoList(familyinfo);
		emp.setLicenseInfoList(licenseinfo);*/

			empInfoService.modifyEmployee(emp);
	 		empInfoService.modifyEmployee2(emp);

	}

	@RequestMapping("/empdetail/empcode2")
	public void removeEmployeeList(@RequestAttribute("reqData") PlatformData reqData,
						@RequestAttribute("resData") PlatformData resData) throws Exception{

	}
}
