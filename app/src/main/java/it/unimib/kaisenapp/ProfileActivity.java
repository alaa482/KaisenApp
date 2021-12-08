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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;  //id di firebase legato all'utente


    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        btnLogout = (Button) findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, LoginUser.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        userID = user.getUid();

        final TextView greetingTextView = (TextView) findViewById(R.id.txtBenvenuto);
        final TextView txtFullName = (TextView) findViewById(R.id.txtFullNameActivity);
        final TextView txtMail = (TextView) findViewById(R.id.txtMailActivity);
        final TextView txtAge = (TextView) findViewById(R.id.txtAgeActivity);

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
}