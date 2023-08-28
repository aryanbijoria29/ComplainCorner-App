package com.celes.complaincorner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class stuCompActivityAuthority extends AppCompatActivity {
    TextView subComp, comp, sugges, compUser, compUserEnroll, compUserEmail;
    ImageView compImgView;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceUser;
    DatabaseReference databaseReferenceStatus;
    StorageReference storageReference;
    Toolbar toolbar;
    RadioGroup radioGroup;
    RadioButton compSentBtn, onGoingBtn, completedBtn, rejectedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_comp_authority);

        Intent intent = getIntent();
        String compID = intent.getStringExtra("key");
        String compType = intent.getStringExtra("comtype");

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(stuCompActivityAuthority.this, authorityActivity.class);
                startActivity(i);
            }
        });

        subComp=findViewById(R.id.subComp);
        comp=findViewById(R.id.comp);
        sugges=findViewById(R.id.sugges);
        compImgView=findViewById(R.id.compImgView);
        compUser=findViewById(R.id.compUser);
        compUserEnroll=findViewById(R.id.compEnroll);
        compUserEmail=findViewById(R.id.compEmail);
        databaseReference = FirebaseDatabase.getInstance().getReference("Complaints");
        databaseReference.child(compType).child(compID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String subject = String.valueOf(snapshot.child("subject").getValue());
                    String complaint = String.valueOf(snapshot.child("complaint").getValue());
                    String suggestion = String.valueOf(snapshot.child("suggestions").getValue());
                    String compImg = String.valueOf(snapshot.child("compImg").getValue());
                    String userUIDget = String.valueOf(snapshot.child("userUID").getValue());
                    String compStatus = String.valueOf(snapshot.child("compStatus").getValue());
                    subComp.setText(subject);
                    comp.setText(complaint);
                    sugges.setText(suggestion);

                    defaultRadioBtn(compStatus);

                    getUserInActivity(userUIDget);

                    storageReference= FirebaseStorage.getInstance().getReference().child("imagesComp/"+compImg);
                    try{
                        final File localFile = File.createTempFile(compImg, "jpg");
                        storageReference.getFile(localFile).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                                if(task.isSuccessful()){
                                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    compImgView.setImageBitmap(bitmap);
                                }
                                else{
                                    Toast.makeText(stuCompActivityAuthority.this, "Error in loading image", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(stuCompActivityAuthority.this, "Try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        radioGroupSetUp();

    }

    private void getUserInActivity(String userUIDget) {
        databaseReferenceUser = FirebaseDatabase.getInstance().getReference("User");
        databaseReferenceUser.child(userUIDget).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String fname = String.valueOf(snapshot.child("fullname").getValue());
                    String enroll = String.valueOf(snapshot.child("enrollment").getValue());
                    String emailID = String.valueOf(snapshot.child("email").getValue());
                    compUser.setText(fname);
                    compUserEnroll.setText(enroll);
                    compUserEmail.setText(emailID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void radioGroupSetUp() {
        radioGroup = findViewById(R.id.radioGrpStatus);
        Intent intent = getIntent();
        String compID1 = intent.getStringExtra("key");
        String compType1 = intent.getStringExtra("comtype");
        databaseReferenceStatus = FirebaseDatabase.getInstance().getReference("Complaints").child(compType1).child(compID1);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.compSentBtn:
                        databaseReferenceStatus.child("compStatus").setValue("complaint sent");
                        break;
                    case R.id.onGoingBtn:
                        databaseReferenceStatus.child("compStatus").setValue("On Going");
                        break;
                    case R.id.completedBtn:
                        databaseReferenceStatus.child("compStatus").setValue("Completed");
                        break;
                    case R.id.rejectedBtn:
                        databaseReferenceStatus.child("compStatus").setValue("Rejected");
                        break;
                }
            }
        });
    }
    private void defaultRadioBtn(String complaintStatus) {
        compSentBtn = findViewById(R.id.compSentBtn);
        onGoingBtn = findViewById(R.id.onGoingBtn);
        completedBtn = findViewById(R.id.completedBtn);
        rejectedBtn = findViewById(R.id.rejectedBtn);
        switch (complaintStatus) {
            case "complaint sent":
                compSentBtn.setChecked(true);
                break;
            case "On Going":
                onGoingBtn.setChecked(true);
                break;
            case "Completed":
                completedBtn.setChecked(true);
                break;
            case "Rejected":
                rejectedBtn.setChecked(true);
                break;
        }
    }
}