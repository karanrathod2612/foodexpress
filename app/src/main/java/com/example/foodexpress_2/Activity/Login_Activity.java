//package com.example.foodexpress_2.Activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.foodexpress_2.Domain.User;
//import com.example.foodexpress_2.R;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class Login_Activity extends AppCompatActivity {
//
//    private EditText editTextemail, editTextpassword;
//    private Button login,skip;
//
//    private FirebaseAuth mAuth;
//    private DatabaseReference mDatabase;
//    boolean isAllFieldsChecked = false;
//    FirebaseUser firebaseUser;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_login);
//
//        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        editTextemail = findViewById(R.id.signup_email);
//        editTextpassword = findViewById(R.id.signup_password);
//        login = findViewById(R.id.login_button);
//
//        skip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Login_Activity.this, MainActivity.class);
//                startActivity(i);
//            }
//        });
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                registerUser();
//                dataclear();
//            }
//        });
//    }
//    private void dataclear()
//    {
//        editTextpassword.setText("");
//        editTextemail.setText("");
//    }
//
//    private void registerUser()
//    {
//        if (editTextemail.length() == 0)
//        {
//            editTextemail.setError("This field is required");
//        }
//        else if (editTextpassword.length()==0)
//        {
//            editTextpassword.setError("Password is required");
//        }
//        else if (editTextpassword.length()<8)
//        {
//            editTextpassword.setError("password 8 character reqired");
//        }
//        else
//        {
//            final String email = editTextemail.getText().toString().trim();
//            final String password = editTextpassword.getText().toString().trim();
//
//            mAuth.createUserWithEmailAndPassword(email,password)
//                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()){
//                                FirebaseUser user = mAuth.getCurrentUser();
//                                String userId = user.getUid();
//
//                                User newUser = new User(email,password);
//                                mDatabase.child("User").child(userId).setValue(newUser);
//                                Toast.makeText(Login_Activity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
//                                Intent i = new Intent(Login_Activity.this, MainActivity.class);
//                                startActivity(i);
//                                finish();
//                            } else
//                            {
//                                Toast.makeText(Login_Activity.this, "Login Failed", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//        }
//    }
//}

//            New Code..........................

package com.example.foodexpress_2.Activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodexpress_2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login_Activity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private EditText loginUsername, loginPassword;
    private Button loginButton , skipbtn;
    private TextView signupRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        skipbtn = findViewById(R.id.skipbtn);
        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginUsername.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();
                loginButton(email,password);
                Intent intent = new Intent(Login_Activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername() | !validatePassword()) {

                } else {
                    checkUser();
                }
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loginButton(String email, String password) {
    }

    public Boolean validateUsername() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()) {
            loginUsername.setError("Username cannot be empty");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }


    public void checkUser(){
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    loginUsername.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userPassword)) {
                        loginUsername.setError(null);

                        String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
                        String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
                        String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);

                        Intent intent = new Intent(Login_Activity.this, MainActivity.class);

                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("password", passwordFromDB);

                        startActivity(intent);
                    } else {
                        loginPassword.setError("Invalid Credentials");
                        loginPassword.requestFocus();
                    }
                } else {
                    loginUsername.setError("User does not exist");
                    loginUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login_Activity.this,"Error fetching data:" + error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}