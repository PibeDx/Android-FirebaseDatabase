package com.josecuentas.android_firebasedatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {
  EditText mEteEmail, mEtePassword, mEtePasswordConfirm;
  Button mButSignIn;
  String email, password, passwordConfirm;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_in);
    injectView();
    events();
  }

  private void injectView() {
    mEteEmail = (EditText) findViewById(R.id.eteEmail);
    mEtePassword = (EditText) findViewById(R.id.etePassword);
    mEtePasswordConfirm = (EditText) findViewById(R.id.etePasswordConfirm);
    mButSignIn = (Button) findViewById(R.id.butSignIn);
  }

  private void events() {
    mButSignIn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (form()){
          //TODO: SIGN IN
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
}
