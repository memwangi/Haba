package com.oasis.haba.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.oasis.haba.Database.DatabaseHelper;
import com.oasis.haba.DbModel.memberDetails;
import com.oasis.haba.R;
import com.oasis.haba.util.CircleTransform;
import com.oasis.haba.util.memberPicsAdapter;
import com.squareup.picasso.Picasso;
import com.twigafoods.daraja.Daraja;
import com.twigafoods.daraja.DarajaListener;
import com.twigafoods.daraja.model.AccessToken;
import com.twigafoods.daraja.model.LNMExpress;
import com.twigafoods.daraja.model.LNMResult;

import java.lang.reflect.Array;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chama extends AppCompatActivity {

    private static String TAG = "Chama Activity";
    //private TextView chamaName;
    private List<memberDetails> member = new ArrayList<>();
    private EditText MemberNumber, DepositAmount;
    private RecyclerView recyclerView;
    private TextView groupTitle;
    private Button btnAddMember, btnsendContribution;
    private FirebaseDatabase mInstance;
    private DatabaseReference dbRef;
    private ImageView groupPicture;
    private FirebaseAuth mAuth;
    private DatabaseHelper db;
    Daraja daraja;
    private String ThegroupName = FormCreateChama.getGroupName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chama);

        db = new DatabaseHelper(getApplicationContext());
        btnAddMember = findViewById(R.id.addMember);
        btnsendContribution = findViewById(R.id.Pay);
        mAuth = FirebaseAuth.getInstance();
        mInstance = FirebaseDatabase.getInstance();
        dbRef = mInstance.getReference();
        groupTitle = findViewById(R.id.groupTitle);
        DepositAmount = findViewById(R.id.Amount);
        MemberNumber = findViewById(R.id.etNumber);


        //Display Member Profile Pictures in the recycler View
        recyclerView = findViewById(R.id.usersRecyclerView);
        memberPicsAdapter picsAdapter = new memberPicsAdapter(getApplicationContext(),member);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(picsAdapter);

        //Init Daraja
        // THIS IS SANDBOX DEMO
        daraja = Daraja.with("RAfScigeYk6pfGvPL27EjvnpROVHhJvv", "YrlXogGsxrcZWJv2", new DarajaListener<AccessToken>() {
            @Override
            public void onResult(@NonNull AccessToken accessToken) {
                Log.i(this.getClass().getSimpleName(), accessToken.getAccess_token());
                //Toast.makeText(getApplicationContext(), "TOKEN : " + accessToken.getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Log.e(this.getClass().getSimpleName(), error);
            }
        });

        //Onclick listener for send payment
        btnsendContribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
                makePayment();
            }
        });


        //Onclick listener for add memmbers
        btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMembertoGroup();
            }
        });


    }

    public void makePayment() {

        //Get Phone Number
        String phoneNumber = String.valueOf(db.getNumber().get(mAuth.getInstance().getUid()));

        //Get Amount
        String aDepositAmount = DepositAmount.getText().toString().trim();
        //New Object
        LNMExpress lnmExpress = new LNMExpress(
                "174379",
                "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",  //https://developer.safaricom.co.ke/test_credentials
                aDepositAmount,
                "254716953405",
                "174379",
                "254716953405",
                "http://mycallbackurl.com/checkout.php",
                "HabahABA",
                "Chama Contribution"
        );


        daraja.requestMPESAExpress(lnmExpress,
                new DarajaListener<LNMResult>() {
                    @Override
                    public void onResult(@NonNull LNMResult lnmResult) {
                        Toast.makeText(getApplicationContext(), "Twende Kazi", Toast.LENGTH_SHORT).show();
                        Log.i(this.getClass().getSimpleName(), lnmResult.ResponseDescription);
                    }

                    @Override
                    public void onError(String error) {
                        Log.i(this.getClass().getSimpleName(), error);
                    }
                }
        );


    }


    public void addMembertoGroup() {


        //I thought it best to get user number, then fetch the group key from the user number
        //The user number of the group admin is stored locally, using SQLite
        final String adminKey = FirebaseAuth.getInstance().getUid();

        Object userDetails = db.getNumber().get(adminKey);

        final String aNumber = String.valueOf(userDetails);


        DatabaseReference getKey = dbRef.child("Users").child(aNumber).child("AdminOfGroup");
        getKey.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Object _key = dataSnapshot.getKey();

               // final String groupKey = (String.valueOf(_key));

                final String groupKey = FormCreateChama.getUserGroupId();

                Log.i("Chama Activity", "Group Key Fetched");

                final DatabaseReference refGroupKey = dbRef.child("GroupAndMembers").child(groupKey);

                //Use the entered number to get more user details of the member to be added from the users database,
                //as long as the user is registered he will already be stored there

                final String amemberNumber = MemberNumber.getText().toString().trim();

                final DatabaseReference userRef = dbRef.child("Users").child(amemberNumber);
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Object key = dataSnapshot.child("userID").getValue();
                        if (key != null) {
                            //Add user Auth Key and his number to a node with the group in which they belong
                            String memberkey = String.valueOf(key);

                            Map<String, Object> objectMap = new HashMap<>();
                            objectMap.put(memberkey, amemberNumber);
                            refGroupKey.updateChildren(objectMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(), "User added", Toast.LENGTH_LONG).show();

                                            //Add the list of groups to which the user belongs, to the userNode
                                            //First get the group name
                                            //Map to store group name and group ID
                                            Map<String, Object> mapGroup = new HashMap<>();
                                            mapGroup.put(groupKey,ThegroupName );
                                            DatabaseReference userGroups = dbRef.child("Users").child(amemberNumber).child("MemberOfGroup");
                                            userGroups.updateChildren(mapGroup)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            MemberNumber.setText("");
                                                            Toast.makeText(getApplicationContext(), "Launched", Toast.LENGTH_SHORT)
                                                                    .show();

                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(getApplicationContext(), "Tumejaribu", Toast.LENGTH_SHORT)
                                                                    .show();
                                                        }
                                                    });


                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "User already registered" + e.getMessage(), Toast
                                                    .LENGTH_SHORT).show();
                                        }
                                    });


                        } else {
                            Toast.makeText(getApplicationContext(), "User is not registered", Toast.LENGTH_LONG).show();
                            MemberNumber.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "User not added" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to retrieve group key");
            }
        });


    }


    //Prepare member data, fetches the data to be input into the recycler view that displays the list of members
    public void prepareMemberData(){
        //Get the phone number:Since the phone number is stored with the group key in child Groups and Members
        //I can access it if only i have the group key



    }

}
