package it.unimib.kaisenapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.models.User;

public class ProfileActivity extends AppCompatActivity {

    //private ImageButton backButton;
    private ImageButton modifyImage;
    private ImageView profileImage;
    private FirebaseUser user;
    private DatabaseReference reference;
    private DatabaseReference slot;
    private String userID;  //id di firebase legato all'utente
    private GoogleSignInClient mGoogleSignInClient;
    private BottomNavigationView bottomNavigationView;


    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        createRequest();
        btnLogout = (Button) findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                signOut();
                startActivity(new Intent(ProfileActivity.this, LoginUser.class));
                finish();
            }




        });




        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        /*backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }




        });*/
        modifyImage = (ImageButton) findViewById(R.id.modifyImage);
        modifyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, listProfilePic.class));
                finish();
            }




        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        userID = user.getUid();





       // final TextView greetingTextView = (TextView) findViewById(R.id.txtBenvenuto);
        final TextView txtFullName = (TextView) findViewById(R.id.txtFullNameActivity);
        final TextView txtMail = (TextView) findViewById(R.id.txtMailActivity);
        final ImageView imageProfile = (ImageView) findViewById(R.id.imageProfile);
        final TextView txtNumSf = (TextView) findViewById(R.id.textView4);
        final TextView txtOre = (TextView) findViewById(R.id.textView6);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            //greetingTextView.setText("Welcome, " + signInAccount.getDisplayName() + "!");
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
                    int numSf =  userProfile.numSf;
                    int ore = userProfile.ore;
                    String iid = userProfile.imId;



                    //greetingTextView.setText("Welcome, " + fullName + "!");
                    txtFullName.setText(fullName);
                    txtMail.setText(email);
                    String app = String.valueOf(numSf);
                    txtNumSf.setText(app);
                    app = String.valueOf(ore);
                    txtOre.setText(app);

                    //String iid = "pp1";
                    int path = getResources().getIdentifier("it.unimib.kaisenapp:drawable/" + iid, null, null);
                    Log.d("msg", String.valueOf(path));
                    imageProfile.setImageResource(path);
                   

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


}