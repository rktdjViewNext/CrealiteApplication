package com.crealite.crealiteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.crealite.crealiteapp.controlador.CRUD_Clientes;
import com.crealite.crealiteapp.controlador.CRUD_Proyecto;
import com.crealite.crealiteapp.controlador.Constantes;
import com.crealite.crealiteapp.modelo.Cliente;
import com.crealite.crealiteapp.vista.HomePageActivity;
import com.crealite.crealiteapp.vista.RegistroActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etUser, etPass;
    private TextView tvRegistrarme;
    private AppCompatButton btnIniciarSesion;
    private String usuario, contrasena;
    private CRUD_Clientes crud_clientes;
    private CRUD_Proyecto crudProyecto;


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

        initComponents();



        //---- PULSAR BOTON DE INICAR SESION
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Recoger credenciales de los EditText.
                usuario  = etUser.getText().toString();
                contrasena = etPass.getText().toString();
                Cliente c = crud_clientes.search(new Cliente(usuario));

                //----- CONTROLAR CREDENCIALES
                if(controlCredenciales()){
                    // Comprobar usuario en la base de datos.
                    if ( c != null){
                        // Comprobar si la contraseña coincide con
                        // la contraseña del usuario.
                        if (c.getContrasena().equals(contrasena)){
                                iniciarSesion(c);
                        }else{
                            Toast.makeText(MainActivity.this,"CONTRASEÑA INCORRECTA",Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(MainActivity.this,"USUARIO INCORRECTO",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        //---- PULSAR BOTON DE REGISTRARME
        tvRegistrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lanzar activity de registro
                Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });
    }

    //-----------------------------------------METODOS----------------------------------
    /**
     * Iniciar el activity del home Page con
     * el usuario que ha iniciado sesion.
     */
    private void iniciarSesion(Cliente c) {
        Intent intent = new Intent(this, HomePageActivity.class);
        intent.putExtra(Constantes.EXTRA_CLIENTE,c);
        startActivity(intent);
    }
    //---------------------------------------------------------------------------
    /**
     * Comprobar que cumple los riquisitos minimos
     * para comprobar las credenciales en la base de datos.
     *
     * @return boolean
     */
    private boolean controlCredenciales() {
        return true;
    }
    //---------------------------------------------------------------------------
    /**
     * Iniciar componentes del activity
     * y los controladores necesarios.
     */
    private void initComponents() {
        etUser = findViewById(R.id.editTextUsuario);
        etPass = findViewById(R.id.editTextContrasena);
        tvRegistrarme = findViewById(R.id.textViewRegistrarme);
        btnIniciarSesion = findViewById(R.id.btnLogin);
        crud_clientes = new CRUD_Clientes();
        crudProyecto = new CRUD_Proyecto();
    }
}