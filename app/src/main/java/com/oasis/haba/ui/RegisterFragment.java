package com.oasis.haba.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.oasis.haba.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterFragment extends Fragment implements View.OnClickListener {


    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPhone;
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputConfirmPassword;


    public RegisterFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //Inflate the Layout of this fragment

        View view;
        view = inflater.inflate(R.layout.signup_fragment, container, false);

        final Button signUpButton = view.findViewById(R.id.signup_btn);
        textInputEmail = view.findViewById(R.id.text_input_email);
        textInputUsername = view.findViewById(R.id.text_input_username);
        textInputPassword = view.findViewById(R.id.text_input_password);
        textInputConfirmPassword = view.findViewById(R.id.text_input_confirmPassword);
        textInputPhone = view.findViewById(R.id.text_input_phone);
        signUpButton.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {

        //Only shows toast message if all inputs are available
//        if (!validateEmail() | !validateUsername() | !validatePassword() | !isPasswordMatching() | !isValidPhone())
//            return;
//
//        String input = "Email: " + textInputEmail.getEditText().getText().toString();
//        Toast.makeText(getActivity(), input, Toast.LENGTH_SHORT).show();

        //Or once the data is verified you can push it to Firebase

        // Once Validated go to Homepage as follows:




    }

    //
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

    private boolean validateUsername() {
        String usernameInput = textInputUsername.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()) {
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

    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.* [@#$%^&+=!])(?=\\S+$)$";
        // Use 8 or more characters with a mix of letters, numbers & symbols
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(passwordInput);


        if (passwordInput.isEmpty() | passwordInput.length() <= 8 | !matcher.matches()) {
            textInputPassword.setError("Use 8 or more characters with a mix of letters, numbers & symbols");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }

    }

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
