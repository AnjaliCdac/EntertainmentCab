package com.example.entertainmentcab.models;

public class Video {
    private String mName;
    private String mImageUrl;
    private String mVideoUrl;

    Video()
    {

    }

    public Video(String name, String imageUrl, String videoUrl)
    {
        if(name.trim().equals(" "))
        {
            name="No name";
        }
        this.mName=name;
        this.mImageUrl=imageUrl;
        this.mVideoUrl = videoUrl;
    }
    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public void setmVideoUrl(String mVideoUrl) {
        this.mVideoUrl = mVideoUrl;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmVideoUrl() {
        return mVideoUrl;
    }
}
