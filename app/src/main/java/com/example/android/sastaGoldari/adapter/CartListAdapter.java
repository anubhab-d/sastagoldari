package com.example.android.sastaGoldari.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.sastaGoldari.R;
import com.example.android.sastaGoldari.interfaces.OnCartListRemoveBtnClicked;
import com.example.android.sastaGoldari.model.CartModel;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartListViewHolder> {
    ArrayList<CartModel> cartList = new ArrayList<>();
    ArrayList<CartModel> newCartList = new ArrayList<>(cartList);
    OnCartListRemoveBtnClicked click;
    public CartListAdapter(OnCartListRemoveBtnClicked click){
        this.click = click;
    }
    @Override
    public CartListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item,parent,false);
        return new CartListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CartListAdapter.CartListViewHolder holder, int position) {
        int itemCount = cartList.size();
        holder.tblName.setText(cartList.get(position).getName());
        holder.tblPrice.setText(cartList.get(position).getPrice()+"/"+cartList.get(position).getUnit());
        holder.tblQuantity.setText(cartList.get(position).getQty()+ " "+cartList.get(position).getUnit());
        double total = Double.parseDouble(cartList.get(position).getPrice().substring(1)) * Double.parseDouble(cartList.get(position).getQty());
        holder.tblTotal.setText(cartList.get(position).getPrice()+ "×" +cartList.get(position).getQty()+"= ₹"+Double.toString(total));
        holder.tblRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // cartList.remove(position);
                click.onCartListButtonClicked(position);
                notifyDataSetChanged();
            }
        });
    }
    public void updateList(ArrayList newList) {
        cartList.clear();
        cartList.addAll(newList);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CartListViewHolder extends RecyclerView.ViewHolder {
        TextView tblName;
        TextView tblPrice;
        TextView tblQuantity;
        TextView tblTotal;
        ImageView tblRemoveBtn;
        TextView gtotal;
        TextView noti;

        public CartListViewHolder(View itemView) {
            super(itemView);
            tblName = itemView.findViewById(R.id.tblName);
            tblPrice = itemView.findViewById(R.id.tblPrice);
            tblQuantity = itemView.findViewById(R.id.tblQuantity);
            tblTotal = itemView.findViewById(R.id.tblTotal);
            tblRemoveBtn = itemView.findViewById(R.id.tblRemoveBtn);
            gtotal=itemView.findViewById(R.id.txtGrandTotal);
            noti= itemView.findViewById(R.id.txtNoti);
        }
    }
}
