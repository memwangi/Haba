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

import com.oasis.haba.DbModel.ChamaPOJO;
import com.oasis.haba.R;
import com.oasis.haba.util.groupItemAdapter;

import java.util.List;

public class HomeFragment extends Fragment {
    private CardView createChama;
    private CardView joinChama;
    private CardView makePayment;
    private CardView mySchedule;
    private RecyclerView myRecyclerView;
    List<ChamaPOJO> chamadetails;
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

        //Recycler View
        myRecyclerView = (RecyclerView) view.findViewById(R.id.groupItemRecyclerView);
        groupItemAdapter adapter = new groupItemAdapter(getContext(), chamadetails);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
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
}
