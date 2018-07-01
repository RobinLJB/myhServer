package com.spark.p2p.entity.member;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONObject;

import com.sparkframework.sql.annotation.Field;

public class Member implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Field("id")
    private long id;
    @Field("member_id")
    private long memberId;
    @Field("commicateDetail")
    private String commicateDetail;
    @Field("phoneRecordUrl")
    private String phoneRecordUrl;
    @Field("memberNo")
    private String memberNo;

    @Field("member_status")
    private int memberStatus;
    @Field("successBorrowTimes")
    private int successBorrowTimes;

    private String username;
    private String detail;
    private String lastIP;
    private String avatar;
    private String token;
    private String email;
    @Field("real_name")
    private String realName;
    @Field("inviteSum")
    private int inviteSum;
    @Field("ios_invite_sum")
    private int iosInviteSum;
    @Field("android_invite_sum")
    private int androidInviteSum;

    @Field("ident_no")
    private String identNo;
    @Field("invateActive")
    private String invateActive;

    @Field("commisionSum")
    private double commisionSum;
    @Field("create_time")
    private String createTime;
    @Field("freezeCommision")
    private String freezeCommision;
    @Field("memberImgPath")
    private String memberImgPath;
    @Field("taskId")
    private String taskId;
    private String mobilePhone;
    private int role;

    private String agreeNo;
    private int alreadyRepaySum;
    private int alreadyBorrowSum;
    private int overdueSum;

    private String callDetail;
    private String callStatus;


    public int getInviteSum() {
        return inviteSum;
    }

    public void setInviteSum(int inviteSum) {
        this.inviteSum = inviteSum;
    }

    public int getIosInviteSum() {
        return iosInviteSum;
    }

    public void setIosInviteSum(int iosInviteSum) {
        this.iosInviteSum = iosInviteSum;
    }

    public int getAndroidInviteSum() {
        return androidInviteSum;
    }

    public void setAndroidInviteSum(int androidInviteSum) {
        this.androidInviteSum = androidInviteSum;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getInvateActive() {
        return invateActive;
    }

    public void setInvateActive(String invateActive) {
        this.invateActive = invateActive;
    }

    public String getPhoneRecordUrl() {
        return phoneRecordUrl;
    }

    public void setPhoneRecordUrl(String phoneRecordUrl) {
        this.phoneRecordUrl = phoneRecordUrl;
    }

    public String getCommicateDetail() {
        return commicateDetail;
    }

    public void setCommicateDetail(String commicateDetail) {
        this.commicateDetail = commicateDetail;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getFreezeCommision() {
        return freezeCommision;
    }

    public void setFreezeCommision(String freezeCommision) {
        this.freezeCommision = freezeCommision;
    }

    public long getRole() {
        return role;
    }

    public void setRole(int r) {
        this.role = r;
    }

    public String getIdentNo() {
        return identNo;
    }

    public void setIdentNo(String identNo) {
        this.identNo = identNo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getId() {
        return id;
    }

    public long getMemberId() {
        return memberId;
    }

    public String getUsername() {
        return username;
    }


    public String getLastIP() {
        return lastIP;
    }


    public String getEmail() {
        return email;
    }

    public String getRealName() {
        return realName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLastIP(String lastIP) {
        this.lastIP = lastIP;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(int memberStatus) {
        this.memberStatus = memberStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getCommisionSum() {
        return commisionSum;
    }

    public void setCommisionSum(double commisionSum) {
        this.commisionSum = commisionSum;
    }

    public String getMemberImgPath() {
        return memberImgPath;
    }

    public void setMemberImgPath(String memberImgPath) {
        this.memberImgPath = memberImgPath;
    }

    public int getSuccessBorrowTimes() {
        return successBorrowTimes;
    }

    public void setSuccessBorrowTimes(int successBorrowTimes) {
        this.successBorrowTimes = successBorrowTimes;
    }

    public String getAgreeNo() {
        return agreeNo;
    }

    public void setAgreeNo(String agreeNo) {
        this.agreeNo = agreeNo;
    }


    public int getAlreadyBorrowSum() {
        return alreadyBorrowSum;
    }

    public void setAlreadyBorrowSum(int alreadyBorrowSum) {
        this.alreadyBorrowSum = alreadyBorrowSum;
    }

    public int getAlreadyRepaySum() {
        return alreadyRepaySum;
    }

    public void setAlreadyRepaySum(int alreadyRepaySum) {
        this.alreadyRepaySum = alreadyRepaySum;
    }

    public int getOverdueSum() {
        return overdueSum;
    }

    public void setOverdueSum(int overdueSum) {
        this.overdueSum = overdueSum;
    }

    public String getCallDetail() {
        return callDetail;
    }

    public void setCallDetail(String callDetail) {
        this.callDetail = callDetail;
    }

    public String getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }

}
