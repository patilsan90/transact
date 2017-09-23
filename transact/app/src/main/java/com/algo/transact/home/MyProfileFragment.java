package com.algo.transact.home;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.algo.transact.AppConfig.SessionManager;
import com.algo.transact.R;
import com.algo.transact.login.LoginActivity;
import com.algo.transact.login.UserDetails;

import java.util.Calendar;
import java.util.HashMap;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment implements View.OnClickListener{


    public MyProfileFragment() {
        // Required empty public constructor
    }
    private LinearLayout llprofileDetailsLayout;

    private EditText etFirstName;
    private EditText etLastName;

    private TextView tvFirstNameLable;
    private TextView tvLastNameLable;


    private EditText etMobileNo;

    private EditText etDisplayName;

    private EditText etEmailID;
    private TextView etDob;
    private RadioButton rdGenderMale;
    private RadioButton rdGenderFemale;
    private TextView tvOtpVerify;
    UserDetails userDetails;


    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private SessionManager session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        TextView tvEditProfile = (TextView) view.findViewById(R.id.my_profile_tv_edit_profile);
        tvEditProfile.setOnClickListener(this);

        Button btSaveProfile = (Button) view.findViewById(R.id.my_profile_bt_save_profile);
        btSaveProfile.setOnClickListener(this);

        etFirstName = (EditText) view.findViewById(R.id.my_profile_et_first_name);
        etLastName = (EditText) view.findViewById(R.id.my_profile_et_last_name);

        tvFirstNameLable = (TextView) view.findViewById(R.id.my_profile_tv_first_name_lable);
        tvLastNameLable = (TextView) view.findViewById(R.id.my_profile_tv_last_name_lable);

        etMobileNo = (EditText) view.findViewById(R.id.my_profile_et_mobile_no);
        etEmailID = (EditText) view.findViewById(R.id.my_profile_et_email_id);
        etDob = (TextView) view.findViewById(R.id.my_profile_et_date_of_birth);
        etDob.setOnClickListener(this);
        etDisplayName  = (EditText) view.findViewById(R.id.my_profile_et_display_name);
        TextView tvSignOut = (TextView) view.findViewById(R.id.my_profile_tv_sign_out);
    tvSignOut.setOnClickListener(this);

        tvOtpVerify = (TextView) view.findViewById(R.id.my_profile_tv_otp_verify);

        rdGenderMale = (RadioButton) view.findViewById(R.id.my_profile_rb_gender_male);
        rdGenderFemale = (RadioButton) view.findViewById(R.id.my_profile_rb_gender_female);

        llprofileDetailsLayout = (LinearLayout) view.findViewById(R.id.my_profile_ll_details_layout);

        LinearLayout llprofileDetailsClickLayout = (LinearLayout) view.findViewById(R.id.my_profile_ll_click_details);
        llprofileDetailsClickLayout.setOnClickListener(this);

        userDetails = UserDetails.getUserPreferences(this.getActivity());

        if(userDetails.familyName!=null)
        etFirstName.setText(""+userDetails.firstName +" "+userDetails.familyName);
        else if(userDetails.firstName!=null)
            etFirstName.setText(""+userDetails.firstName);
        else if(userDetails.displayName!=null)
            etFirstName.setText(""+userDetails.displayName);

        if(userDetails.mobNo!=null)
        etMobileNo.setText(""+userDetails.mobNo);

        makeDetailsInvisible();
        session= new SessionManager(getApplicationContext());

        return view;
    }

    public void makeDetailsVisible()
    {
        ViewGroup.LayoutParams params = llprofileDetailsLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        llprofileDetailsLayout.setVisibility(View.VISIBLE);
        llprofileDetailsLayout.setLayoutParams(params);

        params = tvFirstNameLable.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        tvFirstNameLable.setVisibility(View.VISIBLE);

        params = tvLastNameLable.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        tvLastNameLable.setVisibility(View.VISIBLE);


        etFirstName.setEnabled(true);

        params = etLastName.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        etLastName.setLayoutParams(params);

        etMobileNo.setEnabled(true);

        tvOtpVerify.setVisibility(View.VISIBLE);
    }

    private void setUserDetails()
    {


        if(userDetails.familyName!=null)
        etFirstName.setText(""+userDetails.firstName);

        if(userDetails.familyName!=null)
        etLastName.setText(""+userDetails.familyName);

        if(userDetails.mobNo!=null)
        etMobileNo.setText(""+userDetails.mobNo);

        if(userDetails.emailID!=null)
        etEmailID.setText(""+userDetails.emailID);

        if(userDetails.dob!=null)
            if(userDetails.dob.trim().length()!=0)
                etDob.setText(""+userDetails.dob);

        if(userDetails.displayName!=null)
        etDisplayName.setText(""+userDetails.displayName);

/*
        if(SQLiteHandler.GENDER.MALE == SQLiteHandler.getGender(usersDetails.get(SQLiteHandler.KEY_GENDER_AT)))
            rdGenderMale.setSelected(true);
        else if(SQLiteHandler.GENDER.FEMALE == SQLiteHandler.getGender(usersDetails.get(SQLiteHandler.KEY_GENDER_AT)))
            rdGenderFemale.setSelected(true);
*/

    }

    public void makeDetailsInvisible()
    {
        llprofileDetailsLayout.setVisibility(View.INVISIBLE);
        ViewGroup.LayoutParams params = llprofileDetailsLayout.getLayoutParams();
        params.height = 0;
        llprofileDetailsLayout.setLayoutParams(params);

        params = tvFirstNameLable.getLayoutParams();
        params.height = 0;
        tvFirstNameLable.setVisibility(View.VISIBLE);

        params = tvLastNameLable.getLayoutParams();
        params.height = 0;
        tvLastNameLable.setVisibility(View.VISIBLE);

        params = etLastName.getLayoutParams();
        params.height = 0;
        etLastName.setLayoutParams(params);

        etFirstName.setEnabled(false);

        etMobileNo.setEnabled(false);

        if (userDetails.familyName != null)
            etFirstName.setText("" + userDetails.firstName + " " + userDetails.familyName);
        else if (userDetails.firstName != null)
            etFirstName.setText("" + userDetails.firstName);
        else if (userDetails.displayName != null)
            etFirstName.setText("" + userDetails.displayName);

        tvOtpVerify.setVisibility(View.INVISIBLE);


    }

    public void signOut(View v)
    {
        session.logoutUser(session);
        UserDetails.signOut(getActivity());

        Intent intent = new Intent(this.getActivity(), LoginActivity.class);

        startActivity(intent);
        getActivity().finish();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.my_profile_tv_sign_out:
            {
                signOut(null);
                break;
            }
            case R.id.my_profile_ll_click_details:
            case R.id.my_profile_tv_edit_profile:
            {
                if(llprofileDetailsLayout.getVisibility()== View.VISIBLE) {
                    makeDetailsInvisible();
                }
                else
                {
                    setUserDetails();
                    makeDetailsVisible();

                    calendar = Calendar.getInstance();
                    year = calendar.get(Calendar.YEAR);

                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                   // showDate(year, month+1, day);

                }
                break;
            }
            case R.id.my_profile_bt_save_profile:
            {
                ProgressDialog progress=new ProgressDialog(getActivity());
                progress.setMessage("wait... ");
                progress.setTitle("Updating profile");
                progress.show();
                userDetails.firstName = etFirstName.getText().toString().trim();
                userDetails.displayName =etDisplayName.getText().toString().trim();
                userDetails.familyName =etLastName.getText().toString().trim();
                userDetails.emailID =etEmailID.getText().toString().trim();
                userDetails.dob=etDob.getText().toString().trim();
                userDetails.gender = "male";
                userDetails.setUserPreferences(getActivity());
                progress.dismiss();
                Toast.makeText(getActivity(),"Your profile is updated", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.my_profile_et_date_of_birth:
            {
                DatePickerDialog picker = new DatePickerDialog(this.getActivity(),
                        myDateListener, year, month, day);
                picker.show();

                break;

            }

        }
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        etDob.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
}
