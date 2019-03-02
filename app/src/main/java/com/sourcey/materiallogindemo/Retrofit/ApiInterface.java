package com.sourcey.materiallogindemo.Retrofit;

import com.sourcey.materiallogindemo.Model.AddTripResponse;
import com.sourcey.materiallogindemo.Model.LoginResponse;
import com.sourcey.materiallogindemo.Model.SignupResponse;
import com.sourcey.materiallogindemo.Model.TripResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {


   /* @POST("api/api.php?req=login")
    Call<LoginRequest> loginUser(@Body LoginRequest loginRequest);
*/

   @Multipart
    @POST("api/api.php?req=login")
    Call<LoginResponse> loginUser(@Part("email_id") RequestBody email, @Part("password") RequestBody password);


    @Multipart
    @POST("api/api.php?req=registration")
    Call<SignupResponse> registerUser(@Part("fname") RequestBody fname,
                                      @Part("lname") RequestBody lname,
                                      @Part("email_id") RequestBody email,
                                      @Part("password") RequestBody password,
                                      @Part("mobile") RequestBody mobile);
    @Multipart
    @POST("api/api.php?req=addtrip")
    Call<AddTripResponse> addLocation(@Part("user_id") RequestBody Userid,
                                      @Part("trip_id") RequestBody Tripid,
                                      @Part("visit_id") RequestBody Visitid,
                                      @Part("lat") RequestBody Lat,
                                      @Part("long") RequestBody Long,
                                      @Part("status") RequestBody Status);

    @POST("api/api.php?req=trip")
    Call<TripResponse>tripRespons();







}