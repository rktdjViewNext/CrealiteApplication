package com.crealite.crealiteapp.vista;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;


import com.crealite.crealiteapp.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


public class HomePageActivity extends AppCompatActivity {

    ImageView fotoPerfil;
    FloatingActionButton btnFotoPerfil;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_page_activity);

        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.orange)));


        initComponents();
        asignarMenu();
        SeleccionarFotoDePerfil();


    }


    // ----------------------- MÉTODOS ------------------------------------------------


    //Iniciar Componentes de la pantalla necesarios para su funcionamiento.
    private void initComponents() {
        fotoPerfil = findViewById(R.id.imagenDePerfil);
        btnFotoPerfil = findViewById(R.id.btnCambiarFotoPerfil);
        drawerLayout = findViewById(R.id.homepage);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

    }

    private void asignarMenu() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawe_open,R.string.navigation_drawe_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
    }

    private void SeleccionarFotoDePerfil() {
        btnFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(HomePageActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }





    //-------------------------------------------- Overrides ----------------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        if(uri!= null){
            fotoPerfil.setImageURI(uri);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }
}
