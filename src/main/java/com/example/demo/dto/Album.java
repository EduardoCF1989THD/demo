package com.example.demo.dto;

public class Album {
    protected int singerId;
    protected int albumId;
    protected String title;


    public Album() {
    }

    public Album(int singerId, int albumId, String title) {
        this.singerId = singerId;
        this.albumId = albumId;
        this.title = title;
    }

    public int getSingerId() {
        return singerId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public String getTitle() {
        return title;
    }

}
