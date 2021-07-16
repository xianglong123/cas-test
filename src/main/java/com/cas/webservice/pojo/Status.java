package com.cas.webservice.pojo;

import com.cas.constant.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author xianglong
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Status", namespace = Constants.TSM_URL)
public class Status {

    @XmlElement(name = "StatusCode", namespace = Constants.TSM_URL)
    @JsonProperty(value = "StatusCode")
    private String statusCode;

    @XmlElement(name = "StatusDescription", namespace = Constants.TSM_URL)
    @JsonProperty(value = "StatusDescription")
    private String statusDescription;

    public Status() {}

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
