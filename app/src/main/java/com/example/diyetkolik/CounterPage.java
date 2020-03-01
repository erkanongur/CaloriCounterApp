package com.example.diyetkolik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CounterPage extends AppCompatActivity {
    RecyclerView counterList_recyclerView;
    TextView caloriCount_textView;
    Button back_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_page);

        caloriCount_textView = findViewById(R.id.caloriCount_textView);
        counterList_recyclerView = findViewById(R.id.counterList_recyclerView);
        back_Button = findViewById(R.id.back_button);

        back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainPage();
            }
        });

        new FirebaseDataBaseHelper("Counter").readItemsContinuously(new FirebaseDataBaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Item> Items, List<String> keys) {
                int count = 0;
                for (Item i : Items) {
                    count += i.getNumber()*Integer.parseInt(i.getCalori().split(" ")[0]);
                }
                caloriCount_textView.setText(Integer.toString(count));
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        }, "");

        new FirebaseDataBaseHelper("Counter").readItemsContinuously(new FirebaseDataBaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Item> Items, List<String> keys) {
                if (!Items.isEmpty()) {
                    new RecyclerViewConfig().setConfig_counterPage(counterList_recyclerView, CounterPage.this, Items, keys);
                }else{
                    Toast.makeText(getApplicationContext(), "Listeniz Boş !!", Toast.LENGTH_LONG).show();
                    toMainPage();
                }
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        }, "");
    }
    public void toMainPage(){
        Intent intent = new Intent(getApplicationContext(), MainPage.class);
        startActivity(intent);
    }
}
