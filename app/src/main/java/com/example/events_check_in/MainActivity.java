package com.example.events_check_in;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Map<Integer, Fragment> fragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // cor barra de status
        getWindow().setStatusBarColor(Color.parseColor("#1A1E29"));
        getWindow().setNavigationBarColor(Color.parseColor("#FFFFFF"));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        String cpf = getIntent().getStringExtra("cpfLogin");

        fragmentMap = new HashMap<>();
        fragmentMap.put(R.id.nav_gen, new GeradorFragment());
        fragmentMap.put(R.id.nav_aju, new AjustesFragment());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, Objects.requireNonNull(fragmentMap.get(R.id.nav_gen))).commit();
        }

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = fragmentMap.get(item.getItemId());

                if (selectedFragment != null) {
                    if (item.getItemId() == R.id.nav_aju) {
                        AjustesFragment ajustesFragment = (AjustesFragment) selectedFragment;
                        Bundle args = new Bundle();
                        args.putString("cpfLogin", cpf);
                        ajustesFragment.setArguments(args);
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, selectedFragment).commit();
                }
                return true;
            }
        });

    }

}