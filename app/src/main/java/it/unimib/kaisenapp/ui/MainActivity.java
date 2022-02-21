package it.unimib.kaisenapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.fragment.HomeFragment;
import it.unimib.kaisenapp.fragment.MyMoviesFragment;
import it.unimib.kaisenapp.fragment.ProfileFragment;
import it.unimib.kaisenapp.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        bottomNavigationView=findViewById(R.id.bottomBar);
        bottomNavigationClick();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

    }

    private void bottomNavigationClick(){
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment =null;
            switch(item.getItemId()){
                case R.id.home:
                    selectedFragment= new HomeFragment();
                    break;
                case R.id.search:
                    selectedFragment=new SearchFragment();
                    break;
                case R.id.mylist:
                    selectedFragment=new MyMoviesFragment();
                    break;
                case R.id.account:
                    selectedFragment = new ProfileFragment();
                    break;
                default:
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}