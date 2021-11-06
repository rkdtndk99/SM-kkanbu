package com.example.smswhapplication;

public class UserAccount {
    private String idToken;    //firebase uid
    private String name;
    private String birthday;
    private String major;
    private String stuNum;
    private String email;
    private String pw;
    private String profileUri;
    private String kkanbu ;
    private Integer age;
    private Integer interest;

    public String getKkanbu() { return kkanbu; }

    public void setKkanbu(String kkanbu) { this.kkanbu = kkanbu; }


    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getProfileUri() {
        return profileUri;
    }

    public void setProfileUri(String profileUri) {
        this.profileUri = profileUri;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getInterest() {
        return interest;
    }

    public void setInterest(Integer interest) {
        this.interest = interest;
    }
}
