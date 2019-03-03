package com.sourcey.materiallogindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.sourcey.materiallogindemo.Adapter.LocationListAdapter;
import com.sourcey.materiallogindemo.Model.TripResponse;
import com.sourcey.materiallogindemo.Retrofit.RestClient;
import com.sourcey.materiallogindemo.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripResponseActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private TripResponse tripResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_response);
        recyclerView = findViewById(R.id.recycler);
        getTripLocation();

    }

    private void getTripLocation() {

        //show progress dialog
        Utils.showProgressDialog(TripResponseActivity.this);

        RestClient.tripRespons(new Callback<TripResponse>() {
            @Override
            public void onResponse(Call<TripResponse> call, Response<TripResponse> response) {


                tripResponse =response.body();
                if (response.isSuccessful()) {
                    if (tripResponse != null && tripResponse.getTripDetail()!=null&& tripResponse.getTripDetail().size() > 0) {
                        Utils.dismissProgressDialog();
                        Log.d("Api Response :", "Got Success from Api");
                        LocationListAdapter locationListAdapter = new LocationListAdapter(getApplicationContext());
                        locationListAdapter.setData(tripResponse.getTripDetail());

                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setAdapter(locationListAdapter);

                        Log.d("Api Response :", "Got Success from Api");

                    }
                    ;
                } else {
                    Log.d("Api Response :", "Got Success from Api");

                    Toast.makeText(TripResponseActivity.this, "No data", Toast.LENGTH_SHORT).show();

                }
            }


            @Override
            public void onFailure(Call<TripResponse> call, Throwable t) {
                Utils.dismissProgressDialog();

            }
        });


    }
}
    /*private TripResponse tripResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_response);
        getTripLocation();


    }

    private void getTripLocation() {

        Utils.showProgressDialog(this);
        RestClient.tripRespons(new Callback<TripResponse>() {
            @Override
            public void onResponse(Call<TripResponse> call, Response<TripResponse> response) {

                Utils.dismissProgressDialog();

                if (response.body() != null) {
                    if (response.body() != null) {


                        tripResponse = response.body();

                    } else {
                        Toast.makeText(TripResponseActivity.this, "Failed AddData", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TripResponse> call, Throwable t) {
                Utils.dismissProgressDialog();
                Utils.displayToast(TripResponseActivity.this, "Unable to AddData, please try again later");

            }
        });

    }

}
*/