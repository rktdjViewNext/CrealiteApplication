package com.crealite.crealiteapp.vista;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import androidx.drawerlayout.widget.DrawerLayout;


import com.crealite.crealiteapp.MainActivity;
import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.controlador.listUltimosProyectos;
import com.crealite.crealiteapp.modelo.Servicio;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.util.ArrayList;


public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ImageView fotoPerfil;
    FloatingActionButton btnFotoPerfil;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ListView lvUltimosProyecotos;

    AppCompatButton btnCrearProyecto1, btnCrearProyecto2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_page_activity);

        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.orange)));
        initComponents();
        asignarMenu();
        SeleccionarFotoDePerfil();
        cargarUltimosProyectos();
        btnsCrearProyecto();
    }

    private void btnsCrearProyecto() {

        btnCrearProyecto1.setOnClickListener(iniciarCrearProyectoListener());
        btnCrearProyecto2.setOnClickListener(iniciarCrearProyectoListener());

    }

    private View.OnClickListener iniciarCrearProyectoListener(){
        return v -> iniciarCrearProyectoActivity();
    }

    private void iniciarCrearProyectoActivity() {
        Intent intent = new Intent(HomePageActivity.this, NewProyectActivity.class);
        startActivity(intent);
    }

    private void iniciarLoginActivity(){
        Intent intent = new Intent(HomePageActivity.this, MainActivity.class);
        startActivity(intent);
    }

    // ----------------------- MÉTODOS ------------------------------------------------


    //Iniciar Componentes de la pantalla necesarios para su funcionamiento.
    private void initComponents() {
        fotoPerfil = findViewById(R.id.imagenDePerfil);
        btnFotoPerfil = findViewById(R.id.btnCambiarFotoPerfil);
        drawerLayout = findViewById(R.id.homepage);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        lvUltimosProyecotos = findViewById(R.id.lvUltimosProyectos);
        btnCrearProyecto1 = findViewById(R.id.btnRealizarProyecto);
        btnCrearProyecto2 = findViewById(R.id.btnRealizarProyecto2);


    }

    private void asignarMenu() {
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawe_open,R.string.navigation_drawe_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void SeleccionarFotoDePerfil() {
        btnFotoPerfil.setOnClickListener(v -> ImagePicker.with(HomePageActivity.this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start());
    }


    private void cargarUltimosProyectos() {

        ArrayList<Servicio> servicios = new ArrayList<>();
        Servicio s;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            s = new Servicio("Servicio Fotografia", LocalDate.of(2024,2,11));
            servicios.add(s);
            s = new Servicio("Servicio Filmmaking", LocalDate.of(2024,1,19));
            servicios.add(s);
            s = new Servicio("Servicio Diseño", LocalDate.of(2024,10,14));
            servicios.add(s);
            s = new Servicio("Servicio Filmmaking", LocalDate.of(2024,12,4));
            servicios.add(s);
            s = new Servicio("Servicio Fotografia", LocalDate.of(2024,3,24));
            servicios.add(s);
            s = new Servicio("Servicio Diseño", LocalDate.of(2024,4,26));
            servicios.add(s);
            s = new Servicio("Servicio Filmmaking", LocalDate.of(2024,12,12));
            servicios.add(s);
        }


        listUltimosProyectos listAdapter = new listUltimosProyectos(this, servicios);
        lvUltimosProyecotos.setAdapter(listAdapter);
        lvUltimosProyecotos.setClickable(true);
    }



    //-------------------------------------------- Overrides ----------------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            assert data != null;
            Uri uri = data.getData();
            if(uri!= null){
                fotoPerfil.setImageURI(uri);
            }
        }catch (Exception ignored){

        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_home){
            return true;
        }else if (id == R.id.nav_nuevo_proyecto){
            iniciarCrearProyectoActivity();
            return true;
        }else if (id == R.id.nav_mis_pedidos){
            Toast.makeText(this, "En proceso mis pedidos", Toast.LENGTH_SHORT).show();
            return true;
        }else if (id == R.id.nav_mi_perfil){
            Toast.makeText(this, "En proceso mis perfil", Toast.LENGTH_SHORT).show();
            return true;
        }else if (id == R.id.nav_logout){
            iniciarLoginActivity();
            return true;
        }else if (id == R.id.nav_telefono){
            Toast.makeText(this, "En proceso telefono", Toast.LENGTH_SHORT).show();
            return true;
        }else if (id == R.id.nav_instagram){
            Toast.makeText(this, "En proceso instagram", Toast.LENGTH_SHORT).show();
            return true;
        }else if (id == R.id.nav_correo){
            Toast.makeText(this, "En proceso correo", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
