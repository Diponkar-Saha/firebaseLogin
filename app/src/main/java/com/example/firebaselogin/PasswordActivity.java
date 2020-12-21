package com.example.firebaselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {
    private EditText passEmailET;
    private Button buttonBT;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        passEmailET=findViewById(R.id.passwordemailET);
        buttonBT=findViewById(R.id.forgotpassworsBT);
        firebaseAuth=FirebaseAuth.getInstance();;
        buttonBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail=passEmailET.getText().toString().trim();
                if (TextUtils.isEmpty(useremail)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PasswordActivity.this,"password sent",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(PasswordActivity.this, MainActivity.class));
                                finish();

                            }else{
                                Toast.makeText(PasswordActivity.this,"error",Toast.LENGTH_SHORT).show();


                            }

                        }
                    });
                }

            }
        });



    }
}