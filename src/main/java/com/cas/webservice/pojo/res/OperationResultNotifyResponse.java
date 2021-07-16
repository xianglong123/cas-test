package com.cas.webservice.pojo.res;


import com.cas.constant.Constants;
import com.cas.webservice.pojo.Personalization;
import com.cas.webservice.pojo.Status;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author xianglong
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "OperationResultNotifyResponse", namespace = Constants.TSM_URL)
@XmlType(name = "OperationResultNotifyResponse", namespace = Constants.TSM_URL)
public class OperationResultNotifyResponse {


    @XmlElement(name = "SeqNum", namespace = Constants.TSM_URL)
    private String seqNum;

    @XmlElement(name = "TimeStamp", namespace = Constants.TSM_URL)
    private String timeStamp;

    @XmlElement(name = "Status", namespace = Constants.TSM_URL)
    private Status status;

    @XmlElement(name = "IfContinueOpt", namespace = Constants.TSM_URL)
    private String ifContinueOpt;

    @XmlElement(name = "Personalization", namespace = Constants.TSM_URL)
    private List<Personalization> personalization;

    @XmlElement(name = "PersoType", namespace = Constants.TSM_URL)
    private String persoType;

    public String getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(String seqNum) {
        this.seqNum = seqNum;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getIfContinueOpt() {
        return ifContinueOpt;
    }

    public List<Personalization> getPersonalization() {
        return personalization;
    }

    public void setPersonalization(List<Personalization> personalization) {
        this.personalization = personalization;
    }

    public void setIfContinueOpt(String ifContinueOpt) {
        this.ifContinueOpt = ifContinueOpt;
    }

    public String getPersoType() {
        return persoType;
    }

    public void setPersoType(String persoType) {
        this.persoType = persoType;
    }
}
