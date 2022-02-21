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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.models.User;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private TextView logIn;
    private TextView btnRegister;
    private EditText editTextFullName, editTextMail, editTextPassword;
    private ProgressBar progressBar;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        mAuth = FirebaseAuth.getInstance();

        logIn = (TextView) findViewById(R.id.tornaALogin);
        logIn.setOnClickListener(this);
     //   btnRegister = (TextView) findViewById(R.id.btnRegister);
      //  btnRegister.setOnClickListener(this);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.editTextFullName);
        editTextMail = (EditText) findViewById(R.id.editTextMail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBarRegister);



        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                registerUser();
                break;
            case R.id.tornaALogin:
                startActivity(new Intent(this, LoginUser.class));
                break;
        }
    }

    private void registerUser() {
        String mail = editTextMail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();

        if(fullName.isEmpty()){
            editTextPassword.setError("Username richiesto");
            editTextFullName.requestFocus();
            return;
        }

        if(mail.isEmpty()){
            editTextMail.setError("Email richiesta");
            editTextMail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            editTextMail.setError("Inserisci un email valida");
            editTextMail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password richiesta");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Password richiesta minimo 6 caratteri");
            editTextPassword.requestFocus();
            return;
        }

        //progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                    if (task.isSuccessful()) {
                        int numSf = 0;
                        int ore = 0;
                        String imId = "pp0";
                        User user = new User(fullName, mail, numSf, ore, imId);

                        FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    FirebaseUser userF = FirebaseAuth.getInstance().getCurrentUser();

                                    userF.sendEmailVerification();

                                    Toast.makeText(RegisterUser.this, "Utente registrato con successo!", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                    FirebaseAuth.getInstance().signOut();
                                    startActivity(new Intent(RegisterUser.this, LoginUser.class));

                                } else {
                                    Toast.makeText(RegisterUser.this, "Registrazione fallita, riprova!", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }

                            }
                        });
                    } else {
                        Toast.makeText(RegisterUser.this, "Utente già registrato!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }

        });



    }
}


























