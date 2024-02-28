package kr.co.seoulit.insa.conexpense.service;

import kr.co.seoulit.insa.conexpense.to.ConExpenseTO;

import java.util.ArrayList;

public interface ConExpenseService {

    ArrayList<ConExpenseTO> getAllConExpenseList();

    // 경조사 신청 조회
    ArrayList<ConExpenseTO> getConExpenseList(String startDate, String endDate);

    // 경조사 신청 등록
    void insertConExpense(ConExpenseTO conExpenseTO);

    // 경조사 신청 수정
    void updateConExpense(ConExpenseTO conExpenseTO);

    // 경조사 신청 삭제
    void deleteConExpense(String reqNum);
}
