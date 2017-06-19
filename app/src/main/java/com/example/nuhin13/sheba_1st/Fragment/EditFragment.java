package com.example.nuhin13.sheba_1st.Fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.nuhin13.sheba_1st.Informations.Information_for_service_holder;
import com.example.nuhin13.sheba_1st.R;
import com.example.nuhin13.sheba_1st.SQLite.DatabaseHelper;

import java.util.ArrayList;
import java.util.zip.InflaterOutputStream;


public class EditFragment extends Fragment  implements View.OnClickListener{

    private AppCompatButton btn_edit, btn_cancel;
    private EditText et_email, et_phone, et_password, et_confirm_password ;
    private TextView tv_login;
    private ProgressBar progress;

    private SharedPreferences pref;

    private ArrayList<Information_for_service_holder> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit,container,false);
        initViews(view);

        Intent intent = getActivity().getIntent();
        Information_for_service_holder in = (Information_for_service_holder) intent.getSerializableExtra("user");

        return view;
    }

    private void initViews(View view){

        btn_edit = (AppCompatButton)view.findViewById(R.id.btn_edit);
        btn_cancel = (AppCompatButton)view.findViewById(R.id.btn_back);
        tv_login = (TextView)view.findViewById(R.id.tv_login);
        et_phone = (EditText)view.findViewById(R.id.et_phone_edit);
        et_email = (EditText)view.findViewById(R.id.et_email_edit);
        et_password = (EditText)view.findViewById(R.id.et_password_edit);
        et_confirm_password = (EditText)view.findViewById(R.id.et_confirm_password_edit);

        Information_for_service_holder info= new Information_for_service_holder();

        et_phone.setText(info.getPhone()+"ddsf");
       // et_email.setText(Information_for_service_holder.getEmail());
        progress = (ProgressBar)view.findViewById(R.id.progress);

        btn_edit.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_back:
                Toast.makeText(getActivity(),"dadf",Toast.LENGTH_LONG).show();
                goToProfile();
                break;

            case R.id.btn_edit:

                Toast.makeText(getActivity(),"daeqrqrqrdf",Toast.LENGTH_LONG).show();
               // int id = Information_for_service_holder.getId();
                String phone = et_phone.getText().toString();
                String email = et_email.getText().toString();
                String old_password = et_password.getText().toString();
                String new_password = et_confirm_password.getText().toString();

             /*   if(old_password == "" && new_password == ""){
                    upgrateProcess(id, phone, email);

                }else{
                    if(old_password.equals(Information_for_service_holder.getPassword())){
                        upgrateProcessWithPass(id,phone,email,new_password);
                    }else{
                        Snackbar.make(getView(), "Password Not Match..", Snackbar.LENGTH_LONG).show();
                    }
                }*/

                break;

        }

    }

    private void upgrateProcessWithPass(int id, String phone, String email, String password) {

        DatabaseHelper db = new DatabaseHelper(getActivity());
        db.updateUserWithPass(id, phone, email, password);
        Toast.makeText(getActivity(),"Update Success with password",Toast.LENGTH_LONG).show();
        goToLogin();
    }

    private void upgrateProcess(int id, String phone, String email){

        DatabaseHelper db = new DatabaseHelper(getActivity());
        db.updateUser(id, phone,email);
        Toast.makeText(getActivity(),"Update Success ",Toast.LENGTH_LONG).show();
        goToLogin();

    }

    private void goToLogin(){

        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,login);
        ft.commit();
    }

    private void goToProfile(){

        Fragment profile = new ProfileFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,profile);
        ft.commit();
    }
}
