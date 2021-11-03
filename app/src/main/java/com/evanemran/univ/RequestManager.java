package com.evanemran.univ;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://universities.hipolabs.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getUniv(OnFetchDataListener listener, String name, String country){
        getUniversities getUniversities = retrofit.create(getUniversities.class);
        Call<List<APIResponse>> call =getUniversities.callUniversity(name, country);

        try{
            call.enqueue(new Callback<List<APIResponse>>() {
                @Override
                public void onResponse(Call<List<APIResponse>> call, Response<List<APIResponse>> response) {
                    if (!response.isSuccessful()){
                        Toast.makeText(context, "Request Failed!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    listener.onResponse(response.body(), response.message());
                }

                @Override
                public void onFailure(Call<List<APIResponse>> call, Throwable t) {
                    listener.onError(t.getMessage());
                }

            });
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Error Occurred!!", Toast.LENGTH_SHORT).show();
        }

    }

    public void getUnivByName(OnFetchDataListener listener, String name){
        getUniversitiesByName getUniversitiesByName = retrofit.create(getUniversitiesByName.class);
        Call<List<APIResponse>> call =getUniversitiesByName.callUniversityByName(name);

        try{
            call.enqueue(new Callback<List<APIResponse>>() {
                @Override
                public void onResponse(Call<List<APIResponse>> call, Response<List<APIResponse>> response) {
                    if (!response.isSuccessful()){
                        Toast.makeText(context, "Request Failed!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    listener.onResponse(response.body(), response.message());
                }

                @Override
                public void onFailure(Call<List<APIResponse>> call, Throwable t) {
                    listener.onError(t.getMessage());
                }

            });
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Error Occurred!!", Toast.LENGTH_SHORT).show();
        }

    }
    public void getUnivByCountry(OnFetchDataListener listener, String country){
        getUniversitiesByCountry getUniversitiesByCountry = retrofit.create(getUniversitiesByCountry.class);
        Call<List<APIResponse>> call =getUniversitiesByCountry.callUniversityByCountry(country);

        try{
            call.enqueue(new Callback<List<APIResponse>>() {
                @Override
                public void onResponse(Call<List<APIResponse>> call, Response<List<APIResponse>> response) {
                    if (!response.isSuccessful()){
                        Toast.makeText(context, "Request Failed!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    listener.onResponse(response.body(), response.message());
                }

                @Override
                public void onFailure(Call<List<APIResponse>> call, Throwable t) {
                    listener.onError(t.getMessage());
                }

            });
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Error Occurred!!", Toast.LENGTH_SHORT).show();
        }

    }


    public interface getUniversitiesByName {
        @GET("search")
        Call<List<APIResponse>> callUniversityByName(
                @Query("name") String name
        );
    }

    public interface getUniversitiesByCountry {
        @GET("search")
        Call<List<APIResponse>> callUniversityByCountry(
                @Query("country") String name
        );
    }

    public interface getUniversities {
        @GET("search")
        Call<List<APIResponse>> callUniversity(
                @Query("name") String name,
                @Query("Country") String country
        );
    }
}
