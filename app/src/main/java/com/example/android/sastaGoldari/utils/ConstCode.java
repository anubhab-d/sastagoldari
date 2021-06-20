package com.example.android.sastaGoldari.utils;

import android.content.Context;
import android.widget.Toast;

public class ConstCode {
    public static void showToast(Context context, String txt) {
        Toast.makeText(context, txt, Toast.LENGTH_SHORT).show();
    }
}
