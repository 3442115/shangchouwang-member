package com.atguigu.crowd.po;

public class ReturnPO {
    private Integer id;

    private Integer projectid;

    private Integer type;

    private Integer supportmoney;

    private String content;

    private Integer count;

    private Integer signalpurchase;

    private Integer purchase;

    private Integer freight;

    private Integer invoice;

    private Integer returndate;

    private String describPicPath;

    public ReturnPO(Integer id, Integer projectid, Integer type, Integer supportmoney, String content, Integer count, Integer signalpurchase, Integer purchase, Integer freight, Integer invoice, Integer returndate, String describPicPath) {
        this.id = id;
        this.projectid = projectid;
        this.type = type;
        this.supportmoney = supportmoney;
        this.content = content;
        this.count = count;
        this.signalpurchase = signalpurchase;
        this.purchase = purchase;
        this.freight = freight;
        this.invoice = invoice;
        this.returndate = returndate;
        this.describPicPath = describPicPath;
    }

    public Integer getInvoice() {
        return invoice;
    }

    public void setInvoice(Integer invoice) {
        this.invoice = invoice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSupportmoney() {
        return supportmoney;
    }

    public void setSupportmoney(Integer supportmoney) {
        this.supportmoney = supportmoney;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSignalpurchase() {
        return signalpurchase;
    }

    public void setSignalpurchase(Integer signalpurchase) {
        this.signalpurchase = signalpurchase;
    }

    public Integer getPurchase() {
        return purchase;
    }

    public void setPurchase(Integer purchase) {
        this.purchase = purchase;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public Integer getReturndate() {
        return returndate;
    }

    public void setReturndate(Integer returndate) {
        this.returndate = returndate;
    }

    public String getDescribPicPath() {
        return describPicPath;
    }

    public void setDescribPicPath(String describPicPath) {
        this.describPicPath = describPicPath == null ? null : describPicPath.trim();
    }
}