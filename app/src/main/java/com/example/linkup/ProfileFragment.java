package com.example.linkup;

import static android.content.Intent.getIntent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ImageView avatartv, covertv;
    TextView nam, email, phone;
    RecyclerView postrecycle;
    StorageReference storageReference;
    String storagepath = "Users_Profile_Cover_image/";
    FloatingActionButton fab, open_calendar_button;
    List<ModelPost> posts;
    AdapterPosts adapterPosts;
    String uid;
    ProgressDialog pd;
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private static final int IMAGEPICK_GALLERY_REQUEST = 300;
    private static final int IMAGE_PICKCAMERA_REQUEST = 400;
    String cameraPermission[];
    String storagePermission[];
    Uri imageuri;

    public ProfileFragment() {
        // Required empty public constructor
    }
    boolean notMyProfile = false;
    String actualEmail;
    String actualUid = null;
    Boolean myProfile = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {// was here:

        Log.v("myTag", "profileFragment: onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // test
        /*
        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle == null)
        {
            Log.v("myTag", "profileFragment: bundle is null");
        }
        if(bundle != null)
        {
            notMyProfile = Boolean.parseBoolean(bundle.getString("notMyProfile", "false"));
            actualEmail = bundle.getString("email", null);
            Log.v("myTag", "profileFragment: actualEmail = " + actualEmail);
            Log.v("myTag", "profileFragment: notMyProfile = " + notMyProfile);
            //
            //getIntent().removeExtra("email");
        }*/

        // test

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        avatartv = view.findViewById(R.id.avatartv);
        nam = view.findViewById(R.id.nametv);
        email = view.findViewById(R.id.emailtv);
        uid = FirebaseAuth.getInstance().getUid();

        fab = view.findViewById(R.id.fab);
        open_calendar_button = view.findViewById(R.id.open_calendar_button);
        postrecycle = view.findViewById(R.id.recyclerposts);
        posts = new ArrayList<>();
        pd = new ProgressDialog(getActivity());
        loadMyPosts();
        pd.setCanceledOnTouchOutside(false);

        // Retrieving user data from firebase
        /*if(actualEmail != null){
            Query query = databaseReference.orderByChild("email").equalTo(actualEmail);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String name = "" + dataSnapshot1.child("name").getValue();
                        String emaill = "" + dataSnapshot1.child("email").getValue();
                        String image = "" + dataSnapshot1.child("image").getValue();
                        nam.setText(name);
                        email.setText(emaill);
                        actualUid = (String) dataSnapshot1.child("uid").getValue();
                        try {
                            Glide.with(getActivity()).load(image).into(avatartv);
                        } catch (Exception e) {

                        }
                        loadMyPosts();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }*/
        //else{
            Query query = databaseReference.orderByChild("email").equalTo(firebaseUser.getEmail());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String name = "" + dataSnapshot1.child("name").getValue();
                        String emaill = "" + dataSnapshot1.child("email").getValue();
                        String image = "" + dataSnapshot1.child("image").getValue();
                        nam.setText(name);
                        email.setText(emaill);

                        try {
                            Glide.with(getActivity()).load(image).into(avatartv);
                        } catch (Exception e) {

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
       // }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditProfilePage.class));
            }
        });

        open_calendar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CalendarActivity.class));
            }
        });
        return view;
    }

    private void loadMyPosts() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        postrecycle.setLayoutManager(layoutManager);
        /*if(actualUid != null)
        {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
            Query query = databaseReference.orderByChild("uid").equalTo(actualUid);
            Log.v("myTag", "profileFragment: actualUid = " + actualUid);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    posts.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        ModelPost modelPost = dataSnapshot1.getValue(ModelPost.class);
                        posts.add(modelPost);
                        adapterPosts = new AdapterPosts(getActivity(), posts);
                        postrecycle.setAdapter(adapterPosts);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }*/
        //else
        //{
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
            Query query = databaseReference.orderByChild("uid").equalTo(uid);
            Log.v("myTag", "profileFragment: uid = " + uid);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    posts.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        ModelPost modelPost = dataSnapshot1.getValue(ModelPost.class);
                        modelPost.setEventDate(String.valueOf(dataSnapshot1.child("event_date").getValue()));
                        modelPost.setEventTime(String.valueOf(dataSnapshot1.child("event_time").getValue()));
                        modelPost.setEventLocation(String.valueOf(dataSnapshot1.child("event_location").getValue()));
                        posts.add(modelPost);
                        adapterPosts = new AdapterPosts(getActivity(), posts);
                        postrecycle.setAdapter(adapterPosts);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    //}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // test
        //savedInstanceState = null;
        // test
        //super.onCreate(savedInstanceState);
        //if(savedInstanceState != null)
          //  savedInstanceState.clear();
        super.onCreate(savedInstanceState);
    }
   /* @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
    }*/
}
