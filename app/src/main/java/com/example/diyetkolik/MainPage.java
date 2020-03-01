package com.example.diyetkolik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainPage extends AppCompatActivity {
    Button toCounterPage_Button;
    Button search_Button;
    Button addItem_Button;
    EditText search_editText;
    Button user_Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        toCounterPage_Button = findViewById(R.id.caloriCounter_button);
        search_Button = findViewById(R.id.search_button);
        addItem_Button = findViewById(R.id.addItem_button);
        user_Button = findViewById(R.id.user_button);

        search_editText = findViewById(R.id.search_editText);

        user_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toUserPage("MainPage");
            }
        });

        toCounterPage_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toCounterPage();
            }
        });

        search_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = search_editText.getText().toString();
                if(!TextUtils.isEmpty(word)){
                    toSearchResults(word);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Lütfen Arama Kısmını Boş Bırakmayınız!!", Toast.LENGTH_LONG).show();
                }
            }
        });
        addItem_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAddItemPage();
            }
        });
    }
    public void toCounterPage(){
        Intent intent = new Intent(getApplicationContext(), CounterPage.class);
        startActivity(intent);
    }
    public void toSearchResults(String word){
        Intent intent = new Intent(getApplicationContext(), SearchResults.class);
        intent.putExtra("search_word", word);
        startActivity(intent);
    }
    public void toAddItemPage(){
        Intent intent = new Intent(getApplicationContext(), AddItem.class);
        startActivity(intent);
    }
    public void toUserPage(String context){
        Intent intent = new Intent(getApplicationContext(), UserPage.class);
        intent.putExtra("lastPage", context);
        startActivity(intent);
    }
}
