package com.cas.webservice.pojo.pre;


import com.cas.constant.Constants;
import com.cas.webservice.pojo.Personalization;
import com.cas.webservice.pojo.Status;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PreOperationsReqResponse", namespace = Constants.TSM_URL)
@XmlType(name = "PreOperationsReqResponse", namespace = Constants.TSM_URL)
public class PreOperationsResponse {

    @XmlElement(name = "SeqNum", namespace = Constants.TSM_URL)
    private String seqNum;

    @XmlElement(name = "TimeStamp", namespace = Constants.TSM_URL)
    private String timeStamp;

    @XmlElement(name = "Status", namespace = Constants.TSM_URL)
    private Status status;

    @XmlElement(name = "Personalization", namespace = Constants.TSM_URL)
    private List<Personalization> personalization;

    @XmlElement(name = "PersoMode", namespace = Constants.TSM_URL)
    private String persoMode;

    @XmlElement(name = "PersoType", namespace = Constants.TSM_URL)
    private String persoType;

    public String getPersoType() {
        return persoType;
    }

    public void setPersoType(String persoType) {
        this.persoType = persoType;
    }

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

    public List<Personalization> getPersonalization() {
        return personalization;
    }

    public void setPersonalization(List<Personalization> personalization) {
        this.personalization = personalization;
    }

    public String getPersoMode() {
        return persoMode;
    }

    public void setPersoMode(String persoMode) {
        this.persoMode = persoMode;
    }
}
