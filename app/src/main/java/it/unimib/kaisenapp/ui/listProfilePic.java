package it.unimib.kaisenapp.ui;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.fragment.ProfileFragment;


public class listProfilePic extends AppCompatActivity {
    private ImageButton backButton;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_profile_pic);

        backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listProfilePic.this, ProfileFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); finish();
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        String userID = user.getUid();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);



        ImageView pp1 = (ImageView) findViewById(R.id.pp1);
        pp1.setOnClickListener(mCorkyListener);
        ImageView pp2 = (ImageView) findViewById(R.id.pp2);
        pp2.setOnClickListener(mCorkyListener);
        ImageView pp3 = (ImageView) findViewById(R.id.pp3);
        pp3.setOnClickListener(mCorkyListener);
        ImageView pp4 = (ImageView) findViewById(R.id.pp4);
        pp4.setOnClickListener(mCorkyListener);
        ImageView pp5 = (ImageView) findViewById(R.id.pp5);
        pp5.setOnClickListener(mCorkyListener);
        ImageView pp6 = (ImageView) findViewById(R.id.pp6);
        pp6.setOnClickListener(mCorkyListener);


    }
    View.OnClickListener mCorkyListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String id = String.valueOf(view.getId());
            String app = id.substring(id.length() - 2, id.length());
            if (view.getId()==R.id.pp1) {
                FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("imId").setValue("pp1");
                Intent intent = new Intent(listProfilePic.this, ProfileFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); finish();
            } else if (view.getId()==R.id.pp2) {
                FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("imId").setValue("pp2");
                Intent intent = new Intent(listProfilePic.this, ProfileFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); finish();
            } else if (view.getId()==R.id.pp3) {
                FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("imId").setValue("pp3");
                Intent intent = new Intent(listProfilePic.this, ProfileFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); finish();
            } else if (view.getId()==R.id.pp4) {
                FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("imId").setValue("pp4");
                Intent intent = new Intent(listProfilePic.this, ProfileFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); finish();
            } else if (view.getId()==R.id.pp5) {
                FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("imId").setValue("pp5");
                Intent intent = new Intent(listProfilePic.this, ProfileFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); finish();
            } else if (view.getId()==R.id.pp6) {
                FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("imId").setValue("pp6");
                Intent intent = new Intent(listProfilePic.this, ProfileFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); finish();
            }


        }


    };


}