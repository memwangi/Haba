package com.oasis.haba.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oasis.haba.DbModel.ChamaPOJO;
import com.oasis.haba.R;
import com.oasis.haba.util.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyaccountFragment extends Fragment {
    View view;
    private RecyclerView myRecyclerView;
    private List<ChamaPOJO> myChamaPOJODetails;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    public MyaccountFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.myaccount_fragment,container,false);

        // Adding nested fragments

        tabLayout = (TabLayout) view.findViewById(R.id.tablayout_id );
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_id);

        //
        ViewPagerAdapter mAdapter = new ViewPagerAdapter(getChildFragmentManager());
        mAdapter.addFragment(new LoansFragment(),"");
        mAdapter.addFragment(new HistoryFragment(),"");
        mAdapter.addFragment(new ReceiptsFragment(),"");
        //Adapter setup
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //Configure Icons
        int[] imageResId = {
                R.drawable.ic_loans,
                R.drawable.ic_history,
                R.drawable.ic_receipt };

        for (int i = 0; i < imageResId.length; i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
        }




     // Inflate card view Chama Recycler View to show the detailed list of chamas that the user is a member of
        myRecyclerView = (RecyclerView) view.findViewById(R.id.chamaRecyclerView);
        ChamaGraphCardsAdapter chamaGraphCardsAdapter = new ChamaGraphCardsAdapter(getContext(), myChamaPOJODetails);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
        myRecyclerView.setAdapter(chamaGraphCardsAdapter);
        return view;



    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        myChamaPOJODetails = new ArrayList<>();

        myChamaPOJODetails.add(new ChamaPOJO("Mapato Group",32000,500000,200000));

        myChamaPOJODetails.add(new ChamaPOJO("Mapato Group",32000,500000,200000));

        myChamaPOJODetails.add(new ChamaPOJO("Mapato Group",32000,500000,200000));

        myChamaPOJODetails.add(new ChamaPOJO("Mapato Group",32000,500000,200000));
    }


}
