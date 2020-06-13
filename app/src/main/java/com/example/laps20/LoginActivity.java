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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText emailId, password;
    Button bttnlogin;
    TextView tv;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv=findViewById(R.id.logintext);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });
        mFirebaseAuth=FirebaseAuth.getInstance();
        emailId=findViewById(R.id.loginemail);
        password=findViewById(R.id.loginpassword);
        bttnlogin=findViewById(R.id.loginbutton);
        mAuthStateListener= new FirebaseAuth.AuthStateListener(){

            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser mFirebaseUser=mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser!=null)
                {
                    Toast.makeText(LoginActivity.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(LoginActivity.this,MapActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };
        bttnlogin.setOnClickListener(new View.OnClickListener() {
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
            emailId.setError("enter the email");
            password.setError("Enter the Password");
        }
        else if (!(email.isEmpty() && pass.isEmpty()))
        {
            mFirebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"Check this for login error",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Intent tohome=new Intent(LoginActivity.this,MapActivity.class);
                        startActivity(tohome);
                    }
                }
            });

        }
        else
        {
            Toast.makeText(LoginActivity.this,"check for this message",Toast.LENGTH_SHORT).show();
        }
    }

});

        }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}

