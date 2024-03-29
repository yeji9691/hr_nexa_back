package kr.co.seoulit.insa.conexpense.service;

import kr.co.seoulit.insa.conexpense.mapper.ConExpenseMapper;
import kr.co.seoulit.insa.conexpense.to.ConExpenseTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ConExpenseServiceImpl implements ConExpenseService {

    @Autowired
    private ConExpenseMapper conExpenseMapper;

    // 사원별 경조사 신청 전체 조회
    @Override
    public ArrayList<ConExpenseTO> getAllConExpenseList() {
        return conExpenseMapper.getAllConExpenseList();
    }

    // 사원별 경조사 신청 조회
    @Override
    public ArrayList<ConExpenseTO> getConExpenseList(String startDate, String endDate) {
        HashMap<String, Object> param = new HashMap<>();

        param.put("startDate", startDate);
        param.put("endDate", endDate);

        return conExpenseMapper.getConExpenseList(param);
    }

    // 경조사 신청 등록 / 수정
    @Override
    public void insertConExpense(ConExpenseTO conExpenseTO) {
        ArrayList<ConExpenseTO> conExpenseList = conExpenseMapper.getConExpenseByReqNum(conExpenseTO.getReqNum());

        if (conExpenseList.size() == 0) {
            StringBuffer reqNo = new StringBuffer();
            String reqNoDate = conExpenseTO.getReqDate().replace("-", ""); // 2023-12-13 -> 20231213
            reqNo.append(reqNoDate);
            reqNo.append("REQ"); // 20231213REQ

            String lastNo = conExpenseMapper.getMaxReqNo(conExpenseTO.getReqDate());
            if (lastNo == null){
               lastNo = "00000";
            }
            int length = lastNo.length();

            String code = "0000" + (Integer.parseInt(lastNo.substring(length - 5)) + 1) + "";
            reqNo.append(code.substring(code.length() - 5));
            conExpenseTO.setReqNum(reqNo.toString());

            conExpenseMapper.insertConExpense(conExpenseTO);
        }
        else {
            conExpenseMapper.updateConExpense(conExpenseTO);
        }

    }

    // 경조사 신청 수정
    @Override
    public void updateConExpense(ConExpenseTO conExpenseTO) {
        conExpenseMapper.updateConExpense(conExpenseTO);
    }

    // 경조사 신청 삭제
    @Override
    public void deleteConExpense(String reqNum) {
        conExpenseMapper.deleteConExpense(reqNum);
    }
}
