package com.cu.crazypocket.Data;

public class Deals_data implements Comparable  {
    String title,image;
    int Priority;

    public String getTitle () {
        return title;
    }

    public void setTitle ( String title ) {
        this.title = title;
    }

    public String getImage () {
        return image;
    }

    public void setImage ( String image ) {
        this.image = image;
    }

    public int getPriority () {
        return Priority;
    }

    public void setPriority ( int priority ) {
        Priority = priority;
    }

    public Deals_data () {
    }

    public Deals_data ( String title , String image , int priority ) {
        this.title = title;
        this.image = image;
        Priority = priority;
    }
    @Override
    public int compareTo (Object o) {
 //        return  (int)(this.timestamp%10000) -(int)time;
        return ((Deals_data) o).getPriority()-this.getPriority();
    }
}
