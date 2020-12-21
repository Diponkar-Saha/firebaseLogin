package com.example.firebaselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainProfileActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView nameTV,emailTV,ageTV;
    private Button signBT;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile);

        nameTV=findViewById(R.id.nameTV);
        ageTV=findViewById(R.id.ageTV);
        emailTV=findViewById(R.id.emailTV);
        imageView=findViewById(R.id.imageIv);
        signBT=findViewById(R.id.signBT);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();




        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               // UserProfile userProfile= snapshot.getValue(UserProfile.class);
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                nameTV.setText("Name: " + userProfile.getUserName());
                ageTV.setText("Age: " + userProfile.getUserAge());
                emailTV.setText("Email: " + userProfile.getUserEmail());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainProfileActivity.this,error.getCode(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}