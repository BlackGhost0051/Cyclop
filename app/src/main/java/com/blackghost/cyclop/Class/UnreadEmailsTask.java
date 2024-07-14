package com.blackghost.cyclop.Class;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

public class UnreadEmailsTask extends AsyncTask<Void, Void, List<Message>> {
    private static final String TAG = "UnreadEmailsTask";
    private String username;
    private String password;

    public UnreadEmailsTask(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected List<Message> doInBackground(Void... voids) {
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

        } catch (AuthenticationFailedException e) {
            Log.e(TAG, "Authentication failed. Check username/password.", e);
        } catch (NoSuchProviderException e) {
            Log.e(TAG, "No provider for IMAP.", e);
        } catch (MessagingException e) {
            Log.e(TAG, "Error connecting to email server.", e);
        }

        return unreadMessages;
    }

    @Override
    protected void onPostExecute(List<Message> unreadMessages) {
        for (Message message : unreadMessages) {
            try {
                String from = ((javax.mail.internet.InternetAddress) message.getFrom()[0]).getAddress();
                String subject = message.getSubject();
                Log.d("Messages", "From: " + from + ", Subject: " + subject);
            } catch (MessagingException e) {
                Log.e("Messages", "Error reading message details.", e);
            }
        }
    }
}
