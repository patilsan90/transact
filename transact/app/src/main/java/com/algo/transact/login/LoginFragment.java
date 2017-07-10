package com.algo.transact.login;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.AppConfig.Login;
import com.algo.transact.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{

    RegisterUserFragment registerUserFragment;

    EditText email;
    EditText mobNo;
    public ProgressDialog pDialog;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button sign_in_button = (Button) view.findViewById(R.id.sign_in_button);
        sign_in_button.setOnClickListener(this);

        email = (EditText) view.findViewById(R.id.email);
        mobNo = (EditText) view.findViewById(R.id.password);

        email.setText("a");
        mobNo.setText("a");

        pDialog = new ProgressDialog(AppState.getInstance().loginActivity);
        return view;

    }

    @Override
    public void onClick(View v) {

        Log.i(AppState.TAG, "Clickeddddd ");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (registerUserFragment == null)
            registerUserFragment = new RegisterUserFragment();

        signin();

      //  fragmentTransaction.replace(R.id.login_fragment_place, registerUserFragment);
       // fragmentTransaction.commit();

    }

        private void signin() {
            String emailStr = email.getText().toString().trim();
            String password = mobNo.getText().toString().trim();

            // Check for empty data in the form
            if(!emailStr.isEmpty() && !password.isEmpty())
            {
                checkLogin(emailStr, password);
            }
    }

    private void checkLogin(String emailStr, String password) {
        Login login =new Login(this);
        login.checkLogin(emailStr, password);
    }

    public void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
