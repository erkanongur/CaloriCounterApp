package com.example.diyetkolik;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewConfig {
    private Context mContext;
    private ItemAdapter mItemAdapter;
    private CounterItemAdapter mCounterItemAdapter;
    public void setConfig_searchPage(RecyclerView recyclerView, Context context, List<Item> Items, List<String> keys){
        mContext = context;
        mItemAdapter = new ItemAdapter(Items, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mItemAdapter);
    }
    public void setConfig_counterPage(RecyclerView recyclerView, Context context, List<Item> Items, List<String> keys){
        mContext = context;
        mCounterItemAdapter = new CounterItemAdapter(Items, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mCounterItemAdapter);
    }

    class ItemView extends RecyclerView.ViewHolder {
        private TextView name_textView, calori_textView, about_textView, number_textView;
        private Button addToList_button;
        private String key;

        public ItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false));

            name_textView = (TextView) itemView.findViewById(R.id.name_textView);
            calori_textView = (TextView) itemView.findViewById(R.id.calori_textView);
            about_textView = (TextView) itemView.findViewById(R.id.about_textView);
            number_textView = (TextView) itemView.findViewById(R.id.number_textView);
            addToList_button = (Button) itemView.findViewById(R.id.addToList_button);

        }
        public void bind(Item item, String key){
            name_textView.setText(item.getName());
            calori_textView.setText(item.getCalori());
            about_textView.setText(item.getAbout());
            number_textView.setText(String.valueOf(item.getNumber()));
            this.key = key;
        }
    }
    class ItemAdapter extends RecyclerView.Adapter<ItemView>{
        private List<Item> mItemList;
        private List<String> mKeys;

        public ItemAdapter(List<Item> mItemList, List<String> mKeys) {
            this.mItemList = mItemList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull final ItemView holder, final int position) {
            holder.bind(mItemList.get(position), mKeys.get(position));
            holder.addToList_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final Item item = new Item();
                    item.setName(holder.name_textView.getText().toString());
                    item.setCalori(holder.calori_textView.getText().toString());
                    item.setAbout(holder.about_textView.getText().toString());
                    item.setNumber(Integer.parseInt(holder.number_textView.getText().toString()));

                    new FirebaseDataBaseHelper("Counter").readItems(new FirebaseDataBaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(final List<Item> Items, final List<String> keys) {
                            Item tempItem = new Item();
                            String key = null;
                            boolean isItemOnCounter = false;
                            for (Item i : Items) {
                                if (i.getName().equals(item.getName())){
                                    isItemOnCounter = true;
                                    key = keys.get(Items.indexOf(i));
                                    int number = i.getNumber();
                                    number++;
                                    item.setNumber(number);
                                    break;
                                }
                            }
                            if(isItemOnCounter){
                                final String finalKey = key;
                                new FirebaseDataBaseHelper("Counter").updateItem(key, item, new FirebaseDataBaseHelper.DataStatus() {
                                    @Override
                                    public void DataIsLoaded(List<Item> Items, List<String> keys) {

                                    }

                                    @Override
                                    public void DataIsInserted() {

                                    }

                                    @Override
                                    public void DataIsUpdated() {
                                        Log.d("Success", "Item updated");
                                        holder.bind(item, finalKey);
                                    }

                                    @Override
                                    public void DataIsDeleted() {

                                    }
                                });
                            }else{
                                final String finalKey = key;
                                new FirebaseDataBaseHelper("Counter").addItem(item, new FirebaseDataBaseHelper.DataStatus() {
                                    @Override
                                    public void DataIsLoaded(List<Item> Items, List<String> keys) {

                                    }

                                    @Override
                                    public void DataIsInserted() {
                                        Log.d("Success", "Item updated");
                                        holder.bind(item, finalKey);
                                    }

                                    @Override
                                    public void DataIsUpdated() {

                                    }

                                    @Override
                                    public void DataIsDeleted() {

                                    }
                                });
                            }
                            return;
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
            });
        }

        @Override
        public int getItemCount() {
            return mItemList.size();
        }
    }
    class CounterItemView extends RecyclerView.ViewHolder {
        private TextView name_textView, calori_textView, about_textView, number_textView;
        private Button addToList_button, removeFromList_button;
        private String key;

        public CounterItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.counter_item_list, parent, false));

            name_textView = (TextView) itemView.findViewById(R.id.name_textView);
            calori_textView = (TextView) itemView.findViewById(R.id.calori_textView);
            about_textView = (TextView) itemView.findViewById(R.id.about_textView);
            number_textView = (TextView) itemView.findViewById(R.id.number_textView);
            addToList_button = (Button) itemView.findViewById(R.id.addToList_button);
            removeFromList_button = (Button) itemView.findViewById(R.id.removeFromList_button);

        }
        public void bind(Item item, String key){
            name_textView.setText(item.getName());
            calori_textView.setText(item.getCalori());
            about_textView.setText(item.getAbout());
            number_textView.setText(String.valueOf(item.getNumber()));
            this.key = key;
        }
    }
    class CounterItemAdapter extends RecyclerView.Adapter<CounterItemView>{
        private List<Item> mItemList;
        private List<String> mKeys;

        public CounterItemAdapter(List<Item> mItemList, List<String> mKeys) {
            this.mItemList = mItemList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public CounterItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CounterItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull final CounterItemView holder, final int position) {
            holder.bind(mItemList.get(position), mKeys.get(position));
            holder.removeFromList_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Item item = new Item();
                    item.setName(holder.name_textView.getText().toString());
                    item.setCalori(holder.calori_textView.getText().toString());
                    item.setAbout(holder.about_textView.getText().toString());
                    item.setNumber(Integer.parseInt(holder.number_textView.getText().toString()));

                    new FirebaseDataBaseHelper("Counter").readItems(new FirebaseDataBaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(final List<Item> Items, final List<String> keys) {
                            Item tempItem = new Item();
                            String key = null;
                            int index = 0;
                            boolean isItemOnCounter = false;
                            for (Item i : Items) {
                                if (i.getName().equals(item.getName())){
                                    isItemOnCounter = true;
                                    index = Items.indexOf(i);
                                    key = keys.get(index);
                                    break;
                                }
                            }
                            if(isItemOnCounter){
                                final String finalKey = key;
                                final int index2 = index;
                                new FirebaseDataBaseHelper("Counter").deleteItem(key, new FirebaseDataBaseHelper.DataStatus() {
                                    @Override
                                    public void DataIsLoaded(List<Item> Items, List<String> keys) {

                                    }

                                    @Override
                                    public void DataIsInserted() {

                                    }

                                    @Override
                                    public void DataIsUpdated() {

                                    }

                                    @Override
                                    public void DataIsDeleted() {
                                        Log.d("Success", "Item Deleted.");
                                    }
                                });
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

            });

            holder.addToList_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Item item = new Item();
                    item.setName(holder.name_textView.getText().toString());
                    item.setCalori(holder.calori_textView.getText().toString());
                    item.setAbout(holder.about_textView.getText().toString());
                    item.setNumber(Integer.parseInt(holder.number_textView.getText().toString()));


                    new FirebaseDataBaseHelper("Counter").readItems(new FirebaseDataBaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(List<Item> Items, List<String> keys) {
                            Item tempItem = new Item();
                            String key = null;
                            boolean isItemOnCounter = false;
                            for (Item i : Items) {
                                if (i.getName().equals(item.getName())){
                                    isItemOnCounter = true;
                                    key = keys.get(Items.indexOf(i));
                                    int number = i.getNumber();
                                    number++;
                                    item.setNumber(number);
                                    break;
                                }
                            }
                            if(isItemOnCounter){
                                final String finalKey = key;
                                new FirebaseDataBaseHelper("Counter").updateItem(key, item, new FirebaseDataBaseHelper.DataStatus() {
                                    @Override
                                    public void DataIsLoaded(List<Item> Items, List<String> keys) {

                                    }

                                    @Override
                                    public void DataIsInserted() {

                                    }

                                    @Override
                                    public void DataIsUpdated() {
                                        Log.d("Success", "Item updated");
                                        holder.bind(item, finalKey);
                                    }

                                    @Override
                                    public void DataIsDeleted() {

                                    }
                                });
                            }else{
                                final String finalKey = key;
                                new FirebaseDataBaseHelper("Counter").addItem(item, new FirebaseDataBaseHelper.DataStatus() {
                                    @Override
                                    public void DataIsLoaded(List<Item> Items, List<String> keys) {

                                    }

                                    @Override
                                    public void DataIsInserted() {
                                        Log.d("Success", "Item updated");
                                        holder.bind(item, finalKey);
                                    }

                                    @Override
                                    public void DataIsUpdated() {

                                    }

                                    @Override
                                    public void DataIsDeleted() {

                                    }
                                });
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
            });
        }

        @Override
        public int getItemCount() {
            return mItemList.size();
        }
    }
}
