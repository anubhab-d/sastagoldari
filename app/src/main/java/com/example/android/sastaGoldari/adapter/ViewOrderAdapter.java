package com.example.android.sastaGoldari.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.sastaGoldari.R;
import com.example.android.sastaGoldari.interfaces.OnViewOrderListBtnClicked;
import com.example.android.sastaGoldari.model.CustomerModel;

import java.util.ArrayList;

public class ViewOrderAdapter extends RecyclerView.Adapter<ViewOrderAdapter.ViewOrderViewHolder> {
    ArrayList<CustomerModel> list = new ArrayList<>();
    OnViewOrderListBtnClicked listBtnClick;
    Context context;
    RecyclerView rvViewOrderItem;
public ViewOrderAdapter(OnViewOrderListBtnClicked listBtnClick,Context context){
    this.listBtnClick = listBtnClick;
    this.context = context;
}
    @Override
    public ViewOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_order_item, parent, false);
        return new ViewOrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewOrderAdapter.ViewOrderViewHolder holder, int position) {
        holder.etCName.setText(list.get(position).getName());
        holder.etCAddress.setText(list.get(position).getAddress());
        holder.etCPara.setText(list.get(position).getPara());
        holder.etCPhone.setText(list.get(position).getPhone());
        holder.imgList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // listBtnClick.onListButtonClicked(position);
                View customLayout = LayoutInflater.from(context).inflate(
                                R.layout.view_order_dialog,null
                        );
                listInit(customLayout);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(customLayout);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void listInit(View v) {
   rvViewOrderItem = v.findViewById(R.id.rvViewOrderItem);
   rvViewOrderItem.setLayoutManager(new LinearLayoutManager(context));
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

    public class ViewOrderViewHolder extends RecyclerView.ViewHolder {
        TextView etCName;
        TextView etCAddress;
        TextView etCPara;
        TextView etCPhone;
        ImageView imgList;
        public ViewOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            etCName = itemView.findViewById(R.id.etCName);
            etCAddress = itemView.findViewById(R.id.etCAddress);
            etCPara = itemView.findViewById(R.id.etCPara);
            etCPhone = itemView.findViewById(R.id.etCPhone);
            imgList = itemView.findViewById(R.id.imgList);
        }
    }
}
