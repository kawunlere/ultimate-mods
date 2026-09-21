package com.ultimatemods;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.BLACK);
        root.setPadding(30, 80, 30, 30);

        TextView header = new TextView(this);
        header.setText("⬢ ULTIMATE MODS ⬢");
        header.setTextColor(Color.GREEN);
        header.setTextSize(32);
        header.setPadding(0, 0, 0, 10);
        root.addView(header);

        TextView sub = new TextView(this);
        sub.setText("The Modder's Dream Toolkit");
        sub.setTextColor(0xFF00AA00);
        sub.setTextSize(12);
        sub.setPadding(0, 0, 0, 40);
        root.addView(sub);

        TextView section = new TextView(this);
        section.setText("→ TAP ANY APP TO MOD:");
        section.setTextColor(0xFF00FFAA);
        section.setTextSize(14);
        section.setPadding(0, 20, 0, 20);
        root.addView(section);

        loadInstalledApps(root);

        android.widget.ScrollView scroll = new android.widget.ScrollView(this);
        scroll.setBackgroundColor(Color.BLACK);
        scroll.addView(root);
        setContentView(scroll);
    }

    private void loadInstalledApps(LinearLayout container) {
        PackageManager pm = getPackageManager();
        int count = 0;
        try {
            // Method 1: Get packages via shell command (no filter)
            Process process = Runtime.getRuntime().exec(
                new String[]{"sh", "-c", "pm list packages | cut -d':' -f2"});
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                if (line.equals(getPackageName())) continue;

                // Try to get package info
                String label;
                Drawable icon;
                try {
                    PackageInfo pkg = pm.getPackageInfo(line, 0);
                    label = pm.getApplicationLabel(pkg.applicationInfo).toString();
                    icon = pkg.applicationInfo.loadIcon(pm);
                } catch (Exception ex) {
                    // Package hidden from us - show generic
                    label = line;
                    icon = null;
                }
                addAppCard(container, line, label, icon);
                count++;
                if (count > 300) break;
            }
            reader.close();
            process.waitFor();
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

    private void addAppCard(LinearLayout container, String pkgName, String label, Drawable icon) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setBackgroundColor(0xFF0F1F0F);
        card.setPadding(15, 15, 15, 15);
        LinearLayout.LayoutParams cardLp = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
        cardLp.setMargins(0, 8, 0, 8);
        card.setLayoutParams(cardLp);

        ImageView iv = new ImageView(this);
        if (icon != null) iv.setImageDrawable(icon);
        else iv.setBackgroundColor(0xFF222222);
        LinearLayout.LayoutParams iconLp = new LinearLayout.LayoutParams(80, 80);
        iconLp.setMargins(0, 0, 15, 0);
        iv.setLayoutParams(iconLp);
        card.addView(iv);

        LinearLayout infoCol = new LinearLayout(this);
        infoCol.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams infoLp = new LinearLayout.LayoutParams(
            0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        infoCol.setLayoutParams(infoLp);

        TextView appName = new TextView(this);
        appName.setText(label);
        appName.setTextColor(Color.GREEN);
        appName.setTextSize(15);
        infoCol.addView(appName);

        TextView pkgText = new TextView(this);
        pkgText.setText(pkgName);
        pkgText.setTextColor(0xFF66AA66);
        pkgText.setTextSize(10);
        infoCol.addView(pkgText);

        card.addView(infoCol);
        container.addView(card);

        final String fPkg = pkgName;
        final String fLabel = label;
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showModMenu(fPkg, fLabel);
            }
        });
    }

    private void showModMenu(String pkgName, String label) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("⬢ MOD OPTIONS");
        builder.setMessage("App: " + label + "\n" + pkgName);

        final String[] opts = {
            "🔓 Unlock Premium",
            "📵 Remove Ads", 
            "🔐 Bypass Login",
            "📦 Backup APK",
            "🗑️ Uninstall App",
            "❌ Cancel"
        };

        builder.setItems(opts, (d, w) -> {
            switch (w) {
                case 0: showMsg("🔓 Premium - coming soon"); break;
                case 1: showMsg("📵 Ads - coming soon"); break;
                case 2: showMsg("🔐 Login - coming soon"); break;
                case 3: showMsg("📦 Backup - coming soon"); break;
                case 4: showMsg("🗑️ Uninstalling..."); break;
            }
        });
        builder.show();
    }

    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
