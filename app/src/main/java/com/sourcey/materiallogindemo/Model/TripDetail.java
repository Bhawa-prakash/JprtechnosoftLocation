package com.sourcey.materiallogindemo.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TripDetail {

@SerializedName("id")
@Expose
private String id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("lat")
@Expose
private String lat;
@SerializedName("long")
@Expose
private String _long;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getLat() {
return lat;
}

public void setLat(String lat) {
this.lat = lat;
}

public String getLong() {
return _long;
}

public void setLong(String _long) {
this._long = _long;
}

}