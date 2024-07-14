package com.blackghost.cyclop.Managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

import android.util.Log;

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
    public void fetchEmails(){}

    public void getUnreadEmails(){

    }
}
