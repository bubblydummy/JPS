package com.example.jps;

public class UserAccount
{
    private String idToken; // Firebase Uid(고유 토큰 정보)
    private String emailId;
    private String password;

    public UserAccount() {
    }//클래스 생성자 //firebase realtime 을 사용시에는 빈생성자를 사용해야한다.



    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
