package com.example.newsapp.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.MyApplication;
import com.example.newsapp.activites.NewsDetailActivity;
import com.example.newsapp.activites.NewsEditActivity;
import com.example.newsapp.databinding.RowNewsAdminBinding;
import com.example.newsapp.filters.FilterNewsAdmin;
import com.example.newsapp.models.ModelNews;

import java.io.IOException;
import java.util.ArrayList;

public class AdapterNewsAdmin extends RecyclerView.Adapter<AdapterNewsAdmin.HolderNewsAdmin> implements Filterable {

    //context
    private Context context;

    //arraylist to hold list of data of type ModelNews
    public ArrayList<ModelNews> newsArrayList, filterList;

    //view binding row_news_admin.xml
    private RowNewsAdminBinding binding;

    private FilterNewsAdmin filter;

    private static final String TAG = "NEWS_ADAPTER_TAG";

    //progress
    private ProgressDialog progressDialog;

    //constructor
    public AdapterNewsAdmin(Context context, ArrayList<ModelNews> newsArrayList) {
        this.context = context;
        this.newsArrayList = newsArrayList;
        this.filterList = newsArrayList;

        //init progress dialog
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @NonNull
    @Override
    public HolderNewsAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //bind layout using view binding
        binding = RowNewsAdminBinding.inflate(LayoutInflater.from(context), parent, false);

        return new HolderNewsAdmin(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderNewsAdmin holder, int position) {
        /*Get data, set data, handle clicks etc.*/

        //get data
        ModelNews model = newsArrayList.get(position);
        String newsId = model.getId();
        String categoryId = model.getCategoryId();
        String title = model.getTitle();
        String description = model.getDescription();
        String date = model.getDate();
        String newsUrl = model.getUrl();
        long timestamp = model.getTimestamp();
        //we need to convert timestamp to dd/MM/yyyy format
        String formattedDate = MyApplication.formatTimestamp(timestamp);

        //set data
        holder.titleTv.setText(title);
        holder.descriptionTv.setText(description);
        holder.dateTv.setText(formattedDate);

        //load further details like category, news from url, news size in seprate functions
        MyApplication.loadCategory(
                ""+categoryId,
                holder.categoryTv
        );
        try {
            MyApplication.loadNewsFromUrlSinglePage(
                    ""+newsUrl,
                    ""+title,
                    holder.newsView
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        //handle click, show dialog with option 1) Edit, 2) Delete
        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreOptionsDialog(model, holder);
            }
        });

        //handle news click, open news details page, pass news id to get details of it
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

    private void moreOptionsDialog(ModelNews model, HolderNewsAdmin holder) {
        String newsId = model.getId();
        String newsUrl = model.getUrl();
        String newsTitle = model.getTitle();

        //options to show in dialog
        String[] options = {"Edit", "Delete"};

        //alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Options")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //handle dialog option click
                        if (which==0){
                            //Edit clicked, Open NewsEditActivity to edit the news info
                            Intent intent = new Intent(context, NewsEditActivity.class);
                            intent.putExtra("newsId", newsId);
                            context.startActivity(intent);
                        }
                        else if (which==1){
                            //Deleted clicked
                            MyApplication.deleteNews(
                                    context,
                                    ""+newsId,
                                    ""+newsUrl,
                                    ""+newsTitle
                            );
                            //deleteNews(model, holder);
                        }
                    }
                })
                .show();
    }

    @Override
    public int getItemCount() {
        return newsArrayList.size(); //return number of records / list size
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new FilterNewsAdmin(filterList, this);
        }
        return filter;
    }

    /*View Holder class for row_news_Admin.xml*/
    class HolderNewsAdmin extends RecyclerView.ViewHolder{

        //UI Views of row_news_admin.xml
        ProgressBar progressBar;
        TextView titleTv, descriptionTv, categoryTv, dateTv;
        ImageButton moreBtn, shareBtn;
        ImageView newsView;

        public HolderNewsAdmin(@NonNull View itemView) {
            super(itemView);

            //init ui views
            titleTv = binding.titleTv;
            descriptionTv = binding.descriptionTv;
            categoryTv = binding.categoryTv;
            dateTv = binding.dateTv;
            moreBtn = binding.moreBtn;
            shareBtn = binding.shareBtn;
            newsView = binding.newsView;
        }
    }
}
