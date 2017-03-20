package com.josecuentas.android_firebasedatabase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    EditText mEteName, mEteLastName, mEteAge;
    Button  mButSave;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference dbUserRef;
    String userId;
    User mUser;

    String name, lastName, sAge;
    int age;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        injectView();
        setup();
        listenerFirebase();
        events();
    }

    private void injectView() {
        mEteName = (EditText) findViewById(R.id.eteName);
        mEteLastName = (EditText) findViewById(R.id.eteLastName);
        mEteAge = (EditText) findViewById(R.id.eteAge);
        mButSave = (Button) findViewById(R.id.butSave);
    }

    private void setup() {
        setupFirebaseAuth();
        setupFirebaseDatabase();
    }

    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
    }

    private void setupFirebaseDatabase() {
        database = FirebaseDatabase.getInstance();
        dbUserRef = database.getReference("users");
    }

    private void listenerFirebase() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) return;
        userId = currentUser.getUid();
        dbUserRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange() called with: dataSnapshot = [" + dataSnapshot + "]");
                mUser = dataSnapshot.getValue(User.class);
                populate();

            }

            @Override public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled() called with: databaseError = [" + databaseError + "]");
                clear();
            }
        });
    }

    private void events() {
        mButSave.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (form()) {
                    saveUser(mUser);
                }
            }
        });
    }

    private void populate() {
        if (mUser == null) return;
        name = mUser.getName();
        lastName = mUser.getLastName();
        age = mUser.getAge();
        mEteName.setText(name);
        mEteLastName.setText(lastName);
        mEteAge.setText(String.valueOf(age));
    }

    private void clear() {
        mEteName.setText("");
        mEteLastName.setText("");
        mEteAge.setText("");
    }

    private boolean form() {
        name = mEteName.getText().toString();
        lastName = mEteLastName.getText().toString();
        sAge = mEteAge.getText().toString();


        //TODO: validate
        if (mUser == null) mUser = new User();
        mUser.setName(name);
        mUser.setLastName(lastName);
        mUser.setAge(Integer.valueOf(sAge));

        return true;
    }

    private void saveUser(User user) {
        dbUserRef.child(userId).setValue(user)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete() called with: task = [" + task + "]");
                        if (task.isSuccessful()) {
                            finish();
                        } else {
                            Toast.makeText(ProfileActivity.this, "No se ha podido guardar", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
