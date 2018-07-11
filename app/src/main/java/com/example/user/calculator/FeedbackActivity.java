package com.example.user.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.calculator.R;

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

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                message="My email: "+email_text+"\n"+"Message:\n"+message;
                subject="The application calculator:"+subject;
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"kirill_belitsky@mail.ru"});
                intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                intent.putExtra(Intent.EXTRA_TEXT,message);

                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent,"Choose the email client"));
            }
        });
    }



}
