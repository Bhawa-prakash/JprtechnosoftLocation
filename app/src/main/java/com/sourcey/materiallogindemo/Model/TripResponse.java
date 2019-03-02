package com.sourcey.materiallogindemo.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TripResponse {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("trip_detail")
@Expose
private List<TripDetail> tripDetail = null;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<TripDetail> getTripDetail() {
return tripDetail;
}

public void setTripDetail(List<TripDetail> tripDetail) {
this.tripDetail = tripDetail;
}

}