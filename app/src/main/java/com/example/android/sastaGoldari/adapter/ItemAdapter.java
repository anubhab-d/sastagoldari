package com.example.android.sastaGoldari.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.sastaGoldari.R;
import com.example.android.sastaGoldari.model.SellingItems;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
     private ArrayList<SellingItems> list = new ArrayList<>();
     public ArrayList<String> itemlist = new ArrayList<>();
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ItemViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.price.setText("₹"+list.get(position).getPrice());
        holder.unit.setText("/"+list.get(position).getUnit());
        holder.unit2.setText(list.get(position).getUnit());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void updateList(ArrayList newList){
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        TextView unit;
        TextView unit2;
        Button atc;
        Button plus;
        Button minus;
        TextView qty;
        public ItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            unit = itemView.findViewById(R.id.unit);
            atc = itemView.findViewById(R.id.btnATC);
            plus=itemView.findViewById(R.id.plus);
            minus=itemView.findViewById(R.id.minus);
            qty=itemView.findViewById(R.id.txtQuantity);
            unit2=itemView.findViewById(R.id.txtUnit);

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    double qtykg = Double.parseDouble(qty.getText().toString());
                    if(unit.getText().toString().equals("/kg")){
                        String stringdouble= Double.toString(qtykg+0.5);
                        qty.setText(stringdouble);
                }
                    else if(unit.getText().toString().equals("/qty")){
                        String stringdouble= Double.toString(qtykg+1.0);
                        qty.setText(stringdouble);
                }
                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    double qtykg = Double.parseDouble(qty.getText().toString());
                    if(qtykg>0.0){
                    if(unit.getText().toString().equals("/kg")){
                        String stringdouble= Double.toString(qtykg-0.5);
                        qty.setText(stringdouble);
                    }
                    else if(unit.getText().toString().equals("/qty")){
                        String stringdouble= Double.toString(qtykg-1.0);
                        qty.setText(stringdouble);
                    }
                }}
            });

            atc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemlist.add(name+" "+price+" "+ qty);
                    Log.d("Name: ", name.getText().toString());


                }
            });
        }



    }
}
