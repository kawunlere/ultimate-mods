package com.ultimatemods;

import android.app.Activity;
import android.app.AlertDialog;
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
        setContentView(root);
    }

    private void loadInstalledApps(LinearLayout container) {
        PackageManager pm = getPackageManager();
        int count = 0;
        try {
            for (PackageInfo pkg : pm.getInstalledPackages(0)) {
                String name = pkg.packageName;
                if (name.equals(getPackageName())) continue;
                if (name.startsWith("com.android.")) continue;
                if (name.startsWith("android.")) continue;

                // Create app card with icon
                LinearLayout card = new LinearLayout(this);
                card.setOrientation(LinearLayout.HORIZONTAL);
                card.setBackgroundColor(0xFF0F1F0F);
                card.setPadding(15, 15, 15, 15);
                LinearLayout.LayoutParams cardLp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
                cardLp.setMargins(0, 8, 0, 8);
                card.setLayoutParams(cardLp);

                // App icon
                ImageView icon = new ImageView(this);
                try {
                    Drawable drawable = pm.getApplicationIcon(pkg.applicationInfo);
                    icon.setImageDrawable(drawable);
                } catch (Exception e) {
                    icon.setBackgroundColor(0xFF222222);
                }
                LinearLayout.LayoutParams iconLp = new LinearLayout.LayoutParams(80, 80);
                iconLp.setMargins(0, 0, 15, 0);
                icon.setLayoutParams(iconLp);
                card.addView(icon);

                // App info container
                LinearLayout infoCol = new LinearLayout(this);
                infoCol.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams infoLp = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                infoCol.setLayoutParams(infoLp);

                TextView appName = new TextView(this);
                appName.setText(pm.getApplicationLabel(pkg.applicationInfo));
                appName.setTextColor(Color.GREEN);
                appName.setTextSize(15);
                infoCol.addView(appName);

                TextView pkgName = new TextView(this);
                pkgName.setText(name);
                pkgName.setTextColor(0xFF66AA66);
                pkgName.setTextSize(10);
                infoCol.addView(pkgName);

                card.addView(infoCol);
                container.addView(card);

                final String pkg = name;
                final String label = pm.getApplicationLabel(pkg.applicationInfo).toString();
                card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showModMenu(pkg, label);
                    }
                });
                count++;
            }
        } catch (Exception e) {}

        TextView total = new TextView(this);
        total.setText("\n→ Total apps: " + count);
        total.setTextColor(Color.GREEN);
        total.setTextSize(14);
        total.setPadding(0, 30, 0, 0);
        container.addView(total);
    }

    private void showModMenu(String pkgName, String label) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("⬢ MOD OPTIONS");
        builder.setMessage("App: " + label + "\nPackage: " + pkgName + 
                          "\n\nSelect a mod option:");
        
        String[] options = {
            "🔓 Unlock Premium",
            "📵 Remove Ads", 
            "🔐 Bypass Login",
            "📦 Backup APK",
            "🗑️ Uninstall App",
            "❌ Cancel"
        };
        
        builder.setItems(options, (dialog, which) -> {
            switch (which) {
                case 0:
                    showToast("🔓 Premium unlock - Coming soon!");
                    break;
                case 1:
                    showToast("📵 Ad removal - Coming soon!");
                    break;
                case 2:
                    showToast("🔐 Login bypass - Coming soon!");
                    break;
                case 3:
                    showToast("📦 Backup - Coming soon!");
                    break;
                case 4:
                    showToast("🗑️ Uninstall - Coming soon!");
                    break;
            }
        });
        builder.show();
    }

    private void showToast(String msg) {
        android.widget.Toast.makeText(this, msg, android.widget.Toast.LENGTH_SHORT).show();
    }
}
