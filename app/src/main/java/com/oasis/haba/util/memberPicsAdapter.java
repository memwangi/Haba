package com.oasis.haba.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oasis.haba.DbModel.ChamaPOJO;
import com.oasis.haba.DbModel.memberDetails;
import com.oasis.haba.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class memberPicsAdapter extends RecyclerView.Adapter<memberPicsAdapter.MyViewHolder> {

    Context mContext;
    private List<memberDetails> memberDetailsList;

    public memberPicsAdapter(Context mContext, List<memberDetails> moviesList) {
        this.mContext = mContext;
        this.memberDetailsList = moviesList;
    }


    //Viewholder for the recyclerView

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout item_member;
        private ImageView memberPhoto;
        private TextView memberNumber;



        public MyViewHolder(View itemView) {
            super(itemView);

            item_member = (LinearLayout) itemView.findViewById(R.id.item_member);
            memberPhoto = itemView.findViewById(R.id.dialog_img);
            memberNumber = itemView.findViewById(R.id.user_number);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_member,parent,false);
        final MyViewHolder viewHolder = new MyViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        memberDetails details = memberDetailsList.get(position);
        Picasso.get()
                .load(details.getPictureUri())
                .transform(new CircleTransform())
                .resize(190, 190)
                .centerCrop()
                .into(holder.memberPhoto);

        holder.memberNumber.setText(details.getPhoneNumber());
    }

    @Override
    public int getItemCount() {

        return memberDetailsList.size();
    }


}
