package com.example.pokmonh;
import com.example.pokmonh.model.AllPokemons;
import com.example.pokmonh.model.Pokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/v2/pokemon/?limit=1500&offset=0")
    public Call<AllPokemons> getPokemon();

    @GET("/api/v2/pokemon/{name}")
    public Call<Pokemon> getPokemon(@Path("name") String name);


}
