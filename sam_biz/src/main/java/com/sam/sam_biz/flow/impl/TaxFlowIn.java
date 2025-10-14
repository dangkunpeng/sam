package com.sam.sam_biz.flow.impl;

import com.sam.sam_biz.flow.TaxFlowAction;
import com.sam.sam_biz.flow.TaxForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 会计流量去
 *
 * @author kunpeng.dang
 * @date 2023/09/25
 */
@Slf4j
@Service
@Transactional
public class TaxFlowIn extends TaxFlowAction {
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
//    @Resource
//    private TaxFlowTool taxFlowTool;
//    @Resource
//    private CaseTraceService caseTraceService;
//    @Resource
//    private TaxDashboardMapper taxDashBoardMapper;
//    @Resource
//    private CaseDashBoardMapper caseDashBoardMapper;
//
//    @Override
//    public void beforeAction(TaxForm taxForm) {
//    }
//
//    /**
//     * 更新会计仪表板
//     *
//     * @param taxForm 税表
//     */
//    @Override
//    public void updateTaxDashBoard(TaxForm taxForm) {
//
//        // 删除可能存在的记录
//        this.taxDashBoardMapper.deleteById(taxForm.getCaseId());
//        Long currentTime = DateTimeUtils.getCurrentDateTimeToLong();
//        // 默认TAX_TASK_NOT_ASSIGNED
//        TaxDashBoard taxDashBoard = TaxDashBoard.builder()
//                .caseId(taxForm.getCaseId())
//                .aging(0)
//                .agingGroup("in-0-1-2-")
//                .requestDate(taxForm.getRequestDate())
//                .receiveDate(currentTime)
//                .createUserId(-1)
//                .updateUserId(-1)
//                .createDateTime(currentTime)
//                .updateDateTime(currentTime)
//                .build();
//        // 计算aging
//        taxDashBoard.setAgingGroup(this.taxFlowTool.taxAgingGroup(taxDashBoard.getAging()));
//        // 查询符合条件的数据
//        String assignee = this.taxFlowTool.getTaxAssignee(taxForm.getBizSubProcessId(), taxForm.getCompanyCode());
//        taxDashBoard.setAssignee(assignee);
//
//        taxDashBoard.setCaseStatus(TaxFlowTool.getTaxCaseStatus(assignee));
//        log.info("caseId={}, tax工作流->Tax案件状态为{},案件分配到{}", taxForm.getCaseId(), taxDashBoard.getCaseStatus(), assignee);
//        // 传递到下一个方法中使用
//        taxForm.setAssignee(assignee);
//        // 记录taxDashboard
//        this.taxDashBoardMapper.insert(taxDashBoard);
//    }
//
//    /**
//     * 更新仪表板
//     *
//     * @param taxForm 税表
//     */
//    @Override
//    public void updateDashBoard(TaxForm taxForm) {
//
//        // 更新SSC的案件状态
//        CaseDashBoard board = new CaseDashBoard();
//        board.setCaseId(taxForm.getCaseId());
//        board.setCaseStatus(TaxFlowTool.getSscCaseStatus(taxForm.getAssignee()));
//        board.setUpdateDateTime(LocalDateTime.now());
//        log.info("caseId={}, tax工作流->更新SSC案件状态为{}", taxForm.getCaseId(), board.getCaseStatus());
//        this.caseDashBoardMapper.updateCaseByTax(board);
//    }
//
//    @Override
//    public void saveStepLog(TaxForm taxForm) {
//        this.caseTraceService.saveLogInfo(taxForm.getCaseId(), TaxFlowTool.getSscCaseStatus(taxForm.getAssignee()),
//                taxForm.getAssignee(), "Case Flow Into Tax");
//    }
//
//    @Override
//    public void afterAction(TaxForm taxForm) {
//
//    }
}
