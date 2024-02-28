package kr.co.seoulit.insa.conexpense.controller;

import com.nexacro17.xapi.data.PlatformData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import kr.co.seoulit.insa.conexpense.service.ConExpenseService;
import kr.co.seoulit.insa.conexpense.to.ConExpenseTO;
import kr.co.seoulit.insa.empmgmtsvc.documentmgmt.to.CertificateTO;
import kr.co.seoulit.insa.empmgmtsvc.documentmgmt.to.DocumentsTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import kr.co.seoulit.insa.sys.mapper.DatasetBeanMapper;

import java.util.ArrayList;
import java.util.HashMap;

@CrossOrigin("*")
@Tag(name = "경조비 신청", description = "경조비 신청 API")
@RestController
@RequestMapping("/conexpense/*")
public class ConExpenseController {

    @Autowired
    private ConExpenseService conExpenseService;
    @Autowired
    private DatasetBeanMapper datasetBeanMapper;

    // 경조비 신청 내역 전체 조회
    @Operation(summary = "경조비 신청 내역 전체 조회", description = "경조비 신청 내역 전체를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @GetMapping(value = "conexp")
    public void getAllConExpenseList(@RequestAttribute("reqData") PlatformData reqData,
                                     @RequestAttribute("resData") PlatformData resData) throws Exception {
        ArrayList<ConExpenseTO> conExpenseList = conExpenseService.getAllConExpenseList();

        datasetBeanMapper.beansToDataset(resData, conExpenseList, ConExpenseTO.class);
    }

    // 경조비 신청 내역 조회
    @RequestMapping(value = "conexp-selectdate")
    public void getConExpenseList(
            @RequestAttribute("reqData") PlatformData reqData,
            @RequestAttribute("resData") PlatformData resData
    ) throws Exception {

        String startDate = reqData.getVariable("startDate").getString();
        String endDate = reqData.getVariable("endDate").getString();

        ArrayList<ConExpenseTO> conExpenseList = conExpenseService.getConExpenseList(startDate, endDate);

        datasetBeanMapper.beansToDataset(resData, conExpenseList, ConExpenseTO.class);
    }

    // 경조비 신청 등록
    @RequestMapping("conexpp")
    public void insertConExpense(
            @RequestAttribute("reqData") PlatformData reqData,
            @RequestAttribute("resData") PlatformData resData
    ) throws Exception {
        ConExpenseTO conExpenseTO = datasetBeanMapper.datasetToBean(reqData, ConExpenseTO.class);
        conExpenseService.insertConExpense(conExpenseTO);
        System.out.println("reqData = " + reqData);
    }

    // 경조비 신청 수정
    @RequestMapping("conexppp")
    public void updateConExpense(
            @RequestAttribute("reqData") PlatformData reqData,
            @RequestAttribute("resData") PlatformData resData
    ) throws Exception {
        ConExpenseTO conExpenseTO = datasetBeanMapper.datasetToBean(reqData, ConExpenseTO.class);
        conExpenseService.updateConExpense(conExpenseTO);
        System.out.println("reqData = " + reqData);
    }

    // 경조비 신청 삭제
    @RequestMapping("conexpppp")
    public void deleteConExpense(
            @RequestAttribute("reqData") PlatformData reqData,
            @RequestAttribute("resData") PlatformData resData
    ) throws Exception {
//        String reqNum = reqData.getVariable("reqNum").getString();
//        System.out.println("===================================reqNum = " + reqNum);
        ConExpenseTO conExpenseTO = datasetBeanMapper.datasetToBean(reqData, ConExpenseTO.class);
        String reqNum = conExpenseTO.getReqNum();
        conExpenseService.deleteConExpense(reqNum);
    }
}

