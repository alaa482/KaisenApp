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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.IOException;


public class LoginUser extends AppCompatActivity implements View.OnClickListener{

    private TextView register, forgotPassword;
    private EditText editTextLoginEmail, editTestLoginPassword;
    private Button singIn;
    private FirebaseAuth mAuth;
    private ProgressBar progressBarLogin;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private Button verify;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        createRequest();
        findViewById(R.id.bottone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }



        });






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

    private void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }




    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Toast.makeText(LoginUser.this, "Something get wrong!", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginUser.this, "Qualcosa è andato storto!", Toast.LENGTH_LONG).show();

                        }
                    }
                });
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

            editTextLoginEmail.setError("Email richiesta");
            editTextLoginEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            editTextLoginEmail.setError("Inserisci un email valida");
            editTextLoginEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){

            editTestLoginPassword.setError("Password richiesta");
            editTestLoginPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTestLoginPassword.setError("Password richiesta minimo 6 caratteri");
            editTestLoginPassword.requestFocus();
            return;
        }

        //progressBarLogin.setVisibility(View.VISIBLE);

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
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(LoginUser.this, "Veirifica il tuo account!", Toast.LENGTH_LONG).show();
                    }


                }
                else
                {
                   Toast.makeText(LoginUser.this, "Log in fallito, controlla le credenziali!", Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}
















