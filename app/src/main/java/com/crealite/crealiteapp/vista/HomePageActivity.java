package com.crealite.crealiteapp.vista;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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
import com.crealite.crealiteapp.controlador.CRUD_Clientes;
import com.crealite.crealiteapp.controlador.CRUD_Proyecto;
import com.crealite.crealiteapp.controlador.Constantes;
import com.crealite.crealiteapp.controlador.listUltimosProyectos;
import com.crealite.crealiteapp.modelo.Cliente;
import com.crealite.crealiteapp.modelo.Proyecto;
import com.crealite.crealiteapp.modelo.Servicio;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.net.MediaType;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView fotoPerfil;
    private TextView nombreUsuario, proyectosEnCurso;
    private FloatingActionButton btnFotoPerfil;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ListView lvUltimosProyecotos;
    private AppCompatButton btnCrearProyecto1, btnCrearProyecto2;
    private Cliente cliente;
    private CRUD_Proyecto crudProyecto;
    private CRUD_Clientes crud_clientes;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_page_activity);

        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.orange)));
        initComponents();

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            cliente = (Cliente) extras.get(Constantes.EXTRA_CLIENTE);
            if (cliente != null){
                nombreUsuario.setText(cliente.getNombre() +  " " + cliente.getApellidos());
            }

        }


        contarProyectosEnCurso();
        cargarFotoPerfil();
        asignarMenu();
        SeleccionarFotoDePerfil();
        cargarUltimosProyectos();
        btnsCrearProyecto();

    }

    private void contarProyectosEnCurso() {

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
        intent.putExtra(Constantes.EXTRA_CLIENTE,cliente);
        startActivity(intent);
    }

    private void iniciarLoginActivity(){
        Intent intent = new Intent(HomePageActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
        nombreUsuario = findViewById(R.id.txtNombreUsuario);
        proyectosEnCurso = findViewById(R.id.txtNumProyectosEncurso);
        crudProyecto = new CRUD_Proyecto();
        crud_clientes = new CRUD_Clientes();
        context = this;

    }

    private void  asignarMenu() {
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawe_open,R.string.navigation_drawe_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void SeleccionarFotoDePerfil() {
        btnFotoPerfil.setOnClickListener(v -> ImagePicker.with(HomePageActivity.this)
                .cropSquare()	    			//Crop image(Optional), Check Customization for more option
                .start());
    }


    private void cargarUltimosProyectos() {

        crudProyecto.obtenerTodosProyectos(new CRUD_Proyecto.ResponseCallback() {
            @Override
            public void onComplete(boolean success, List<Proyecto> proyectos) {
                ArrayList<Proyecto> ultimosProyectos = crudProyecto.ultimosCinco(cliente);
                //CARGAR SERVICIOS
                listUltimosProyectos listAdapter = new listUltimosProyectos(context, ultimosProyectos);
                lvUltimosProyecotos.setAdapter(listAdapter);
                lvUltimosProyecotos.setClickable(true);
            }
        });


    }



    //-------------------------------------------- Overrides ----------------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri pickedImage = data.getData();
            if (pickedImage != null) {
                Bitmap bitmap = null;
                ContentResolver contentResolver = getContentResolver();
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        // Use ImageDecoder for Android P and above
                        ImageDecoder.Source source = ImageDecoder.createSource(contentResolver, pickedImage);
                        bitmap = ImageDecoder.decodeBitmap(source);
                    } else {
                        // Use MediaStore.Images.Media.getBitmap for below Android P
                        bitmap = MediaStore.Images.Media.getBitmap(contentResolver, pickedImage);
                        System.out.println(bitmap);
                    }

                    // Set the bitmap to the ImageView
                    fotoPerfil.setImageBitmap(bitmap);

                    // Do something with the bitmap
                    crud_clientes.updateFotoPerfil(cliente.getId(), bitmap, new CRUD_Clientes.ResponseCallback() {
                        @Override
                        public void onComplete(boolean success, List<Cliente> clientes) {
                            if (success) {
                                Toast.makeText(HomePageActivity.this, "Foto de perfil actualizada", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(HomePageActivity.this, "Error al actualizar la foto de perfil", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error al seleccionar la imagen", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "No se seleccionó ninguna imagen", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No se seleccionó ninguna imagen", Toast.LENGTH_SHORT).show();
        }
    }





        /*}catch (Exception ignored){

        }*/


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_home){
            return true;
        }else if (id == R.id.nav_nuevo_proyecto){
            iniciarCrearProyectoActivity();
            return true;
        }else if (id == R.id.nav_mis_pedidos){
            Intent intent = new Intent(HomePageActivity.this,ListProyectsActivity.class);
            intent.putExtra(Constantes.EXTRA_CLIENTE,cliente);
            startActivity(intent);
            return true;
        }else if (id == R.id.nav_mi_perfil){
            Intent intent = new Intent(HomePageActivity.this,RegistroActivity.class);
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

    private void cargarFotoPerfil() {
        // Asumiendo que tienes un método en tu controlador para obtener la foto de perfil
        new Thread(() -> {

            Bitmap foto = BitmapFactory.decodeByteArray(cliente.getFoto(), 0, cliente.getFoto().length);
            System.out.println(foto);
            runOnUiThread(() -> {
                if (foto != null) {
                    fotoPerfil.setImageBitmap(foto);
                }
            });
        }).start();
    }

}
