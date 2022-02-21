package it.unimib.kaisenapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import it.unimib.kaisenapp.ui.LoginUser;
import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.models.User;
import it.unimib.kaisenapp.ui.listProfilePic;

public class ProfileFragment extends Fragment {
    private ImageButton backButton;
    private ImageButton modifyImage;
    private ImageView profileImage;
    private FirebaseUser user;
    private DatabaseReference reference;
    private DatabaseReference slot;
    private String userID;  //id di firebase legato all'utente
    private GoogleSignInClient mGoogleSignInClient;


    private Button btnLogout;

    @Override
    public void onResume() {
        super.onResume();
        final TextView txtFullName = (TextView) this.getView().findViewById(R.id.txtFullNameActivity);
        final TextView txtMail = (TextView) this.getView().findViewById(R.id.txtMailActivity);
        final ImageView imageProfile = (ImageView) this.getView().findViewById(R.id.imageProfile);
        final TextView txtNumSf = (TextView) this.getView().findViewById(R.id.textView4);
        final TextView txtOre = (TextView) this.getView().findViewById(R.id.textView6);

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

                    imageProfile.setImageResource(path);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        createRequest();
        btnLogout = (Button) view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                signOut();
                startActivity(new Intent(getContext(), LoginUser.class));
            }




        });
        backButton = (ImageButton) view.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HomeFragment.class));
            }




        });
        modifyImage = (ImageButton) view.findViewById(R.id.modifyImage);
        modifyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), listProfilePic.class));
            }




        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        userID = user.getUid();





        // final TextView greetingTextView = (TextView) findViewById(R.id.txtBenvenuto);
        final TextView txtFullName = (TextView) view.findViewById(R.id.txtFullNameActivity);
        final TextView txtMail = (TextView) view.findViewById(R.id.txtMailActivity);
        final ImageView imageProfile = (ImageView) view.findViewById(R.id.imageProfile);
        final TextView txtNumSf = (TextView) view.findViewById(R.id.textView4);
        final TextView txtOre = (TextView) view.findViewById(R.id.textView6);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
        if(signInAccount != null){
            //greetingTextView.setText("Welcome, " + signInAccount.getDisplayName() + "!");
            txtFullName.setText(signInAccount.getDisplayName());
            txtMail.setText(signInAccount.getEmail());
        }



        return view;
    }


    private void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);


    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }


}
