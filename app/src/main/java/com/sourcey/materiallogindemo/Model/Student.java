package com.sourcey.materiallogindemo.Model;

public class Student {

    private int id;
    private String longitude;
    private String lattitude;
    private String time;


    public Student()
    {

    }

    public Student(String longitude, String lattitude, String time) {

        this.longitude = longitude;
        this.lattitude = lattitude;
        this.time = time;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }




}
