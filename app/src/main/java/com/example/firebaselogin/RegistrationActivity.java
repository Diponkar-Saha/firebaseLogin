package com.example.firebaselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    private EditText bnameET,bemailET,passwordET,ageET;
    private TextView loginTV;
    private Button signupBT;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private ProgressBar progressbar;
    String name,password,age,email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        bnameET=findViewById(R.id.namexET);
        ageET=findViewById(R.id.agexET);
        bemailET=findViewById(R.id.emailxET);
        passwordET=findViewById(R.id.passwordET);
        signupBT=findViewById(R.id.signupBT);
        loginTV=findViewById(R.id.loginTV);
        mAuth = FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        progressbar=findViewById(R.id.progressbar);
        progressbar.setVisibility(View.INVISIBLE);



        signupBT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(validate()) {
                    String email= bemailET.getText().toString().trim();
                    String password=passwordET.getText().toString().trim();
                    progressbar.setVisibility(View.VISIBLE);

                    mAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {

                                        Toast.makeText(RegistrationActivity.this, "ERROR",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        progressbar.setVisibility(View.INVISIBLE);
                                        sendUserdata();
                                        Toast.makeText(RegistrationActivity.this, "Registration Success",Toast.LENGTH_LONG).show();

                                        startActivity(new Intent(RegistrationActivity.this,ProfileActivity.class));
                                        finish();
                                    }
                                }
                            });

                }
            }
        });




        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                finish();

            }
        });



    }
    private Boolean validate(){
       Boolean xresult=false;
       name= bnameET.getText().toString();
       email= bemailET.getText().toString();
       password=passwordET.getText().toString();
       age=ageET.getText().toString();

       if(name.isEmpty()||password.isEmpty()|| email.isEmpty()){
           Toast.makeText(this,"please enter all the details",Toast.LENGTH_SHORT).show();
       }else{
           xresult=true;
       }
       return xresult;
    }
    private void sendUserdata(){
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(mAuth.getUid());
        UserProfile userProfile=new UserProfile(age,name,email);
        myRef.setValue(userProfile);

    }
}