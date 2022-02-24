package com.example.jsnevent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jsnevent.fragment.HomeFragment;
import com.example.jsnevent.model.EventModel;
import com.example.jsnevent.model.SharedPreference;
import com.example.jsnevent.model.UserModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    //private ActivityMainBinding binding;
    ImageView header;
//    SearchView searchView;
    LinearLayout linearLayout;
    Toolbar toolbar;
//    private TextView edittext_Name, edittext_venue, edittext_date, edittext_description;
//    String eventName, venue, date, description, datepicker, location, userEmail, userPassword;
    ImageView imageDatePicker1;
    //ExtendedFloatingActionButton extendedFloatingActionButton;
    Button btnCreate;
    ListView listView;
    List<EventModel> listEvents = new ArrayList<>();
    Dialog dialog;
//    RecyclerView recyclerView;
    DrawerLayout drawer;
    NavigationView navigationView;

    // BottomSheetBehavior bottomSheetBehavior;
//    ImageView imageDatePicker, imageLocation;

    private ImageView imageMenu;

//    RecyclerViewAdapter recyclerViewAdapter;
    List<UserModel> userList = new ArrayList<>();
    DBHelper db, myDB;
    View shadowView;
//    RecyclerViewAdapter recyclerViewAdapter;

        boolean isViewProfile = true;
    String userEmail,userPassword;
    String userName = "";
    String userMobileNo = "";
    TextView txtvUserName, txtvUserEmail;
    TextView logout;
    SharedPreference sharedPreference;
    RecyclerViewAdapter recyclerViewAdapter;

    @SuppressLint({"WrongViewCast", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_home_container,new HomeFragment()).commit();
/*
        findViewById();*/

//               db = new DBHelper(this);


        myDB = new DBHelper(this);
        userList = new ArrayList<>();
        userList = myDB.getUserProfileDetails("select * from users");

        SharedPreference sharedPreference=new SharedPreference(this);
        userEmail = sharedPreference.getUserEmail();
        userPassword = sharedPreference.getPassword();
        userName = sharedPreference.getUseName();
        userMobileNo = sharedPreference.getUserMobileNO();

        for(int i = 0;i<userList.size();i++){
            UserModel userModel = userList.get(i);
            if(userEmail.equals(userModel.getEmail()) && userPassword.equals(userModel.getPassword())){
                userName = userModel.getName();
                userMobileNo = userModel.getMobile();
                isViewProfile = true;
                break;
            }
        }

        if (isViewProfile){

//            Toast.makeText(this, "View Profile Successfully", Toast.LENGTH_SHORT).show();
            /*
            Bundle intent1 = getIntent().getExtras();
            userEmail = intent1.getString("UserEmail");
            userPassword = intent1.getString("UserPassword");
            userName = intent1.getString("UserName")*/;

        }else{
//            Toast.makeText(this, "profile Unsuccessful", Toast.LENGTH_SHORT).show();
        }


           /* binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
*/

        toolbar = findViewById(R.id.toolbar);
        getSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //DrawerLayout drawer = binding.drawerLayout;
        //NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder()
                .setOpenableLayout(drawer)
                .build();

        NavigationView nav_view;
        navigationView = findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);

            txtvUserName = (TextView) headerview.findViewById(R.id.txtv_userName);
            txtvUserEmail = (TextView) headerview.findViewById(R.id.txtv_userEmail);

            txtvUserName.setText(userName);
            txtvUserEmail.setText(userEmail);

        logout = findViewById(R.id.profile_logout);
//        searchView = findViewById(R.id.image_search);
//        searchView.setOnQueryTextListener(this);

        /* searchView = (SearchView) MenuItemCompat.getActionView();
// Expand the search view
        searchItem.expandActionView();*/

      /*  searchView.setIconified(false);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.set("");
                ((SearchView) findViewById(R.id.image_search)).setIconified(false);
            }
        });*/

        LinearLayout header = (LinearLayout) headerview.findViewById(R.id.linearlayout);
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container, homeFragment);
        ft.commit();


        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);

            }

        });

        sharedPreference = new SharedPreference(getApplicationContext());
        String userEmail = sharedPreference.getUserEmail();
        String userPassword = sharedPreference.getPassword();

        SharedPreference finalSharedPreference = sharedPreference;
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Log out");
                builder.setMessage("Are you sure to Log out?");

                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
/*

                        sharedPreference.setLoggedIn(false);
                        sharedPreference.setUserEmail(userEmail);
                        sharedPreference.setUserPassword(userPassword);
*/

                        finalSharedPreference.setLoggedIn(false);
                        finalSharedPreference.setUserEmail(userEmail);
                        finalSharedPreference.setPassword(userPassword);
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                       /* finish();*/
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void getSupportActionBar(Toolbar toolbar) {
    }


}

    /*    db = new DBHelper(this);



        listEvents = new ArrayList<>();
        listEvents = db.getAllEvents("select * from events");
        if (listEvents.size() > 0) {
            recyclerViewAdapter = new RecyclerViewAdapter(this, listEvents);
            recyclerView.setAdapter(recyclerViewAdapter);
        }
*/

/*

    private void findViewById() {
        edittext_Name = findViewById(R.id.edittext_name);
        edittext_date = findViewById(R.id.edittext_date);
        edittext_venue = findViewById(R.id.edittext_venue);
        edittext_description = findViewById(R.id.edittext_description);
        btnCreate = findViewById(R.id.btn_create);
        shadowView = findViewById(R.id.view_shadow);
//        btnCreate = findViewById(R.id.btn_create);

*/
/*
        inputUserEmail = findViewById(R.id.edittext_useremail);
        inputUserPassword = findViewById(R.id.edittext_userpassword);*//*


        imageMenu = findViewById(R.id.imageMenu);
        imageDatePicker = findViewById(R.id.image_picker);
        imageLocation = findViewById(R.id.image_location);
        recyclerView = findViewById(R.id.recyclerview1);


        LinearLayout linearBottomSheet = findViewById(R.id.btm_sheet_create_event);
        bottomSheetBehavior = BottomSheetBehavior.from(linearBottomSheet);

        extendedFloatingActionButton = findViewById(R.id.extended_floating_action_button);

        //listeners
        extendedFloatingActionButton.setOnClickListener(this);
        btnCreate.setOnClickListener(this);
        imageDatePicker.setOnClickListener(this);
        imageLocation.setOnClickListener(this);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        shadowView.setVisibility(View.GONE);
                        break;

                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;

                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


    }

    private void showdatepicker() {

        Calendar cal = Calendar.getInstance();

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, date);
                String selectedDate = dateFormatter.format(newDate.getTime());
                edittext_date.setText(selectedDate);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }

    @Override
    public void onClick(View view) {
        if (view == imageDatePicker) {
            showdatepicker();
        } else if (view == extendedFloatingActionButton) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            shadowView.setVisibility(View.VISIBLE);
            extendedFloatingActionButton.setVisibility(View.GONE);
        } else if (view == btnCreate) {
            eventName = edittext_Name.getText().toString();
            date = edittext_date.getText().toString();
            venue = edittext_venue.getText().toString();
            description = edittext_description.getText().toString();


            boolean checkinserteventdata = db.insertEventData(eventName, date, venue, description);
            if (checkinserteventdata) {
                Toast.makeText(MainActivity.this, "InsertSuccessful", Toast.LENGTH_SHORT).show();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                shadowView.setVisibility(View.GONE);
                extendedFloatingActionButton.setVisibility(View.VISIBLE);

                listEvents = new ArrayList<>();
                listEvents = db.getAllEvents("select * from events");
                listEvents.size();


                recyclerViewAdapter = new RecyclerViewAdapter(this, listEvents);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

            } else
                Toast.makeText(MainActivity.this, "InsertUnsuccessful", Toast.LENGTH_SHORT).show();
        }
        
        
*/


