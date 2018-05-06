package com.hobbythai.android.findfriend.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hobbythai.android.findfriend.R;
import com.hobbythai.android.findfriend.utility.MyAlert;

public class MainFragment extends Fragment {

    private ProgressDialog progressDialog;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        check status
        checkStatus();


//        Register controller
        registerController();

//        signin controller bind widget
        signinController();


    } //main method

    private void signinController() {

        Button button = getView().findViewById(R.id.btnSignIn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("Please Wait...");
                //progressDialog.setMessage("Check Authen few minus!");
                progressDialog.show();

                EditText emailEditText = getView().findViewById(R.id.edtEmail);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);

                String emailString = emailEditText.getText().toString().trim();
                String passwordString = passwordEditText.getText().toString().trim();

                MyAlert myAlert = new MyAlert(getActivity());

                if (emailString.isEmpty()||passwordString.isEmpty()) {
                    //have space

                    progressDialog.dismiss();

                    myAlert.normalDialog(getString(R.string.title_space)
                            ,getString(R.string.message_space));
                } else {
                    //no space already data
                    checkEmailAndPass(emailString,passwordString);
                }

            }
        });
    }

    private void checkEmailAndPass(String emailString, String passwordString) {

        progressDialog.dismiss();

        // sign in Firebase ***
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
//                            Sign in true go new fragment
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.contentMainFragment, new ServiceFragment())
                                    .commit();

                        } else {
//                            sign in false

                            MyAlert myAlert = new MyAlert(getActivity());
                            myAlert.normalDialog("Can't Sign In!!",
                                    task.getException().getMessage().toString());
                        }

                    }
                });

    }

    private void checkStatus() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentMainFragment, new ServiceFragment())
                    .commit();

        }
    }

    private void registerController() {
        TextView textView = getView().findViewById(R.id.txtRegister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                replace fragment
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        return view;
    }
}
