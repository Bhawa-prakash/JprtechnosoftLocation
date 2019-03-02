
package com.sourcey.materiallogindemo.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GpsTracker extends RealmObject {

     @PrimaryKey
    private String id;

    private String Lattitude;
    private String Longitude;
    private String time;

    public GpsTracker(String id, String lattitude, String longitude, String time) {
        this.id = id;
        Lattitude = lattitude;
        Longitude = longitude;
        this.time = time;
    }



    public GpsTracker()
    {

    }

    public String getLattitude() {
        return Lattitude;
    }

    public void setLattitude(String lattitude) {
        Lattitude = lattitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
   /* public static List<GpsTracker> getAllLocation() {
        try {
            Realm realm = Realm.getDefaultInstance();
            return realm.where(GpsTracker.class).findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/


}




