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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private TextView btnRegister;
    private EditText editTextFullName, editTextAge, editTextMail, editTextPassword;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

     //   btnRegister = (TextView) findViewById(R.id.btnRegister);
      //  btnRegister.setOnClickListener(this);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.editTextFullName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextMail = (EditText) findViewById(R.id.editTextMail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBarRegister);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String mail = editTextMail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();

        if(fullName.isEmpty()){
            editTextPassword.setError("Full name is required");
            editTextFullName.requestFocus();
            return;
        }

        if(mail.isEmpty()){
            editTextMail.setError("Email is required");
            editTextMail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            editTextMail.setError("Please provide valid email");
            editTextMail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Min password length should be 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    User user = new User(fullName, age, mail);

                    FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                FirebaseUser userF = FirebaseAuth.getInstance().getCurrentUser();
                                userF.sendEmailVerification();

                                Toast.makeText(RegisterUser.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);

                                startActivity(new Intent(RegisterUser.this, LoginUser.class));
                            }
                            else
                            {
                                Toast.makeText(RegisterUser.this, "Failed to register! Try Again!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
                }
                else{
                    Toast.makeText(RegisterUser.this, "Failed to register! Try Again!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });



    }
}


























