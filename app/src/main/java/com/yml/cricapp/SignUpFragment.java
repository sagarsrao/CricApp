package com.yml.cricapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignInFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private EditText mEmail;
    private EditText mPassword;

    private Button mSignup, mResetPassword;

    private FirebaseAuth firebaseAuth;



    private OnFragmentInteractionListener mListener;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override

    //Just inflate the view in OnCreateView and do the rest of the process being the same in activities
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mEmail = (EditText) view.findViewById(R.id.edUserName); //email
        mEmail.addTextChangedListener(new CustomTextWatcher(mEmail));
        mPassword = (EditText) view.findViewById(R.id.edpassword);//password
        mPassword.addTextChangedListener(new CustomTextWatcher(mPassword));
        mSignup = (Button) view.findViewById(R.id.btSignUp);//signUp
        mResetPassword = (Button) view.findViewById(R.id.btResetPassword);
        firebaseAuth = FirebaseAuth.getInstance();


        return view;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class CustomTextWatcher implements TextWatcher {

        private EditText mEditText;

        public CustomTextWatcher(EditText e) {
            mEditText = e;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            //OnbuttonClick
            mSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkFieldsForValidation();

                }
            });


        }


    }

    private void checkFieldsForValidation() {

        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString();

        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (!email.matches(emailPattern) && email.toString().length() > 0) {
            Toast.makeText(getActivity(), "Invalid email address", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "Invalid password", Toast.LENGTH_SHORT).show();


        }
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Toast.makeText(getActivity(), "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                        if (!task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            //startActivity(new Intent(getActivity(), MainActivity.class));


                            //finish();

                            Toast.makeText(getActivity(),"SignUpSuccess",Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }
}
