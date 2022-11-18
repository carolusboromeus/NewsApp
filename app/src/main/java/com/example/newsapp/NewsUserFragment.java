package com.example.newsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapp.adapters.AdapterNewsUser;
import com.example.newsapp.databinding.FragmentNewsUserBinding;
import com.example.newsapp.models.ModelNews;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsUserFragment extends Fragment {

    //that we passed while creating instance of this fragment
    private String categoryId;
    private String category;
    private String uid;

    private ArrayList<ModelNews> newsArrayList;
    private AdapterNewsUser adapterNewsUser;

    //view binding
    private FragmentNewsUserBinding binding;

    private static final String TAG = "NEWS_USER_TAG";

    public NewsUserFragment() {
        // Required empty public constructor
    }

    public static NewsUserFragment newInstance(String categoryId, String category, String uid) {
        NewsUserFragment fragment = new NewsUserFragment();
        Bundle args = new Bundle();
        args.putString("categoryId", categoryId);
        args.putString("category", category);
        args.putString("uid", uid);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getString("categoryId");
            category = getArguments().getString("category");
            uid = getArguments().getString("uid");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate/bind the layout for this fragment
        binding = FragmentNewsUserBinding.inflate(LayoutInflater.from(getContext()), container, false);

        Log.d(TAG, "onCreateView: Category: "+category);
        if (category.equals("All")) {
            //load all news
            loadAllNews();
        }
        else if (category.equals("Most Viewed")) {
            //load most viewed news
            loadMostViewedDownloadedNews("viewsCount");
        }
        else {
            //load selected category news
            loadCategorizedNews();
        }

        //search
        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //called as and when user type any latter
                try {
                    adapterNewsUser.getFilter().filter(s);
                }
                catch (Exception e) {
                    Log.d(TAG, "onTextChanged: "+e.getMessage());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return binding.getRoot();
    }

    private void loadAllNews() {
        //init list
        newsArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("News");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear list before starting adding data into it
                newsArrayList.clear();

                for (DataSnapshot ds: snapshot.getChildren()) {
                    //get data
                    ModelNews model = ds.getValue(ModelNews.class);

                    //add to list
                    newsArrayList.add(model);
                }

                //setup adapter
                adapterNewsUser = new AdapterNewsUser(getContext(), newsArrayList);

                //set adapter to recyclerview
                binding.newsRv.setAdapter(adapterNewsUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadMostViewedDownloadedNews(String orderBy) {
        //init list
        newsArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("News");
        ref.orderByChild(orderBy).limitToLast(10) //load 10 most viewed
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear list before starting adding data into it
                newsArrayList.clear();

                for (DataSnapshot ds: snapshot.getChildren()) {
                    //get data
                    ModelNews model = ds.getValue(ModelNews.class);

                    //add to list
                    newsArrayList.add(model);
                }

                //setup adapter
                adapterNewsUser = new AdapterNewsUser(getContext(), newsArrayList);

                //set adapter to recyclerview
                binding.newsRv.setAdapter(adapterNewsUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadCategorizedNews() {
        //init list
        newsArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("News");
        ref.orderByChild("categoryId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //clear list before starting adding data into it
                        newsArrayList.clear();

                        for (DataSnapshot ds: snapshot.getChildren()) {
                            //get data
                            ModelNews model = ds.getValue(ModelNews.class);

                            //add to list
                            newsArrayList.add(model);
                        }

                        //setup adapter
                        adapterNewsUser = new AdapterNewsUser(getContext(), newsArrayList);

                        //set adapter to recyclerview
                        binding.newsRv.setAdapter(adapterNewsUser);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}