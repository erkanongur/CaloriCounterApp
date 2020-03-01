package com.example.diyetkolik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.List;

public class AddItem extends AppCompatActivity {
    Button toCounterPage_Button;
    Button toSearchPage_Button;
    Button toAddItemPage_Button;
    Button addNewItem_Button;
    Button user_Button;
    EditText itemName_EditText;
    EditText itemCalori_EditText;
    EditText itemAbout_EditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        //TODO item ekleme yapılacak

        toCounterPage_Button = findViewById(R.id.caloriCounter_button);
        toSearchPage_Button = findViewById(R.id.searchCalori_button);
        toAddItemPage_Button = findViewById(R.id.addItem_button);
        addNewItem_Button = findViewById(R.id.addNewItem_button);
        user_Button = findViewById(R.id.user_button);

        itemName_EditText = findViewById(R.id.itemName_editText);
        itemCalori_EditText = findViewById(R.id.itemCalori_editText);
        itemAbout_EditText = findViewById(R.id.itemAbout_editText);

        user_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toUserPage("AddItem");
            }
        });

        addNewItem_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = itemName_EditText.getText().toString();
                String itemCalori = itemCalori_EditText.getText().toString() + " cal";
                String itemAbout = itemAbout_EditText.getText().toString();

                if(!TextUtils.isEmpty(itemName)){
                    if (!itemCalori.equals(" cal")){
                        if(!TextUtils.isEmpty(itemAbout)){
                            Item item = new Item();
                            item.setNumber(1);
                            item.setName(itemName);
                            item.setCalori(itemCalori);
                            item.setAbout(itemAbout);
                            new FirebaseDataBaseHelper("Items").addItem(item, new FirebaseDataBaseHelper.DataStatus() {
                                @Override
                                public void DataIsLoaded(List<Item> Items, List<String> keys) {

                                }

                                @Override
                                public void DataIsInserted() {
                                    Toast.makeText(getApplicationContext(),"Ürün başarı ile eklendi.", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void DataIsUpdated() {

                                }

                                @Override
                                public void DataIsDeleted() {

                                }
                            });
                        }else{
                            Toast.makeText(getApplicationContext(), "Ürün Hakkında kısmı boş bırakılamaz", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Ürün kalori miktarı boş bırakılamaz", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Ürün adı boş bırakılamaz !!", Toast.LENGTH_LONG).show();
                }
            }
        });

        toCounterPage_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toCounterPage();
            }
        });

        toSearchPage_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSearchPage();
            }
        });

        toAddItemPage_Button.setOnClickListener(new View.OnClickListener() {
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
    public void toSearchPage(){
        Intent intent = new Intent(getApplicationContext(), MainPage.class);
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
