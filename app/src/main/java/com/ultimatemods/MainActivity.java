package com.ultimatemods;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.BLACK);
        root.setPadding(40, 80, 40, 40);

        // Header
        TextView header = new TextView(this);
        header.setText("⬢ ULTIMATE MODS ⬢");
        header.setTextColor(Color.GREEN);
        header.setTextSize(32);
        header.setPadding(0, 0, 0, 20);
        root.addView(header);

        TextView sub = new TextView(this);
        sub.setText("The Modder's Dream Toolkit");
        sub.setTextColor(0xFF00AA00);
        sub.setTextSize(14);
        sub.setPadding(0, 0, 0, 60);
        root.addView(sub);

        // Load apps
        loadInstalledApps(root);

        setContentView(root);
    }

    private void loadInstalledApps(LinearLayout container) {
        TextView section = new TextView(this);
        section.setText("→ INSTALLED APPS:");
        section.setTextColor(0xFF00FFAA);
        section.setTextSize(16);
        section.setPadding(0, 30, 0, 20);
        container.addView(section);

        PackageManager pm = getPackageManager();
        int count = 0;
        try {
            for (PackageInfo pkg : pm.getInstalledPackages(0)) {
                String name = pkg.packageName;
                if (name.equals(getPackageName())) continue;
                if (name.startsWith("com.android.")) continue;
                if (name.startsWith("android.")) continue;

                TextView tv = new TextView(this);
                tv.setText("• " + pm.getApplicationLabel(pkg.applicationInfo) + 
                          "\n   " + name);
                tv.setTextColor(0xFFAAFFAA);
                tv.setTextSize(13);
                tv.setPadding(20, 20, 20, 20);
                tv.setBackgroundColor(0xFF0F1F0F);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0, 10, 0, 10);
                tv.setLayoutParams(lp);
                container.addView(tv);
                count++;
            }
        } catch (Exception e) {
            TextView err = new TextView(this);
            err.setText("Error: " + e.getMessage());
            err.setTextColor(Color.RED);
            container.addView(err);
        }

        TextView total = new TextView(this);
        total.setText("\n→ Total apps loaded: " + count);
        total.setTextColor(Color.GREEN);
        total.setTextSize(14);
        total.setPadding(0, 30, 0, 0);
        container.addView(total);
    }
}
