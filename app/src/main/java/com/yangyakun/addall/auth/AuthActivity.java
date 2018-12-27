package com.yangyakun.addall.auth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yangyakun.addall.R;

public class AuthActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Button BtnCreateUser;
    private Button BtnSignUser;
    private Button btn_sign_out_user;
    private TextView tv_userinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        BtnCreateUser = findViewById(R.id.btn_create_user);
        BtnSignUser = findViewById(R.id.btn_sign_user);
        tv_userinfo = findViewById(R.id.tv_userinfo);
        btn_sign_out_user = findViewById(R.id.btn_sign_out_user);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    tv_userinfo.setText(user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    tv_userinfo.setText("");
                }
            }
        };
        BtnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewUser();
            }
        });
        BtnSignUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUser();
            }
        });
        btn_sign_out_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOUtUser();
            }
        });
    }

    private void signOUtUser() {
        mAuth.signOut();
    }

    private void signUser() {
        mAuth.signInWithEmailAndPassword("361176335@qq.com", "cbf6511fc5")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(AuthActivity.this, "登录失败",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            AuthResult result = task.getResult();
                            tv_userinfo.setText(result.getUser().getEmail());
                        }
                    }
                });
    }

    private void addNewUser() {
        mAuth.createUserWithEmailAndPassword("361176335@qq.com", "cbf6511fc5")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(AuthActivity.this, "注册失败",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
