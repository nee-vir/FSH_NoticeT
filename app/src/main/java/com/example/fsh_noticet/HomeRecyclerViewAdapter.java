package com.example.fsh_noticet;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {
    private List<Upload> homeUploads;
    private Context rContext;
    //private int adapterPos;

    public HomeRecyclerViewAdapter(List<Upload> someUploads, Context rContext) {
        this.homeUploads = someUploads;
        this.rContext = rContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item_layout,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d("SomeAdapter", "onBindViewHolder: Started");
        holder.someTitle.setText(homeUploads.get(position).getTitle());
        holder.someDept.setText(homeUploads.get(position).getDepartmentNames());
        holder.somePublisher.setText(homeUploads.get(position).getPublisherNames());
        holder.label1.setText("Posted By:");
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos= position;
                Intent intent=new Intent(rContext,EntirePostActivity.class);
                intent.putExtra("itemPos",adapterPos);
                rContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return homeUploads.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout parentLayout;
        TextView someTitle,someDept,somePublisher,label1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout=itemView.findViewById(R.id.home_item_layout);
            someTitle=itemView.findViewById(R.id.some_title);
            someDept=itemView.findViewById(R.id.some_dept);
            somePublisher=itemView.findViewById(R.id.some_publisher);
            label1=itemView.findViewById(R.id.published);

        }
    }
}
