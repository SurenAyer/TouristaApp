package com.example.touristaapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.touristaapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText editTextFirstName, editTextLastName, editTextEmail, editTextPassword;
    private Button buttonRegister;
    private TextView signInLink;
    private FirebaseAuth mAuth;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "RegisterActivityTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        editTextFirstName = findViewById(R.id.firstName);
        editTextLastName = findViewById(R.id.lastName);
        editTextEmail = findViewById (R.id.email);
        editTextPassword = findViewById (R.id.password);
        buttonRegister = findViewById (R.id.signupBtn);
        signInLink = findViewById(R.id.signIn);

        signInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Objects.requireNonNull(editTextEmail.getText()).toString();
                String password = Objects.requireNonNull(editTextPassword.getText()).toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser current_user = mAuth.getCurrentUser();
                                    Toast.makeText(RegisterActivity.this, "Account created", Toast.LENGTH_SHORT).show();

                                    String user_id = Objects.requireNonNull(current_user.getUid());
                                    String first_name = Objects.requireNonNull(editTextFirstName.getText().toString());
                                    String last_name = Objects.requireNonNull(editTextLastName.getText().toString());
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("first_name", first_name);
                                    user.put("last_name", last_name);
                                    user.put("email", email);

                                    db.collection("users").document(user_id)
                                            .set(user)
                                            .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + user_id));
                                    SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("isLoggedIn", true);
                                    editor.apply();
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterActivity.this, "Registration Failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}