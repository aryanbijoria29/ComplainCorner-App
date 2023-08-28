package com.celes.complaincorner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapterTrackCompAuthority extends RecyclerView.Adapter<adapterTrackCompAuthority.MyViewHolder> {
    Context context;
    ArrayList<Complaints> list;

    public adapterTrackCompAuthority(Context context, ArrayList<Complaints> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public adapterTrackCompAuthority.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyleviewcomplayout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterTrackCompAuthority.MyViewHolder holder, int position) {
        Complaints complaints=list.get(position);
        holder.subject.setText(complaints.getSubject());
        String st = context.getResources().getString(R.string.status);
        holder.compStatus.setText(st.concat(" ").concat(complaints.getCompStatus()));
        switch (complaints.getCompStatus()) {
            case "On Going":
                holder.compStatus.setTextColor(Color.parseColor("#FFBF00"));
                break;
            case "Completed":
                holder.compStatus.setTextColor(Color.parseColor("#000000"));
                break;
            case "Rejected":
                holder.compStatus.setTextColor(Color.parseColor("#FF0000"));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView subject, compStatus;
        ImageView compImg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            subject=itemView.findViewById(R.id.subjectPreview);
            compStatus=itemView.findViewById(R.id.compStatus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Complaints complaints=list.get(getAdapterPosition());
                    String temp= complaints.getCompID();
                    String comtype = complaints.getCompType();
                    Intent intent = new Intent(view.getContext(),stuCompActivityAuthority.class);
                    intent.putExtra("key" , temp);
                    intent.putExtra("comtype", comtype);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}

