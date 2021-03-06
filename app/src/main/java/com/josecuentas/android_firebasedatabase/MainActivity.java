package com.josecuentas.android_firebasedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    FirebaseAuth mAuth;
    Button mButLogOut, mButProfile;
    TextView mTviUser;
    ValueEventListener mValueEventInfo;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        injectView();
        setup();
        events();
    }

    private void injectView() {
        mButLogOut = (Button) findViewById(R.id.butLogOut);
        mButProfile = (Button) findViewById(R.id.butProfile);
        mTviUser = (TextView) findViewById(R.id.tviUser);
    }

    private void setup() {
        setupFirebaseAuth();
        setupFirebase();
    }

    private void events() {
        mButLogOut.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                myRef.child(mAuth.getCurrentUser().getUid()).removeEventListener(mValueEventInfo);
                mAuth.signOut();
                finish();
            }
        });
        mButProfile.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupFirebase() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) return;
        String userId = currentUser.getUid();

        User user = new User();
        user.setName("José");
        user.setLastName("Cuentas");
        user.setAge(23);
        user.setAbout("Hola!");

        //myRef.child(userId).setValue(user);

        mValueEventInfo = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User user = dataSnapshot.getValue(User.class);
                if (user == null) return;
                Log.d(TAG, "Value is: " + user.toString());
                String fullName =  user.getName() + " " + user.getLastName();
                mTviUser.setText(fullName);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        };
        myRef.child(userId).addValueEventListener(mValueEventInfo);


    }

    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
    }


}
