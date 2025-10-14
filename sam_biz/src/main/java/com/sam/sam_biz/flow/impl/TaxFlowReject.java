package com.sam.sam_biz.flow.impl;

import com.sam.sam_biz.flow.TaxFlowAction;
import com.sam.sam_biz.flow.TaxForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tax拒绝案件
 * 1.修改案件的tax状态
 * 2.记录history
 * 3.与SNOW交互或者直接发出邮件
 * 4.关闭NC及相关数据
 */
@Slf4j
@Service
@Transactional
public class TaxFlowReject extends TaxFlowAction {
    @Override
    public void beforeAction(TaxForm taxForm) {

    }

    @Override
    public void updateDashBoard(TaxForm taxForm) {

    }

    @Override
    public void updateTaxDashBoard(TaxForm taxForm) {

    }

    @Override
    public void saveStepLog(TaxForm taxForm) {

    }

    @Override
    public void afterAction(TaxForm taxForm) {

    }
//
//    @Resource
//    private RejectionApi rejectionApi;
//    @Resource
//    private CaseTraceService caseTraceService;
//    @Resource
//    private TaxDashboardMapper taxDashBoardMapper;
//    @Resource
//    private CaseDashBoardMapper caseDashBoardMapper;
//
//    @Override
//    public void beforeAction(TaxForm taxForm) {
//
//    }
//
//
//    @Override
//    public void updateTaxDashBoard(TaxForm taxForm) {
//        log.info("TaxFlowReject:caseId={}, tax工作流->更新Tax案件状态为{}", taxForm.getCaseId(), EnumTaxCaseStatus.TAX_REJECTED.name());
//        Long currentTime = DateTimeUtils.getCurrentDateTimeToLong();
//        // 1.更新案件状态为TAX_SUBMITTED
//        TaxDashBoard entity = this.taxDashBoardMapper.selectById(taxForm.getCaseId());
//        // 修改状态
//        entity.setCaseStatus(EnumTaxCaseStatus.TAX_REJECTED.getValue());
//        // 记录备注
//        entity.setTaxComment(taxForm.getTaxComment());
//        // 记录完成时间
//        entity.setProcessDate(currentTime);
//        entity.setUpdateDateTime(currentTime);
//        entity.setUpdateUserId(UserUtil.getUserId());
//        this.taxDashBoardMapper.updateById(entity);
//    }
//
//    @Override
//    public void updateDashBoard(TaxForm taxForm) {
//        log.info("TaxFlowReject:caseId={}, tax工作流->更新SSC案件状态为{}", taxForm.getCaseId(), EnumCaseStatus.TAX_REJECTED.getValue());
//        // 更新SSC案件信息
//        CaseDashBoard board = new CaseDashBoard();
//        board.setCaseId(taxForm.getCaseId());
//        board.setCaseStatus(EnumCaseStatus.TAX_REJECTED.getValue());
//        board.setUpdateDateTime(LocalDateTime.now());
//        this.caseDashBoardMapper.updateCaseByTax(board);
//    }
//
//    @Override
//    public void saveStepLog(TaxForm taxForm) {
//        // 记录日志
//        this.caseTraceService.saveLogInfo(taxForm.getCaseId(),
//                EnumCaseStatus.TAX_REJECTED, UserUtil.getUserId().toString(), "Tax Rejected");
//    }
//
//    @Override
//    public void afterAction(TaxForm taxForm) {
//        // 用于取邮件模板
//        taxForm.getRejectData().setSscCaseStatus(EnumCaseStatus.TAX_REJECTED.getValue());
//        this.rejectionApi.closeCase(taxForm.getRejectData());
//    }
}
