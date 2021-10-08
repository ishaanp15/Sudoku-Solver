package com.example.table;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    TextView textView;
    LinearLayout ll = null;
    public static final String int_name = "setting.level";
    int k = 55;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_settings);

        SharedPreferences sp = getSharedPreferences("dif_lvl", MODE_PRIVATE);

        if(sp.contains("highlighted"))
            highlight(sp.getString("highlighted", ""));
    }

    public void lvl(View v){
        textView = findViewById(v.getId());
        String s = textView.getText().toString();

        highlight(s);

        SharedPreferences sp = getSharedPreferences("dif_lvl", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();

        ed.putString("highlighted", s);
        ed.apply();
    }

    public void highlight(String s){
        if(ll!=null)
            ll.setBackgroundColor(0x00000000);

        Resources r = getResources();
        String name = getPackageName();
        int i = 0;

        if(s.equals("Easy")) {
            k = 45;
            ll = findViewById(r.getIdentifier("llh1", "id", name));
            ll.setBackgroundColor(0xFF31D1CC);
            i = 0xFF26FF00;
        }
        else if(s.equals("Medium")) {
            k = 55;
            ll = findViewById(r.getIdentifier("llh2", "id", name));
            ll.setBackgroundColor(0xFF31D1CC);
            i = 0xFFFFE400;
        }
        else if(s.equals("Hard")){
            k = 65;
            ll = findViewById(r.getIdentifier("llh3", "id", name));
            ll.setBackgroundColor(0xFF31D1CC);
            i = 0xFFFF0000;
        }

        SharedPreferences sp = getSharedPreferences("dif_lvl", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();

        ed.putInt(int_name, k);
        ed.putString("diff_name", s);
        ed.putInt("colour", i);
        ed.apply();
    }
}