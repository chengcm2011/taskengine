package com.application.a.model;

import com.cheng.lang.model.SuperModel;
import com.cheng.lang.model.UFBoolean;

/**
 *
 */
public class AccountNOModel extends SuperModel {
    private String pkAccount;
    private int aid;
    private String accountno;
    private UFBoolean success;
    private String remsg;
    private int dr;
    private String ts;

    @Override
    public String getParentPKFieldName() {
        return null;
    }

    @Override
    public String getPKFieldName() {
        return "pkAccount";
    }

    @Override
    public String getTableName() {
        return "tmp_account";
    }

    public String getPkAccount() {
        return pkAccount;
    }

    public void setPkAccount(String pkAccount) {
        this.pkAccount = pkAccount;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public UFBoolean getSuccess() {
        return success;
    }

    public void setSuccess(UFBoolean success) {
        this.success = success;
    }

    public int getDr() {
        return dr;
    }

    public void setDr(int dr) {
        this.dr = dr;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public static void main(String[] args) {
        System.out.println(SuperModel.toTableSql(AccountNOModel.class));
    }

    public String getRemsg() {
        return remsg;
    }

    public void setRemsg(String remsg) {
        this.remsg = remsg;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }
}
