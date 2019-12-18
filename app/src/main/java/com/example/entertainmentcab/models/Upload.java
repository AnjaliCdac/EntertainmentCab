package com.example.entertainmentcab.models;

import com.google.firebase.database.PropertyName;

public class Upload {
    private String mName;
    private String mImageUrl;

    Upload()
    {

    }
    Upload(String imageUrl)
    {
        mImageUrl=imageUrl;
    }
    public Upload(String name, String imageUrl)
    {
        if(name.trim().equals(" "))
        {
            name="No name";
        }
        this.mName=name;
        this.mImageUrl=imageUrl;
    }

    public void setName(String name)
    {
        mName=name;
    }
    @PropertyName("name")
    public String getmName()
    {
        return mName;
    }
    public void setImageUrl(String imageUrl)
    {
        mImageUrl=imageUrl;
    }
    @PropertyName("imageUrl")
    public String getImageUrl()
    {
        return mImageUrl;
    }

}
