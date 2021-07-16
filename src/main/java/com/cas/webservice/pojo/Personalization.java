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
@XmlType(name = "Personalization", namespace = Constants.TSM_URL)
public class Personalization {

    @XmlElement(name = "AppAID", namespace = Constants.TSM_URL)
    @JsonProperty(value = "AppAID")
    private String appAid;

    @XmlElement(name = "FileContent", namespace = Constants.TSM_URL)
    @JsonProperty(value = "FileContent")
    private String fileContent;

    public Personalization() {}

    public Personalization(String appAid, String fileContent) {
        this.appAid = appAid;
        this.fileContent = fileContent;
    }

    public String getAppAid() {
        return appAid;
    }

    public void setAppAid(String appAid) {
        this.appAid = appAid;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

}
