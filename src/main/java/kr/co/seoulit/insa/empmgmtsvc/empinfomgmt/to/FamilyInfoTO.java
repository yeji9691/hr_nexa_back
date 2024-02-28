package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to;

import kr.co.seoulit.insa.commsvc.systemmgmt.to.BaseTO;
import kr.co.seoulit.insa.sys.annotation.Dataset;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Dataset(name="ds_familyInfo")
@EqualsAndHashCode(callSuper=false)
public class FamilyInfoTO extends BaseTO implements Serializable {
	
	private String empCode,familyCode,familyName,relation,birthdate,liveTogether;

}
