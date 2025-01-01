package com.u063.pumpkin;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.u063.pumpkin.databinding.ActivityMainBinding;

public class ShellActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.shell_console);
    }
    public void enter(View v){
        EditText etx = findViewById(R.id.shell);
        ShellGuy sh = new ShellGuy();
        TextView tx = new TextView(this);
        tx.setTextColor(Color.BLACK);
        tx.setTextSize(25);
        LinearLayout ll = findViewById(R.id.pull);
        Thread thr = new Thread(new Runnable() {
            @Override
            public void run() {
                tx.setText(sh.createInput(etx.getText().toString()));
            }
        });
        thr.setPriority(1);
        thr.start();
        ll.addView(tx);
    }
}
