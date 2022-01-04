package it.unimib.kaisenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView register;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;  //id di firebase legato all'utente
    private GoogleSignInClient mGoogleSignInClient;


    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        createRequest();
        btnLogout = (Button) findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                signOut();
                startActivity(new Intent(ProfileActivity.this, LoginUser.class));
            }


        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        userID = user.getUid();

        register = (TextView) findViewById(R.id.prova);
        register.setOnClickListener(this::onClick);

        final TextView greetingTextView = (TextView) findViewById(R.id.txtBenvenuto);
        final TextView txtFullName = (TextView) findViewById(R.id.txtFullNameActivity);
        final TextView txtMail = (TextView) findViewById(R.id.txtMailActivity);
        final TextView txtAge = (TextView) findViewById(R.id.txtAgeActivity);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            greetingTextView.setText("Welcome, " + signInAccount.getDisplayName() + "!");
            txtFullName.setText(signInAccount.getDisplayName());
            txtMail.setText(signInAccount.getEmail());
        }


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){

                    String fullName = userProfile.fullName;
                    String email = userProfile.mail;
                    String age = userProfile.age;

                    greetingTextView.setText("Welcome, " + fullName + "!");
                    txtFullName.setText(fullName);
                    txtMail.setText(email);
                    txtAge.setText(age);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ProfileActivity.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });



    }
    private void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prova:
                startActivity(new Intent(this, RegisterUser.class));
                break;
        }
    }
}