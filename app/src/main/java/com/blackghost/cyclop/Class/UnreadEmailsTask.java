package com.blackghost.cyclop.Class;

import android.os.AsyncTask;

import java.util.List;

import javax.mail.Message;

public class UnreadEmailsTask extends AsyncTask<Void, Void, List<Message>> {
    private String username;
    private String password;

    @Override
    protected List<Message> doInBackground(Void... voids) {
        return null;
    }
}
