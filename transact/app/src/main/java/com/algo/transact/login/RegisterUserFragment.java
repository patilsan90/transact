package com.algo.transact.login;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.algo.transact.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterUserFragment extends Fragment {

   private User newUser;

    public RegisterUserFragment() {
        // Required empty public constructor
        newUser = new User();
    }

    EditText etName;
    EditText etMobileNo;
    AutoCompleteTextView etEmail;
    EditText etPassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_user, container, false);

        Button sign_in_button = (Button) view.findViewById(R.id.register_user_bt_sign_up);
        sign_in_button.setOnClickListener(new SignUpListener());
        LoginActivity.visibleForm = LoginActivity.VISIBLE_FORM.REGISTRATION_FORM;
        etName=(EditText) view.findViewById(R.id.register_user_et_name);
        etMobileNo = (EditText) view.findViewById(R.id.register_user_et_mobile_no);
        etEmail = (AutoCompleteTextView) view.findViewById(R.id.register_user_actv_email);
        etPassword= (EditText) view.findViewById(R.id.register_user_et_password);

        etName.setText("sandeep");
        etMobileNo.setText("9423346174");
        etEmail.setText("sansad@gmail.com");
        etPassword.setText("9423346174");

        return view;
    }

    private class SignUpListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Log.i("Login","SignUp Clicked");

            newUser.displayName = etName.getText().toString();
            newUser.firstName = etName.getText().toString();
            newUser.mobileNo = etMobileNo.getText().toString();
            newUser.emailID = etEmail.getText().toString();
            newUser.password = etPassword.getText().toString();
            newUser.loginType = User.LOGIN_OTIONS.OTHER;

            Intent myIntent = new Intent(getActivity(), VerifyMobileNoActivity.class);
            myIntent.putExtra("newUser", newUser); //Optional parameter pass parameters
            getActivity().startActivity(myIntent);
        }
    }


}
