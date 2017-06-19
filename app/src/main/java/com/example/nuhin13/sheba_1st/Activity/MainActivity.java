package com.example.nuhin13.sheba_1st.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nuhin13.sheba_1st.Fragment.LoginFragment;
import com.example.nuhin13.sheba_1st.Fragment.ProfileFragment;
import com.example.nuhin13.sheba_1st.R;
import com.example.nuhin13.sheba_1st.SQLite.Constants;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        pref = getSharedPreferences("prefName", Context.MODE_PRIVATE);
        initFragment();
    }

    private void initFragment(){
        Fragment fragment;
        if(pref.getBoolean(Constants.IS_LOGGED_IN,false)){
            fragment = new ProfileFragment();
        }else {
            fragment = new LoginFragment();
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,fragment);
        ft.commit();
    }

}
