package com.example.newsapp.filters;

import android.widget.Filter;

import com.example.newsapp.adapters.AdapterNewsUser;
import com.example.newsapp.models.ModelNews;

import java.util.ArrayList;

public class FilterNewsUser extends Filter {

    //arraylist in which we want to search
    ArrayList<ModelNews> filterList;

    //adapter in which filter need to be implemented
    AdapterNewsUser adapterNewsUser;

    //constructor
    public FilterNewsUser(ArrayList<ModelNews> filterList, AdapterNewsUser adapterNewsUser) {
        this.filterList = filterList;
        this.adapterNewsUser = adapterNewsUser;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        //value to be searched should no be null/empty
        if (constraint!=null || constraint.length() > 0) {
            //not null or empty
            //change to uppercase or lower case to avoid case sensitivity
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelNews> filterModels = new ArrayList<>();

            for (int i=0; i<filterList.size(); i++) {
                //validate
                if (filterList.get(i).getTitle().toUpperCase().contains(constraint)) {
                    //search matches, add to list
                    filterModels.add(filterList.get(i));
                }
            }

            results.count = filterModels.size();
            results.values = filterModels;
        }
        else {
            //empty or null, make original list/result
            results.count = filterList.size();
            results.values = filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        //apply filter changes
        adapterNewsUser.newsArrayList = (ArrayList<ModelNews>)results.values;

        //notify changes
        adapterNewsUser.notifyDataSetChanged();
    }
}
