package com.example.p13connectinternet;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    private JSONObject items;
    LayoutInflater inflater;
    private JSONArray names;
    Context mContext;

    public ProductListAdapter (Context ctx, JSONObject obj) {
        // store reference to the loaded JSONArray from internet
        //items = obj;
        setItems(obj);

        //get inflater for later use
        inflater = LayoutInflater.from(ctx);
    }

    public JSONObject getItems() {
        return items;
    }

    public void setItems(JSONObject items) {
        this.items = items;
        //store key names for easy use later to lessen processing load
        names = items.names();
    }

    @NonNull
    @Override
    public ProductListAdapter.ProductViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        // inflate xml layout for single row item
        View itemView = inflater.inflate(R.layout.wordlist_item, parent, false);
        //create view holder and return it
        return new ProductViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ProductViewHolder holder, int position) {
        //retrieve data from array
        try {
            //get json object at this row position
            JSONObject obj = items.getJSONObject(names.getString(position));
            //display name in textView
            holder.tv.setText(obj.getString("name"));
        } catch (Exception e) {
            Log.e("ProductListAdapter", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return items.length();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv;
        ProductListAdapter mAdapter;
        public ProductViewHolder(View itemView, ProductListAdapter adapter) {
            super(itemView);
            //get textView for later use
            tv = itemView.findViewById(R.id.word);
            mAdapter = adapter;
            //set click listener
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            try {
                //get json object at clicked position
                JSONObject obj = items.getJSONObject(names.getString(this.getAdapterPosition()));
                //toast
                //print details from json object in Logcat
                Log.d("ProductViewHolder", "Clicked: " +
                        this.getAdapterPosition() + " - " +
                        obj.getString("name") + " $" +
                        obj.getString("price"));
                Toast.makeText(
                        view.getContext(),
                        obj.getString("name") + " $" +
                        obj.getString("price"),
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Log.e("ProductViewHolder", e.getMessage());
            }
        }
    }
}
