package com.celes.complaincorner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class profileFragmentAuthority extends Fragment {
    View view;
    TextView authoID, authoType;
    Button signOut;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_authority, container, false);

        authoID = view.findViewById(R.id.authID);
        authoType = view.findViewById(R.id.authType);
        signOut = view.findViewById(R.id.signOutAuth);
        sharedPreferences = getActivity().getSharedPreferences("authLogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String authTypeTemp = sharedPreferences.getString("authTypeTemp", "default :(");
        String authIDTemp = sharedPreferences.getString("authIDTemp", "default :(");
        authoType.setText(authTypeTemp);
        authoID.setText(authIDTemp);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Toast.makeText(getActivity(), "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        return view;
    }
}