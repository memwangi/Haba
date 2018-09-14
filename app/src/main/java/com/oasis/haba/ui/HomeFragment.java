package com.oasis.haba.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.oasis.haba.Database.DatabaseHelper;
import com.oasis.haba.DbModel.ChamaPOJO;
import com.oasis.haba.R;
import com.oasis.haba.util.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    private CardView createChama;
    private CardView joinChama;
    private CardView makePayment;
    private CardView mySchedule;
    private RecyclerView myRecyclerView;
    private  FirebaseRecyclerAdapter<ChamaPOJO,ChamaViewHolder> adapter;
    FirebaseDatabase mIstance;
    DatabaseReference dbRef;
    List<ChamaPOJO> chamadetails;
    DatabaseHelper db;
    FirebaseAuth mAuth;
    private String number;
    View view;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);
        createChama = view.findViewById(R.id.createChama);
        joinChama = view.findViewById(R.id.joinGroup);
        makePayment = view.findViewById(R.id.makePayment);
        mySchedule = view.findViewById(R.id.mySchedule);
        mIstance = FirebaseDatabase.getInstance();
        dbRef = mIstance.getReference();
        db = new DatabaseHelper(getActivity());

        mAuth = FirebaseAuth.getInstance();
        //Get user number
        String key = mAuth.getCurrentUser().getUid();
        //Using the key get the user number
        Map userNumber = db.getNumber();
        number = String.valueOf(userNumber.get(key));



        //Recycler View
        myRecyclerView = (RecyclerView) view.findViewById(R.id.groupItemRecyclerView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        //Ref where to listen for changes
        Query keyQuery =  dbRef.child("Users").child(number).child("MemberOfGroup").orderByKey();

        DatabaseReference groupReference = dbRef.child("Chamas");

        FirebaseRecyclerOptions<ChamaPOJO> options = new FirebaseRecyclerOptions.Builder<ChamaPOJO>()
                .setIndexedQuery(keyQuery,groupReference, ChamaPOJO.class)
                .build();

       adapter = new FirebaseRecyclerAdapter<ChamaPOJO, ChamaViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ChamaViewHolder holder, int position, @NonNull ChamaPOJO model) {
                holder.setImage(model.getPictureLocation());
                holder.AmountPerMember.setText(model.getContributionAmount());
                holder.mPesaPaybill.setText(String.valueOf(model.getpaybillNumber()));
                holder.grossIncome.setText("0");
                holder.chamaname.setText(model.getGroupname());

                final String chamaKey = String.valueOf(getRef(position));
                //Set onclick listener;
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       Intent memberActivity = new Intent(getActivity(),MemberChama.class);
                       memberActivity.putExtra("chamaKey",chamaKey);
                       startActivity(memberActivity);
                    }
                });




            }

            @NonNull
            @Override
            public ChamaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.itemgroup, parent, false);
                return new ChamaViewHolder(view);

            }


       };

        myRecyclerView.setAdapter(adapter);



        //Onclick for createChama
        createChama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Redirect to Create Chama Form
                Intent intent = new Intent(getActivity(), CreateChamaActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    public static class ChamaViewHolder extends RecyclerView.ViewHolder{

        private TextView chamaname,grossIncome,mPesaPaybill,AmountPerMember;
        private ImageView image;
        public ChamaViewHolder(View itemView) {
            super(itemView);
           chamaname = itemView.findViewById(R.id.groupName);
           grossIncome = itemView.findViewById(R.id.groupIncome);
           mPesaPaybill = itemView.findViewById(R.id.paybillNo);
           AmountPerMember = itemView.findViewById(R.id.ContributionAmnt);
           image = itemView.findViewById(R.id.groupProfilePic);

        }


        public void setChamaname(TextView chamaname) {
            this.chamaname = chamaname;
        }


        public void setGrossIncome(TextView grossIncome) {
            this.grossIncome = grossIncome;
        }

        public void setmPesaPaybill(TextView mPesaPaybill) {
            this.mPesaPaybill = mPesaPaybill;
        }


        public void setAmountPerMember(TextView amountPerMember) {
            AmountPerMember = amountPerMember;
        }


        public void setImage(String downLoadUrl) {

            Picasso.get()
                    .load(downLoadUrl)
                    .resize(200, 120)
                    .centerInside()
                    .into(image);
        }
    }




}
