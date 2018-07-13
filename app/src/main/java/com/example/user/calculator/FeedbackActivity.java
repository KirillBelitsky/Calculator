package com.example.user.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import javax.mail.MessagingException;

import com.example.user.calculator.R;
import com.example.user.calculator.utils.GMailSender;

import static com.example.user.calculator.constants.Constants.SMTP_login;
import static com.example.user.calculator.constants.Constants.SMTP_password;

public class FeedbackActivity extends AppCompatActivity {

    private EditText emailText;
    private EditText subjectText;
    private EditText messageText;
    private Button sendButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        emailText = findViewById(R.id.email_text);
        subjectText=findViewById(R.id.subject_text);
        messageText=findViewById(R.id.message_text);
        sendButton=findViewById(R.id.send_button);
        sendButton.setOnClickListener(v -> click());
    }

    public void onBackPressed() {
        finish();
    }

    public void click(){

        String email_text=emailText.getText().toString();
        String subject=subjectText.getText().toString();
        String message=messageText.getText().toString();

        if(subject.equals("") && message.equals("")){
            Toast.makeText(getApplicationContext(),"Enter the message and subject!",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(subject.equals("") || message.equals("")){
            if(subject.equals("")){
                Toast.makeText(getApplicationContext(),"Enter the subject!",Toast.LENGTH_SHORT).show();
                return;
            }
            else{
                Toast.makeText(getApplicationContext(),"Enter the message!",Toast.LENGTH_SHORT).show();
                return;
            }
        }

        sendButton.setEnabled(false);
        new sendEmail().execute();
    }

    private class sendEmail extends AsyncTask<Void, Void, Boolean> {

        protected Boolean doInBackground(Void... voids) {
            GMailSender sender = new GMailSender(SMTP_login,SMTP_password);

            String message = "Subject: " +subjectText.getText().toString()
                    + "\nEmail: " + emailText.getText().toString()
                    + "\nMessage: " + messageText.getText().toString();

            try {
                sender.sendMail("Feedback", message);
            } catch (MessagingException e) {
                return false;
            }
            return true;
        }

        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Toast.makeText(FeedbackActivity.this,"Thank you!",
                        Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(FeedbackActivity.this, "Error!",
                        Toast.LENGTH_SHORT).show();

            }
            sendButton.setEnabled(true);
        }
    }
}
