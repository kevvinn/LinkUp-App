package com.example.linkup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyHolder> {

    Context context;
    FirebaseAuth firebaseAuth;
    String uid;

    public AdapterUsers(Context context, List<ModelUsers> list) {
        this.context = context;
        this.list = list;
        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getUid();

    }

    List<ModelUsers> list;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users, parent, false);
        de.hdodenhof.circleimageview.CircleImageView profilePic = view.findViewById(R.id.imagep);
        TextView name = view.findViewById(R.id.namep);
        TextView email = view.findViewById(R.id.emailp);

        name.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("receiver", (String) email.getText());
                intent.putExtras(bundle);
                context.startActivity(intent);
                //getActivity().finish();
                Toast.makeText(parent.getContext(), "new chat pless", Toast.LENGTH_LONG).show();
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, DashboardActivity.class);
                intent.putExtra("profiling", true);
                intent.putExtra("email", (String) email.getText());
                context.startActivity(intent);
            }

        });
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        final String hisuid = list.get(position).getUid();
        Log.v("myTag", "AdapterUsers: hisuid = " + hisuid);
        String userImage = list.get(position).getImage();
        String username = list.get(position).getName();
        String usermail = list.get(position).getEmail();
        holder.name.setText(username);
        holder.email.setText(usermail);
        try {
            Glide.with(context).load(userImage).into(holder.profiletv);
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        CircleImageView profiletv;
        TextView name, email;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            profiletv = itemView.findViewById(R.id.imagep);
            name = itemView.findViewById(R.id.namep);
            email = itemView.findViewById(R.id.emailp);
        }
    }
}
