package com.boc.bocsoft.mobile.bocmobile.buss.account.virtual.model;

import org.threeten.bp.LocalDate;

import java.math.BigDecimal;

/**
 * @author wangyang
 *         16/9/3 21:11
 *         账单交易明细
 */
public class VirtualBillTransModel {

    /**
     * 借方标识
     */
    public static final String FLAG_DEBT = "DEBT";
    /**
     * 贷方标识
     */
    public static final String FLAG_CRED = "CRED";
    /**
     * 年费标识
     */
    public static final String FLAG_NMON = "NMON";

    /**
     * 摘要(商户名称)
     */
    private String remark;
    /**
     * 交易日期
     */
    private LocalDate transDate;
    /**
     * 借方合计
     */
    private String transCode;
    /**
     * 记账币种--001=人民币元,014=美元,027=日元
     */
    private String bookCurrency;
    /**
     * 借贷标识--CRED表示贷方,DEBT表示借方,NMON表示减免年费
     */
    private String debitCreditFlag;
    /**
     * 记账金额
     */
    private BigDecimal bookAmount;
    /**
     * 记账日期
     */
    private LocalDate bookDate;
    /**
     * 卡号--后四位
     */
    private String cardNumberTail;
    /**
     * 交易币种--001=人民币元,014=美元,027=日元
     */
    private String tranCurrency;
    /**
     * 交易金额
     */
    private BigDecimal tranAmount;

    public String getRemark() {
        return remark.replaceAll(" ", "");
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDate getTransDate() {
        return transDate;
    }

    public void setTransDate(LocalDate transDate) {
        this.transDate = transDate;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getBookCurrency() {
        return bookCurrency;
    }

    public void setBookCurrency(String bookCurrency) {
        this.bookCurrency = bookCurrency;
    }

    public String getDebitCreditFlag() {
        return debitCreditFlag;
    }

    public void setDebitCreditFlag(String debitCreditFlag) {
        this.debitCreditFlag = debitCreditFlag;
    }

    public BigDecimal getBookAmount() {
        return bookAmount;
    }

    public void setBookAmount(BigDecimal bookAmount) {
        this.bookAmount = bookAmount;
    }

    public LocalDate getBookDate() {
        return bookDate;
    }

    public void setBookDate(LocalDate bookDate) {
        this.bookDate = bookDate;
    }

    public String getCardNumberTail() {
        return cardNumberTail;
    }

    public void setCardNumberTail(String cardNumberTail) {
        this.cardNumberTail = cardNumberTail;
    }

    public String getTranCurrency() {
        return tranCurrency;
    }

    public void setTranCurrency(String tranCurrency) {
        this.tranCurrency = tranCurrency;
    }

    public BigDecimal getTranAmount() {
        return tranAmount;
    }

    public void setTranAmount(BigDecimal tranAmount) {
        this.tranAmount = tranAmount;
    }

    public String getType(){
        switch (debitCreditFlag){
            case FLAG_DEBT:
                return "支出";
            case FLAG_CRED:
                return "存入";
            case FLAG_NMON:
                return "减免年费";
        }
        return null;
    }
}
