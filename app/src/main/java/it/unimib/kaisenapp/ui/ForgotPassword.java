package it.unimib.kaisenapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuth;

import it.unimib.kaisenapp.R;

public class ForgotPassword extends AppCompatActivity {

    private EditText editTextRecoveryMail;
    private Button resetPasswordButton;
    private ProgressBar progressBar;

    private TextView tornalog;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        editTextRecoveryMail = (EditText) findViewById(R.id.editTextRecoveryMail);
        resetPasswordButton = (Button) findViewById(R.id.btnRecovery);
        progressBar = (ProgressBar) findViewById(R.id.progressBarRecovery);

        auth = FirebaseAuth.getInstance();

        tornalog = (TextView) findViewById((R.id.ritornaLogin));

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

        tornalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPassword.this, LoginUser.class));

            }
        });
    }

    private void resetPassword(){
        String email =editTextRecoveryMail.getText().toString().trim();

        if (email.isEmpty()){
            editTextRecoveryMail.setError("Email richiesta");
            editTextRecoveryMail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextRecoveryMail.setError("Inserisci un email valida");
            editTextRecoveryMail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this,"Controlla la tua email!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ForgotPassword.this, LoginUser.class));
                }else{
                    Toast.makeText(ForgotPassword.this,"Qualcosa è andato storto!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}