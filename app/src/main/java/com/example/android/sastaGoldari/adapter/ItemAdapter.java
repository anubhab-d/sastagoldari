package com.example.android.sastaGoldari.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.sastaGoldari.R;
import com.example.android.sastaGoldari.interfaces.OnButtonClicked;
import com.example.android.sastaGoldari.model.SellingItems;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> implements Filterable {
    private ArrayList<SellingItems> data;
    public ArrayList<String> itemlist = new ArrayList<>();
    ArrayList<SellingItems> dataFull;
    int sum=0;
    OnButtonClicked click;
    Context context;
    public ItemAdapter(OnButtonClicked click, Context context, ArrayList<SellingItems> data){
        this.click = click;
        this.context = context;
        this.data = data;
        dataFull = new ArrayList<>(data);
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ItemViewHolder holder, int position) {
        holder.name.setText(data.get(position).getName());
        holder.price.setText("₹" + data.get(position).getPrice());
        holder.unit.setText(data.get(position).getUnit());
        holder.unit2.setText(data.get(position).getUnit());
        holder.txtItemId.setText(data.get(position).getId());
        Glide.with(context).load(data.get(position).getImgL()).into(holder.itemimg);
        holder.atc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onAddButtonClicked(holder.name,holder.price,holder.unit,holder.qty);


            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

//    public void updateList(ArrayList newList) {
//        data.clear();
//        data.addAll(newList);
//        dataFull.addAll(newList);
//        notifyDataSetChanged();
//    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private final Filter exampleFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<SellingItems> sample=new ArrayList<>();
            if(constraint.toString().length()<1)
            {
                Log.d("chk_sample",Integer.toString(sample.size()));
                sample.addAll(dataFull);
            }
            else {
                String pattern=constraint.toString().toLowerCase().trim();
                for(SellingItems data: dataFull)
                {
                    String name =data.getName();
                    if(name.toLowerCase().contains(pattern))
                    {
                        sample.add(data);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=sample;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data.clear();
            ArrayList<SellingItems> demo = new ArrayList<>();
            demo.addAll((List)results.values);
            Log.d("chk_data",Integer.toString(demo.size()));
            data.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        TextView unit;
        TextView unit2;
        Button atc;
        Button plus;
        Button minus;
        TextView qty;
        ImageView itemimg;
        TextView txtItemId;
        TextView noti;
        ImageView imgItem;
        public ItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            unit = itemView.findViewById(R.id.unit);
            atc = itemView.findViewById(R.id.btnATC);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);
            qty = itemView.findViewById(R.id.txtQuantity);
            unit2 = itemView.findViewById(R.id.txtUnit);
            itemimg = itemView.findViewById(R.id.imgItem);
            txtItemId = itemView.findViewById(R.id.txtItemId);
            noti = itemView.findViewById(R.id.txtNoti);

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    double qtykg = Double.parseDouble(qty.getText().toString());
                    if (unit.getText().toString().equals("kg")) {
                        String stringdouble = Double.toString(qtykg + 0.5);
                        qty.setText(stringdouble);
                    } else if (unit.getText().toString().equals("qty")) {
                        String stringdouble = Double.toString(qtykg + 1.0);
                        qty.setText(stringdouble);
                    }
                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    double qtykg = Double.parseDouble(qty.getText().toString());
                    if (qtykg > 0.0) {
                        if (unit.getText().toString().equals("kg")) {
                            String stringdouble = Double.toString(qtykg - 0.5);
                            qty.setText(stringdouble);
                        } else if (unit.getText().toString().equals("qty")) {
                            String stringdouble = Double.toString(qtykg - 1.0);
                            qty.setText(stringdouble);
                        }
                    }
                }
            });

            atc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemlist.add(name + " " + price + " " + qty);
                    Log.d("Name: ", name.getText().toString());
                }
            });
        }
    }
}
