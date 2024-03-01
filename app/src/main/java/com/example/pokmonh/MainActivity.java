package com.example.pokmonh;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.example.pokmonh.model.AllPokemons;
import com.example.pokmonh.model.Pokemon;
import com.example.pokmonh.model.Type;
import com.example.pokmonh.model.Types;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ApiService apiService;
    private AutoCompleteTextView autoCompleteTextView;

    private ListView listView;

    private ImageView imageView;

    private Button button;

    private TextView textView1, textView2;

    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        button = findViewById(R.id.button);
        listView = findViewById(R.id.pokemoninfo);
        imageView = findViewById(R.id.pokemonimg);
        textView1 = findViewById(R.id.pokemonname);
        textView2 = findViewById(R.id.pokemontypes);
        getAllPokemons(context);
        button.setOnClickListener(this::onButtonClick);
        textView1.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
    }

    public void getAllPokemons(Context context) {
        ApiCall.getInstance().apiService.getPokemon().enqueue(new Callback<AllPokemons>() {
            @Override
            public void onResponse(Call<AllPokemons> call, Response<AllPokemons> response) {
                if (response != null) {
                    AllPokemons pokemons = response.body();
                    List<String> namesList = new ArrayList<>();
                    for (Pokemon p : pokemons.getResults()) {
                        namesList.add(p.getName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, namesList);

                    autoCompleteTextView.setAdapter(adapter);
                }


            }

            @Override
            public void onFailure(Call<AllPokemons> call, Throwable t) {

                Log.e("api", t.getLocalizedMessage());
            }
        });
    }

    public void onButtonClick(View view) {
        try {
            String name = autoCompleteTextView.getText().toString();
            getPokemon(context, name);
        }
        catch (Exception e){
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void getPokemon(Context context, String name){
         ApiCall.getInstance().apiService.getPokemon(name).enqueue(new Callback<Pokemon>() {
             @Override
             public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                 try {
                     if (response != null){

                         Pokemon pokemon = response.body();
                         String url = pokemon.getSprites().getFront_default();

                         String name = pokemon.getName();
                         int weight = pokemon.getWeight();
                         int height  = pokemon.getHeight();
                         ArrayList<Types> types = pokemon.getTypes();

                         RequestOptions options = new RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round);
                         Glide.with(context).load(url).apply(options).into(imageView);

                         ArrayList<String> Info = new ArrayList<>();
                         Info.add("Height: " + height + "ft") ;
                         Info.add("weight: " + weight + "lbs");


                         String TypeList = "Types: ";
                         for (Types type: types){
                             TypeList += type.getType().getName() + ",";
                         }
                         TypeList = TypeList.replaceAll(",$","" );
                         textView1.setText(name);
                         textView2.setText(TypeList);

                         ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, Info);

                         listView.setAdapter(adapter);
                         textView1.setVisibility(View.VISIBLE);
                         textView2.setVisibility(View.VISIBLE);
                         imageView.setVisibility(View.VISIBLE);
                         listView.setVisibility(View.VISIBLE);

                     }
                 }
                 catch (Exception e){
                     Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                 }
             }

             @Override
             public void onFailure(Call<Pokemon> call, Throwable t) {

             }
         });
    }
}