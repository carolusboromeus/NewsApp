package com.example.newsapp.filters;

import android.widget.Filter;

import com.example.newsapp.adapters.AdapterCategory;
import com.example.newsapp.adapters.AdapterNewsAdmin;
import com.example.newsapp.models.ModelCategory;
import com.example.newsapp.models.ModelNews;

import java.util.ArrayList;

public class FilterNewsAdmin extends Filter {

    //arraylist in which we want to search
    ArrayList<ModelNews> filterList;
    //adapter in which filter meet to be implemented
    AdapterNewsAdmin adapterNewsAdmin;

    //constructor
    public FilterNewsAdmin(ArrayList<ModelNews> filterList, AdapterNewsAdmin adapterNewsAdmin) {
        this.filterList = filterList;
        this.adapterNewsAdmin = adapterNewsAdmin;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        //value should not be null and empty
        if (constraint != null && constraint.length() > 0){
            //changes to upper case, or lower case to avoid case sensitivity
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelNews> filteredModels = new ArrayList<>();
            for (int i=0; i<filterList.size(); i++){
                //validate
                if (filterList.get(i).getTitle().toUpperCase().contains(constraint)){
                    //add to filtered list
                    filteredModels.add(filterList.get(i));
                }
            }

            results.count = filteredModels.size();
            results.values = filteredModels;
        }
        else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        //apply filter changes
        adapterNewsAdmin.newsArrayList = (ArrayList<ModelNews>)results.values;

        //notify changes
        adapterNewsAdmin.notifyDataSetChanged();
    }
}
