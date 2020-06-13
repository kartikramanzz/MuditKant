package com.example.laps20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText emailId, password;
    Button bttnsignup;
    TextView tv;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.signuptext);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
        mFirebaseAuth=FirebaseAuth.getInstance();
        emailId=findViewById(R.id.signupemail);
        password=findViewById(R.id.signuppassword);
        bttnsignup=findViewById(R.id.signupbutton);

        bttnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emailId.getText().toString();
                String pass=password.getText().toString();
                if(email.isEmpty())
                {
                    emailId.setError("Provide Email");
                    emailId.requestFocus();
                }
                else if(pass.isEmpty())
                {
                    password.setError("Enter the password");
                    password.requestFocus();
                }
                else if (email.isEmpty() && pass.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Enter the ID  and Password", Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && pass.isEmpty()))
                {
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this, "UNSUCCESSFUL",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                startActivity(new Intent(MainActivity.this,MapActivity.class));
                            }
                        }
                    });

                }
                else
                {
                    Toast.makeText(MainActivity.this,"check for this message",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
