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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

public class LoginActivity extends BaseActivity {

    TextInputEditText editTextEmail, editTextPassword;
    private Button signInBtn;
    private TextView signUp;

    FirebaseAuth mAuth;
    private static final String TAG = "LoginActivityTAG";
    private UserRepository userRepository;
    private Gson gson;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        signInBtn = findViewById(R.id.signinBtn);
        signUp = findViewById(R.id.signUpLink);
        userRepository = new UserRepositoryImpl();
        gson = new Gson();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing in...");
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFinishing()) {
                    progressDialog.show();
                }
                Log.d(TAG, "Sign In Button Clicked");
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                performSignIn(email, password);
            }
        });
    }
    public void performSignIn(String email,String password) {


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "Sign In Complete");
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Sign In Successful");
                            // Store the login state
                            //DB Call get User Object
                            userRepository.getUserByEmail(email, task1 -> {
                                Log.d(TAG, "User Retrieved ok");
                                if (task1.isSuccessful()) {
                                    Log.d(TAG, "User Retrieved");
                                    QuerySnapshot document = task1.getResult();
                                    User user = new User();
                                    if (!document.getDocuments().isEmpty()) {
                                        user = document.getDocuments().get(0).toObject(User.class);
                                    }
                                    String userJson = gson.toJson(user);
                                    SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("isLoggedIn", true);
                                    editor.putString("user", userJson);
                                    editor.apply();
                                    Log.d(TAG, "User Details Stored");
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Log.d(TAG, "User Not Found");
                                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            Log.d(TAG, "Sign In Failed");
                            Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}