package com.sam.sam_biz.flow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 税表
 *
 * @author kunpeng.dang
 * @date 2023/09/25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxForm implements Serializable {

    private String caseId;
    private Long requestId;
    // pajakNo
    private String customer4;
    private String invoiceNo;
    private String netAmount;
    private String taxAmount;
    private String amount;
    private String taxRate;
    private String taxCode;
    private String whtRequired;
    private String skb;
    private String busPlace;
    private String wtaxType;
    private String awTax;
    private String awTaxCode;
    private String assignmentWHT;
    private String amountCurrency;
    private String whtAssignment;
    private String ppnType;
    private String taxComment;

    private String assignee;
    private String sscCaseStatus;
    private String taxCaseStatus;
    private String companyCode;
    private String sscReturnBy;
    private String sscReturnComment;
    private String sscReturnStatus;
    private Integer bizSubProcessId;
    private Long requestDate;

    private Long updateDateTime;
    private Integer updateUserId;
    private String spokeSite;
}
