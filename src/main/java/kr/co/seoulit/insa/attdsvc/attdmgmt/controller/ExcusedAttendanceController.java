package kr.co.seoulit.insa.attdsvc.attdmgmt.controller;

import java.util.ArrayList;

import kr.co.seoulit.insa.attdsvc.attdmgmt.entity.RestAttdEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexacro17.xapi.data.PlatformData;

import kr.co.seoulit.insa.attdsvc.attdmgmt.service.AttdMgmtService;
import kr.co.seoulit.insa.attdsvc.attdmgmt.to.RestAttdTO;
import kr.co.seoulit.insa.sys.mapper.DatasetBeanMapper;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ExcusedAttendanceController {
	
	private final AttdMgmtService attdMgmtService;
	private final DatasetBeanMapper datasetBeanMapper;

	// ✔️근태외신청 - [신청하기]
	@RequestMapping("/attdmgmt/excused-attnd-create")
	public void registRestAttd(@RequestAttribute("reqData") PlatformData reqData,
			@RequestAttribute("resData") PlatformData resData) throws Exception{
		log.info("✅✅✅========== 근태신청 컨드롤러 시작===========");

		RestAttdTO restAttd = datasetBeanMapper.datasetToBean(reqData, RestAttdTO.class);//한 행이 insert됨
		//뷰단에서 ds_restAttd이 넘어오는데 몇몇 칼럼값들 뷰단에서 세팅되어서 넘어옴
		//나머지 칼럽값들은 어떤식으로 세팅되서 넘어오는거❓❓
		//이미 조회하기눌러서 dataset에 조회된 칼럼값들이 들어있고, 나머지 세팅하는 값만 수정되서 INSERT되는 거같음??????????? 아닌듯 ,,.몰라
		attdMgmtService.registRestAttd(restAttd);
		log.info("✅✅✅========== 근태신청 컨드롤러 끝===========");
	}


	//🆕출장/교육 신청 - [신청하기]
	@RequestMapping("/attdmgmt/chulzang")
	public void registRestAttd1(@RequestAttribute("reqData") PlatformData reqData,
							   @RequestAttribute("resData") PlatformData resData) throws Exception{
		System.out.println("========== 근태신청 컨드롤러 시작===========");

		RestAttdTO restAttd = datasetBeanMapper.datasetToBean(reqData, RestAttdTO.class);
		attdMgmtService.registRestAttd(restAttd);

		System.out.println("========== 근태신청 컨드롤러 끝===========");
	}


	//✔️근태외 신청 - [신청하기]
	// 75기 새롬 만든것 JPA 사용
	@RequestMapping("/attdmgmt/excused-attnd-create-jpa")
	public void registRestAttd2(@RequestAttribute("reqData") PlatformData reqData,
							   @RequestAttribute("resData") PlatformData resData) throws Exception{
		System.out.println("========== 근태신청 컨드롤러 시작===========");

		RestAttdTO restAttdTo = datasetBeanMapper.datasetToBean(reqData, RestAttdTO.class);
		System.out.println("restAttd ============= " + restAttdTo);
		attdMgmtService.registRestAttdjpa(restAttdTo);

		System.out.println("========== 근태신청 컨드롤러 끝===========");
	}


	//✔️ 근태외신청 - [조회]
	@RequestMapping("/attdmgmt/excused-attnd2")
	public void findRestAttdList(@RequestAttribute("reqData") PlatformData reqData,
									@RequestAttribute("resData") PlatformData resData) throws Exception{

		System.out.println("=============🤢근태외 조회 컨트롤러 시작🤢===============");
		long start1 = System.currentTimeMillis();

		//뷰단에서 세팅된 파라미터들이 넘어옴
		String empCode = reqData.getVariable("empCode").getString();//페이지화면이 로드될때 세팅되서 넘어옴
		String startDate = reqData.getVariable("startDate").getString();//첫번째 달력컴포넌트에 값이 세팅되서 넘어옴
		String endDate = reqData.getVariable("endDate").getString();//두번쨰 달력컴포넌트에 값이 세팅되서 넘어옴
		String code = reqData.getVariable("code").getString();//넥사 innerdataset에서 값이 넘어옴
		//근태외신청에서 근태구분에 innerdataset인 ds_restType의 DETAIL_CODE_NAME이 바인딩되어있고, code에는 innerdataset의 DETAIL_CODE_NUMBER가 들어가있음
		ArrayList<RestAttdTO> restAttdList = attdMgmtService.findRestAttdList(empCode, startDate, endDate, code); // 테이블 분리 해놔야 될 거 같은데 존나 병신같음 이거
		datasetBeanMapper.beansToDataset(resData, restAttdList, RestAttdTO.class);

		long end1 = System.currentTimeMillis();
		System.out.println("걸린 시간⏰⌚" + (end1 - start1));
		System.out.println("=============🤢근태외 조회 컨트롤러 종료🤢===============");

	}





	//✔️ 근태외신청 - [삭제] -jpa 적용
	@RequestMapping("/attdmgmt/excused-attnd-elimination")
	public void removeRestAttdList(@RequestAttribute("reqData") PlatformData reqData,
			@RequestAttribute("resData") PlatformData resData) throws Exception{

		System.out.println("========== 연차삭제 컨드롤러 끝===========");
		System.out.println("reqData ============== " + reqData);

		ArrayList<RestAttdTO> restAttdList = (ArrayList<RestAttdTO>)datasetBeanMapper.datasetToBeans(reqData, RestAttdTO.class);
		//뷰단에서 뒷단으로 리스트가 넘어올때에는 형변환해줘야함
		System.out.println("restAttdList ================ " + restAttdList);
		attdMgmtService.removeRestAttdList(restAttdList);

		System.out.println("========== 연차삭제 컨드롤러 끝===========");
	}
}