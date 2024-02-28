package kr.co.seoulit.insa.salarysvc.salarystdinfomgmt.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexacro17.xapi.data.PlatformData;

import kr.co.seoulit.insa.salarysvc.salarystdinfomgmt.service.SalaryStdInfoMgmtService;
import kr.co.seoulit.insa.salarysvc.salarystdinfomgmt.to.BaseSalaryTO;
import kr.co.seoulit.insa.sys.mapper.DatasetBeanMapper;


@RequestMapping("/salarystdinfomgmt/*")
@RestController
public class BaseSalaryController {
	
	@Autowired
	private SalaryStdInfoMgmtService salaryStdInfoMgmtService;	
	ModelMap map = null;
	
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
	
	@RequestMapping("base-salary1")
	public void findBaseSalaryList(@RequestAttribute("reqData") PlatformData reqData,
									@RequestAttribute("resData") PlatformData resData) throws Exception{

			ArrayList<BaseSalaryTO> baseSalaryList = salaryStdInfoMgmtService.findBaseSalaryList();
			datasetBeanMapper.beansToDataset(resData, baseSalaryList, BaseSalaryTO.class);
	}

	
	@RequestMapping("base-salary2")
	public void modifyBaseSalaryList(@RequestAttribute("reqData") PlatformData reqData,
			@RequestAttribute("resData") PlatformData resData) throws Exception{
			
			ArrayList<BaseSalaryTO> baseSalaryList = (ArrayList<BaseSalaryTO>)datasetBeanMapper.datasetToBeans(reqData, BaseSalaryTO.class);
			salaryStdInfoMgmtService.modifyBaseSalaryList(baseSalaryList);

	}
	
}
