package com.example.foodexpress_2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodexpress_2.R;
import com.example.foodexpress_2.databinding.ActivityMainBinding;
import com.example.foodexpress_2.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile_Activity extends BaseActivity {

    ActivityProfileBinding binding;
    TextView usertxt,passwordtxt;
    ImageView probackbtn;
    Button logout;
    FirebaseUser firebaseUser;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.black,this.getTheme()));


        usertxt = findViewById(R.id.usertxt);
        passwordtxt = findViewById(R.id.passwordtxt);
        logout = findViewById(R.id.logout);
        probackbtn = findViewById(R.id.probackbtn);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        usertxt.setText(firebaseUser.getEmail());
        reference = FirebaseDatabase.getInstance().getReference("User").child("Username");


        probackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile_Activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}