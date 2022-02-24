package com.example.jsnevent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jsnevent.model.EventModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

    private Context context;
    List<EventModel> list;//filter
    List<EventModel> listEvents;
    private LayoutInflater layoutInflater;
    private RequestFilter requestFilter;
    public RecyclerViewAdapter(Context context,List<EventModel> list) {

        this.context = context;
        this.list = list;
        this.listEvents =list;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=layoutInflater.inflate(R.layout.rvitem,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        EventModel eventModel = list.get(position);
        holder.text_Name.setText(eventModel.getEventName());
        holder.text_date.setText(eventModel.getDate());
        holder.text_venue.setText(eventModel.getVenue());
        holder.text_description.setText(eventModel.getDescription());

        if (position == 0){
            holder.llMain.setBackgroundResource(R.drawable.newyear3);
        }
        int val= position%2;
        if (val == 0){
            holder.llMain.setBackgroundResource(R.drawable.diwali3);
        }else{
            holder.llMain.setBackgroundResource(R.drawable.pongal3);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        if (requestFilter == null) {
            requestFilter = new RequestFilter();
        }

        return requestFilter;
    }

    private class RequestFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();


            if (constraint != null && constraint.length() > 0) {
                List<EventModel> filterList = new ArrayList<>();
                for (int i = 0; i < listEvents.size(); i++) {
                    EventModel request = listEvents.get(i);

                    if ((request.getEventName().toUpperCase()).contains(constraint.toString().toUpperCase()) ||
                            (request.getDate().toUpperCase()).contains(constraint.toString().toUpperCase())||
                            (request.getVenue().toUpperCase()).contains(constraint.toString().toUpperCase())||
                            (request.getDescription().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(listEvents.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = listEvents.size();
                results.values = listEvents;
            }
            return results;
        }



        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            list = (ArrayList) results.values;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView text_Name;
        TextView text_venue;
        TextView text_date;
        TextView text_description;
        ImageView image_picker,image_location;
        CardView llMain;
//        LinearLayout cardViewLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_Name= (TextView) itemView.findViewById(R.id.text_titleEvent);
            text_date= (TextView) itemView.findViewById(R.id.text_date);
            text_venue= (TextView) itemView.findViewById(R.id.text_location);
            text_description= (TextView) itemView.findViewById(R.id.text_Description);
            image_picker = (ImageView) itemView.findViewById(R.id.datepicker);
            image_location =(ImageView) itemView.findViewById(R.id.image_location);
            llMain =(CardView) itemView.findViewById(R.id.card_view);
//            cardViewLinearLayout = (LinearLayout) itemView.findViewById(R.id.card_view_layout);
        }

    }
}




/*

    @Override
    public Filter getFilter() {

     return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<EventModel> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty()){
                filteredList.addAll(listEvents);
            }else{
                for (EventModel eventModel : listEvents){
                    if (eventModel.toString().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(eventModel);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            listEvents.clear();
            listEvents.addAll((Collection<? extends EventModel>) filterResults.values);
            notifyDataSetChanged();
        }
    };
*/

