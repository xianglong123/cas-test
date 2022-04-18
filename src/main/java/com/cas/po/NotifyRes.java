package com.cas.po;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/4/13 10:06 上午
 * @desc
 */
public class NotifyRes {

    private String returnCode;

    private String returnMessage;

    private String partnerSessionId;

    private String signature;

    public NotifyRes(Builder builder) {
        this.partnerSessionId = builder.partnerSessionId;
        this.returnCode = builder.returnCode;
        this.returnMessage = builder.returnMessage;
        this.signature = builder.signature;
    }

    public static class Builder {
        private String partnerSessionId;

        private String returnCode;

        private String returnMessage;

        private String signature;

        public Builder setPartnerSessionId(String partnerSessionId) {
            this.partnerSessionId = partnerSessionId;
            return this;
        }

        public Builder setReturnCode(String returnCode) {
            this.returnCode = returnCode;
            return this;
        }

        public Builder setReturnMessage(String returnMessage) {
            this.returnMessage = returnMessage;
            return this;
        }

        public Builder setSignature(String signature) {
            this.signature = signature;
            return this;
        }

        public NotifyRes build() {
            return new NotifyRes(this);
        }

    }

    public String getPartnerSessionId() {
        return partnerSessionId;
    }

    public void setPartnerSessionId(String partnerSessionId) {
        this.partnerSessionId = partnerSessionId;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
