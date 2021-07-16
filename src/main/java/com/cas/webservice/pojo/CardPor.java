package com.cas.webservice.pojo;


import com.cas.constant.Constants;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author xianglong
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardPOR", namespace = Constants.TSM_URL)
public class CardPor {

    @XmlElement(name = "APDUSum", namespace = Constants.TSM_URL)
    private String apduSum;

    @XmlElement(name = "LastApduSW", namespace = Constants.TSM_URL)
    private String lastApduSw;

    @XmlElement(name = "LastData", namespace = Constants.TSM_URL)
    private String lastData;

    @XmlElement(name = "LastApdu", namespace = Constants.TSM_URL)
    private String lastApdu;

    public String getApduSum() {
        return apduSum;
    }

    public void setApduSum(String apduSum) {
        this.apduSum = apduSum;
    }

    public String getLastApduSw() {
        return lastApduSw;
    }

    public void setLastApduSw(String lastApduSw) {
        this.lastApduSw = lastApduSw;
    }

    public String getLastData() {
        return lastData;
    }

    public void setLastData(String lastData) {
        this.lastData = lastData;
    }

    public String getLastApdu() {
        return lastApdu;
    }

    public void setLastApdu(String lastApdu) {
        this.lastApdu = lastApdu;
    }
}
