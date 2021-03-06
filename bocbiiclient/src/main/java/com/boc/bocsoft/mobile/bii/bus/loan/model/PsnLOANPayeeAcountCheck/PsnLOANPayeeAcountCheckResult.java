package com.boc.bocsoft.mobile.bii.bus.loan.model.PsnLOANPayeeAcountCheck;

import java.util.List;

/**
 * PsnLOANPayeeAcountCheck收款账户检查，返回参数
 * Created by xintong on 2016/6/24.
 */
public class PsnLOANPayeeAcountCheckResult {

    /**
     * 账户检查结果
     * 01：符合业务条件
     * 02： 贷款账户与收款账户币种不一致
     * 03：收款账户为钞户
     * 04：已签约投资理财服务
     * 注：02、03和04不符合业务条件，前端须对应提示
     */
    private List<String> checkResult;

    public List<String> getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(List<String> checkResult) {
        this.checkResult = checkResult;
    }


}
