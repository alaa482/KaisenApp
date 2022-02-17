package it.unimib.kaisenapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


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
                startActivity(new Intent(listProfilePic.this, ProfileActivity.class));
                finish();
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        String userID = user.getUid();




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
            if (app.equals("11")) {
                FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("imId").setValue("pp1");
                startActivity(new Intent(listProfilePic.this, ProfileActivity.class));
                finish();
            } else if (app.equals("12")) {
                FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("imId").setValue("pp2");
                startActivity(new Intent(listProfilePic.this, ProfileActivity.class));
                finish();
            } else if (app.equals("13")) {
                FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("imId").setValue("pp3");
                startActivity(new Intent(listProfilePic.this, ProfileActivity.class));
                finish();
            } else if (app.equals("14")) {
                FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("imId").setValue("pp4");
                startActivity(new Intent(listProfilePic.this, ProfileActivity.class));
                finish();
            } else if (app.equals("15")) {
                FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("imId").setValue("pp5");
                startActivity(new Intent(listProfilePic.this, ProfileActivity.class));
                finish();
            } else if (app.equals("16")) {
                FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("imId").setValue("pp6");
                startActivity(new Intent(listProfilePic.this, ProfileActivity.class));
                finish();
            }


        }


    };


}