package com.zhizhong.yujian.network.response;

import com.library.base.BaseObj;

/**
 * Created by Administrator on 2018/4/27.
 */

public class HuZhaoObj extends BaseObj {
    /**
     * authority : 公安部出入境管理局*
     * birth_date : 19861030
     * birth_day : 861030
     * birth_place : 广西
     * country : CHN
     * expiry_date : 20230501
     * expiry_day : 230501
     * issue_date : 20130502
     * issue_place : 广西
     * line0 : P0CHNWANG<<JING<<<<<<<<<<<<<<<<<<<<<<<<<<
     * line1 : E203545580CHN8610304M2305019MNPELOLIOKLPA938
     * name : WANG.JING
     * name_cn : 汪婧
     * passport_no : E20354xxxx
     * person_id : MNPELOLIOKLPA9
     * request_id : 20171120113612_813974f02a16b81ab911292d181b0b42
     * sex : M
     * src_country : CHN
     * success : true
     * type : P0
     */

    private String authority;
    private String birth_date;
    private String birth_day;
    private String birth_place;
    private String country;
    private String expiry_date;
    private String expiry_day;
    private String issue_date;
    private String issue_place;
    private String line0;
    private String line1;
    private String name;
    private String name_cn;
    private String passport_no;
    private String person_id;
    private String request_id;
    private String sex;
    private String src_country;
    private boolean success;
    private String type;

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(String birth_day) {
        this.birth_day = birth_day;
    }

    public String getBirth_place() {
        return birth_place;
    }

    public void setBirth_place(String birth_place) {
        this.birth_place = birth_place;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getExpiry_day() {
        return expiry_day;
    }

    public void setExpiry_day(String expiry_day) {
        this.expiry_day = expiry_day;
    }

    public String getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(String issue_date) {
        this.issue_date = issue_date;
    }

    public String getIssue_place() {
        return issue_place;
    }

    public void setIssue_place(String issue_place) {
        this.issue_place = issue_place;
    }

    public String getLine0() {
        return line0;
    }

    public void setLine0(String line0) {
        this.line0 = line0;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_cn() {
        return name_cn;
    }

    public void setName_cn(String name_cn) {
        this.name_cn = name_cn;
    }

    public String getPassport_no() {
        return passport_no;
    }

    public void setPassport_no(String passport_no) {
        this.passport_no = passport_no;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSrc_country() {
        return src_country;
    }

    public void setSrc_country(String src_country) {
        this.src_country = src_country;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
