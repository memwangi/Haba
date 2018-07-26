package com.oasis.haba.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oasis.haba.DbModel.ChamaPOJO;
import com.oasis.haba.R;

import java.util.List;

public class ChamaGraphCardsAdapter extends RecyclerView.Adapter<ChamaGraphCardsAdapter.MyViewHolder> {

    Context mContext;
    List<ChamaPOJO> mData;


    public ChamaGraphCardsAdapter(Context mContext, List<ChamaPOJO> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    //Viewholder for the recyclerView

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout item_chama;
        private TextView Expenses;
        private TextView name;



        public MyViewHolder(View itemView) {
            super(itemView);

            item_chama = (LinearLayout) itemView.findViewById(R.id.item_chama);
            Expenses = itemView.findViewById(R.id.Expenses);
            name = itemView.findViewById(R.id.chamaName);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_chama,parent,false);
        final MyViewHolder viewHolder = new MyViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Expenses.setText(mData.get(position).getExpenses());
        holder.name.setText(mData.get(position).getname());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }




}
