package com.example.fellowtraveller2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class login extends AppCompatActivity {
    //frontend part
    private TextView linkText;

    //dbsender buttons
    private EditText emailField, passField;
    private Button logSubmit;

    //database part
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //startActivity(new Intent(getApplicationContext(),HomePage2.class));
        //finish();

        emailField = findViewById(R.id.emailField);
        passField = findViewById(R.id.passField);
        logSubmit = findViewById(R.id.logSubmit);
        linkText = findViewById(R.id.regText);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        linkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, register.class));
                finish();
            }
        });

        logSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    void check(DocumentReference documentReference){
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.getBoolean("active")){
                    startActivity(new Intent(getApplicationContext(),HomePage.class));
                    finish();
                }
            }
        });
    }

    private void loginUser(){
        String email = emailField.getText().toString();
        String password = passField.getText().toString();

        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(!password.isEmpty()){
                mAuth.signInWithEmailAndPassword(email,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(login.this, "Login Successfully!!", Toast.LENGTH_SHORT).show();
                                //DocumentReference documentReference = fStore.collection("users").document(userID);
                               // check(documentReference);
                                Intent i = new Intent(login.this, infoProfile.class);
                                i.putExtra("email", email);
                                startActivity(i);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(login.this, "Login failed!",Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                passField.setError("Empty fields are not allowed!");
            }
        } else if(email.isEmpty()){
            emailField.setError("Empty fields are not allowed!");
        } else{
            emailField.setError("Enter correct email");
        }
    }

}