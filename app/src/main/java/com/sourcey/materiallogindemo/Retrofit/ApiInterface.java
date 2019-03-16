package com.sourcey.materiallogindemo.Retrofit;

import com.sourcey.materiallogindemo.Model.AddTripResponse;
import com.sourcey.materiallogindemo.Model.LoginResponse;
import com.sourcey.materiallogindemo.Model.SignupResponse;
import com.sourcey.materiallogindemo.Model.TripResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {


    @Multipart
    @POST("test_api?action=login")
    Call<LoginResponse> loginUser(@Part("email_id") RequestBody email,
                                  @Part("password") RequestBody password);


    @Multipart
    @POST("test_api?action=reg")
    Call<SignupResponse> registerUser(@Part("fname") RequestBody fname,
                                      @Part("lname") RequestBody lname,
                                      @Part MultipartBody.Part body,
                                      @Part("email_id") RequestBody email,
                                      @Part("password") RequestBody password,
                                      @Part("mobile") RequestBody mobile);

    @Multipart
    @POST("test_api?action=addtrip")
    Call<AddTripResponse> addLocation(@Part("id") RequestBody Userid,
                                      @Part("user_latitude") RequestBody Lat,
                                      @Part("user_longitude") RequestBody Long);


    @POST("api/api.php?req=trip")
    Call<TripResponse> tripRespons();


}