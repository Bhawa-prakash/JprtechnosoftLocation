package com.sourcey.materiallogindemo.Retrofit;

import com.sourcey.materiallogindemo.Model.AddTripResponse;
import com.sourcey.materiallogindemo.Model.LoginResponse;
import com.sourcey.materiallogindemo.Model.SignupResponse;
import com.sourcey.materiallogindemo.Model.TripResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;

public class RestClient {
    private static final String TAG = "RestClient";




   public static void LoginUser(RequestBody email, RequestBody password, Callback<LoginResponse> callback) {
       RetrofitClient.getClient().loginUser(email, password).enqueue(callback);
   }

    public static void registerUser(RequestBody fname, RequestBody lname,MultipartBody.Part image, RequestBody email_id, RequestBody password, RequestBody mobile, Callback<SignupResponse> callback) {
        RetrofitClient.getClient().registerUser(fname,lname,image, email_id, password,mobile).enqueue(callback);
    }

    public static void AddUser(RequestBody id,  RequestBody user_latitude,RequestBody user_longitude, Callback<AddTripResponse> callback) {
        RetrofitClient.getClient().addLocation(id,   user_latitude, user_longitude).enqueue(callback);
    }

    public static void tripRespons( Callback<TripResponse> callback) {
        RetrofitClient.getClient().tripRespons().enqueue(callback);
    }

}