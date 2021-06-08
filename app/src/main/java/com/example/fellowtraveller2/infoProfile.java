package com.example.fellowtraveller2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class infoProfile extends AppCompatActivity {

    public static final String TAG = "TAG";
    private EditText nameField, surnameField, phoneField, emailField;
    private Button submit;
    private ImageView imageField;
     FirebaseAuth fAuth;
     FirebaseFirestore fStore;
    private String userID;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_profile);

        Intent data = getIntent();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        nameField = findViewById(R.id.nameProfile);
        surnameField = findViewById(R.id.surNameProfile);
        phoneField = findViewById(R.id.phoneProfile);
        imageField = findViewById(R.id.imageProfile);
        emailField = findViewById(R.id.emailProfile);
        submit = findViewById(R.id.profileSubmit);

        String email = data.getStringExtra("email");
        String name = nameField.getText().toString();
        String surname = surnameField.getText().toString();
        String phone = phoneField.getText().toString();
        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                boolean status;
                emailField.setText(email);
                phoneField.setText(documentSnapshot.getString("phone"));
                nameField.setText(documentSnapshot.getString("name"));
                surnameField.setText(documentSnapshot.getString("surname"));
                status = documentSnapshot.getBoolean("active");
                if(status==true){
                    Intent intent = new Intent(getApplicationContext(),HomePage.class);
                    intent.putExtra("userID",userID);
                    startActivity(intent);
                    finish();
                }
            }
        });



        imageField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(infoProfile.this,"Profile Image Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameField.getText().toString().isEmpty() || surnameField.getText().toString().isEmpty() ||
                        phoneField.getText().toString().isEmpty() || emailField.getText().toString().isEmpty()) {
                    Toast.makeText(infoProfile.this, "Some field is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = emailField.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fStore.collection("users").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("active",true);
                        edited.put("email",email);
                        edited.put("name", nameField.getText().toString());
                        edited.put("surname", surnameField.getText().toString());
                        edited.put("phone",phoneField.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(infoProfile.this, "profile Updated",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),HomePage.class));
                                finish();
                            }
                        });
                        Toast.makeText(infoProfile.this,"Email is changed",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(infoProfile.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

}