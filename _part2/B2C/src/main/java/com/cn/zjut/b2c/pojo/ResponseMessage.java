package com.cn.zjut.b2c.pojo;

import java.io.Serializable;

public class ResponseMessage implements Serializable {

	private static final long serialVersionUID = 3544046351379168604L;


	private Long id;

    private String version;

    private String encoding;

    private String certid;

    private String signature;

    private String signmethod;

    private String txntype;

    private String txnsubtype;

    private String biztype;

    private String accesstype;

    private String merid;

    private String orderid;

    private String origqryid;

    private String txntime;

    private String txnamt;

    private String reqreserved;

    private String reserved;

    private String queryid;

    private String respcode;

    private String respmsg;

//    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding == null ? null : encoding.trim();
    }

    public String getCertid() {
        return certid;
    }

    public void setCertid(String certid) {
        this.certid = certid == null ? null : certid.trim();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public String getSignmethod() {
        return signmethod;
    }

    public void setSignmethod(String signmethod) {
        this.signmethod = signmethod == null ? null : signmethod.trim();
    }

    public String getTxntype() {
        return txntype;
    }

    public void setTxntype(String txntype) {
        this.txntype = txntype == null ? null : txntype.trim();
    }

    public String getTxnsubtype() {
        return txnsubtype;
    }

    public void setTxnsubtype(String txnsubtype) {
        this.txnsubtype = txnsubtype == null ? null : txnsubtype.trim();
    }

    public String getBiztype() {
        return biztype;
    }

    public void setBiztype(String biztype) {
        this.biztype = biztype == null ? null : biztype.trim();
    }

    public String getAccesstype() {
        return accesstype;
    }

    public void setAccesstype(String accesstype) {
        this.accesstype = accesstype == null ? null : accesstype.trim();
    }

    public String getMerid() {
        return merid;
    }

    public void setMerid(String merid) {
        this.merid = merid == null ? null : merid.trim();
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public String getOrigqryid() {
        return origqryid;
    }

    public void setOrigqryid(String origqryid) {
        this.origqryid = origqryid == null ? null : origqryid.trim();
    }

    public String getTxntime() {
        return txntime;
    }

    public void setTxntime(String txntime) {
        this.txntime = txntime == null ? null : txntime.trim();
    }

    public String getTxnamt() {
        return txnamt;
    }

    public void setTxnamt(String txnamt) {
        this.txnamt = txnamt == null ? null : txnamt.trim();
    }

    public String getReqreserved() {
        return reqreserved;
    }

    public void setReqreserved(String reqreserved) {
        this.reqreserved = reqreserved == null ? null : reqreserved.trim();
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved == null ? null : reserved.trim();
    }

    public String getQueryid() {
        return queryid;
    }

    public void setQueryid(String queryid) {
        this.queryid = queryid == null ? null : queryid.trim();
    }

    public String getRespcode() {
        return respcode;
    }

    public void setRespcode(String respcode) {
        this.respcode = respcode == null ? null : respcode.trim();
    }

    public String getRespmsg() {
        return respmsg;
    }

    public void setRespmsg(String respmsg) {
        this.respmsg = respmsg == null ? null : respmsg.trim();
    }
}
