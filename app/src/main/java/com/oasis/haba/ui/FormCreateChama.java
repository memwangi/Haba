package com.oasis.haba.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.oasis.haba.Database.DatabaseHelper;
import com.oasis.haba.DbModel.ChamaPOJO;
import com.oasis.haba.R;


import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class FormCreateChama extends Fragment {

    private TextInputLayout chamaname, mpesaPaybill, amountPerMember;
    private EditText contributionTimesPerPeriod;
    private String contributionPeriod;
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    Context context;
    Spinner spinner;
    private static String groupName;
    private String phone;
    private String userGroupId;
    private DatabaseHelper helper;
    ArrayAdapter<CharSequence> adapter;
    private Button btnCreateChama;


    public FormCreateChama() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.createchama_form, container, false);
        context = getActivity();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Chamas");
        helper = new DatabaseHelper(getActivity());

        //PhoneNumber

        Map userMap = helper.getNumber();
        Object number = (String) userMap.get(mAuth.getUid());
        phone = String.valueOf(number);


        // Initialize Spinner Object
        spinner = view.findViewById(R.id.spinnerSelectPeriod);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.ContributionPeriods, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Attributes
        chamaname = view.findViewById(R.id.chamaName);
        mpesaPaybill = view.findViewById(R.id.paybillNumber);
        amountPerMember = view.findViewById(R.id.amountPerMember);
        contributionTimesPerPeriod = view.findViewById(R.id.contributionTimes);
        contributionPeriod = spinner.getSelectedItem().toString();



        btnCreateChama = view.findViewById(R.id.btnCreate);

        //Button for Submit Form Data
        btnCreateChama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createChama();


            }
        });

        return view;
    }

    public void createChama() {

        final String admin = mAuth.getCurrentUser().getEmail();
        final String groupname = chamaname.getEditText().getText().toString();
        final int payBillNumber = Integer.parseInt(mpesaPaybill.getEditText().getText().toString());
        final int amountPerPerson = Integer.parseInt(amountPerMember.getEditText().getText().toString());
        final int timesOfContribution = Integer.parseInt(contributionTimesPerPeriod.getText().toString());
        final String periodOfContribution = contributionPeriod.trim();

        // Chama Object
        ChamaPOJO newchama = new ChamaPOJO(admin, groupname, payBillNumber, amountPerPerson, timesOfContribution, periodOfContribution);

        //Create a db reference called for a single chama

        // A single chama with a unique key
        final DatabaseReference aChama = mFirebaseDatabase.push();
        aChama.setValue(newchama).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Your chama details have been registered", Toast.LENGTH_SHORT).show();

                    //Get group Key and store it as a string to be used while adding users to a group.
                    userGroupId = aChama.getKey();


                    addGroupID();
                    // Redirect to upload photo layout
                    CreateChamaActivity.fragmentManager.beginTransaction().
                            replace(R.id.fragment_container, new AddGroupPhotoFragment(), null).commit();
                } else {
                    Toast.makeText(getActivity(), "There was an Error in writing to firebase" + task.getException(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    //This class adds a group ID to a chama Member once they create the group,
    //to show that they belong to the group now
    public void addGroupID() {
        String CurrentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // String userID = CurrentUser.getUid();

        //Get group name
        String groupname = chamaname.getEditText().getText().toString();

        //To be used in the getter below
        groupName = groupname;
        //Use the user ID to get reference to the profile of the current user
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userprofile = database.getReference("Users").child(phone).child("AdminOfGroup");

        //Map holds the new group info to which a user belongs
        Map<String, Object> userGroup = new HashMap<>();
        userGroup.put(groupname, userGroupId);

        //Update the user info
        userprofile.setValue(userGroup)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "It works", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "It dont works", Toast.LENGTH_LONG).show();
                    }
                });

    }

    public static String getGroupName() {

        return groupName;
    }


}
