package com.cas.webservice.pojo.pre;


import com.cas.constant.Constants;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PreOperationsReq", namespace = Constants.TSM_URL)
@XmlType(name = "PreOperationsReq", namespace = Constants.TSM_URL)
public class PreOperationsRequest {

    @XmlElement(name = "SeqNum", namespace = Constants.TSM_URL)
    String seqNum;

    @XmlElement(name = "SessionID", namespace = Constants.TSM_URL)
    String sessionID;

    @XmlElement(name = "SessionType", namespace = Constants.TSM_URL)
    String sessionType;

    @XmlElement(name = "TimeStamp", namespace = Constants.TSM_URL)
    String timeStamp;

    @XmlElement(name = "CommType", namespace = Constants.TSM_URL)
    String commType;

    @XmlElement(name = "Msisdn", namespace = Constants.TSM_URL)
    String Msisdn;

    @XmlElement(name = "SEID", namespace = Constants.TSM_URL)
    String seid;

    @XmlElement(name = "IMEI", namespace = Constants.TSM_URL)
    String imei;

    @XmlElement(name = "AppAID", namespace = Constants.TSM_URL)
    String appAid;

    public String getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(String seqNum) {
        this.seqNum = seqNum;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCommType() {
        return commType;
    }

    public void setCommType(String commType) {
        this.commType = commType;
    }

    public String getMsisdn() {
        return Msisdn;
    }

    public void setMsisdn(String msisdn) {
        Msisdn = msisdn;
    }

    public String getSeid() {
        return seid;
    }

    public void setSeid(String seid) {
        this.seid = seid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getAppAid() {
        return appAid;
    }

    public void setAppAid(String appAid) {
        this.appAid = appAid;
    }
}
