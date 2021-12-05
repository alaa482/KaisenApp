package it.unimib.kaisenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginUser extends AppCompatActivity implements View.OnClickListener{

    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        register = (TextView) findViewById(R.id.textViewLoginRegistrati);
        register.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewLoginRegistrati:
                startActivity(new Intent(this, RegisterUser.class));
                break;
        }
    }
}