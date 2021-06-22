package com.example.android.sastaGoldari.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.sastaGoldari.R;
import com.example.android.sastaGoldari.interfaces.OnProductButtonsClicked;
import com.example.android.sastaGoldari.model.SellingItems;

import java.util.ArrayList;

public class ProductMaintainAdapter extends RecyclerView.Adapter<ProductMaintainAdapter.ProductMaintaiViewHolder> {
    ArrayList<SellingItems> list = new ArrayList<>();
    OnProductButtonsClicked click;
    public ProductMaintainAdapter(OnProductButtonsClicked click){
        this.click = click;
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

        public ProductMaintaiViewHolder(View itemView) {
            super(itemView);
            etPName = itemView.findViewById(R.id.etPName);
            etPPrice = itemView.findViewById(R.id.etPPrice);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
