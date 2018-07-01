package com.spark.p2p.entity;

import com.sparkframework.sql.annotation.Field;

public class BorrowAmount {
    @Field("amount")
    private int amount;
    @Field("service_amount")
    private int serviceAmount;
    @Field("paid_amount")
    private int paidAmount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(int serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public int getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(int paidAmount) {
        this.paidAmount = paidAmount;
    }

    @Override
    public String toString() {
        return "BorrowAmount{" +
                "amount=" + amount +
                ", serviceAmount=" + serviceAmount +
                ", paidAmount=" + paidAmount +
                '}';
    }
}
