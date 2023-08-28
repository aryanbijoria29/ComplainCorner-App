package com.celes.complaincorner;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class homeFragmentAuthority extends Fragment {
    View view;
    RecyclerView recyclerView;
    ArrayList<Complaints> list;
    DatabaseReference databaseReference;
    adapterTrackCompAuthority adapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_authority, container, false);

        recyclerView=view.findViewById(R.id.recycleViewAuthComp);
        databaseReference = FirebaseDatabase.getInstance().getReference("Complaints");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new adapterTrackCompAuthority(getContext(), list);
        recyclerView.setAdapter(adapter);

        sharedPreferences = getActivity().getSharedPreferences("authLogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String authTypeTemp = sharedPreferences.getString("authTypeTemp", "default :(");

        databaseReference.child(authTypeTemp).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Complaints complaints=dataSnapshot.getValue(Complaints.class);
                    if(complaints.compType.equals(authTypeTemp)){
                        list.add(complaints);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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