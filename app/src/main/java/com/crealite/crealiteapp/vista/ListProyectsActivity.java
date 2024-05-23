package com.crealite.crealiteapp.vista;

import static java.lang.Integer.parseInt;
import static java.time.LocalDate.of;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.controlador.Constantes;
import com.crealite.crealiteapp.modelo.Cliente;
import com.google.android.material.navigation.NavigationView;

public class ListProyectsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Cliente cliente;
    private AppCompatButton btnCrearProyecto;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.list_proyects_activivty);
        initComponents();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cliente = (Cliente) extras.get(Constantes.EXTRA_CLIENTE);
        }
        asignarMenu();

    }

    private void btnCrearProyecto() {
        Intent intent = new Intent(ListProyectsActivity.this, NewProyectActivity.class);
        intent.putExtra(Constantes.EXTRA_CLIENTE,cliente);
        startActivity(intent);
    }

    private void initComponents() {
        drawerLayout = findViewById(R.id.homepage);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        btnCrearProyecto = findViewById(R.id.btnRealizarProyecto2);
    }

    private void  asignarMenu() {
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawe_open,R.string.navigation_drawe_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
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
            Intent intent = new Intent(ListProyectsActivity.this,RegistroActivity.class);
            intent.putExtra(Constantes.EXTRA_CLIENTE,cliente);
            startActivity(intent);
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

    private void iniciarCrearProyectoActivity() {
    }

    private void iniciarLoginActivity() {

    }
}
