package com.spark.p2p.entity.member;

import java.io.Serializable;

import com.sparkframework.sql.annotation.Field;

public class Identity implements Serializable {
    private static final long serialVersionUID = 2L;

    @Field("member_id")
    private long memberId;
    private long id;
    @Field("real_name")
    private String realName;
    @Field("card_no")
    private String cardNo;
    @Field("submit_time")
    private String submitTime;
    @Field("card_imgA")
    private String cardImgA;
    @Field("card_imgB")
    private String cardImgB;

    public String getCardImgA() {
        return cardImgA;
    }

    public void setCardImgA(String cardImgA) {
        this.cardImgA = cardImgA;
    }

    public String getCardImgB() {
        return cardImgB;
    }

    public void setCardImgB(String cardImgB) {
        this.cardImgB = cardImgB;
    }

    public String getHandleImg() {
        return handleImg;
    }

    public void setHandleImg(String handleImg) {
        this.handleImg = handleImg;
    }

    @Field("handle_img")

    private String handleImg;
    @Field("qq_mail")
    private String qqMail;
    @Field("now_address")
    private String nowAddress;

    //手机抓取的地址
    @Field("true_address")
    private String trueAddress;

    public String getTrueAddress() {
        return trueAddress;
    }

    public void setTrueAddress(String trueAddress) {
        this.trueAddress = trueAddress;
    }

    public String getQqMail() {
        return qqMail;
    }

    public void setQqMail(String qqMail) {
        this.qqMail = qqMail;
    }

    public String getNowAddress() {
        return nowAddress;
    }

    public void setNowAddress(String nowAddress) {
        this.nowAddress = nowAddress;
    }


    private int status;

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
