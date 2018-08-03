package com.zhizhong.yujian.module.my.network.response;

public class BankAccountObj {
    /**
     * userid : 2
     * id : 2
     * bank_image : http://121.40.186.118:10011/upload/201709/18/201709181455150015.png
     * bank_name : 中国银行
     * bank_card : 尾号4499
     */

    private String userid;
    private String id;
    private String bank_image;
    private String bank_name;
    private String bank_card;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBank_image() {
        return bank_image;
    }

    public void setBank_image(String bank_image) {
        this.bank_image = bank_image;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }
}
