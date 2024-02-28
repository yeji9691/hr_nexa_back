package kr.co.seoulit.insa.conexpense.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import kr.co.seoulit.insa.conexpense.to.ConExpenseTO;

@Mapper
public interface ConExpenseMapper {

    ArrayList<ConExpenseTO> getAllConExpenseList();

    // 신청날 최신 신청번호 조회
    String getMaxReqNo(String reqDate);

    // 사원별 경조사 조회
    ArrayList<ConExpenseTO> getConExpenseList(HashMap<String, Object> param);

    // 경조사 신청 번호 조회
    ArrayList<ConExpenseTO> getConExpenseByReqNum(String reqNum);

    // 경조사 신청 등록
    void insertConExpense(ConExpenseTO conExpenseTO);

    // 경조사 신청 수정
    void updateConExpense(ConExpenseTO conExpenseTO);

    // 경조사 신청 삭제
    void deleteConExpense(String reqNum);
}
