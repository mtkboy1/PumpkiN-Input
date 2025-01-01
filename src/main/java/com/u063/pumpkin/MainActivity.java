package com.u063.pumpkin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.u063.pumpkin.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'pumpkin' library on application startup.
    static {
        System.loadLibrary("pumpkin");
    }
    static int idnum = 0;
    static ArrayList<Integer> idof = new ArrayList<>();
    String adapterString;
    Thread thr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.objects);
        String[] spi={"text","tap","keyevent"};
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spi);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapterString = (String) parent.getItemAtPosition(position);
                Log.e("hi",(String) parent.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }
    public void create(View w){
        Spinner spinner = (Spinner) findViewById(R.id.objects);
        LinearLayout ll = new LinearLayout(this);
        ll.setGravity(LinearLayout.VERTICAL);
        EditText name = new EditText(this);
        if(Objects.equals(adapterString, "text")) {
            name.setText("input " + adapterString+" hello");
        } else if(Objects.equals(adapterString, "keyevent")){
            name.setText("input " + adapterString+" 10");
        } else {
            name.setText("input " + adapterString+" 0 0");
        }
        idnum+=1;
        name.setId(idnum);
        idof.add(idnum);

        LinearLayout ls = findViewById(R.id.macros);
        ls.addView(name);
    }
    public void save(View w){
        File f = new File(this.getFilesDir(),"config.txt");
        try {
            f.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            for(int i=0; i<idof.size(); i++) {
                EditText etx = findViewById(idof.get(i));
                for (int i1 = 0; i1 < etx.getText().length(); i1++) {
                    out.write(etx.getText().charAt(i1));
                }
                out.write('\n');
            }
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void run(View w){
        for(int i=0; i<idof.size(); i++) {
            EditText etx = findViewById(idof.get(i));
            ShellGuy sh = new ShellGuy();
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    sh.createInput(etx.getText().toString());
                }
            };
            EditText delayer = findViewById(R.id.delay);
            Timer timer = new Timer();
            timer.schedule(tt,Integer.parseInt(delayer.getText().toString()),100);
            //thr.start();
        }
    }
    public void read(View w){
        LinearLayout ll = findViewById(R.id.macros);
        idnum=0;
        for(int i=0; i<idof.size(); i++){
            idof.remove(0);
            ll.removeAllViews();
        }
        File f = new File(this.getFilesDir(),"config.txt");
        try {
            FileInputStream fs = new FileInputStream(f);
            BufferedReader bf = new BufferedReader(new InputStreamReader(fs));
            String l;
            while((l=bf.readLine())!=null){
                idnum+=1;
                idof.add(idnum);
                EditText tx = new EditText(this);
                tx.setId(idnum);
                tx.setText(l);
                ll.addView(tx);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void startShell(View w){
        Intent i = new Intent(this, ShellActivity.class);
        startActivity(i);
    }
    public native int stringFromJNI();
}