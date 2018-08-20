package com.oasis.haba.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;

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
import com.oasis.haba.R;

import static android.support.constraint.Constraints.TAG;

public class LoginFragment extends Fragment {
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button loginBtn;
    private Button signUpBtn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private ProgressBar mProgress;
    Context context;

    public LoginFragment() {
        //Constructor Default

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view;
        view = inflater.inflate(R.layout.login_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();
        mEmail = view.findViewById(R.id.emailEditText);
        mPassword = view.findViewById(R.id.passwordEditText);
        loginBtn = view.findViewById(R.id.login);
        signUpBtn = view.findViewById(R.id.register);
        mProgress = view.findViewById(R.id.progressBar);



        signUpBtn.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             MainActivity.fragmentManager.beginTransaction().
                                                     replace(R.id.landingPage,new RegisterFragment(),null).
                                                     addToBackStack(null).commit();

                                         }

                                     });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getEditText().getText().toString();
                final String password = mPassword.getEditText().getText().toString();

                //Ensure email and password are not empty
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getActivity(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getActivity(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mProgress.setVisibility(View.VISIBLE);

                //Authenticate User
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    mProgress.setVisibility(View.GONE);
                                    startActivity(new Intent(getActivity(), HomeActivity.class));

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(getActivity(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    mProgress.setVisibility(View.GONE);

                                }

                            }
                        });
            }
       });

        return view;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        if (mAuth.getCurrentUser() != null) {
//            startActivity(new Intent(getActivity(), HomeActivity.class));
//        }
//
//    }


}
