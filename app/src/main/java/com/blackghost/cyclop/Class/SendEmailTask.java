package com.blackghost.cyclop.Class;

public class SendEmailTask {
    private boolean withFile;
    private String username;
    private String password;

    public SendEmailTask(String username, String password, boolean withFile){
        this.username = username;
        this.password = password;
        this.withFile = withFile;
    }
}
