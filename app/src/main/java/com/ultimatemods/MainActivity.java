package com.ultimatemods;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("⬢ ULTIMATE MODS ⬢\nThe Modder's Dream Toolkit\n\nv1.0 Ready!");
        tv.setTextSize(20);
        tv.setPadding(40, 80, 40, 40);
        setContentView(tv);
    }
}
