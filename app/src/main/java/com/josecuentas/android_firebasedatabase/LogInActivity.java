package com.josecuentas.android_firebasedatabase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

  private static final String TAG = "LogInActivity";
  private FirebaseAuth mAuth;
  private FirebaseAuth.AuthStateListener mAuthListener;
  EditText mEteEmail, mEtePassword;
  Button mButLogIn, mButSignIn;
  private String email, password;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_log_in);
    injects();
    events();
  }

  private void injects() {
    injectView();
    setupFirebaseAuth();
  }

  private void events() {
    mButLogIn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (form()) logIn(email, password);
        else Toast.makeText(LogInActivity.this, "Ingrese user y pass", Toast.LENGTH_SHORT).show();
      }
    });

    mButSignIn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent intent = new Intent(LogInActivity.this, SignInActivity.class);
        startActivity(intent);
      }
    });
  }

  private void injectView() {
    mEteEmail = (EditText) findViewById(R.id.eteEmail);
    mEtePassword = (EditText) findViewById(R.id.etePassword);
    mButLogIn = (Button) findViewById(R.id.butLogIn);
    mButSignIn = (Button) findViewById(R.id.butSignIn);
  }

  private void setupFirebaseAuth() {
    mAuth = FirebaseAuth.getInstance();

    mAuthListener = new FirebaseAuth.AuthStateListener() {
      @Override public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
          // User is signed in
          Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
        } else {
          // User is signed out
          Log.d(TAG, "onAuthStateChanged:signed_out");
        }
      }
    };
  }

  private boolean form() {
    email = mEteEmail.getText().toString();
    password = mEtePassword.getText().toString();

    if (email==null || email.isEmpty()) {
      return false;
    }
    if (password==null || password.isEmpty()) {
      return false;
    }
    return true;
  }

  private void logIn(String email, String password) {
    mAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override public void onComplete(@NonNull Task<AuthResult> task) {
            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

            // If sign in fails, display a message to the user. If sign in succeeds
            // the auth state listener will be notified and logic to handle the
            // signed in user can be handled in the listener.
            if (!task.isSuccessful()) {
              Log.w(TAG, "signInWithEmail:failed", task.getException());
              Toast.makeText(LogInActivity.this, R.string.auth_failed,Toast.LENGTH_SHORT).show();
            } else {
              navigationMain();
            }
          }
        });
  }

  private void navigationMain() {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
  }

  @Override protected void onStart() {
    super.onStart();
    mAuth.addAuthStateListener(mAuthListener);
  }

  @Override protected void onStop() {
    super.onStop();
    if (mAuthListener != null) {
      mAuth.removeAuthStateListener(mAuthListener);
    }
  }
}