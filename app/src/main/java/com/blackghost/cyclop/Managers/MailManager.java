package com.blackghost.cyclop.Managers;

import java.util.Properties;

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
    public void fetchEmails(int numberOfEmails){
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");

        try {
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();
            int end = messages.length;
            int start = Math.max(0, end - numberOfEmails);

            for (int i = start; i < end; i++) {
                Message message = messages[i];
                Log.d("Email", message.getSubject() + "\n" +
                        InternetAddress.toString(message.getFrom()) + "\n" +
                        message.getContent().toString());
            }

            inbox.close(false);
            store.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
