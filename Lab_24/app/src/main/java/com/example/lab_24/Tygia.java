package com.example.lab_24;

import android.graphics.Bitmap; // Để lưu ảnh của tiền tệ

public class Tygia {
    private String type; // Ví dụ: USD, EUR, JPY
    private String imageurl; // URL ảnh của cờ quốc gia/tiền tệ
    private Bitmap bitmap; // Bitmap của ảnh đã tải
    private String muatienmat; // Mua tiền mặt
    private String muack; // Mua chuyển khoản
    private String bantuenmat; // Bán tiền mặt
    private String banck; // Bán chuyển khoản

    public Tygia(String type, String imageurl, Bitmap bitmap, String muatienmat, String muack, String bantuenmat, String banck) {
        this.type = type;
        this.imageurl = imageurl;
        this.bitmap = bitmap;
        this.muatienmat = muatienmat;
        this.muack = muack;
        this.bantuenmat = bantuenmat;
        this.banck = banck;
    }

    // Getters
    public String getType() {
        return type;
    }

    public String getImageurl() {
        return imageurl;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getMuatienmat() {
        return muatienmat;
    }

    public String getMuack() {
        return muack;
    }

    public String getBantuenmat() {
        return bantuenmat;
    }

    public String getBanck() {
        return banck;
    }

    // Setters (Nếu cần, để cập nhật giá trị sau khi tạo object)
    public void setType(String type) {
        this.type = type;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setMuatienmat(String muatienmat) {
        this.muatienmat = muatienmat;
    }

    public void setMuack(String muack) {
        this.muack = muack;
    }

    public void setBantuenmat(String bantuenmat) {
        this.bantuenmat = bantuenmat;
    }

    public void setBanck(String banck) {
        this.banck = banck;
    }
}
