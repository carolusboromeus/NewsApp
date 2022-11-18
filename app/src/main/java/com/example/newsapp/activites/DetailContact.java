package com.example.newsapp.activites;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapp.R;
import com.example.newsapp.databinding.ActivityContactusDetailBinding;

public class DetailContact extends AppCompatActivity {
    ActivityContactusDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactusDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();
        if(intent != null){
            String nama = intent.getStringExtra("namaDeveloper");
            String nim = intent.getStringExtra("nimDeveloper");
            String kelas = intent.getStringExtra("kelasDeveloper");
            String email = intent.getStringExtra("emailDeveloper");
            String telp = intent.getStringExtra("telpDeveloper");
            int imageProfil = intent.getIntExtra("gambarProfil", R.drawable.ic_person_black);

            binding.imgProfile.setImageResource(imageProfil);
            binding.tvNama.setText(nama);
            binding.tvNIM.setText(nim);
            binding.tvKelas.setText(kelas);
            binding.tvEmail.setText(email);
            binding.tvTelepon.setText(telp);

            binding.logoEmail.setOnClickListener(v -> {

                if(isGmailInstalled()){
                    Intent intentEmail = new Intent(Intent.ACTION_VIEW);
                    intentEmail.setData(Uri.parse("mailto:" + Uri.encode(email) +
                            "?subject=" + Uri.encode("Email : " + nama) +
                            "&body=" + Uri.encode("Halo, " + nama)));
                    startActivity(intentEmail);
                }
                else {
                    Toast.makeText(DetailContact.this, "Gmail tidak terinstall !!!", Toast.LENGTH_SHORT).show();
                }
            });
            binding.logoTelepon.setOnClickListener(v -> {
                String waURL = "https://api.whatsapp.com/send?phone=" + telp;

                if(isWAInstalled()){
                    Intent intentWA = new Intent(Intent.ACTION_VIEW);
                    intentWA.setData(Uri.parse(waURL));
                    startActivity(intentWA);
                }
                else {
                    Toast.makeText(DetailContact.this, "Whatsapp tidak terinstall !!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        binding.backBtn.setOnClickListener(view-> back());
    }

    private boolean isGmailInstalled(){
        PackageManager packageManager = getPackageManager();
        boolean isInstalled;

        try {
            packageManager.getPackageInfo("com.google.android.gm", PackageManager.GET_ACTIVITIES);
            isInstalled = true;
        }catch (PackageManager.NameNotFoundException e){
            isInstalled = false;
            e.printStackTrace();
        }
        return isInstalled;
    }

    private boolean isWAInstalled(){
        PackageManager packageManager = getPackageManager();
        boolean isInstalled;

        try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            isInstalled = true;
        }catch (PackageManager.NameNotFoundException e){
            isInstalled = false;
            e.printStackTrace();
        }
        return isInstalled;
    }

    private void back(){
        Intent backIntent = new Intent(DetailContact.this, ContactUs.class);
        startActivity(backIntent);
        finish();
    }
}