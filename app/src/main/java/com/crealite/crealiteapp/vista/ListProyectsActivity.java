package com.crealite.crealiteapp.vista;



import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;

import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.crealite.crealiteapp.MainActivity;
import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.controlador.CRUD_Proyecto;
import com.crealite.crealiteapp.controlador.Constantes;
import com.crealite.crealiteapp.controlador.listMisProyectosAdapter;
import com.crealite.crealiteapp.modelo.Cliente;
import com.crealite.crealiteapp.modelo.Proyecto;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class ListProyectsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Cliente cliente;
    private AppCompatButton btnCrearProyecto;
    private CRUD_Proyecto crudProyecto;
    private ListView listaProyectos;
    private RadioGroup radio;
    private ListAdapter listAdapter;
    private ArrayList<Proyecto> proyectosFiltadros;
    private SearchView searchView;

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
        btnCrearProyecto();
        crudProyecto.obtenerTodosProyectos(new CRUD_Proyecto.ResponseCallback() {
            @Override
            public void onComplete(boolean success, List<Proyecto> proyectos) {
                proyectosFiltadros =crudProyecto.searchByCliente(cliente);
                iniciarListView(proyectosFiltadros);
                configurarBuscador(proyectosFiltadros);
                radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        View radioButton = radio.findViewById(checkedId);
                        int index = radio.indexOfChild(radioButton);


                        // Add logic here

                        switch (index) {
                            case 0: // first button
                                proyectosFiltadros = crudProyecto.searchByCliente(cliente);
                                iniciarListView(proyectosFiltadros);
                                configurarBuscador(proyectosFiltadros);
                                break;
                            case 1: // secondbutton
                                proyectosFiltadros = crudProyecto.obtenerProyectosPagadosCliente(cliente);
                                iniciarListView(proyectosFiltadros);
                                configurarBuscador(proyectosFiltadros);
                                break;
                            case 2: // secondbutton
                                proyectosFiltadros = crudProyecto.obtenerProyectosFinalizadosCliente(cliente);
                                iniciarListView(proyectosFiltadros);
                                configurarBuscador(proyectosFiltadros);
                                break;
                        }
                    }
                });

            }
        });





    }

    private void iniciarListView(ArrayList<Proyecto> proyectos) {


        listAdapter = new listMisProyectosAdapter(this,proyectos);
        listaProyectos.setAdapter(listAdapter);
        listaProyectos.setClickable(false);

    }

    private void btnCrearProyecto() {
        btnCrearProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarCrearProyectoActivity();
            }
        });

    }

    private void initComponents() {
        drawerLayout = findViewById(R.id.homepage);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        btnCrearProyecto = findViewById(R.id.btnRealizarProyecto2);
        crudProyecto = new CRUD_Proyecto();
        listaProyectos = findViewById(R.id.lvMisProyectos);
        radio = findViewById(R.id.rbGroupProyects);
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
            Intent intent = new Intent(ListProyectsActivity.this,HomePageActivity.class);
            intent.putExtra(Constantes.EXTRA_CLIENTE,cliente);
            startActivity(intent);
            return true;
        }else if (id == R.id.nav_nuevo_proyecto){
            iniciarCrearProyectoActivity();
            return true;
        }else if (id == R.id.nav_mis_pedidos){
            Intent intent = new Intent(ListProyectsActivity.this,ListProyectsActivity.class);
            intent.putExtra(Constantes.EXTRA_CLIENTE,cliente);
            startActivity(intent);
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
        Intent intent = new Intent(ListProyectsActivity.this, NewProyectActivity.class);
        intent.putExtra(Constantes.EXTRA_CLIENTE,cliente);
        startActivity(intent);
    }

    private void iniciarLoginActivity() {
        Intent intent = new Intent(ListProyectsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.Search);

        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Busca tu proyecto...");

       return true;
    }


    public void configurarBuscador(ArrayList<Proyecto> proyectos){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Proyecto> proyectosBuscados = new ArrayList<>();
                for (Proyecto p: proyectos){
                    if (p.getNombre().toLowerCase().contains(newText)){
                        proyectosBuscados.add(p);
                    }
                }

                iniciarListView(proyectosBuscados);
                return true;
            }
        });
    }
}
