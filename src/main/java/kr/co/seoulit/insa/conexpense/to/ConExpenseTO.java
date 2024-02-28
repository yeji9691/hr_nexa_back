package kr.co.seoulit.insa.conexpense.to;

import kr.co.seoulit.insa.commsvc.systemmgmt.to.BaseTO;
import kr.co.seoulit.insa.sys.annotation.Dataset;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

@Data
@Dataset(name = "gds_conexpense")
@EqualsAndHashCode(callSuper = false)

public class ConExpenseTO extends BaseTO {
    private String reqNum;
    private String empCode;
    private String empName;
    private String reqDate;
    private String conType;
    private String trgt;
    private String conTermStart;
    private String conTermEnd;
    private String conAmt;
    private String rel;
    private String conPlace;
    private String bank;
    private String accNum;
    private String accHolder;
    private String note;
    private String chk = "0";
}
