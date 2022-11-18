package com.example.newsapp.models;

public class ModelContactUs {
    public String nama;
    public String nim;
    public String kelas;
    String email;
    String telepon;
    public int image;

    public ModelContactUs(String nama, String nim, String kelas, String email, String telepon, int image){
        this.nama = nama;
        this.nim = nim;
        this.kelas = kelas;
        this.email = email;
        this.telepon = telepon;
        this.image = image;
    }
}
