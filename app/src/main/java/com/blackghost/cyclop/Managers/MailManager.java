package com.blackghost.cyclop.Managers;

public class MailManager {
    private String username;
    private String password;

    public MailManager(String username, String password){
        this.username = username;
        this.password = password;
    }

    public boolean sendEmail(){
        return false;
    }
    public void fetchEmails(){
    }
}
