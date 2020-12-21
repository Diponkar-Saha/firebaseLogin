package com.example.firebaselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText emailET,passwordET;
    private TextView registrationTV,forgotTV;
    private Button loginBT;

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private ProgressBar progressbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        emailET=findViewById(R.id.emailET);
        passwordET=findViewById(R.id.passwordET);
        loginBT=findViewById(R.id.loginBT);
        registrationTV=findViewById(R.id.registrationTV);
       forgotTV=findViewById(R.id.forgotTV);

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        progressbar=findViewById(R.id.progressbar);
        progressbar.setVisibility(View.INVISIBLE);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // counterTV.setText("No of attempts remaining :5");
        if(currentUser!=null){
            finish();
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));


        }

        forgotTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"here u change ur pass",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,PasswordActivity.class));
                finish();



            }
        });
        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    String email = emailET.getText().toString().trim();
                    String password = passwordET.getText().toString().trim();
                    progressbar.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {

                                        Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                                    } else {
                                        progressbar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_LONG).show();

                                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                                        finish();
                                    }
                                }
                            });
                }

                }
        });
        registrationTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
                finish();
            }
        });
    }
    private Boolean validate(){
        Boolean xresult=false;

        String xemail= emailET.getText().toString();
        String xpassword=passwordET.getText().toString();
        if(xpassword.isEmpty()|| xemail.isEmpty()){
            Toast.makeText(this,"please enter all the details",Toast.LENGTH_SHORT).show();
        }else{
            xresult=true;
        }
        return xresult;
    }







}