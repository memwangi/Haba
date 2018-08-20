package com.oasis.haba.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oasis.haba.DbModel.ChamaPOJO;
import com.oasis.haba.R;

import java.util.List;

public class groupItemAdapter extends RecyclerView.Adapter<groupItemAdapter.MyViewHolder> {

    Context mContext;
    List<ChamaPOJO> chamadetails;

    public groupItemAdapter(Context mContext, List<ChamaPOJO> chamadetails) {
        this.mContext = mContext;
        this.chamadetails = chamadetails;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout item_group;
        private TextView groupName,groupGrossIncome,myLoans,myShareValue;
         private ImageView groupPicture;



        public MyViewHolder(View itemView) {
            super(itemView);

            item_group = (LinearLayout) itemView.findViewById(R.id.item_group);
            groupName = itemView.findViewById(R.id.groupName);
            groupGrossIncome = itemView.findViewById(R.id.groupIncome);
            myLoans = itemView.findViewById(R.id.LoansMember);
            myShareValue = itemView.findViewById(R.id.netShareValue);
            groupPicture = itemView.findViewById(R.id.groupProfilePic);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.itemgroup,parent,false);
        final MyViewHolder viewHolder = new MyViewHolder(v);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
