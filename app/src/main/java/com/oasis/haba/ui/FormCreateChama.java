package com.oasis.haba.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.oasis.haba.R;
import com.wafflecopter.multicontactpicker.ContactResult;
import com.wafflecopter.multicontactpicker.MultiContactPicker;

import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class FormCreateChama extends Fragment{


    private static final int CONTACT_PICKER_REQUEST = 991;
    Context context;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    public List<String> memberData = new ArrayList<>();

    private Button Btn;
  public EditText title;

    public FormCreateChama() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.createchama_form,container,false);

        // Initialize Spinner Object
        spinner = view.findViewById(R.id.spinnerSelectPeriod);
        adapter = ArrayAdapter.createFromResource(getActivity(),R.array.ContributionPeriods,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //End Spinner Object


        // Todo: Add button for adding chama members and add them
        final Button addMembers = view.findViewById(R.id.btnAddMembers);
        Btn = view.findViewById(R.id.btnCreate);
        title = view. findViewById(R.id.chamaName);



        //Redirect to adding chama members
        addMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MultiContactPicker.Builder(getActivity()) //Activity/fragment context
                        .theme(R.style.MyCustomPickerTheme) //Optional - default: MultiContactPicker.Azure
                        .hideScrollbar(false) //Optional - default: false
                        .showTrack(true) //Optional - default: true
                        .searchIconColor(Color.WHITE) //Option - default: White
                        .setChoiceMode(MultiContactPicker.CHOICE_MODE_MULTIPLE) //Optional - default: CHOICE_MODE_MULTIPLE
                        .handleColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary)) //Optional - default: Azure Blue
                        .bubbleColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary)) //Optional - default: Azure Blue
                        .bubbleTextColor(Color.WHITE) //Optional - default: White
                        .showPickerForResult(CONTACT_PICKER_REQUEST);

            }
        });

        //Button for Submit Form Data
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               createChama();

            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CONTACT_PICKER_REQUEST){
            if(resultCode == RESULT_OK) {
                List<ContactResult> results = MultiContactPicker.obtainResult(data);
                String one = results.get(0).getDisplayName().toString();
                memberData.add(one);
                memberData.add(results.get(1).getDisplayName().toString());

                //Log.d("MyTag", results.get(0).getDisplayName());
            } else if(resultCode == RESULT_CANCELED){
                System.out.println("User closed the picker without selecting items.");
            }
        }



    }

    public void createChama(){
            //String name_chama = memberData.get(0);

            //Bundle extras = new Bundle();
            //extras.putString("CHAMA_NAME", name_chama);

            Intent createChama = new Intent(getActivity(),Chama.class);
            //createChama.putExtras(extras);
            startActivity(createChama);
        }









}
