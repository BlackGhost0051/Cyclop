package com.blackghost.cyclop;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.blackghost.cyclop.Class.Info;
import com.blackghost.cyclop.Managers.MailManager;

public class MainActivity extends AppCompatActivity {

    private MailManager mailManager;
    private Info info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = new Info();
        mailManager = new MailManager(info.getEmail(), info.getPassword());
        mailManager.fetchEmails(5);
    }
}