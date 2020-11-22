package com.atguigu.crowd.vo;

import java.io.Serializable;

public class OrderVO implements Serializable {
    private static final long serialVersionUID = 8446942138396266704L;
    private Integer id;

    private String orderNum;

    private String payOrderNum;

    private Double orderAmount;

    private Integer invoice;

    private String invoiceTitle;

    private String orderRemark;

    private String addressId;

    private OrderProjectVO orderProjectVO;

    public OrderVO() {
    }

    public OrderVO(Integer id, String orderNum, String payOrderNum, Double orderAmount, Integer invoice, String invoiceTitle, String orderRemark, String addressId, OrderProjectVO orderProjectVO) {
        this.id = id;
        this.orderNum = orderNum;
        this.payOrderNum = payOrderNum;
        this.orderAmount = orderAmount;
        this.invoice = invoice;
        this.invoiceTitle = invoiceTitle;
        this.orderRemark = orderRemark;
        this.addressId = addressId;
        this.orderProjectVO = orderProjectVO;
    }

    @Override
    public String toString() {
        return "OrderVO{" +
                "id=" + id +
                ", orderNum='" + orderNum + '\'' +
                ", payOrderNum='" + payOrderNum + '\'' +
                ", orderAmount=" + orderAmount +
                ", invoice=" + invoice +
                ", invoiceTitle='" + invoiceTitle + '\'' +
                ", orderRemark='" + orderRemark + '\'' +
                ", addressId='" + addressId + '\'' +
                ", orderProjectVO=" + orderProjectVO +
                '}';
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public OrderProjectVO getOrderProjectVO() {
        return orderProjectVO;
    }

    public void setOrderProjectVO(OrderProjectVO orderProjectVO) {
        this.orderProjectVO = orderProjectVO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getPayOrderNum() {
        return payOrderNum;
    }

    public void setPayOrderNum(String payOrderNum) {
        this.payOrderNum = payOrderNum;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getInvoice() {
        return invoice;
    }

    public void setInvoice(Integer invoice) {
        this.invoice = invoice;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }
}

