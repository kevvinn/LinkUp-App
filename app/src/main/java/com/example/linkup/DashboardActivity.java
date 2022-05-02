package com.example.linkup;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String myuid;
    ActionBar actionBar;
    BottomNavigationView navigationView;
    boolean sharing = false;
    boolean profiling = false;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Profile Activity");
        firebaseAuth = FirebaseAuth.getInstance();

        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);
        actionBar.setTitle("Home");

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            sharing = bundle.getBoolean("sharing", false);
            profiling = bundle.getBoolean("profiling", false);
            email = bundle.getString("email", null);
        }
        if(sharing == false && profiling == false)
        {
            Log.v("myTag","DashboardActivity: sharing and profiling false");
            // When we open the application first
            // time the fragment should be shown to the user
            // in this case it is home fragment
            BrowseFragment fragment = new BrowseFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, fragment, "");
            fragmentTransaction.commit();
            //finish();

        }
        else if(sharing == true)
        {
            Log.v("myTag","DashboardActivity: only sharing true");
            UsersFragment fragment = new UsersFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, fragment, "");
            fragmentTransaction.commit();
            //dialog.dismiss();
            //finish();
            sharing = false;
        }
        else if(profiling == true)
        {
            Log.v("myTag","DashboardActivity: only profiling true");

            Bundle bundlep = new Bundle();
            bundlep.putString("notMyProfile", "true");
            bundlep.putString("email", email);
            ProfileFragmentOther fragment = new ProfileFragmentOther();
            fragment.setArguments(bundlep);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, fragment);
            fragmentTransaction.commit();
            //finish();
            profiling = false;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                case R.id.nav_browse:
                    actionBar.setTitle("Browse");
                    BrowseFragment fragment = new BrowseFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, fragment, "");
                    fragmentTransaction.commit();
                    return true;

                case R.id.nav_profile:
                    actionBar.setTitle("Profile");
                   /* Bundle bundlep = new Bundle();
                    //bundlep.putBoolean("myProfile", true);
                    bundlep.putString("notMyProfile", "false");
                    //bundlep.putBoolean("myProfile", true);
                    bundlep.putString("email", "amyle707@gmail.com");
                    ProfileFragment fragment1 = new ProfileFragment();
                    fragment1.setArguments(bundlep);
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.content, fragment1);
                    fragmentTransaction1.commit();*/
                    //finish();
                    Log.v("myTag", "DashboardActivity: new profile frag?");


                   /* Bundle bundlep = new Bundle();
                    bundlep.putString("notMyProfile", "false");
                    bundlep.putString("email", "amyle707@gmail.com");*/
                    ProfileFragment fragment1 = new ProfileFragment();
                    //fragment1.setArguments(bundlep);
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.content, fragment1);
                    fragmentTransaction1.commit();
                    return true;

                case R.id.nav_users:
                    actionBar.setTitle("Users");
                    UsersFragment fragment2 = new UsersFragment();
                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.content, fragment2, "");
                    fragmentTransaction2.commit();
                    return true;

                case R.id.nav_chat:
                    actionBar.setTitle("Chats");
                    ChatListFragment listFragment = new ChatListFragment();
                    FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.content, listFragment, "");
                    fragmentTransaction3.commit();
                    return true;

                case R.id.nav_addevents:
                    actionBar.setTitle("Add Events");
                    AddEventsFragment fragment4 = new AddEventsFragment();
                    FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction4.replace(R.id.content, fragment4, "");
                    fragmentTransaction4.commit();
                    return true;
            }
            return false;
        }
    };
}
