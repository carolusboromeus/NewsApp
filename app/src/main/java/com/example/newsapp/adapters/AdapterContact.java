package com.example.newsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newsapp.R;
import com.example.newsapp.models.ModelContactUs;

import java.util.ArrayList;

public class AdapterContact extends ArrayAdapter<ModelContactUs> {
    TextView tvNama, tvNIM, tvKelas;
    ImageView imgProfile;

    public AdapterContact(Context context, ArrayList<ModelContactUs> contactArrayList){
        super(context, R.layout.row_contactus, contactArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ModelContactUs contactUs = getItem(position);

        if(convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_contactus, parent, false);
        }
        tvNama = convertView.findViewById(R.id.tvNama);
        tvNIM = convertView.findViewById(R.id.tvNIM);
        tvKelas = convertView.findViewById(R.id.tvKelas);
        imgProfile = convertView.findViewById(R.id.imgProfile);

        tvNama.setText(contactUs.nama);
        tvNIM.setText(contactUs.nim);
        tvKelas.setText(contactUs.kelas);
        imgProfile.setImageResource(contactUs.image);
        return convertView;
    }
}