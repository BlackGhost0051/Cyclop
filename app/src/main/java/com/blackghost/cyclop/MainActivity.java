package com.blackghost.cyclop;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.blackghost.cyclop.Class.Info;
import com.blackghost.cyclop.Managers.MailManager;

import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

public class MainActivity extends AppCompatActivity {
    private EditText inputMail;
    private EditText inputPass;
    private Button saveBtn;
    private MailManager mailManager;
    private Info info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info = new Info();

        inputMail = findViewById(R.id.inputMail);
        inputPass = findViewById(R.id.inputPass);
        saveBtn = findViewById(R.id.saveBtn);


        SharedPreferences sharedPreferences = getSharedPreferences("saveInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String savedEmail = sharedPreferences.getString("email", "");
        String savedPassword = sharedPreferences.getString("password", "");

        if(!savedEmail.isEmpty() && !savedPassword.isEmpty()){
            info.setEmail(savedEmail);
            info.setPassword(savedPassword);

            inputMail.setText(savedEmail);
            inputPass.setText(savedPassword);
        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(inputMail.getText());
                String password = String.valueOf(inputPass.getText());

                editor.putString("email", email);
                editor.putString("password", password);
                editor.apply();

                info.setEmail(email);
                info.setPassword(password);


                /*mailManager = new MailManager(info.getEmail(), info.getPassword());
                List<Message> unreadMessages = mailManager.getUnreadEmails();

                for(Message message : unreadMessages){
                    try {
                        String from = ((InternetAddress) message.getFrom()[0]).getAddress();
                        String subject = message.getSubject();
                        Log.d("Messages", "From: " + from + ", Subject: " + subject);
                    } catch (MessagingException e) {
                        Log.e("Messages", "Error reading message details.", e);
                    }
                }*/
            }
        });

    }
}