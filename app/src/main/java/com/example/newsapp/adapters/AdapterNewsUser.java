package com.example.newsapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.MyApplication;
import com.example.newsapp.activites.NewsDetailActivity;
import com.example.newsapp.activites.NewsEditActivity;
import com.example.newsapp.databinding.RowNewsUserBinding;
import com.example.newsapp.filters.FilterNewsUser;
import com.example.newsapp.models.ModelNews;

import java.io.IOException;
import java.util.ArrayList;

public class AdapterNewsUser extends RecyclerView.Adapter<AdapterNewsUser.HolderNewsUser> implements Filterable {

    private Context context;
    public ArrayList<ModelNews> newsArrayList, filterList;
    private FilterNewsUser filter;

    private RowNewsUserBinding binding;

    private static final String TAG = "ADAPTER_NEWS_USER_TAG";

    public AdapterNewsUser(Context context, ArrayList<ModelNews> newsArrayList) {
        this.context = context;
        this.newsArrayList = newsArrayList;
        this.filterList = newsArrayList;
    }

    @Override
    public HolderNewsUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //bind the view
        binding = RowNewsUserBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HolderNewsUser(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderNewsUser holder, int position) {
        /*Get data, set data, handle click etc*/

        //get data
        ModelNews model = newsArrayList.get(position);
        String newsId = model.getId();
        String title = model.getTitle();
        String description = model.getDescription();
        //String date = model.getDate();
        String newsUrl = model.getUrl();
        String categoryId = model.getCategoryId();
        long timestamp = model.getTimestamp();

        //convert time
        String date = MyApplication.formatTimestamp(timestamp);

        //set data
        holder.titleTv.setText(title);
        holder.descriptionTv.setText(description);
        holder.dateTv.setText(date);

        try {
            MyApplication.loadNewsFromUrlSinglePage(
                    ""+newsUrl,
                    ""+title,
                    holder.newsView
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        MyApplication.loadCategory(
                ""+categoryId,
                holder.categoryTv
        );

        //handle click, show news details activity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("newsId", newsId);
                context.startActivity(intent);
            }
        });

        //button to share the news
        holder.shareBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(shareIntent, "Share This News Via"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsArrayList.size(); //return list size || number of records
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new FilterNewsUser(filterList, this);
        }
        return filter;
    }

    class HolderNewsUser extends RecyclerView.ViewHolder {

        TextView titleTv, descriptionTv, categoryTv, dateTv;
        ImageButton shareBtn;
        ImageView newsView;

        public HolderNewsUser (@NonNull View itemView) {
            super(itemView);
            titleTv = binding.titleTv;
            descriptionTv = binding.descriptionTv;
            categoryTv = binding.categoryTv;
            dateTv = binding.dateTv;
            shareBtn = binding.shareBtn;
            newsView = binding.newsView;
        }
    }
}
