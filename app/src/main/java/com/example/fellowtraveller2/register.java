package com.example.fellowtraveller2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    public static final String TAG = "TAG";
    public static final String TAG1 = TAG;
    //frontend part
    private TextView linkText;

    //dbsender buttons
    private EditText emailField, passField, confPassField;
    private Button regSubmit;

    //database part
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //declaration of widgets
        emailField = findViewById(R.id.emailField);
        passField = findViewById(R.id.passField);
        confPassField = findViewById(R.id.confPassField);
        regSubmit = findViewById(R.id.regSubmit);
        linkText = findViewById(R.id.loginText);

        //declaration of db
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        //frontend textlink (no connection to db)


        linkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this, login.class));
                finish();
            }
        });

        regSubmit.setOnClickListener((v) -> {
                String email = emailField.getText().toString().trim();
                String password = passField.getText().toString().trim();
                String confirmation = confPassField.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    emailField.setError("Email is required");
                }

                if (TextUtils.isEmpty(password)){
                    passField.setError("Password is required");
                }

                if (TextUtils.isEmpty(confirmation)){
                    confPassField.setError("Confirmation is required");
                }

                if (!password.equals(confirmation)){
                    passField.setError("Mistake in fields");
                    confPassField.setError("Mistake in fields");
                }

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(register.this,"User created",Toast.LENGTH_SHORT).show();
                        userID = mAuth.getCurrentUser().getUid();
                        Intent intent = new Intent(getApplicationContext(),login.class);
                        DocumentReference documentReference = fStore.collection("users").document(userID);
                        Map<String,Object>user = new HashMap<>();
                        user.put("name", "");
                        user.put("surname","");
                        user.put("email",email);
                        user.put("phone","");
                        user.put("active",false);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                    Log.d(TAG1,"onSuccess: user Profile is created for "+userID);
                                    intent.putExtra("userID",userID);
                                startActivity(intent);
                            }
                        });
                    } else{
                        Toast.makeText(register.this,"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        });
    }
}

