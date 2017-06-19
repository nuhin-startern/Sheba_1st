package com.example.nuhin13.sheba_1st.Fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.nuhin13.sheba_1st.Adapter.Service_holder_recycle_view_Adapter;
import com.example.nuhin13.sheba_1st.Informations.Information_for_service_holder;
import com.example.nuhin13.sheba_1st.R;
import com.example.nuhin13.sheba_1st.SQLite.Constants;
import com.example.nuhin13.sheba_1st.SQLite.DatabaseHelper;

import java.util.ArrayList;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private SharedPreferences pref;
    private AppCompatButton btn_logout;

    RecyclerView recyclerView;

    ArrayList<Information_for_service_holder> Dataadapter;

    Service_holder_recycle_view_Adapter RecycleViewAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        initViews(view);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ImageButton imageButton = (ImageButton) toolbar.findViewById(R.id.toolbar_edit);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"oa",Toast.LENGTH_LONG).show();
                goEdit();

            }
        });

        DatabaseHelper db = new DatabaseHelper(getActivity());
        Dataadapter = db.getAllUsers();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_service_holder);
        RecycleViewAdapter = new Service_holder_recycle_view_Adapter(getActivity(), Dataadapter);
        recyclerView.setAdapter(RecycleViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        pref = getActivity().getSharedPreferences("prefName", Context.MODE_PRIVATE);

    }

    private void initViews(View view){

        btn_logout = (AppCompatButton)view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_logout:
                logout();
                break;
        }
    }

    private void logout() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constants.IS_LOGGED_IN,false);
        editor.apply();
        goToLogin();
    }

    private void goToLogin(){

        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,login);
        ft.commit();
    }

    private void goEdit(){

        Fragment edit = new EditFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,edit);
        ft.commit();
    }

}
