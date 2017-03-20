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

public class SignInActivity extends AppCompatActivity {
  private static final String TAG = "SignInActivity";
  EditText mEteEmail, mEtePassword, mEtePasswordConfirm;
  Button mButSignIn;
  String email, password, passwordConfirm;
  
  FirebaseAuth mAuth;
  FirebaseAuth.AuthStateListener mAuthListener;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_in);
    injectView();
    setup();
    events();
  }

  private void injectView() {
    mEteEmail = (EditText) findViewById(R.id.eteEmail);
    mEtePassword = (EditText) findViewById(R.id.etePassword);
    mEtePasswordConfirm = (EditText) findViewById(R.id.etePasswordConfirm);
    mButSignIn = (Button) findViewById(R.id.butSignIn);
  }

  private void setup() {
    setupFirebaseAuth();
  }

  private void setupFirebaseAuth() {
    mAuth = FirebaseAuth.getInstance();
    mAuthListener = new FirebaseAuth.AuthStateListener() {
      @Override public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        Log.d(TAG, "onAuthStateChanged() called with: firebaseAuth = [" + firebaseAuth + "]");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
          Log.i(TAG, "onAuthStateChanged: LogIn");
          navigationMain();
        } else {
          Log.i(TAG, "onAuthStateChanged: LogIn Fail");
        }
      }
    };
  }

  private void events() {
    mButSignIn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (form()){
          signIn(email, password);
        } else {
          Toast.makeText(SignInActivity.this, "Ingrese un email y password", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  private boolean form() {
    email = mEteEmail.getText().toString();
    password = mEtePassword.getText().toString();
    passwordConfirm = mEtePasswordConfirm.getText().toString();

    if (email == null || email.isEmpty()) {
      return false;
    }
    if (password == null || password.isEmpty()) {
      return false;
    }
    if (passwordConfirm == null || passwordConfirm.isEmpty()) {
      return false;
    }
    if (!password.equals(passwordConfirm)) {
      return false;
    }
    return true;
  }

  private void signIn(String email, String password) {
    mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
              @Override public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "onComplete() called with: task = [" + task + "]");
              }
            });
  }

  private void navigationMain() {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
    finish();
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
