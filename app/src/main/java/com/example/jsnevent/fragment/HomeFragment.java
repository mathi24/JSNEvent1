package com.example.jsnevent.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jsnevent.DBHelper;
import com.example.jsnevent.R;
import com.example.jsnevent.RecyclerViewAdapter;
import com.example.jsnevent.model.EventModel;
import com.example.jsnevent.model.UserModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment implements View.OnClickListener {

    public DBHelper db;
    private TextView edittext_Name, edittext_venue, edittext_date, edittext_description,title_upcomingEvents;
    String eventName, venue, date, description, datepicker, location, userEmail, userPassword;
    ImageView imageDatePicker1;
    RecyclerView rvEvents;
    ExtendedFloatingActionButton fabNewEvent;
    ListView listView;
    List<EventModel> listEvents = new ArrayList<>();
    Dialog dialog;
    BottomSheetBehavior bottomSheetBehavior;
    ImageView imageDatePicker, imageLocation;
    SearchView searchView;
    private ImageView imageMenu;
    DBHelper myDB;
    View shadowView;
    RecyclerViewAdapter recyclerViewAdapter;
//    List<UserModel> userList = new ArrayList<>();
    Button btnCreate;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findViewById(view);
        return view;

    }



    private void findViewById(View view) {
        rvEvents = view.findViewById(R.id.recyclerview1);
        fabNewEvent = view.findViewById(R.id.extended_floating_action_button1);
        edittext_Name = view.findViewById(R.id.edittext_name);
        edittext_date = view.findViewById(R.id.edittext_date);
        edittext_venue = view.findViewById(R.id.edittext_venue);
        edittext_description = view.findViewById(R.id.edittext_description);
        btnCreate = view.findViewById(R.id.btn_create);
        shadowView = view.findViewById(R.id.view_shadow);
        imageDatePicker = view.findViewById(R.id.image_picker);
        imageLocation = view.findViewById(R.id.image_location);
//        search = view.findViewById(R.id.image_search);
        searchView = view.findViewById(R.id.image_searchView);
        title_upcomingEvents = view.findViewById(R.id.title_upcomingEvents);
        LinearLayout linearBottomSheet = view.findViewById(R.id.btm_sheet_create_event);
        bottomSheetBehavior = BottomSheetBehavior.from(linearBottomSheet);

        db = new DBHelper(getActivity());

        listEvents = new ArrayList<>();
        listEvents = db.getAllEvents("select * from events");
        if (listEvents.size() > 0) {


            Collections.sort(listEvents, new Comparator<EventModel>() {
                @Override
                public int compare(EventModel eventModel, EventModel t1) {
                    if (eventModel.getDate() == null || t1.getDate() == null)
                        return 0;
                    return eventModel.getDate().compareTo(t1.getDate());
                }

            });


            recyclerViewAdapter = new RecyclerViewAdapter(getContext(), listEvents);
            rvEvents.setAdapter(recyclerViewAdapter);
        }

        fabNewEvent = view.findViewById(R.id.extended_floating_action_button1);

        btnCreate.setOnClickListener(this);
        imageDatePicker.setOnClickListener(this);
        imageLocation.setOnClickListener(this);
        fabNewEvent.setOnClickListener(this);


        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback(){
        @Override
        public void onStateChanged (@NonNull View bottomSheet,int newState){
        switch (newState) {
            case BottomSheetBehavior.STATE_EXPANDED:
                break;
            case BottomSheetBehavior.STATE_COLLAPSED:
                shadowView.setVisibility(View.GONE);
                fabNewEvent.setVisibility(View.VISIBLE);
                break;

            case BottomSheetBehavior.STATE_DRAGGING:
                //fabNewEvent.setVisibility(View.VISIBLE);
                break;

            case BottomSheetBehavior.STATE_HIDDEN:
                break;

            default:
                break;
        }
    }

        @Override
        public void onSlide (@NonNull View bottomSheet,float slideOffset){

    }
    });

}

    private void showdatepicker() {

        Calendar cal = Calendar.getInstance();

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        DatePickerDialog dpd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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

//    searchView.
    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.image_picker) {
            showdatepicker();
        } else if (view == fabNewEvent) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            shadowView.setVisibility(View.VISIBLE);
            fabNewEvent.setVisibility(View.GONE);
        } else if (view.getId()==R.id.btn_create) {
            eventName = edittext_Name.getText().toString();
            date = edittext_date.getText().toString();
            venue = edittext_venue.getText().toString();
            description = edittext_description.getText().toString();


            boolean checkinserteventdata = db.insertEventData(eventName, date, venue, description);
            if (checkinserteventdata) {
                Toast.makeText(getContext(), "InsertSuccessful", Toast.LENGTH_SHORT).show();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                shadowView.setVisibility(View.GONE);
                fabNewEvent.setVisibility(View.VISIBLE);

                listEvents = new ArrayList<>();
                listEvents = db.getAllEvents("select * from events");
                listEvents.size();

                Collections.sort(listEvents, new Comparator<EventModel>() {
                    @Override
                    public int compare(EventModel eventModel, EventModel t1) {
                        if (eventModel.getDate() == null || t1.getDate() == null)
                            return 0;
                        return eventModel.getDate().compareTo(t1.getDate());
                    }

                });


                recyclerViewAdapter = new RecyclerViewAdapter(getContext(), listEvents);
                rvEvents.setAdapter(recyclerViewAdapter);
                rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));

            } else
                Toast.makeText(getContext(), "InsertUnsuccessful", Toast.LENGTH_SHORT).show();



            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
//                    title_upcomingEvents.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if(recyclerViewAdapter!=null) {
                        recyclerViewAdapter.getFilter().filter(newText);
                    }
                    return false;

                }
            });
        }
    }



  /*  @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }*/

}

