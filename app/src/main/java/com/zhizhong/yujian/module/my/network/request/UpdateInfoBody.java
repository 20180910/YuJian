package com.zhizhong.yujian.module.my.network.request;

public class UpdateInfoBody {
    /**
     * avatar : sample string 1
     * nickname : sample string 2
     * birthday : sample string 3
     * sex : sample string 4
     */

    private String avatar;
    private String nickname;
    private String birthday;
    private String sex;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
