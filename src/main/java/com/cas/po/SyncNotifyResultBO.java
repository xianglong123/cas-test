package com.cas.po;


/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/4/11 5:31 下午
 * @desc 结果通知类
 */
public class SyncNotifyResultBO {

    /**
     * 云平台流水号
     */
    private String notifyId;
    /**
     * 通知码id
     */
    private String partnerSessionId;
    /**
     * 执行结果
     */
    private String executeStatus;
    /**
     * 时间戳
     */
    private String tmSmp;

    public SyncNotifyResultBO() {
    }



    public String getTmSmp() {
        return tmSmp;
    }

    public void setTmSmp(String tmSmp) {
        this.tmSmp = tmSmp;
    }

    public String getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }

    public String getPartnerSessionId() {
        return partnerSessionId;
    }

    public void setPartnerSessionId(String partnerSessionId) {
        this.partnerSessionId = partnerSessionId;
    }
}
