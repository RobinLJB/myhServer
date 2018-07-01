package com.spark.p2p.entity;

import com.sparkframework.sql.annotation.Field;

/**
 * @Author: crazy
 * @Date: Created in 13:50 2018/1/5
 */
public class BorrowMain {
    @Field("member_id")
    private int memberId;
    @Field("id")
    private long Id;
    @Field("iphoneId")
    private int iphoneId;

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public int getIphoneId() {
        return iphoneId;
    }

    public void setIphoneId(int iphoneId) {
        this.iphoneId = iphoneId;
    }

    @Override
    public String toString() {
        return "BorrowMain{" +
                "memberId=" + memberId +
                ", Id=" + Id +
                ", iphoneId=" + iphoneId +
                '}';
    }
}
