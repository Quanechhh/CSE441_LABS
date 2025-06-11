package com.example.lab_14_02;

public class Song {
    private String maSo;
    private String tieuDe;
    private boolean isLiked;

    public Song(String maSo, String tieuDe) {
        this.maSo = maSo;
        this.tieuDe = tieuDe;
        this.isLiked = false; // Default to not liked
    }

    public String getMaSo() {
        return maSo;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
