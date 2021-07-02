package com.cas.binary;

public class SimIdCardInfo {

    private String userConfirmResult;
    private String digitalIdInfo;
    private String name;
    private String idNumber;
    private String valid;
    private String qrCode;
    private String pid;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUserConfirmResult() {
        return userConfirmResult;
    }

    public void setUserConfirmResult(String userConfirmResult) {
        this.userConfirmResult = userConfirmResult;
    }

    public String getDigitalIdInfo() {
        return digitalIdInfo;
    }

    public void setDigitalIdInfo(String digitalIdInfo) {
        this.digitalIdInfo = digitalIdInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    @Override
    public String toString() {
        return "SimIdCardInfo{" +
                "userConfirmResult='" + userConfirmResult + '\'' +
                ", digitalIdInfo='" + digitalIdInfo + '\'' +
                ", name='" + name + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", valid='" + valid + '\'' +
                ", qrCode='" + qrCode + '\'' +
                ", pid='" + pid + '\'' +
                '}';
    }
}
