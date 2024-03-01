package com.example.pokmonh;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ApiCall {
    public static String baseApi="https://pokeapi.co";
    public static ApiCall apiCall;
    ApiService apiService;


    public ApiCall(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static ApiCall getInstance(){
        if (apiCall == null)
            apiCall = new ApiCall();
        return apiCall;
    }
}
