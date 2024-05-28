package com.example.touristaapp.activities;

import android.app.ProgressDialog;
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
import com.example.touristaapp.models.User;
import com.example.touristaapp.repositories.UserRepository;
import com.example.touristaapp.repositories.UserRepositoryImpl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class RegisterActivity extends BaseActivity {

    private TextInputEditText editTextFirstName, editTextLastName, editTextEmail, editTextPhone, editTextPassword;
    private Button buttonRegister;
    private TextView signInLink;
    FirebaseAuth mAuth;

    private static final String TAG = "RegisterActivityTAG";
    private UserRepository userRepository;
    private Gson gson;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        editTextFirstName = findViewById(R.id.firstName);
        editTextLastName = findViewById(R.id.lastName);
        editTextEmail = findViewById(R.id.email);
        editTextPhone = findViewById(R.id.phone);
        editTextPassword = findViewById(R.id.password);
        buttonRegister = findViewById(R.id.signupBtn);
        signInLink = findViewById(R.id.signIn);
        userRepository = new UserRepositoryImpl();
        gson = new Gson();
        signInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up...");
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
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

                performSignUp(email, password);

            }
        });

    }

    public void performSignUp(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser current_user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterActivity.this, "Account created", Toast.LENGTH_SHORT).show();

                            String first_name = Objects.requireNonNull(editTextFirstName.getText().toString());
                            String last_name = Objects.requireNonNull(editTextLastName.getText().toString());
                            long phone_number = Long.parseLong(Objects.requireNonNull(editTextPhone.getText()).toString());
                            User user = new User();
                            user.setUserId(null);
                            user.setFirstName(first_name);
                            user.setLastName(last_name);
                            user.setEmail(email);
                            user.setPhoneNumber(phone_number);

                            userRepository.addUser(user, new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User added to database");
                                        String userJson = gson.toJson(user);
                                        SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean("isLoggedIn", true);
                                        editor.putString("user", userJson);
                                        editor.apply();
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        Log.e(TAG, "Failed to add user to database", task.getException());
                                    }
                                }
                            });
//
//
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Registration Failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}