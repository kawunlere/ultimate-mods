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
import android.widget.Toast;

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
            for (PackageInfo appPkg : pm.getInstalledPackages(0)) {
                String pkgName = appPkg.packageName;
                
                // Skip our own app only
                if (pkgName.equals(getPackageName())) continue;

                // Show ALL apps (including system and user)
                // No filter - user wants to see everything

                LinearLayout card = new LinearLayout(this);
                card.setOrientation(LinearLayout.HORIZONTAL);
                card.setBackgroundColor(0xFF0F1F0F);
                card.setPadding(15, 15, 15, 15);
                LinearLayout.LayoutParams cardLp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
                cardLp.setMargins(0, 8, 0, 8);
                card.setLayoutParams(cardLp);

                ImageView icon = new ImageView(this);
                try {
                    Drawable dr = pm.getApplicationIcon(appPkg.applicationInfo);
                    icon.setImageDrawable(dr);
                } catch (Exception e) {
                    icon.setBackgroundColor(0xFF222222);
                }
                LinearLayout.LayoutParams iconLp = new LinearLayout.LayoutParams(80, 80);
                iconLp.setMargins(0, 0, 15, 0);
                icon.setLayoutParams(iconLp);
                card.addView(icon);

                LinearLayout infoCol = new LinearLayout(this);
                infoCol.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams infoLp = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                infoCol.setLayoutParams(infoLp);

                TextView appName = new TextView(this);
                String label;
                try {
                    label = pm.getApplicationLabel(appPkg.applicationInfo).toString();
                } catch (Exception e) {
                    label = pkgName;
                }
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
                count++;
                if (count > 500) break;
            }
        } catch (Exception e) {
            TextView err = new TextView(this);
            err.setText("Error: " + e.getMessage());
            err.setText(Color.RED);
        }

        TextView total = new TextView(this);
        total.setText("\n→ Total apps loaded: " + count);
        total.setTextColor(Color.GREEN);
        total.setTextSize(14);
        total.setPadding(0, 30, 0, 0);
        container.addView(total);
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
