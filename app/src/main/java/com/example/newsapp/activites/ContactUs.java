package com.example.newsapp.activites;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapp.R;
import com.example.newsapp.adapters.AdapterContact;
import com.example.newsapp.databinding.ActivityContactusBinding;
import com.example.newsapp.models.ModelContactUs;

import java.util.ArrayList;

public class ContactUs extends AppCompatActivity {
    ActivityContactusBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int[] imgProfile = {R.drawable.yor, R.drawable.anya, R.drawable.yuri, R.drawable.loid};
        String[] nama = {"Steffi Kristianti", "Yonatan", "Andryan", "Carolus Boromeus"};
        String[] nim = {"31190016", "31190021", "31190080", "31190085"};
        String[] kelas = {"7PSI1A", "7PSI1A", "7PSI1A", "7PSI1A"};
        String[] email = {"s31190016@student.ubm.ac.id", "s31190021@student.ubm.ac.id",
                "s31190080@student.ubm.ac.id", "s31190085@student.ubm.ac.id"};
        String[] telp = {"+6285775185960", "+6287760691578", "+628567519148", "+6285591691639"};

        ArrayList<ModelContactUs> contactArrayList = new ArrayList<>();

        for(int i=0; i< imgProfile.length; i++){
            ModelContactUs contact = new ModelContactUs(nama[i], nim[i], kelas[i], email[i], telp[i], imgProfile[i]);
            contactArrayList.add(contact);
        }
        AdapterContact adapter = new AdapterContact(ContactUs.this, contactArrayList);
        binding.exampleListView.setAdapter(adapter);
        binding.exampleListView.setClickable(true);
        binding.exampleListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intentDetail = new Intent(ContactUs.this, DetailContact.class);
            intentDetail.putExtra("gambarProfil", imgProfile[position]);
            intentDetail.putExtra("namaDeveloper", nama[position]);
            intentDetail.putExtra("nimDeveloper", nim[position]);
            intentDetail.putExtra("kelasDeveloper", kelas[position]);
            intentDetail.putExtra("emailDeveloper", email[position]);
            intentDetail.putExtra("telpDeveloper", telp[position]);
            startActivity(intentDetail);
        });
        binding.backBtn.setOnClickListener(view-> back());
    }

    private void back(){
        Intent intentBack = new Intent(ContactUs.this, DashboardUserActivity.class);
        startActivity(intentBack);
        finish();
    }
}