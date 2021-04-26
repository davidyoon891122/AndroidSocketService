package com.example.library;

public class LoginCheck {
    private static final String tmpID = "davidyoon";
    private static final String tmpPW = "3302";

    private String id;
    private String pw;

    public LoginCheck(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }


    public boolean checkInfo() {
        if(this.id.equals(tmpID)){
            if(this.pw.equals(tmpPW)){
                return true;
            }else {
                return false;
            }
        }else{
            return false;
        }
    }

}
