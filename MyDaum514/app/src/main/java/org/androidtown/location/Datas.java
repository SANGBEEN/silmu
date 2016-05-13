package org.androidtown.location;

/**
 * Created by Henry on 2016-05-14.
 */
class Data {
    double lat;
    double longi;
    int capacity;


    Data(){
        capacity=0;
        lat=0;
        longi=0;

    }
    //setter
    public void setData1(double la, double lo, int cap){
        setLat(la);
        setLong(lo);
        setCapacity(cap);
    }
    public void setLat(double d){
        lat = d;
    }
    public  void setLong(double d){
        longi=d;
    }
    public void setCapacity(int n){
        capacity=n;
    }

    //getter
    public Data getData1(){
        return this;
    }
    public double getLat(){
        return lat;
    }
    public double getLongi(){
        return longi;
    }
    public int getCapacity(){
        return capacity;
    }

}
