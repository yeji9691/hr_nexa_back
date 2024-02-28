package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.controller;

import java.util.ArrayList;

import kr.co.seoulit.insa.attdsvc.attdappvl.to.DayAttdMgtTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.WorkInfoTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexacro17.xapi.data.PlatformData;

import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.service.EmpInfoService;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpEvalTO;
import kr.co.seoulit.insa.sys.mapper.DatasetBeanMapper;


@RequestMapping("/empinfomgmt/*")
@RestController
public class EmpEvalController {

	// @Autowired
	// private EmpInfoService empInfoService;
	// @Autowired
	//	private DatasetBeanMapper datasetBeanMapper;
	private final EmpInfoService empInfoService;

	private final DatasetBeanMapper datasetBeanMapper;

	public EmpEvalController(EmpInfoService empInfoService, DatasetBeanMapper datasetBeanMapper) {
		this.empInfoService = empInfoService;
		this.datasetBeanMapper = datasetBeanMapper;
	}
	ModelMap map = null;

	//✔️인사고과등록 - [등록]
	@PostMapping("/registevaluation")
	public ModelMap registEmpEval(@RequestAttribute("reqData") PlatformData reqData,
								  @RequestAttribute("resData") PlatformData resData) throws Exception{

		EmpEvalTO empevalto = datasetBeanMapper.datasetToBean(reqData,EmpEvalTO.class);
		System.out.println("@@@@@@@@@@@실행");
		System.out.println("@@@@@@@@@@@emp" + empevalto);

		String workInfo = reqData.getDataSetList().get(0).getString(0, 26);
		System.out.println("@@@@@@@@@@@workInfo"+ workInfo);
		empInfoService.registEmpEval(empevalto,workInfo);
		System.out.print("registEmpEval실행!!!!!!!!!!!!!!!!!!!!!!!!");


		return null;
	}


	//신규❗❗❗
	@RequestMapping("/evalTest2")
	public ModelMap saveEval(@RequestAttribute PlatformData reqData,
							 @RequestAttribute PlatformData resData) throws Exception {

		//String empCode = reqData.getVariable("empCode").getString();
		EmpEvalTO empEvalTO = datasetBeanMapper.datasetToBean(reqData, EmpEvalTO.class);
		//String workInfo = reqData.getDataSetList().get(0).getString(0, 26);

		empInfoService.evalTest2(empEvalTO);

		return null;
	}


	@RequestMapping("/evalTest4") //사원조회에서 사원정보 수정사항하면 EMP와 EMP_EVAL테이블 정보가 모두 수정되는 코드
	public ModelMap saveEval4(@RequestAttribute PlatformData reqData,
							 @RequestAttribute PlatformData resData) throws Exception {

		String empCode = reqData.getVariable("empCode").getString();
		String empName = reqData.getVariable("empName").getString();
		String deptCode = reqData.getVariable("deptCode").getString();
		String positionCode = reqData.getVariable("positionCode").getString();
		EmpEvalTO empEvalTO = datasetBeanMapper.datasetToBean(reqData, EmpEvalTO.class);

		empInfoService.evalTest4(empEvalTO);

		return null;
	}




	@PostMapping("/evaluation")
	public ModelMap findEmpEval(@RequestAttribute("reqData") PlatformData reqData,
								@RequestAttribute("resData") PlatformData resData) throws Exception{


		String	deptName = reqData.getVariable("deptName").getString();
		ArrayList<EmpEvalTO> empevalList = empInfoService.findEmpEval(deptName);
		datasetBeanMapper.beansToDataset(resData, empevalList, EmpEvalTO.class);

		return null;
	}

	@PostMapping("/removalevaluation")
	public ModelMap removeEmpEvalList(@RequestAttribute("reqData") PlatformData reqData,
									  @RequestAttribute("resData") PlatformData resData) throws Exception{

		String empCode = reqData.getVariable("empCode").getString();
		String applyDay = reqData.getVariable("applyDay").getString();

		empInfoService.removeEmpEvalList(empCode, applyDay);

		return null;
	}
}