package geekspocket.farziengineers.com;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import geekspocket.farziengineers.com.geekspocket.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginSignUp extends AppCompatActivity {

    Button signInButton, registerButton;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase db;
    DatabaseReference users;
    RelativeLayout rootRelativeLayout;
    FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Arkhip_font.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build());

        setContentView(R.layout.activity_login_sign_up);
        final LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.progressBar);
        animationView.setAnimation("data.json");
        animationView.loop(true);
        animationView.playAnimation();

        FirebaseApp.initializeApp(this);
        //Init Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        signInButton = findViewById(R.id.signInButton);
        registerButton = findViewById(R.id.registerButton);
        rootRelativeLayout = findViewById(R.id.root_relative_layout);

        //event
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterDialog();
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(LoginSignUp.this, MainActivity.class));
                    finish();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    private void showLoginDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("SIGN IN");
        dialog.setMessage("Please use email to sign in");

        LayoutInflater inflater = LayoutInflater.from(this);
        View login_layout = inflater.inflate(R.layout.login_layout, null);

        final MaterialEditText editEmailId = login_layout.findViewById(R.id.emailId);
        final MaterialEditText editPassword = login_layout.findViewById(R.id.password);

        dialog.setView(login_layout);

        //set Button

        dialog.setPositiveButton("SIGN IN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
                //check validation
                if (TextUtils.isEmpty(editEmailId.getText().toString())){
                    Snackbar.make(rootRelativeLayout, "Please enter valid email address", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(editPassword.getText().toString())){
                    Snackbar.make(rootRelativeLayout, "Please enter password", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (editEmailId.getText().toString().length() < 6){
                    Snackbar.make(rootRelativeLayout, "Password too short!!!", Snackbar.LENGTH_LONG).show();
                    return;
                }

                //login
                firebaseAuth.signInWithEmailAndPassword(editEmailId.getText().toString(), editPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(new Intent(LoginSignUp.this, MainActivity.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(rootRelativeLayout, "Failed"+e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            }
                        });

            }
        });


        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.show();
    }

    private void showRegisterDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("REGISTER");
        dialog.setMessage("Please use email to register");

        LayoutInflater inflater = LayoutInflater.from(this);
        View register_layout = inflater.inflate(R.layout.register_layout, null);

        final MaterialEditText editEmailId = register_layout.findViewById(R.id.emailId);
        final MaterialEditText editPassword = register_layout.findViewById(R.id.password);
        final MaterialEditText editName = register_layout.findViewById(R.id.editName);
        final MaterialEditText editPhone = register_layout.findViewById(R.id.editPhone);

        dialog.setView(register_layout);

        //set Button

        dialog.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
                //check validation
                if (TextUtils.isEmpty(editEmailId.getText().toString())){
                    Snackbar.make(rootRelativeLayout, "Please enter valid email address", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(editPhone.getText().toString())){
                    Snackbar.make(rootRelativeLayout, "Please enter valid phone number", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(editPassword.getText().toString())){
                    Snackbar.make(rootRelativeLayout, "Please enter password", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (editEmailId.getText().toString().length() < 6){
                    Snackbar.make(rootRelativeLayout, "Password too short!!!", Snackbar.LENGTH_LONG).show();
                    return;
                }

                //register new user
                firebaseAuth.createUserWithEmailAndPassword(editEmailId.getText().toString(), editPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                //save user to db
                                User user = new User();
                                user.setEmail(editEmailId.getText().toString());
                                user.setPassword(editPassword.getText().toString());
                                user.setName(editName.getText().toString());
                                user.setPhone(editPhone.getText().toString());

                                //use email to key
                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Snackbar.make(rootRelativeLayout, "Registered successfully", Snackbar.LENGTH_LONG ).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Snackbar.make(rootRelativeLayout, "Registration failed"+e.getMessage(), Snackbar.LENGTH_LONG ).show();
                                            }
                                        });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(rootRelativeLayout, "Registration failed"+e.getMessage(), Snackbar.LENGTH_LONG ).show();
                    }
                });
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.show();
    }
}
