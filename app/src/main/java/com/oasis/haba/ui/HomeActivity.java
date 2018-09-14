package com.oasis.haba.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.pix.Pix;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.oasis.haba.Database.DatabaseHelper;
import com.oasis.haba.DbModel.User;
import com.oasis.haba.R;
import com.oasis.haba.util.CircleTransform;
import com.oasis.haba.util.ViewPagerAdapter;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    public static FragmentManager fragmentManager;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private ArrayList<String> imagePath;
    private TextView DrawerUserName;
    private TextView DrawerUserEmail;
    private ImageView DrawerUserPicture;
    private StorageReference mStorageRef;
    private FirebaseStorage storage;
    private ProgressBar mProgress;
    private String phone;
    Context context;
    private Uri downloadUrl;
    private DatabaseHelper db;
    private NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        String userID = mAuth.getUid();

        db = new DatabaseHelper(this);

        //Reference for storing profile pictures under a user ID of the uploader.
        storage = FirebaseStorage.getInstance();
        Map userMap = db.getNumber();
        Object number = (String) userMap.get(userID);
        mStorageRef =  storage.getReference("User Pictures").child(String.valueOf(number));

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        setUpDrawer();
        setNavigationViewListener();

        fragmentManager = getSupportFragmentManager();

        if (findViewById(R.id.homeview) != null) {

            if (savedInstanceState != null) {

                return;
            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

           HomeFragment homeFragment = new HomeFragment();

           fragmentTransaction.add(R.id.homeview, homeFragment, null);
           fragmentTransaction.commit();

            setUpDrawer();
            setNavigationViewListener();


        }
    }





    public void setUpDrawer() {


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerUserName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_name);
        DrawerUserName.setText("Loah Sabat");
        DrawerUserEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_email);
        DrawerUserEmail.setText("loahsabat@gmail.com");


    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (mToggle.onOptionsItemSelected(item)){


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Logout
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Handle navigation item clicks here
        switch (item.getItemId()) {
            case R.id.logout: {
                FirebaseAuth.getInstance().signOut();
                //Redirect to login after logging out
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
                }
            case R.id.changephoto: {
                selectPicture();
                break;
            }
            case R.id.itemchamas: {
                Intent intent = new Intent(getApplicationContext(),Chama.class);
                startActivity(intent);
                break;
            }
        }
//        Close Navigation drawer
//        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void selectPicture(){
        Pix.start(this,1);
    }

    //Onactivity result to fetch picture
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            imagePath = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);

            uploadPicture();

        }
        else {
            Toast.makeText(this,"Image not selected",Toast.LENGTH_SHORT).show();
        }
    }

    //Method to set navigation listener
    private void setNavigationViewListener() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
    }

    //User profile picture
    public void uploadPicture() {

        //Set profile picture
        Uri imageUri = Uri.fromFile(new File(imagePath.get(0)));

        DrawerUserPicture = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.userPicture);

        //Then upload profile picture
        mStorageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        // Get a URL to the uploaded content

                        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                downloadUrl = uri;
                                //Use Picasso to set downloaded image into image view.
                                Picasso.get()
                                        .load(downloadUrl)
                                        .transform(new CircleTransform())
                                        .resize(190, 190)
                                        .centerCrop()
                                        .into(DrawerUserPicture);
                            }

                            });
                        Toast.makeText(getApplicationContext(),"Image uploaded successfully",Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(getApplicationContext(),"Image not Uploaded" ,Toast.LENGTH_LONG).show();
                    }
                });

    }



}

