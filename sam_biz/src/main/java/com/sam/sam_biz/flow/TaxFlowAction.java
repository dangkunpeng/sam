package com.sam.sam_biz.flow;


/**
 * 会计流量 API
 *
 * @author kunpeng.dang
 * @date 2023/09/25
 */
public abstract class TaxFlowAction {
    /**
     * 入口
     */
    public final void process(TaxForm taxForm) {
        // 前置执行
        this.beforeAction(taxForm);
        // 更新Tax的dashboard
        this.updateTaxDashBoard(taxForm);
        // 更新SSC的dashboard
        this.updateDashBoard(taxForm);
        // 记录操作步骤
        this.saveStepLog(taxForm);
        //后置执行
        this.afterAction(taxForm);
    }

    /**
     * 前置操作
     */
    public abstract void beforeAction(TaxForm taxForm);

    /**
     * 更新SSC的dashboard
     *
     * @param taxForm
     */
    public abstract void updateDashBoard(TaxForm taxForm);

    /**
     * 更新Tax的dashboard
     *
     * @param taxForm
     */
    public abstract void updateTaxDashBoard(TaxForm taxForm);

    /**
     * 记录当前步骤
     *
     * @param taxForm
     */
    public abstract void saveStepLog(TaxForm taxForm);

    /**
     * 后续操作
     */
    public abstract void afterAction(TaxForm taxForm);
}
