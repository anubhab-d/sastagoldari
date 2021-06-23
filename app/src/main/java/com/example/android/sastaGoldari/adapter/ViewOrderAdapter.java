package com.example.android.sastaGoldari.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
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
import com.example.android.sastaGoldari.model.CartModel;
import com.example.android.sastaGoldari.model.CustomerModel;
import com.example.android.sastaGoldari.utils.ConstCode;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewOrderAdapter extends RecyclerView.Adapter<ViewOrderAdapter.ViewOrderViewHolder> {
    ArrayList<CustomerModel> list = new ArrayList<>();
    OnViewOrderListBtnClicked listBtnClick;
    Context context;
    RecyclerView rvViewOrderItem;
    FirebaseFirestore firestore;
    ViewOrderDialogAdapter adapter = new ViewOrderDialogAdapter();
    List<CartModel> cartList = new ArrayList<>();

    public ViewOrderAdapter(OnViewOrderListBtnClicked listBtnClick, Context context) {
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
                        R.layout.view_order_dialog, null
                );
                listInit(customLayout);
                // adapter.updateList((ArrayList<CartModel>) list.get(position).getCartList());
                firestore.collection("orders")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("chk_list3",document.getId());
                                        if (list.get(position).getId().equals(document.getId())) {
                                            List<String> itemName = (List<String>) document.get("orderItemNames");
                                            List<String> itemPrice = (List<String>) document.get("orderItemPrice");
                                            List<String> itemQty = (List<String>) document.get("orderItemQty");
                                            List<String> itemUnit = (List<String>) document.get("orderItemUnit");
                                            Log.d("chk_list3", Integer.toString(itemName.size()));
                                            for (int i = 0; i < itemName.size(); i++) {
                                                cartList.add(new CartModel(itemName.get(i), itemPrice.get(i), itemUnit.get(i), itemQty.get(i)));
                                            }
                                            adapter.updateList((ArrayList<CartModel>) cartList);
                                            rvViewOrderItem.setAdapter(adapter);
                                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                            builder.setView(customLayout);
                                            AlertDialog dialog = builder.create();
                                            dialog.show();
                                            cartList.clear();
                                        }
                                    }
                                    // Log.d("chk_list2",Integer.toString(list.get(1).getCartList().size()));
                                    ConstCode.showToast(context,"Mashallah..");
                                } else {
                                    // Log.w(TAG, "Error getting documents.", task.getException());
                                    ConstCode.showToast(context,"Something went wrong...");
                                }
                            }
                        });
            //    rvViewOrderItem.setAdapter(adapter);
            }
        });
    }

    private void listInit(View v) {
        rvViewOrderItem = v.findViewById(R.id.rvViewOrderItem);
        rvViewOrderItem.setLayoutManager(new LinearLayoutManager(context));
        firestore = FirebaseFirestore.getInstance();
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
