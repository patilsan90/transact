package com.algo.transact.login;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.algo.transact.AppState;
import com.algo.transact.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterUserFragment extends Fragment {


    public RegisterUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_user, container, false);

        Button sign_in_button = (Button) view.findViewById(R.id.sign_up_button);
        sign_in_button.setOnClickListener(new SignUpListener());
        AppState.getInstance().loginState = AppState.LOGIN_STATE.REGISTRATION_VIEW;

        return view;
    }

    private class SignUpListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Log.i("Login","SignUp Clicked");
            Intent myIntent = new Intent(getActivity(), VerifyMobileNoActivity.class);
            myIntent.putExtra("name", "Sample name"); //Optional parameter pass parameters
            getActivity().startActivity(myIntent);
            //getActivity().finish(); check this

        }
    }
}
