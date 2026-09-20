package com.ultimatemods;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setBackgroundColor(0xFF000000);

        TextView header = new TextView(this);
        header.setText("⬢ ULTIMATE MODS ⬢");
        header.setTextColor(0xFF00FF00);
        header.setTextSize(24);
        header.setGravity(android.view.Gravity.CENTER);
        header.setPadding(20, 20, 20, 20);

        TextView subtitle = new TextView(this);
        subtitle.setText("The Modder's Dream Toolkit");
        subtitle.setTextColor(0xFF00AA00);
        subtitle.setTextSize(14);
        subtitle.setGravity(android.view.Gravity.CENTER);

        rootLayout.addView(header);
        rootLayout.addView(subtitle);

        TextView sectionTitle = new TextView(this);
        sectionTitle.setText("Installed Apps:");
        sectionTitle.setTextColor(0xFF00FFAA);
        sectionTitle.setTextSize(18);
        sectionTitle.setPadding(20, 30, 20, 20);
        rootLayout.addView(sectionTitle);

        loadInstalledApps(rootLayout);

        setContentView(rootLayout);
    }

    private void loadInstalledApps(LinearLayout container) {
        PackageManager pm = getPackageManager();

        try {
            for (PackageInfo pkg : pm.getInstalledPackages(PackageManager.GET_META_DATA)) {

                if (pkg.packageName.equals(getPackageName())) continue;
                if (pkg.packageName.startsWith("com.android.")
                    || pkg.packageName.startsWith("com.google.")
                    || pkg.packageName.startsWith("android.")) continue;

                CardView card = new CardView(this);
                card.setCardBackgroundColor(0xFF0F1F0F);
                card.setRadius(8);
                card.setCardElevation(2);
                LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                );
                cardParams.setMargins(20, 10, 20, 10);
                card.setLayoutParams(cardParams);

                TextView appName = new TextView(this);
                String name = pm.getApplicationLabel(pkg.applicationInfo).toString();
                appName.setText(name);
                appName.setTextColor(0xFF00FF00);
                appName.setTextSize(16);
                appName.setPadding(15, 15, 15, 5);

                TextView pkgName = new TextView(this);
                pkgName.setText(pkg.packageName);
                pkgName.setTextColor(0xFF008800);
                pkgName.setTextSize(11);
                pkgName.setPadding(15, 0, 15, 15);

                card.addView(appName);
                card.addView(pkgName);
                container.addView(card);

                final String appPkg = pkg.packageName;
                card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Will add actions next phase
                    }
                });
            }
        } catch (Exception e) {
            TextView error = new TextView(this);
            error.setText("Error: " + e.getMessage());
            error.setTextColor(0xFFFF0000);
            container.addView(error);
        }
    }
}
