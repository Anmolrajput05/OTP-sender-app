package com.nopupio.www.otpsenderupdated;

public class OTPRequest {
    //echo "{\"userPhone\":\"$userPhone\", \"OTP\":\"$m_otp\", \"message\":\"$message\", \"otp_for\":\"$otp_for\"}";

    private String userPhone;
    private String OTP;
    private String message;
    private String otp_for;


    public OTPRequest(String userPhone, String OTP, String message, String otp_for) {
        this.userPhone = userPhone;
        this.OTP = OTP;
        this.message = message;
        this.otp_for = otp_for;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOtp_for() {
        return otp_for;
    }

    public void setOtp_for(String otp_for) {
        this.otp_for = otp_for;
    }
}
