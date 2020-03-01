package com.example.diyetkolik;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.DefaultTaskExecutor;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.DataTruncation;
import java.util.ArrayList;
import java.util.List;

public class FirebaseDataBaseHelper {
    private FirebaseDatabase fDatabase;
    private DatabaseReference fReferenceItems;
    private List<Item> Items = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Item> Items, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDataBaseHelper(String path) {
        fDatabase = FirebaseDatabase.getInstance();
        fReferenceItems = fDatabase.getReference(path);
    }

    public void readItemsContinuously(final DataStatus dataStatus, final String word){
        fReferenceItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Items.clear();
                List<String> keys = new ArrayList<>();

                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Item item = keyNode.getValue(Item.class);
                    if(item.getName().contains(word)) {
                        Items.add(item);
                    }
                }
                dataStatus.DataIsLoaded(Items, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void readItems(final DataStatus dataStatus, final String word){
        fReferenceItems.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Items.clear();
                List<String> keys = new ArrayList<>();

                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Item item = keyNode.getValue(Item.class);
                    if(item.getName().contains(word)) {
                        Items.add(item);
                    }
                }
                dataStatus.DataIsLoaded(Items, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void deleteItem(String key, final DataStatus dataStatus){
        fReferenceItems.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                         dataStatus.DataIsDeleted();
                    }
                });
    }
    public void updateItem(String key, Item item, final DataStatus dataStatus) {
        fReferenceItems.child(key).setValue(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated();
                    }
                });
    }
    public void addItem(Item item, final DataStatus dataStatus){
        String key = fReferenceItems.push().getKey();
        fReferenceItems.child(key).setValue(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted();
                    }
                });
    }
}
