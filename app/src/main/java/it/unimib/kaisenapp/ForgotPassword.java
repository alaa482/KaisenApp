package it.unimib.kaisenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class ForgotPassword extends AppCompatActivity {

    private EditText editTextRecoveryMail;
    private Button resetPasswordButton;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextRecoveryMail = (EditText) findViewById(R.id.editTextRecoveryMail);
        resetPasswordButton = (Button) findViewById(R.id.btnRecovery);
        progressBar = (ProgressBar) findViewById(R.id.progressBarRecovery);

        auth = FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }
    private void resetPassword(){
        String email =editTextRecoveryMail.getText().toString().trim();

        if (email.isEmpty()){
            editTextRecoveryMail.setError("Email is required");
            editTextRecoveryMail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextRecoveryMail.setError("Please Provide a valid email");
            editTextRecoveryMail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this,"Check your email for reset your password!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ForgotPassword.this, LoginUser.class));
                }else{
                    Toast.makeText(ForgotPassword.this,"Try again, something wrong happened!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}