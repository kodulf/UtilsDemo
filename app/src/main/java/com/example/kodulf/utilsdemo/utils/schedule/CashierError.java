package com.example.kodulf.utilsdemo.utils.schedule;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by shiang on 16-2-27.
 */
public class CashierError extends DataSupport implements Serializable {

    private static final long serialVersionUID = 5018405354966933367L;

    private String errorType;

    private String errorObject;

    private String errorMsg;

    private Long  shopUid;

    public String getErrorObject() {
        return errorObject;
    }

    public void setErrorObject(String errorObject) {
        this.errorObject = errorObject;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public Long getShopUid() {
        return shopUid;
    }

    public void setShopUid(Long shopUid) {
        this.shopUid = shopUid;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }



    public CashierError(String errorType, String errorObject, String errorMsg, Long shopUid) {
        this.errorType = errorType;
        this.errorObject = errorObject;
        this.errorMsg = errorMsg;
        this.shopUid = shopUid;
    }

    public CashierError() {
    }
}
