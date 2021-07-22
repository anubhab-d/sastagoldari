package com.adex11.android.sastaGoldari.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.adex11.android.sastaGoldari.R;
import com.adex11.android.sastaGoldari.model.CartModel;
import java.util.ArrayList;

public class ViewOrderDialogAdapter extends RecyclerView.Adapter<ViewOrderDialogAdapter.ViewOrderDialogViewHolder> {
    ArrayList<CartModel> list = new ArrayList<>();
    @Override
    public ViewOrderDialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_order_dialog_list_item,parent,false);
        return new ViewOrderDialogViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewOrderDialogAdapter.ViewOrderDialogViewHolder holder, int position) {
       holder.txtIName.setText(list.get(position).getName());
       holder.txtIPrice.setText(list.get(position).getPrice()+"/"+list.get(position).getUnit());
       holder.txtIQuantity.setText(list.get(position).getQty()+ " "+list.get(position).getUnit());
        double total = Double.parseDouble(list.get(position).getPrice().substring(1)) * Double.parseDouble(list.get(position).getQty());
       holder.txtITotal.setText(list.get(position).getPrice()+ "×" +list.get(position).getQty()+"= ₹"+Double.toString(total));
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

    public class ViewOrderDialogViewHolder extends RecyclerView.ViewHolder{
        TextView txtIName;
        TextView txtIPrice;
        TextView txtIQuantity;
        TextView txtITotal;
        public ViewOrderDialogViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIName = itemView.findViewById(R.id.txtIName);
            txtIPrice = itemView.findViewById(R.id.txtIPrice);
            txtIQuantity = itemView.findViewById(R.id.txtIQuantity);
            txtITotal = itemView.findViewById(R.id.txtITotal);
        }
    }
}
