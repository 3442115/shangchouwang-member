package com.atguigu.crowd.vo;

public class DetailReturnVO {
    // 汇报信息的主键
    private Integer returnId;
    // 当前挡位需要支持的金额
    private Integer supportMoney;
    // 单笔限额，取值为0时，无限额，取值为整数时为具体限额
    private Integer signalPurchase;
    // 具体限额的数量
    private Integer purchase;

    // 当前在该挡位支持者的数量
     private Integer supperCount;
     // 运费 0 包邮
    private Integer freight;
    // 众筹成功后多少天发货
     private Integer returnDate;
    // 汇报的内容
    private  String content;

    public DetailReturnVO() {
    }

    public DetailReturnVO(Integer returnId, Integer supportMoney, Integer signalPurchase, Integer purchase, Integer supperCount, Integer freight, Integer returnDate, String content) {
        this.returnId = returnId;
        this.supportMoney = supportMoney;
        this.signalPurchase = signalPurchase;
        this.purchase = purchase;
        this.supperCount = supperCount;
        this.freight = freight;
        this.returnDate = returnDate;
        this.content = content;
    }

    @Override
    public String toString() {
        return "DetailReturnVO{" +
                "returnId=" + returnId +
                ", supportMoney=" + supportMoney +
                ", signalPurchase=" + signalPurchase +
                ", purchase=" + purchase +
                ", supperCount=" + supperCount +
                ", freight=" + freight +
                ", returnDate=" + returnDate +
                ", content='" + content + '\'' +
                '}';
    }

    public Integer getReturnId() {
        return returnId;
    }

    public void setReturnId(Integer returnId) {
        this.returnId = returnId;
    }

    public Integer getSupportMoney() {
        return supportMoney;
    }

    public void setSupportMoney(Integer supportMoney) {
        this.supportMoney = supportMoney;
    }

    public Integer getSignalPurchase() {
        return signalPurchase;
    }

    public void setSignalPurchase(Integer signalPurchase) {
        this.signalPurchase = signalPurchase;
    }

    public Integer getPurchase() {
        return purchase;
    }

    public void setPurchase(Integer purchase) {
        this.purchase = purchase;
    }

    public Integer getSupperCount() {
        return supperCount;
    }

    public void setSupperCount(Integer supperCount) {
        this.supperCount = supperCount;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public Integer getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Integer returnDate) {
        this.returnDate = returnDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
