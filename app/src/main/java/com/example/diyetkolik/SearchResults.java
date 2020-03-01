package com.example.diyetkolik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class SearchResults extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    View view;
    Button back_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Bundle extras = getIntent().getExtras();
        String word = extras.getString("search_word");

        back_Button = findViewById(R.id.back_button);
        mRecyclerView = (RecyclerView) findViewById(R.id.items_recyclerView);

        back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainPage();
            }
        });

        new FirebaseDataBaseHelper("Items").readItems(new FirebaseDataBaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Item> Items, List<String> keys) {
                if (!Items.isEmpty()) {
                    new RecyclerViewConfig().setConfig_searchPage(mRecyclerView, SearchResults.this, Items, keys);
                }else{
                    Toast.makeText(getApplicationContext(), "Arama sonucunda herhangi bir sonuç bulunamadı..", Toast.LENGTH_LONG).show();
                    toMainPage();
                }
            }

            @Override
            public void DataIsInserted() {
                Toast.makeText(getApplicationContext(), "Ürün başarıyla listeye eklendi.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void DataIsUpdated() {
                Toast.makeText(getApplicationContext(), "Ürün başarıyla listeye eklendi.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void DataIsDeleted() {

            }
        }, word);
    }
    public void toMainPage(){
        Intent intent = new Intent(getApplicationContext(), MainPage.class);
        startActivity(intent);
    }
}
