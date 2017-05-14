package com.example.quoctuan.assetandsharepreference;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    TextView txtFont;
    ListView lvFont;
    ArrayList<String> arrFont;
    ArrayAdapter<String> fontAdapter;

    String tenLuuTru = "TrangThaiFont";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addControls() {
        txtFont = (TextView) findViewById(R.id.txtFont);
        lvFont = (ListView) findViewById(R.id.lvFont);
        arrFont = new ArrayList<>();
        fontAdapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                arrFont);
        lvFont.setAdapter(fontAdapter);
// gọi hết tài nguyên
        AssetManager assetManager = getAssets();
        try {
            String [] arrFontName = assetManager.list("font");
            arrFont.addAll(Arrays.asList(arrFontName));
            fontAdapter.notifyDataSetChanged();

            SharedPreferences preferences = getSharedPreferences(tenLuuTru,MODE_PRIVATE);
            String strFont = preferences.getString("FONTCHU","");
            if (strFont.length() > 0){
                Typeface typeface = Typeface.createFromAsset(getAssets(),strFont);
                txtFont.setTypeface(typeface);
            }
        } catch (IOException e) {
            Log.e("Loi Font ", e.toString());
        }
    }

    private void addEvents() {
        lvFont.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleChangeFont(position);
            }
        });

    }

    private void handleChangeFont(int position) {
        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/" + arrFont.get(position));
        txtFont.setTypeface(typeface);
//      save font
        SharedPreferences preferences = getSharedPreferences(tenLuuTru,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();//acept save data to file
        editor.putString("FONTCHU","font/" + arrFont.get(position));//bookmark(đánh dấu)
        editor.commit();//confirm
    }
}
