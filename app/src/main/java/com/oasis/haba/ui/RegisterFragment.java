package com.oasis.haba.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.RelativeDateTimeFormatter;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oasis.haba.Database.DatabaseHelper;
import com.oasis.haba.DbModel.User;
import com.oasis.haba.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {

    private String TAG = "Registration";
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPhone;
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputConfirmPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private ProgressBar mProgress;
    private DatabaseHelper db;
   // private String UserID;
    private static String phoneNumber;
    Context context;




    public RegisterFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //Inflate the Layout of this fragment

        View view;
        view = inflater.inflate(R.layout.signup_fragment, container, false);
        mAuth = FirebaseAuth.getInstance();

        db = new DatabaseHelper(getActivity());

        final Button signUpButton = view.findViewById(R.id.signup_btn);
        textInputEmail = view.findViewById(R.id.text_input_email);
        textInputUsername = view.findViewById(R.id.text_input_username);
        textInputPassword = view.findViewById(R.id.text_input_password);
        textInputConfirmPassword = view.findViewById(R.id.text_input_confirmPassword);
        textInputPhone = view.findViewById(R.id.text_input_phone);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mProgress = view.findViewById(R.id.progressBar);

        phoneNumber = textInputPhone.getEditText().getText().toString().trim();

        //To store the other user properties we need to create an instance of the database and reference a user node.
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        //Signup users with email and password and validate them before signing up.
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = textInputEmail.getEditText().getText().toString().trim();
                String password = textInputPassword.getEditText().getText().toString().trim();

                final String username = textInputUsername.getEditText().getText().toString().trim();
                final String  phoneNumber = textInputPhone.getEditText().getText().toString().trim();
                final String[] userID = new String[1];

                //Validate values
                if (!validateEmail() | !validateUsername() | !validatePassword() | !isPasswordMatching() | !isValidPhone())

                    return;



                    mProgress.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(getActivity(), "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();


                            if (task.isSuccessful()) {

                                //Store user phone into DB

                                db.insertNumber(mAuth.getCurrentUser().getUid(),phoneNumber);
                                Toast.makeText(getActivity(),"We did it",Toast.LENGTH_SHORT).show();

                                //Create new user with details
                                userID[0] = mAuth.getUid();

                                User user = new User(email, username, userID[0]);

                                //Send data to users node
                                DatabaseReference refDB = FirebaseDatabase.getInstance().getReference("Users").child(phoneNumber);

                                refDB.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                            mProgress.setVisibility(View.GONE);

                                            //Redirect to log in
                                            MainActivity.fragmentManager.beginTransaction().
                                                    replace(R.id.landingPage, new LoginFragment(), null).commit();
                                        } else {
                                            Toast.makeText(getActivity(), "There was an Error in writing to firebase", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {

                                Toast.makeText(getActivity(), "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                                mProgress.setVisibility(View.GONE);
                            }
                        }
                    });



            }
        });

        return view;
    }


    //True if email is invalid
    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Field can't be empty");
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputEmail.setError("Invalid email address!");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    //True if phone is not valid
    private boolean isValidPhone() {
        String phoneNumber = textInputPhone.getEditText().getText().toString().trim();

        if (phoneNumber.isEmpty() | !android.util.Patterns.PHONE.matcher(phoneNumber).matches()) {
            textInputPhone.setError("Please Enter a Valid Phone Number.");
            return false;

        } else {
            textInputPhone.setError(null);

            return true;
        }


    }

    //True if username is not valid
    private boolean validateUsername() {
        String usernameInput = textInputUsername.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(usernameInput)) {
            textInputUsername.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            textInputUsername.setError("Username too long");
            return false;
        } else {
            textInputUsername.setError(null);
            return true;
        }
    }

    //True if password is not valid
    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
//        Pattern pattern;
//        Matcher matcher;

        // Use 8 or more characters with a mix of letters, numbers & symbols
//        pattern = Pattern.compile("/^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])([@$%&#])[0-9a-zA-Z]{4,}$/");
//        matcher = pattern.matcher(passwordInput);


        if (passwordInput.isEmpty() | passwordInput.length() <= 8) {
            textInputPassword.setError("Use 8 or more characters with a mix of letters, numbers & symbols");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }

    }

    //True if password doesn't match.
    public boolean isPasswordMatching() {
        String password = textInputPassword.getEditText().getText().toString().trim();
        String confirmPassword = textInputConfirmPassword.getEditText().getText().toString().trim();

        //Implements the ability to confirm password,by matching it to the password entered.
        Pattern pattern = Pattern.compile(password, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(confirmPassword);

        if (!matcher.matches()) {
            textInputConfirmPassword.setError("Passwords don't match.");

            return false;
        } else {
            textInputConfirmPassword.setError(null);
            return true;
        }


    }






}
