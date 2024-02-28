package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.seoulit.insa.attdsvc.attdmgmt.to.DayAttdTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.nexacro17.xapi.data.PlatformData;

import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.service.EmpInfoService;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpTO;
import kr.co.seoulit.insa.sys.mapper.DatasetBeanMapper;


@RequestMapping("/empinfomgmt/*")
@RestController
public class EmpRegisterController {

//	@Autowired
//	private EmpInfoService empInfoService;
//	ModelMap map = null;
//	@Autowired
//	private DatasetBeanMapper datasetBeanMapper;

	private final EmpInfoService empInfoService;
	private final DatasetBeanMapper datasetBeanMapper;

	public EmpRegisterController(EmpInfoService empInfoService, DatasetBeanMapper datasetBeanMapper) {
		this.empInfoService = empInfoService;
		this.datasetBeanMapper = datasetBeanMapper;

	}

	//✔️사원등록 - [등록]
	@RequestMapping("empinfomgmt/employee")
	public ModelMap registEmployee(HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("===========사원번호 생성 컨트롤러 실행==========="	);

		PlatformData reqData = (PlatformData)request.getAttribute("reqData");
		System.out.println("reqData ==================== " + reqData);
		EmpTO emp = datasetBeanMapper.datasetToBean(reqData, EmpTO.class);
		//System.out.println(emp.getEmpName());
		//System.out.println(emp.getDeptCode());
		//System.out.println(emp.getPositionCode());
		System.out.println("emp ================ " + emp);
		empInfoService.registEmployee(emp);

		System.out.println("===========사원번호 생성 컨트롤러 끝==========="	);
		return null;
	}




//	@RequestMapping("evalTest") //신규❗❗❗
//	public void evalTest(@RequestAttribute("reqData") PlatformData reqData,
//							  @RequestAttribute("resData") PlatformData resData) throws Exception {
//
//		EmpTO eval = datasetBeanMapper.datasetToBean(reqData, EmpTO.class);
//		empInfoService.registEval(eval);
//
//	}





	//✔️사원등록 - [사원번호생성]
	@GetMapping("/employee")
	   public ModelMap findLastEmpCode(@RequestAttribute("reqData") PlatformData reqData,
	         @RequestAttribute("resData") PlatformData resData) throws Exception{
	      EmpTO lastEmpCode= new EmpTO();
	      
	      String lastCode = empInfoService.findLastEmpCode().substring(1);  //lastEmpCode
	      String result = "B" + (Integer.parseInt(lastCode)+1);
	      
	      lastEmpCode.setEmpCode(result);
	         
	      datasetBeanMapper.beanToDataset(resData, lastEmpCode, EmpTO.class);

	      return null;
	   }

}
