package com.example.nuhin13.sheba_1st.Fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nuhin13.sheba_1st.R;
import com.example.nuhin13.sheba_1st.SQLite.DatabaseHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterFragment extends Fragment  implements View.OnClickListener{

    private AppCompatButton btn_register;
    private EditText et_email, et_phone, et_password, et_confirm_password ;
    private TextView tv_login;
    private ProgressBar progress;
    private boolean PhoneExits;

    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register,container,false);

        db = new DatabaseHelper(getActivity());
        initViews(view);
        return view;
    }

    private void initViews(View view){

        btn_register = (AppCompatButton)view.findViewById(R.id.btn_register);
        tv_login = (TextView)view.findViewById(R.id.tv_login);
        et_phone = (EditText)view.findViewById(R.id.et_phone_reg);
        et_email = (EditText)view.findViewById(R.id.et_email_reg);
        et_password = (EditText)view.findViewById(R.id.et_password_reg);
        et_confirm_password = (EditText)view.findViewById(R.id.et_confirm_password_reg);

        progress = (ProgressBar)view.findViewById(R.id.progress);

        btn_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_login:
                goToLogin();
                break;

            case R.id.btn_register:

                String phone = et_phone.getText().toString();
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                String confirm_password = et_confirm_password.getText().toString();

                PhoneExits = db.CheckPhoneInDatabase(phone);

                if (!phone.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirm_password.isEmpty()) {
                    if (!PhoneExits) {
                        if (emailValidator(email)) {
                            if (password.equals(confirm_password)) {
                                if (password.length() > 6) {
                                    progress.setVisibility(View.VISIBLE);
                                    registerProcess(phone, email, password);
                                } else {
                                    Snackbar.make(getView(), "Password 6 Characters", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(getView(), "Password Not Match..", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(getView(), "Email is not valid", Snackbar.LENGTH_LONG).show();
                        }
                    } else{
                        Snackbar.make(getView(), "Phone is Already in Database", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void registerProcess(String phone, String email, String password){

        db.addUser(phone,email,password);
        Toast.makeText(getActivity(),"Success ",Toast.LENGTH_LONG).show();
        goToLogin();

    }

    private void goToLogin(){

        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,login);
        ft.commit();
    }
}
