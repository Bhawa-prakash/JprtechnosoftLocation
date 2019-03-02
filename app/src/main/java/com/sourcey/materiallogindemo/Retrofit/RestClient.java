package com.sourcey.materiallogindemo.Retrofit;

import com.sourcey.materiallogindemo.Model.AddTripResponse;
import com.sourcey.materiallogindemo.Model.LoginResponse;
import com.sourcey.materiallogindemo.Model.SignupResponse;
import com.sourcey.materiallogindemo.Model.TripDetail;
import com.sourcey.materiallogindemo.Model.TripResponse;

import okhttp3.RequestBody;
import retrofit2.Callback;

public class RestClient {
    private static final String TAG = "RestClient";




   public static void LoginUser(RequestBody email, RequestBody password, Callback<LoginResponse> callback) {
       RetrofitClient.getClient().loginUser(email, password).enqueue(callback);
   }

    public static void registerUser(RequestBody fname, RequestBody lname, RequestBody email_id, RequestBody password,RequestBody mobile, Callback<SignupResponse> callback) {
        RetrofitClient.getClient().registerUser(fname, lname, email_id, password,mobile).enqueue(callback);
    }

    public static void AddUser(RequestBody user_id, RequestBody trip_id, RequestBody visit_id, RequestBody lat,RequestBody longit,RequestBody status, Callback<AddTripResponse> callback) {
        RetrofitClient.getClient().addLocation(user_id, trip_id, visit_id, lat, longit, status).enqueue(callback);
    }

    public static void tripRespons( Callback<TripResponse> callback) {
        RetrofitClient.getClient().tripRespons().enqueue(callback);
    }

}