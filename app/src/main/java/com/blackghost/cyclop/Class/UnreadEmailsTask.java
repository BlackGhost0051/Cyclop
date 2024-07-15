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

public class UnreadEmailsTask extends AsyncTask<Void, Void, List<String[]>> {
    private static final String TAG = "UnreadEmailsTask";
    private String username;
    private String password;

    public UnreadEmailsTask(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected List<String[]> doInBackground(Void... voids) {
        List<String[]> unreadMessages = new ArrayList<>();
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", "imap.gmail.com");
        properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.ssl.enable", "true");

        Session emailSession = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Folder emailFolder = null;
        Store store = null;

        try {
            store = emailSession.getStore("imaps");
            store.connect("imap.gmail.com", username, password);

            emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.getMessages();
            for (Message message : messages) {
                if (!message.isSet(Flags.Flag.SEEN)) {
                    String from = ((javax.mail.internet.InternetAddress) message.getFrom()[0]).getAddress();
                    String subject = message.getSubject();
                    if(subject.equals("CYC")){
                        unreadMessages.add(new String[]{from, subject});
                    }

                }
            }

        } catch (AuthenticationFailedException e) {
            Log.e(TAG, "Authentication failed. Check " + username + "/" + password, e);
        } catch (NoSuchProviderException e) {
            Log.e(TAG, "No provider for IMAP.", e);
        } catch (MessagingException e) {
            Log.e(TAG, "Error connecting to email server.", e);
        } finally {
            try {
                if (emailFolder != null && emailFolder.isOpen()) {
                    emailFolder.close(false);
                }
                if (store != null) {
                    store.close();
                }
            } catch (MessagingException e) {
                Log.e(TAG, "Error closing folder/store.", e);
            }
        }

        return unreadMessages;
    }

    @Override
    protected void onPostExecute(List<String[]> unreadMessages) {
        for (String[] email : unreadMessages) {
            Log.d("Messages", "From: " + email[0] + ", Subject: " + email[1]);
        }
    }
}
