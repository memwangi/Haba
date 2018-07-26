package com.oasis.haba.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oasis.haba.R;

public class ReceiptsFragment extends Fragment{

    View view;

    public ReceiptsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.receipts,container,false);
        // Inflate the layout for this fragment
        return view;
    }
}
