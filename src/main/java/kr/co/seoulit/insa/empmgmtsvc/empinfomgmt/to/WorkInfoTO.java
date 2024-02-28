package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to;

import kr.co.seoulit.insa.commsvc.systemmgmt.to.BaseTO;
import kr.co.seoulit.insa.sys.annotation.Dataset;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Dataset(name="ds_workInfo")
@EqualsAndHashCode(callSuper=false)
public class WorkInfoTO extends BaseTO implements Serializable {
	
	private String empCode;
	private String workInfoDays;
	private String hiredate;
	private String retireDate;
	private String occupation;
	private String employmentType;
	private String hobong;
	private String position;
	private String deptName;

}
