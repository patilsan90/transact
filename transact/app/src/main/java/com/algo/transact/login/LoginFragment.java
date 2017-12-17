package com.algo.transact.login;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.AppConfig.SessionManager;
import com.algo.transact.R;
import com.algo.transact.home.HomeActivity;
import com.algo.transact.server_communicator.listener.ILoginListener;
import com.algo.transact.server_communicator.request_handler.LoginRequestHandler;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, ILoginListener {

    RegisterUserFragment registerUserFragment;

    EditText userId;
    EditText password;
    private ProgressDialog pDialog;

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

        LoginActivity.visibleForm = LoginActivity.VISIBLE_FORM.LOGIN_FORM;

        userId = (EditText) view.findViewById(R.id.login_tv_user_id);
        password = (EditText) view.findViewById(R.id.login_tv_password);

        userId.setText("9423346174");
        password.setText("9423346174");

        pDialog = new ProgressDialog(getActivity());
        return view;
    }

    @Override
    public void onClick(View v) {

        Log.i(AppConfig.TAG, "Clicked");
        if (v.getId() == R.id.sign_in_button) {

            if (registerUserFragment == null)
                registerUserFragment = new RegisterUserFragment();

            signIn();


        }
    }

    private void signIn() {
        String id = userId.getText().toString().trim();
        String password = this.password.getText().toString().trim();

        if (!id.isEmpty() && !password.isEmpty()) {
            showDialog();
            User user = new User();
            if (id.contains("@"))
                user.emailID = id;
            else
                user.mobileNo = id;

            user.password = password;
            user.loginType = User.LOGIN_OTIONS.OTHER;
            LoginRequestHandler.login(user, this);
        } else {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.login_fragment_place, registerUserFragment);
            fragmentTransaction.commit();
        }
    }

    public void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onSuccess(User user) {
        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        hideDialog();
        if (user != null) {
            SessionManager session = new SessionManager(getApplicationContext());
            session.setLogin(true);
            user.setUserPreferences(getActivity());
            Intent intent = new Intent(getActivity(),
                    HomeActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        }
    }

    @Override
    public void onFailure() {
        hideDialog();
        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
    }
}
