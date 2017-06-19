package com.example.nuhin13.sheba_1st.Adapter;

/**
 * Created by Nuhin13 on 5/30/2017.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nuhin13.sheba_1st.Activity.MainActivity;
import com.example.nuhin13.sheba_1st.Informations.Information_for_service_holder;
import com.example.nuhin13.sheba_1st.R;
import java.util.ArrayList;

import static android.R.attr.dial;

public class Service_holder_recycle_view_Adapter extends RecyclerView.Adapter<Service_holder_recycle_view_Adapter.MyViewHolder> {

    private Context context;

    private ArrayList<Information_for_service_holder> data;
    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;
    private LayoutInflater inflater;

    private int previousPosition = 0;

    public Service_holder_recycle_view_Adapter(Context context, ArrayList<Information_for_service_holder> data) {

        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        View view = inflater.inflate(R.layout.service_holder_card, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int position) {

        myViewHolder.service_holder_phone.setText(data.get(position).getPhone());
        myViewHolder.service_holder_email.setText(data.get(position).getEmail());

        previousPosition = position;

        final int currentPosition = position;
        final Information_for_service_holder infoData = data.get(position);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView service_holder_phone;
        TextView service_holder_email;
        ImageButton call;

        public MyViewHolder(View itemView) {
            super(itemView);
            service_holder_phone = (TextView) itemView.findViewById(R.id.txt_service_holder_phone);
            service_holder_email = (TextView) itemView.findViewById(R.id.txt_service_holder_email);
            call =(ImageButton)itemView.findViewById(R.id.call_customer);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context context1 = v.getContext();
                    Toast.makeText( context1, getAdapterPosition() + " is clicked!", Toast.LENGTH_SHORT).show();
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    //deleteItem(getAdapterPosition());
                    Context context1 = v.getContext();
                    Toast.makeText( context1, getAdapterPosition() + " is clicked Long & Deleted!", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

/*            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context1 = v.getContext();
                    Toast.makeText( context1, getAdapterPosition() + " is clicked Long & Deleted!", Toast.LENGTH_SHORT).show();
                }
            });*/

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String phoneNumber = data.get(getAdapterPosition()).getPhone();
                    String dial = "tel:" + phoneNumber;
                    Toast.makeText(context,dial,Toast.LENGTH_LONG).show();
                    Intent callIntent;
                    callIntent=new Intent(Intent.ACTION_CALL, Uri.parse(dial));
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CALL_PHONE},MAKE_CALL_PERMISSION_REQUEST_CODE);
                    }else {
                        context.startActivity(callIntent);
                    }
                }
            });

        }
    }

    // This removes the data from our Dataset and Updates the Recycler View.
    private void removeItem(Information_for_service_holder infoData) {

        int currPosition = data.indexOf(infoData);
        data.remove(currPosition);
        notifyItemRemoved(currPosition);
    }

    // This method adds(duplicates) a Object (item ) to our Data set as well as Recycler View.
    private void addItem(int position, Information_for_service_holder infoData) {

        data.add(position, infoData);
        notifyItemInserted(position);
    }

    private void deleteItem(int position) {

        //Toast.makeText(getContext(),"Removed : " + getinfo(position) ,Toast.LENGTH_SHORT).show();
        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());

    }
}
