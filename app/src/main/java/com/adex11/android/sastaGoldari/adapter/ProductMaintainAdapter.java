package com.adex11.android.sastaGoldari.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.adex11.android.sastaGoldari.R;
import com.adex11.android.sastaGoldari.interfaces.OnProductButtonsClicked;
import com.adex11.android.sastaGoldari.model.SellingItems;

import java.util.ArrayList;

public class ProductMaintainAdapter extends RecyclerView.Adapter<ProductMaintainAdapter.ProductMaintaiViewHolder> {
    ArrayList<SellingItems> list = new ArrayList<>();
    OnProductButtonsClicked click;
    Context context;
    public ProductMaintainAdapter(OnProductButtonsClicked click, Context context){
        this.click = click;
        this.context = context;
    }

    @Override
    public ProductMaintaiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.your_item_list, parent, false);
        return new ProductMaintaiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductMaintainAdapter.ProductMaintaiViewHolder holder, int position) {
        holder.etPName.setText(list.get(position).getName());
        holder.etPPrice.setText("₹"+list.get(position).getPrice()+"/"+list.get(position).getUnit());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onEditButtonClicked(list.get(position).getId());
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onDeleteButtonClicked(position,list.get(position).getId());
            }
        });
        Glide.with(context).load(list.get(position).getImgL()).into(holder.imgI);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateList(ArrayList newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }

    public class ProductMaintaiViewHolder extends RecyclerView.ViewHolder {
        TextView etPName;
        TextView etPPrice;
        Button btnEdit;
        Button btnDelete;
        ImageView imgI;
        public ProductMaintaiViewHolder(View itemView) {
            super(itemView);
            etPName = itemView.findViewById(R.id.etPName);
            etPPrice = itemView.findViewById(R.id.etPPrice);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            imgI = itemView.findViewById(R.id.imgI);
        }
    }
}
