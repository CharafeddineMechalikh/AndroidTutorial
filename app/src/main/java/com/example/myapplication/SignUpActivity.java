package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
public class SignUpActivity extends HttpActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText ageEditText;
    private EditText addressEditText;
    private EditText familyEditText;
    private EditText firstEditText;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        PAGE= "signup.php";
        familyEditText = findViewById(R.id.family_name);
        firstEditText = findViewById(R.id.first_name);;
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        ageEditText = findViewById(R.id.age);
        addressEditText = findViewById(R.id.address);
        signUpButton = findViewById(R.id.signup_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String family_name = familyEditText.getText().toString();
                String first_name = firstEditText.getText().toString();
                String age = ageEditText.getText().toString();
                String address = addressEditText.getText().toString();
                Map<String, String> params = new HashMap<>();
                params.put("email",email);
                params.put("family_name", family_name);
                params.put("first_name", first_name);
                params.put("password", password);
                params.put("age", age);
                params.put("address", address);
                send(params);
            }
        });
    }

    @Override
    protected void responseRecieved(String response, Map<String, String> params) {
        if(response.trim().equals("success")){
            // Save user information to shared preferences
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SignUpActivity.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("family_name", params.get("family_name"));
            editor.putString("first_name", params.get("first_name"));
            editor.putString("email", params.get("email"));
            editor.putInt("age", Integer.parseInt(params.get("age")));
            editor.putString("address", params.get("address"));
            editor.apply();
            // Start HomeActivity
            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(SignUpActivity.this, "Sign Up Failed: "+response, Toast.LENGTH_SHORT).show();
        }
    }



}
