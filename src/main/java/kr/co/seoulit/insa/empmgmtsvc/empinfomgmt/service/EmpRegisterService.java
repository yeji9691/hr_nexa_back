package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.service;

import kr.co.seoulit.insa.commsvc.systemmgmt.repository.DetailCodeRepository;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.mapper.EmpMapper;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class EmpRegisterService {

    private final EmpMapper empMapper;
    private final DetailCodeRepository detailCodeRepository;

    public EmpTO findLastEmpCode(){
        String detachedCode = empMapper.selectLastEmpCode().substring(1);
        String empCode = "A"+(Integer.parseInt(detachedCode)+1);
        EmpTO newEmp = new EmpTO();
        newEmp.setEmpCode(empCode);
        return newEmp;
    }

    public void registerEmployee(EmpTO emp){

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
    }

}
