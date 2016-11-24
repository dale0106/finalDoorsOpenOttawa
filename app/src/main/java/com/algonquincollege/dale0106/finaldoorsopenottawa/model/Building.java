package com.algonquincollege.dale0106.finaldoorsopenottawa.model;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by crisdalessio on 2016-11-07.
 */

public class Building {

    private Integer buildingId;
    private String name;
    private String address;
    private String image;
    private Bitmap bitmap;
    private String description;
    private List<String> openHours;



    //Get and Set methods
    public Integer getBuildingId(){ return buildingId;}
    public void setBuildingId( Integer buildingId){this.buildingId = buildingId;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getDescription(){return description;}
    public void SetDescription(String description){this.description = description;}

    public String getAddress(){ return address;}
    public void setAddress(String address){this.address = address + " Ottawa, Ontario";}


    public String getImage(){return image;}
    public  void setImage(String image){this.image = image;}


    public Bitmap getBitmap() { return bitmap; }
    public void setBitmap(Bitmap bitmap) { this.bitmap = bitmap; }

    //create get and set methods for date and hours
    //public List<String> getOpenHours(){return List<String>;}

}

