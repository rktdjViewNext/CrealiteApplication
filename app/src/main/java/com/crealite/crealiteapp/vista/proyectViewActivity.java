package com.crealite.crealiteapp.vista;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.vista.Fragmentos.Fragmento1;
import com.crealite.crealiteapp.vista.Fragmentos.Fragmento2;
import com.crealite.crealiteapp.vista.Fragmentos.Fragmento3;
import com.crealite.crealiteapp.vista.Fragmentos.VPAdapter;
import com.google.android.material.tabs.TabLayout;

public class proyectViewActivity extends AppCompatActivity {

    private TabLayout tableLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.proyect_view_activitys);


        tableLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPage);
        tableLayout.setupWithViewPager(viewPager);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFrament( new Fragmento1(),"Servicios");
        vpAdapter.addFrament( new Fragmento2(), "Proceso");
        vpAdapter.addFrament( new Fragmento3(),"Presupuesto");

        viewPager.setAdapter(vpAdapter);

    }
}
