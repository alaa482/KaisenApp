package it.unimib.kaisenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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


public class LoginUser extends AppCompatActivity implements View.OnClickListener{

    private TextView register, forgotPassword;
    private EditText editTextLoginEmail, editTestLoginPassword;
    private Button singIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBarLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        register = (TextView) findViewById(R.id.textViewLoginRegistrati);
        register.setOnClickListener(this);

        singIn = (Button) findViewById(R.id.btnLogin);
        singIn.setOnClickListener(this);

        editTextLoginEmail = (EditText) findViewById(R.id.editTextLoginMail);
        editTestLoginPassword = (EditText) findViewById(R.id.editTextLoginPassword);

        progressBarLogin = (ProgressBar) findViewById(R.id.progressBarLogin);

        mAuth = FirebaseAuth.getInstance();

        forgotPassword = (TextView) findViewById(R.id.textViewLoginPassDimenticata);
        forgotPassword.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewLoginRegistrati:
                startActivity(new Intent(this, RegisterUser.class));
                break;

            case R.id.btnLogin:
                userLogin();
                break;

            case  R.id.textViewLoginPassDimenticata :
                startActivity(new Intent(this,ForgotPassword.class));
                break;

        }
    }

    private void userLogin() {

        String mail = editTextLoginEmail.getText().toString().trim();
        String password = editTestLoginPassword.getText().toString().trim();

        if(mail.isEmpty()){

            editTextLoginEmail.setError("Email is required");
            editTextLoginEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            editTextLoginEmail.setError("Please enter a valid mail");
            editTextLoginEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){

            editTestLoginPassword.setError("Password is required");
            editTestLoginPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTestLoginPassword.setError("Min pass length is 6 characters");
            editTestLoginPassword.requestFocus();
            return;
        }

        progressBarLogin.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()){
                        //redirect to user profile
                        startActivity(new Intent(LoginUser.this, ProfileActivity.class));
                    }
                    else
                    {
                        user.sendEmailVerification();
                        Toast.makeText(LoginUser.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                    }


                }
                else
                {
                   Toast.makeText(LoginUser.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}
















