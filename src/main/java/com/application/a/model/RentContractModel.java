package com.application.a.model;

import com.cheng.lang.model.SuperModel;

/**
 *
 */
public class RentContractModel extends SuperModel {
    private String pkRentContract;
    private String currentDate;
    private int rentContractNum;
    private int surrrentContractNum;
    private int dr;
    private String ts;

    @Override
    public String getParentPKFieldName() {
        return null;
    }

    @Override
    public String getPKFieldName() {
        return "pkRentContract";
    }

    @Override
    public String getTableName() {
        return "tmp_rent_contract";
    }

    public String getPkRentContract() {
        return pkRentContract;
    }

    public void setPkRentContract(String pkRentContract) {
        this.pkRentContract = pkRentContract;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public int getRentContractNum() {
        return rentContractNum;
    }

    public void setRentContractNum(int rentContractNum) {
        this.rentContractNum = rentContractNum;
    }

    public int getSurrrentContractNum() {
        return surrrentContractNum;
    }

    public void setSurrrentContractNum(int surrrentContractNum) {
        this.surrrentContractNum = surrrentContractNum;
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
}
