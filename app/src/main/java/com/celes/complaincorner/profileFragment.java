package com.celes.complaincorner;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profileFragment extends Fragment {
    View view;
    TextView name, email, enrollment, branch;
    Button logOutbutton;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        logOutbutton=view.findViewById(R.id.signOut);
        name=view.findViewById(R.id.namePro);
        email=view.findViewById(R.id.emailPro);
        enrollment=view.findViewById(R.id.enrollmentPro);
        branch=view.findViewById(R.id.branchPro);
        firebaseAuth=FirebaseAuth.getInstance();

        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        readData();

        logOutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(), "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                toLoginPage();
            }
        });


        return view;
    }

    private void readData() {
        databaseReference= FirebaseDatabase.getInstance().getReference("User");
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nameFB=String.valueOf(snapshot.child("fullname").getValue());
                    String enrollFB=snapshot.child("enrollment").getValue().toString();
                    name.setText(nameFB);
                    enrollment.setText(enrollFB);
                    String branchExtract = enrollFB.substring(4,6);
                    branch.setText(branchExtract);
                }
                else{
                    Toast.makeText(getActivity(), "Try Again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void toLoginPage() {
        Intent intent=new Intent(getActivity(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}