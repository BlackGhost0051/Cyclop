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

    public List<Message> getUnreadEmails(){
        List<Message> unreadMessages = new ArrayList<>();

        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");

        Session emailSession = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Store store = emailSession.getStore("imaps");
            store.connect("imap.gmail.com", username, password);

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.getMessages();
            for (Message message : messages) {
                if (!message.isSet(Flags.Flag.SEEN)) {
                    unreadMessages.add(message);
                }
            }

            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            Log.e("MailManager", "No provider for imap.", e);
        } catch (MessagingException e) {
            Log.e("MailManager", "Error connecting to email server.", e);
        }

        return unreadMessages;
    }
}
