package com.celes.complaincorner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signUp extends AppCompatActivity {
    EditText fullname, enrollment, email, password, cpassword;
    Button register;
    String emailPattern="^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    String enrollPattern="[0-9]{4}+[A-Z]{2}+[0-9]{6}+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    //FirebaseUser mUser;
    DatabaseReference databaseReference;
    boolean enrollExist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fullname=findViewById(R.id.fullname);
        enrollment=findViewById(R.id.enrollment);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        cpassword=findViewById(R.id.cpassword);
        register=findViewById(R.id.register);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        //mUser=mAuth.getCurrentUser();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    checkEnrollExist();
            }
        });
    }

    private void checkEnrollExist() {
        enrollExist = false;
        FirebaseDatabase.getInstance().getReference("User")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            String enroll=enrollment.getText().toString().trim();
                            User user = dataSnapshot.getValue(User.class);
                            String chEnroll = user.getEnrollment();
                            if(enroll.equals(chEnroll)){
                                enrollExist = true;
                                break;
                            }
                        }
                        if(enrollExist){
                            enrollment.setError("Enrollment Already Registered");
                            enrollment.requestFocus();
                        }
                        else{
                            PerformAuth();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void PerformAuth() {
        String fname=fullname.getText().toString().trim();
        String enroll=enrollment.getText().toString().trim();
        String emailID=email.getText().toString().trim();
        String pass=password.getText().toString();
        String cpass=cpassword.getText().toString();
        databaseReference=FirebaseDatabase.getInstance().getReference("User");


        if(!fname.matches("[a-zA-Z]+\\s[a-zA-Z]+")){
            fullname.setError("Please enter your Full Name");
            fullname.requestFocus();
        }
        else if(!enroll.matches(enrollPattern)){
            enrollment.setError("Invalid Enrollment No.");
            enrollment.requestFocus();
        }
        else if(!emailID.matches(emailPattern)){
            email.setError("Invalid Email");
            email.requestFocus();
        }
        else if(pass.isEmpty() || pass.length()<7){
            password.setError("Enter a valid password");
            password.requestFocus();
        }
        else if(!pass.equals(cpass)){
            cpassword.setError("Password doesn't match");
            cpassword.requestFocus();
        }
        else{
            progressDialog.setMessage("Please wait..");
            progressDialog.setTitle("Registering");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(emailID,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if(task.isSuccessful()){
                        //Toast.makeText(signUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(signUp.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(signUp.this, "Can't send verification email", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        User user=new User(fname,enroll,emailID);
                        FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            toLoginPage();
                                        }
                                        else{
                                            Toast.makeText(signUp.this, "Email Already Exists", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    else{
                        Toast.makeText(signUp.this, "Email Already Exists", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void toLoginPage() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void signIn(View view){
        //Toast.makeText(this, "all ok", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}