package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.co.seoulit.insa.commsvc.systemmgmt.entity.DetailCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

//import kr.co.seoulit.insa.commsvc.foudinfomgmt.mapper.DeptMapper;
import kr.co.seoulit.insa.commsvc.foudinfomgmt.repository.DeptRepository;
import kr.co.seoulit.insa.commsvc.foudinfomgmt.to.DeptTO;
import kr.co.seoulit.insa.commsvc.systemmgmt.mapper.DetailCodeMapper;
import kr.co.seoulit.insa.commsvc.systemmgmt.repository.DetailCodeRepository;
import kr.co.seoulit.insa.commsvc.systemmgmt.to.DetailCodeTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.mapper.EmpMapper;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.mapper.EmpAppointmentMapper;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.mapper.EmpEvalMapper;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.mapper.FamilyInfoMapper;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.mapper.LicenseInfoMapper;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.mapper.WorkInfoMapper;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.repository.EmpEvalRepository;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpAppointmentInfoTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpAppointmentTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpAppointmentTypeTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpEvalTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.FamilyInfoTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.LicenseInfoTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.WorkInfoTO;

@Service
public class EmpInfoServiceImpl implements EmpInfoService {

//	@Autowired
//	private DeptMapper deptMapper;
//	@Autowired
//	private EmpMapper empMapper;
//	@Autowired
//	private DetailCodeMapper detailCodeMapper;
//	@Autowired
//	private FamilyInfoMapper familyInfoMapper;
//	@Autowired
//	private WorkInfoMapper workInfoMapper;
//	@Autowired
//	private LicenseInfoMapper licenseInfoMapper;
//	@Autowired
//	private EmpEvalMapper empEvalMapper;
//	@Autowired
//	private DeptRepository deptrepository;
//	@Autowired
//	private DetailCodeRepository detailcoderepository;
//	@Autowired
//	private EmpEvalRepository empEvalrepository;
//	@Autowired
//	private EmpAppointmentMapper empAppointmentMapper;


	private final EmpMapper empMapper;
	private final DetailCodeMapper detailCodeMapper;
	private final FamilyInfoMapper familyInfoMapper;
	private final WorkInfoMapper workInfoMapper;
	private final LicenseInfoMapper licenseInfoMapper;
	private final EmpEvalMapper empEvalMapper;
	private final DeptRepository deptrepository;
	private final DetailCodeRepository detailcoderepository;
	private final EmpEvalRepository empEvalrepository;
	private final EmpAppointmentMapper empAppointmentMapper;

	public EmpInfoServiceImpl(EmpMapper empMapper,
							  DetailCodeMapper detailCodeMapper,
							  FamilyInfoMapper familyInfoMapper,
							  WorkInfoMapper workInfoMapper,
							  LicenseInfoMapper licenseInfoMapper,
							  EmpEvalMapper empEvalMapper,
							  DeptRepository deptrepository,
							  DetailCodeRepository detailcoderepository,
							  EmpEvalRepository empEvalrepository,
							  EmpAppointmentMapper empAppointmentMapper)
	{   this.empMapper = empMapper;
		this.detailCodeMapper = detailCodeMapper;
		this.familyInfoMapper = familyInfoMapper;
		this.workInfoMapper = workInfoMapper;
		this.licenseInfoMapper = licenseInfoMapper;
		this.empEvalMapper = empEvalMapper;
		this.deptrepository = deptrepository;
		this.detailcoderepository = detailcoderepository;
		this.empEvalrepository = empEvalrepository;
		this.empAppointmentMapper = empAppointmentMapper;
	}

	@Override
	public EmpTO getEmp(String name) {

		EmpTO empto = null;
		empto = empMapper.selectEmp(name);
		return empto;

	}

	//
	@Override
	public String findLastEmpCode() {

		String empCode = null;
		empCode = empMapper.selectLastEmpCode();
		return empCode;

	}

	@Override
	@Cacheable(key = "#empCode",value = "findAllEmp")
	public EmpTO findAllEmpInfo(String empCode) {
		System.out.println("=============😐사원 조회 Cell클릭😐===============");
		EmpTO empTO = null;
		empTO = empMapper.selectEmployee(empCode);
		System.out.println("=============😐사원 조회 Cell클릭😐===============" + empCode);
		return empTO;

	}

	@Override
	public ArrayList<EmpTO> findEmpList(String parameter) {

		System.out.println("parameter = " + parameter);
		String deptName = parameter.split(":")[0];

		ArrayList<EmpTO> empList = null;
		if (parameter.equals("전체부서")) {
			empList = empMapper.selectEmpList();
		} else if (parameter.substring(deptName.length() - 1, deptName.length()).equals("팀")) {
			empList = empMapper.selectEmpListD(deptName);

		} else {
			empList = empMapper.selectEmpListN(deptName);
		}
		return empList;

	}

	//✔️사원등록 - [등록]
	@Override
	public void registEmployee(EmpTO emp) {

		HashMap<String, Object> map = new HashMap<>();
		map.put("empCode", emp.getEmpCode());
		map.put("empName", emp.getEmpName());
		map.put("birthdate", emp.getBirthdate());
		map.put("gender", emp.getGender());
		map.put("mobileNumber", emp.getMobileNumber());
		map.put("address", emp.getAddress());
		map.put("detailAddress", emp.getDetailAddress());
		map.put("postNumber", emp.getPostNumber());
		map.put("email", emp.getEmail());
		map.put("lastSchool", emp.getLastSchool());
		map.put("imgExtend", emp.getImgExtend());
		map.put("deptCode", emp.getDeptCode());
		map.put("positionCode", emp.getPositionCode());
		map.put("hobong", emp.getHobong());
		map.put("occupation", emp.getOccupation());
		map.put("employment", emp.getEmployment());
		map.put("WorkplaceCode",emp.getWorkplaceCode());

		for(String key : map.keySet()) {
			System.out.println(key +" : "+ map.get(key));
		}

		empMapper.registEmployee(map);
		//✅empMapper.registEval(map);

		DetailCode detailCode = new DetailCode();
		detailCode.setDetailCodeNumber(emp.getEmpCode());
		detailCode.setDetailCodeName(emp.getEmpName());
		detailCode.setCodeNumber("CO-17");
		detailCode.setDetailCodeNameusing("N");
		detailcoderepository.save(detailCode);
		//detailCodeMapper.registDetailCode(detailCodeto);

	}

	//❗❗❗신규
//	@Override
//	public void registEval(EmpTO empTO) {
//
//		empMapper.insertEval(empTO);
//	}



	@Cacheable(key = "#emp", value = "modifyEmployee2")
	public void modifyEmployee2(EmpTO emp) {

		HashMap<String, Object> map = new HashMap<>();
		map.put("empCode", emp.getEmpCode()); //
		map.put("empName", emp.getEmpName());//
		//map.put("birthdate", emp.getBirthdate());
		//map.put("gender", emp.getGender());//
		//map.put("mobileNumber", emp.getMobileNumber());//
		//map.put("address", emp.getAddress());//
		//map.put("detailAddress", emp.getDetailAddress());//
		//map.put("postNumber", emp.getPostNumber());//
		//map.put("email", emp.getEmail());//
		//map.put("lastSchool", emp.getLastSchool());//
		//map.put("imgExtend", emp.getImgExtend());//
		map.put("deptCode", emp.getDeptCode());
		map.put("positionCode", emp.getPositionCode());
		map.put("deptName", emp.getDeptName());//
		map.put("position", emp.getPosition());//

		empMapper.updateEvalTest(map);
	}


	@Override
	@Cacheable(key = "#emp", value = "modifyEmployee")
	public void modifyEmployee(EmpTO emp) {

		HashMap<String, Object> map = new HashMap<>();
		map.put("empCode", emp.getEmpCode()); //
		map.put("empName", emp.getEmpName());//
		map.put("birthdate", emp.getBirthdate());
		map.put("gender", emp.getGender());//
		map.put("mobileNumber", emp.getMobileNumber());//
		map.put("address", emp.getAddress());//
		map.put("detailAddress", emp.getDetailAddress());//
		map.put("postNumber", emp.getPostNumber());//
		map.put("email", emp.getEmail());//
		map.put("lastSchool", emp.getLastSchool());//
		map.put("imgExtend", emp.getImgExtend());//
		map.put("deptCode", emp.getDeptCode());
		map.put("positionCode", emp.getPositionCode());
		map.put("deptName",emp.getDeptName());//
		map.put("position",emp.getPosition());//

		empMapper.updateEvalTest(map);

		if (emp.getStatus().equals("update")) {
			empMapper.updateEmployee(emp);
		}
		if (emp.getWorkInfo() != null) {
			ArrayList<WorkInfoTO> workInfoList = emp.getWorkInfo();
			for(WorkInfoTO workInfo : workInfoList) {
				switch (workInfo.getStatus()) {
					case "insert":
						workInfoMapper.insertWorkInfo(workInfo);
						break;
					case "update":
						workInfoMapper.updateWorkInfo(workInfo);
						break;
					case "delete":
						workInfoMapper.deleteWorkInfo(workInfo);
						break;
				}
			}
		}

		if (emp.getLicenseInfoList() != null && emp.getLicenseInfoList().size() > 0) {
			ArrayList<LicenseInfoTO> licenseInfoList = emp.getLicenseInfoList();
			for (LicenseInfoTO licenseInfo : licenseInfoList) {
				switch (licenseInfo.getStatus()) {
					case "insert":
						licenseInfoMapper.insertLicenseInfo(licenseInfo);
						break;
					case "update":
						licenseInfoMapper.updateLicenseInfo(licenseInfo);
						break;
					case "delete":
						licenseInfoMapper.deleteLicenseInfo(licenseInfo);
						break;
				}
			}
		}

		if (emp.getFamilyInfoList() != null && emp.getFamilyInfoList().size() > 0) {
			ArrayList<FamilyInfoTO> familyInfoList = emp.getFamilyInfoList();
			for (FamilyInfoTO familyInfo : familyInfoList) {
				switch (familyInfo.getStatus()) {
					case "insert":
						familyInfoMapper.insertFamilyInfo(familyInfo);
						break;
					case "update":
						familyInfoMapper.updateFamilyInfo(familyInfo);
						break;
					case "delete":
						familyInfoMapper.deleteFamilyInfo(familyInfo);
						break;
				}
			}
		}

	}

	@Override
	public void deleteEmpList(ArrayList<EmpTO> empList) {
		HashMap<String, String> map = new HashMap<>();
		for (EmpTO emp : empList) {
			map.put("empCode", emp.getEmpCode());
			empMapper.deleteEmployee(map);
			DetailCodeTO detailCodeto = new DetailCodeTO();
			detailCodeto.setDetailCodeNumber(emp.getEmpCode());
			detailCodeto.setDetailCodeName(emp.getEmpName());
			detailCodeMapper.deleteDetailCode(detailCodeto);
		}

	}

	@Override
	public ArrayList<DeptTO> findDeptList() {

		ArrayList<DeptTO> deptList = null;
		deptList =(ArrayList<DeptTO>)deptrepository.findAll();
		//deptList = deptMapper.selectDeptList();
		return deptList;

	}


	//✔️인사고과등록 - [등록]
	@Override
	public void registEmpEval(EmpEvalTO empeval,String workInfo) {

		System.out.println("workInfo = " + workInfo);


		// 정규표현식 패턴
		String positionPattern = "position=([^,]+)";
		String deptNamePattern = "deptName=([^\\]]+)";

		// 패턴 컴파일
		Pattern positionRegex = Pattern.compile(positionPattern); //Pattern 객체 생성(인자에 정규식)
		Pattern deptNameRegex = Pattern.compile(deptNamePattern);

		// 매칭하기
		Matcher positionMatcher = positionRegex.matcher(workInfo);//Matcher 객체 생성(인자에 검사할 문자열)
		Matcher deptNameMatcher = deptNameRegex.matcher(workInfo);//"^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$"; // 이메일 정규식

		// 원하는 문자열 추출
		String positionCode = "";
		String deptName = "";

		if (positionMatcher.find()) { //Matcher클래스의 find() :Pattern에 일치하는 문자열이 발견 되면 true, 발견 되지 않으면 false 반환.
			// 반복문을 통해 계속 호출하면 두 번째, 세 번째 ...로 Pattern에 일치하는 문자열을 찾는 것도가능합니다.
			positionCode = positionMatcher.group(1); //Matcher클래스의 group() : Pattern에 일치하는 문자열을 반환.
			//정규식 형식에 맞으면 PositionCode를 반환
		}

		if (deptNameMatcher.find()) {
			deptName = deptNameMatcher.group(1);
			//정규식 형식에 맞으면 deptName을 반환
		}

		// 괄호 뒤에 불필요한 문자 제거
		deptName = deptName.replaceAll("\\)", "");

		System.out.println("사원: " + positionCode);
		System.out.println("부서: " + deptName);


		LocalDate now = LocalDate.now();

		int year = now.getYear();
		String yearData = Integer.toString(year);

		empeval.setApplyDay(yearData);
       	//empeval.setDeptName(deptName);
      	//empeval.setPosition(positionCode);

		int a =empeval.getAchievement();
		int b = empeval.getAbility();
		int c = empeval.getAttitude();

		String grade = null;


		if((a+b+c)/3>=90){
			grade = "A";
		} else if ((a+b+c)/3>=80){
			grade = "B";
		} else if ((a+b+c)/3>=70) {
			grade = "C";
		} else {
			grade = "D";
		}

		System.out.println(grade+"aaaaaaaaaa");
		empeval.setGrade(grade);
		//empEvalrepository.save(empeval);
		empEvalMapper.insertEmpEval(empeval);

	}


	//❗❗❗신규
	@Override
	public void evalTest2(EmpEvalTO empeval) { //dayAttdList는 리스트 이기때문에 iter사용

//만약 고과테이블에 있는 사원이면 update, 고과테이블에 없는사원이면 insert

		LocalDate now = LocalDate.now();

		int year = now.getYear();
		String yearData = Integer.toString(year);

		empeval.setApplyDay(yearData);
		//empeval.setDeptName(deptName);
		//empeval.setPosition(positionCode);

		int a = empeval.getAchievement();
		int b = empeval.getAbility();
		int c = empeval.getAttitude();

		String grade = null;


		if ((a + b + c) / 3 >= 90) {
			grade = "A";
		} else if ((a + b + c) / 3 >= 80) {
			grade = "B";
		} else if ((a + b + c) / 3 >= 70) {
			grade = "C";
		} else {
			grade = "D";
		}

		System.out.println(grade + "aaaaaaaaaa");
		empeval.setGrade(grade);
		empeval.setApprovalStatus("승인대기");
		empeval.setEmpName(empeval.getEmpName());



		ArrayList<String> empCode = empEvalMapper.selectEmpEval6();

		if(empCode.contains(empeval.getEmpCode())) {
			System.out.println("❌❌❌❌❌");
			empEvalMapper.updateEval(empeval);
			//empEvalrepository.save(empeval);
		}else{
			empEvalMapper.insertEval(empeval);
			System.out.println("⭕⭕⭕⭕⭕");
		}

		}




	@Override
	public void evalTest4(EmpEvalTO empEvalTO) {
		empEvalMapper.updateEval4(empEvalTO);
	}


//	//✅✅✅ 기존에 있던거
//	@Override
//	public ArrayList<EmpEvalTO> findEmpEval() {
//
//		ArrayList<EmpEvalTO> empevallsit = null;
//		empevallsit = (ArrayList<EmpEvalTO>)empEvalrepository.findAll();
////      empevallsit = empEvalMapper.selectEmpEval();
//		return empevallsit;
//
//	}

	//⭐⭐⭐수정된거
		@Override
	public ArrayList<EmpEvalTO> findEmpEval(String deptName) {
			ArrayList<EmpEvalTO> empevallsit = null;
			if (deptName.equals("전체부서")) {
				empevallsit = empEvalMapper.selectEmpEval2(deptName);
			} else if (deptName.substring(deptName.length() - 1, deptName.length()).equals("팀")) {

				empevallsit = empEvalMapper.selectEmpEval(deptName);
			}
			return empevallsit;
//		ArrayList<EmpEvalTO> empevallsit = null;
////		empevallsit = (ArrayList<EmpEvalTO>)empEvalrepository.findAll();
//		empevallsit = empEvalMapper.selectEmpEval(deptName);
//		return empevallsit;

	}


	//✔️인사고과관리 - [조회]
	@Override
	public ArrayList<EmpEvalTO> findEmpEval2(String dept, String year) {
		HashMap<String, String> map = new HashMap<>();
		map.put("deptName", dept);
		map.put("applyDay", year);
		ArrayList<EmpEvalTO> empevallsit = null;
		if (dept.equals("모든부서")) {
			//empevallsit = (ArrayList<EmpEvalTO>)empEvalrepository.findAllByApplyDay(year);
			empevallsit = empEvalMapper.selectEmpEval7(year);
			System.out.println(empevallsit+"@@@!!!!!");
			empevallsit = empEvalMapper.selectEmpEval4(year);
		} else {
			empevallsit = empEvalMapper.selectEmpEvalDept(map);
		}
		return empevallsit;

	}





	@Override
	public void modifyEmpEvalList(ArrayList<EmpEvalTO> empevalList) {

		for (EmpEvalTO empeval : empevalList) {
			int evalhap = empeval.getAchievement() + empeval.getAbility() + empeval.getAttitude();
			if (evalhap > 260) {
				empeval.setGrade("A");
			} else if (evalhap > 240) {
				empeval.setGrade("B");
			} else if (evalhap > 220) {
				empeval.setGrade("C");
			} else {
				empeval.setGrade("D");
			}
			//empEvalrepository.save(empeval);
			empEvalMapper.updateEmpEval(empeval);
		}

	}

	@Override
	public void removeEmpEvalList(String empCode, String applyDay) {
		HashMap<String, String> map = new HashMap<>();
		map.put("empCode", empCode);
		map.put("applyDay", applyDay);

		empEvalMapper.deleteEmpEval(map);

	}

	@Override
	public void registAppointmentinfo(EmpAppointmentInfoTO empAppointmentInfoTO) {

		empAppointmentMapper.insertAppointmentInfo(empAppointmentInfoTO);
	}

	@Override
	public ArrayList<EmpAppointmentInfoTO> findAppointmentinfo(String searchType){
		ArrayList<EmpAppointmentInfoTO> empAppointmentInfoTO = empAppointmentMapper.selectAppintmentInfo(searchType);
		return empAppointmentInfoTO;
	}

	@Override
	public ArrayList<EmpAppointmentTypeTO> findAppointmentinfoEmp(String hosu,String type){

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("hosu", hosu);
		map.put("type", type);
		ArrayList<EmpAppointmentTypeTO> empAppointmentTypeTO = empAppointmentMapper.selectAppointmentinfoEmp(map);
		return empAppointmentTypeTO;
	}

	@Override
	public ArrayList<EmpAppointmentTypeTO> findAppointmentEmp(String empCode){

		ArrayList<EmpAppointmentTypeTO> empAppointmentTypeTO = empAppointmentMapper.selectAppointmentEmp(empCode);
		return empAppointmentTypeTO;
	}
	@Override
	public ArrayList<EmpAppointmentTypeTO> findAllAppointEmp(String hosu){
		ArrayList<EmpAppointmentTypeTO> empAppointmentTypeTO = empAppointmentMapper.selectAllAppointEmp(hosu);
		return empAppointmentTypeTO;
	}
	@Override
	public EmpAppointmentTO countAppointmentEmp(String hosu){
		EmpAppointmentTO empAppointmentTO = empAppointmentMapper.countAppointmentEmp(hosu);
		return empAppointmentTO;
	}
	@Override
	public EmpAppointmentInfoTO generateHosu(){

		Calendar now = Calendar.getInstance();
		String hosu = empAppointmentMapper.getHosu();
		if(hosu==null) {
			String year = Integer.toString(now.get(Calendar.YEAR));
			String month = Integer.toString((now.get(Calendar.MONTH)) +1);
			if(month.length()<2)
				month='0'+month;
			hosu = year+month+"-1";
		}
		EmpAppointmentInfoTO empAppointmentInfoTO = new EmpAppointmentInfoTO();
		empAppointmentInfoTO.setHosu(hosu);
		return empAppointmentInfoTO;
	}

	@Override
	public void registAppoint(HashMap<String, Object> map, EmpAppointmentTO emp) {
		empAppointmentMapper.insertEmpAppointment2(map);
	}

	@Override
	public void updateAppoint(ArrayList<EmpAppointmentTypeTO> typeList) {

		for (EmpAppointmentTypeTO type : typeList) {
			empAppointmentMapper.updateAppintmentList(type);
		}

	}
}