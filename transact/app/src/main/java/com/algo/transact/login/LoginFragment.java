package com.algo.transact.login;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.algo.transact.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    RegisterUserFragment registerUserFragment;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button sign_in_button = (Button) view.findViewById(R.id.sign_in_button);
        sign_in_button.setOnClickListener(new SignInListener());

        return view;

    }

    private class SignInListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if (registerUserFragment == null)
                registerUserFragment = new RegisterUserFragment();

            fragmentTransaction.replace(R.id.login_fragment_place, registerUserFragment);
            fragmentTransaction.commit();

        }
    }

}
