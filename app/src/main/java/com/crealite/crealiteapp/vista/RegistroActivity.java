package com.crealite.crealiteapp.vista;

import static java.lang.Integer.parseInt;
import static java.time.LocalDate.of;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.crealite.crealiteapp.MainActivity;
import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.controlador.CRUD_Clientes;
import com.crealite.crealiteapp.controlador.Constantes;
import com.crealite.crealiteapp.modelo.Cliente;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RegistroActivity extends AppCompatActivity {

    private TextInputEditText usuario, contrasena, nombre, apellidos, telefono, email, oficio, ciudad;
    private MaterialButton btnFechaNac;
    private AppCompatButton btnRegistrarse;
    private CRUD_Clientes crud_clientes;
    private LocalDate fechaNac;
    private Context context = this;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.registro_activity);
        initComponents();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cliente = (Cliente) extras.get(Constantes.EXTRA_CLIENTE);
            rellenarCampos();
            String[] fechasplit = btnFechaNac.getText().toString().split("-");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                fechaNac = of(parseInt(fechasplit[0]), parseInt(fechasplit[1]), parseInt(fechasplit[2]));
            }
        }
        registrarseListener();


        //SELECCIONAR FECHA DE NACIMIENTO.

        btnFechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Fecha Nacimiento")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();

                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        String date = new SimpleDateFormat("dd-MM-yyy", Locale.getDefault()).format(new Date(selection));
                        String[] separarFecha = date.split("-");
                        LocalDate date2 = null;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            date2 = of(parseInt(separarFecha[2]), parseInt(separarFecha[1]), parseInt(separarFecha[0]));
                        }
                        btnFechaNac.setText(date2.toString());
                    }
                });
                datePicker.show(getSupportFragmentManager(), "tag");
            }
        });

        usuario.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                System.out.println(crud_clientes.search(new Cliente(usuario.getText().toString())));

                if (!hasFocus) {
                    if (usuario.getText().toString().isEmpty()) {
                        usuario.setError("INTRODUCE UN USUARIO");
                        usuario.invalidate();
                    } else if (crud_clientes.search(new Cliente(usuario.getText().toString())) != null) {
                        Toast.makeText(RegistroActivity.this, crud_clientes.search(new Cliente(usuario.getText().toString())) + "", Toast.LENGTH_SHORT).show();
                        usuario.setError("EL USUARIO YA EXISTE");
                        usuario.invalidate();
                    }
                }
            }
        });
    }

    private void rellenarCampos() {
        usuario.setText(cliente.getUsuario());
        contrasena.setText(cliente.getContrasena());
        nombre.setText(cliente.getNombre());
        apellidos.setText(cliente.getApellidos());
        telefono.setText(cliente.getTelefono());
        email.setText(cliente.getCorreo());
        oficio.setText(cliente.getProfesion());
        ciudad.setText(cliente.getCiudad());
        btnFechaNac.setText(cliente.getFechaNacimiento().toString());
        btnRegistrarse.setText("MODIFICAR DATOS");


    }

    private void registrarseListener() {

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Cliente c = new Cliente(usuario.getText().toString(), contrasena.getText().toString(), nombre.getText().toString(), apellidos.getText().toString(), telefono.getText().toString(), email.getText().toString(), fechaNac, false, oficio.getText().toString(), ciudad.getText().toString());
                if (btnRegistrarse.getText().toString().equalsIgnoreCase("REGISTRARSE")) {
                    if (controlDeErrores()) {
                        c.setFechaNacimiento(fechaNac);
                        crud_clientes.add(c, new CRUD_Clientes.ResponseCallback() {
                            @Override
                            public void onComplete(boolean success, List<Cliente> clientes) {
                                if (success) {
                                    Toast.makeText(context, "REGISTRADO CORRECTAMENTE", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(context, MainActivity.class);
                                    startActivity(intent);
                                }

                            }
                        });
                    }
                } else {
                    if (usuario.getText().toString().equals(cliente.getUsuario()) && contrasena.getText().toString().equals(cliente.getContrasena()) &&
                            nombre.getText().toString().equals(cliente.getNombre()) && apellidos.getText().toString().equals(cliente.getApellidos()) &&
                            telefono.getText().toString().equals(cliente.getTelefono()) && email.getText().toString().equals(cliente.getCorreo()) &&
                            oficio.getText().toString().equals(cliente.getProfesion()) && ciudad.getText().toString().equals(cliente.getCiudad()) &&
                            btnFechaNac.getText().toString().equals(cliente.getFechaNacimiento().toString())) {
                        Toast.makeText(context, "No has modificado ninguna campo", Toast.LENGTH_SHORT).show();
                    } else {

                        if (controlDeErrores()) {
                            c.setFechaNacimiento(fechaNac);
                            c.setId(cliente.getId());
                            crud_clientes.update(c, new CRUD_Clientes.ResponseCallback() {
                                @Override
                                public void onComplete(boolean success, List<Cliente> clientes) {
                                    if (success) {
                                        Toast.makeText(context, "DATOS MODIFICADOS CORRECTAMENTE", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(RegistroActivity.this,HomePageActivity.class);
                                        intent.putExtra(Constantes.EXTRA_CLIENTE,cliente);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(context, "NO SE HA PODIDO MODIFICAR", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }

            }
        });
    }

    private void initComponents() {
        btnFechaNac = findViewById(R.id.btnFechaNac);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        usuario = findViewById(R.id.editUsuario);
        contrasena = findViewById(R.id.editTextPassword);
        nombre = findViewById(R.id.editTextNombre);
        apellidos = findViewById(R.id.editTextApellido);
        telefono = findViewById(R.id.editTextTelefono);
        email = findViewById(R.id.editTextEmail);
        oficio = findViewById(R.id.editTextOficio);
        ciudad = findViewById(R.id.editTextCiudad);
        crud_clientes = new CRUD_Clientes();

    }

    private boolean controlDeErrores() {

        String[] fechasSeparada = btnFechaNac.getText().toString().split("-");
        fechaNac = null;


        if (crud_clientes.search(new Cliente(usuario.getText().toString())) != null && btnRegistrarse.getText().toString().equalsIgnoreCase("REGISTRARSE")) {
            Toast.makeText(this, "EL USUARIO YA EXISTE", Toast.LENGTH_SHORT).show();
            return false;
        } else if (usuario.getText().toString().isEmpty()) {
            Toast.makeText(this, "INSERTE UN USUARIO", Toast.LENGTH_SHORT).show();
        } else if (contrasena.getText().toString().isEmpty()) {
            Toast.makeText(this, "INSERTE UN CONTRASEÑA", Toast.LENGTH_SHORT).show();
        } else if (nombre.getText().toString().isEmpty()) {
            Toast.makeText(this, "INSERTE UN NOMBRE", Toast.LENGTH_SHORT).show();
        } else if (apellidos.getText().toString().isEmpty()) {
            Toast.makeText(this, "INSERTE LOS APELLIDOS", Toast.LENGTH_SHORT).show();
        } else if (telefono.getText().toString().isEmpty()) {
            Toast.makeText(this, "INSERTE UN TELEFONO", Toast.LENGTH_SHORT).show();
        } else if (email.getText().toString().isEmpty()) {
            Toast.makeText(this, "INSERTE UN MAIL", Toast.LENGTH_SHORT).show();
        }
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                fechaNac = LocalDate.of(parseInt(fechasSeparada[0]), parseInt(fechasSeparada[1]), parseInt(fechasSeparada[2]));
            }
        } catch (Exception e) {
            Toast.makeText(this, "SELECCIONA UNA FECHA", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
