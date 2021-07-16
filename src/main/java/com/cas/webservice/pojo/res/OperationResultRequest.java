package com.cas.webservice.pojo.res;

import com.cas.constant.Constants;
import com.cas.webservice.pojo.CardPor;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "OperationResultNotify", namespace = Constants.TSM_URL)
@XmlType(name = "OperationResultNotify", namespace = Constants.TSM_URL)
public class OperationResultRequest {

    @XmlElement(name = "SeqNum", namespace = Constants.TSM_URL)
    private String seqNum;

    @XmlElement(name = "SessionID", namespace = Constants.TSM_URL)
    private String sessionId;

    @XmlElement(name = "SessionType", namespace = Constants.TSM_URL)
    private String sessionType;

    @XmlElement(name = "TimeStamp", namespace = Constants.TSM_URL)
    private String timeStamp;

    @XmlElement(name = "OriginalSeqNum", namespace = Constants.TSM_URL)
    private String originalSeqNum;

    @XmlElement(name = "Msisdn", namespace = Constants.TSM_URL)
    private String msisdn;

    @XmlElement(name = "SEID", namespace = Constants.TSM_URL)
    private String seid;

    @XmlElement(name = "AppAID", namespace = Constants.TSM_URL)
    private String appAid;

    @XmlElement(name = "ResultCode", namespace = Constants.TSM_URL)
    private String resultCode;

    @XmlElement(name = "ResultMsg", namespace = Constants.TSM_URL)
    private String resultMsg;

    @XmlElement(name = "Imsi", namespace = Constants.TSM_URL)
    private String imsi;

    @XmlElement(name = "CardPOR", namespace = Constants.TSM_URL)
    private CardPor cardPor;

    public String getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(String seqNum) {
        this.seqNum = seqNum;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    public String getOriginalSeqNum() {
        return originalSeqNum;
    }

    public void setOriginalSeqNum(String originalSeqNum) {
        this.originalSeqNum = originalSeqNum;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getSeid() {
        return seid;
    }

    public void setSeid(String seid) {
        this.seid = seid;
    }

    public String getAppAid() {
        return appAid;
    }

    public void setAppAid(String appAid) {
        this.appAid = appAid;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public CardPor getCardPor() {
        return cardPor;
    }

    public void setCardPor(CardPor cardPor) {
        this.cardPor = cardPor;
    }


    @Override
    public String toString() {
        return "OperationResultRequest{" +
                "sessionId='" + sessionId + '\'' +
                ", sessionType='" + sessionType + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", originalSeqNum='" + originalSeqNum + '\'' +
                ", msisdn='" + msisdn + '\'' +
                ", seid='" + seid + '\'' +
                ", appAid='" + appAid + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                ", imsi='" + imsi + '\'' +
                ", cardPor=" + cardPor +
                '}';
    }
}