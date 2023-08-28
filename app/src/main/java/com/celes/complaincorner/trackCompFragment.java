package com.celes.complaincorner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class trackCompFragment extends Fragment {
    View view;
    Spinner spinner;
    RecyclerView recyclerView;
    ArrayList<Complaints> list;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    adapterTrackComp adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_track_comp, container, false);
        spinner=view.findViewById(R.id.selectCompType);
        recyclerView=view.findViewById(R.id.recyleViewComp);
        /*databaseReference = FirebaseDatabase.getInstance().getReference("Complaints");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new adapterTrackComp(getContext(), list);
        recyclerView.setAdapter(adapter);*/

        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(getContext(), R.array.compType, R.layout.spinnerhome);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                databaseReference = FirebaseDatabase.getInstance().getReference("Complaints");
                list = new ArrayList<>();
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new adapterTrackComp(getContext(), list);
                recyclerView.setAdapter(adapter);
                String liveUserUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                databaseReference.child(text).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            Complaints complaints=dataSnapshot.getValue(Complaints.class);
                            if(complaints.userUID.equals(liveUserUID)) {
                                list.add(complaints);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*databaseReference.child(text).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Complaints complaints=dataSnapshot.getValue(Complaints.class);
                    if(complaints.userUID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        list.add(complaints);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}