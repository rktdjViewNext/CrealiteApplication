package com.crealite.crealiteapp.vista;

import static java.time.LocalDate.of;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.controlador.Constantes;
import com.crealite.crealiteapp.modelo.Diseno;
import com.crealite.crealiteapp.modelo.Proyecto;
import com.crealite.crealiteapp.modelo.Servicio;
import com.crealite.crealiteapp.modelo.Video;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class ConfigurarServicioAnadirDiseno extends AppCompatActivity {

    private ImageButton btnFechaRelizar, btnBack;
    private Button btnAnadirServicio;
    private TextView txtFechaRealizar;
    private TextInputEditText etDescripcion;
    private EditText etAlto, etAncho;
    private LocalDate fechaArealizar;
    private Diseno diseno;
    private Proyecto nuevoProyecto;
    private CheckBox cbAnimado;
    private ArrayList<Servicio> servicios;
    private String descripcion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.configurar_servicio_anadir_diseno);
        initComponents();


        Bundle extras = getIntent().getExtras();
        diseno = (Diseno) extras.get(Constantes.EXTRA_SERVICIO);


        if (diseno.getProyecto()!= null){
            rellenarCampos();
            desabilitarCampos();
            if (diseno.getProyecto().isPagado()){
                desabilitarCampos();
            }else {

            }
        }
        nuevoProyecto = (Proyecto) extras.get(Constantes.EXTRA_PRYECTO);
        servicios = (ArrayList<Servicio>) extras.getSerializable(Constantes.EXTRA_LISTA_SERVICIO);
        configurarBtnDatePicker(btnFechaRelizar,txtFechaRealizar,"REALIZAR");

       /* btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (diseno != null){

                }else{

                }
            }
        });*/

        //PULSAR AÑADIR SERVICIO
        btnAnadirServicio.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (fechaArealizar.isBefore(LocalDate.now())){
                    Toast.makeText(ConfigurarServicioAnadirDiseno.this, "Selecionne una feca Correcta", Toast.LENGTH_SHORT).show();
                }else if (etDescripcion.getText().toString().isEmpty()){
                    Toast.makeText(ConfigurarServicioAnadirDiseno.this, "INTRODUCE UNA DESCRIPCION", Toast.LENGTH_SHORT).show();
                } else{
                    try {
                        Integer.parseInt(etAncho.getText().toString());
                        Integer.parseInt(etAlto.getText().toString());
                        diseno.setDimensiones(etAncho.getText().toString() + "x" + etAlto.getText().toString());
                        descripcion = etDescripcion.getText().toString();
                        diseno.setFechaRealizar(fechaArealizar);
                        diseno.setAnimado(cbAnimado.isSelected());
                        diseno.setDuracion(2);
                        diseno.setDescripcion(descripcion);
                        diseno.setLocalidad("SIN LOCALIDAD");
                        diseno.setProvincia("SIN PROVINCIA");
                        diseno.setProyecto(nuevoProyecto);
                        servicios.add(diseno);
                        Intent intent = new Intent(ConfigurarServicioAnadirDiseno.this,NewProyectActivity.class);
                        intent.putExtra(Constantes.EXTRA_PRYECTO,nuevoProyecto);
                        intent.putExtra(Constantes.EXTRA_LISTA_SERVICIO,servicios);
                        startActivity(intent);
                    }catch (NumberFormatException e){
                        Toast.makeText(ConfigurarServicioAnadirDiseno.this, "INTRODUCE NUMEROS EN LAS DIMENSIONES", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    private void rellenarCampos() {
        txtFechaRealizar.setText(diseno.getFechaRealizar().toString());
        etDescripcion.setText(diseno.getDescripcion());
        cbAnimado.setSelected(diseno.isAnimado());
        String[] dimensiones = diseno.getDimensiones().split("x");
        etAncho.setText(dimensiones[0]);
        etAlto.setText(dimensiones[1]);
    }

    private void desabilitarCampos() {
        btnFechaRelizar.setEnabled(false);

        etDescripcion.setEnabled(false);

        btnAnadirServicio.setEnabled(false);
        btnAnadirServicio.setActivated(false);
        btnAnadirServicio.setVisibility(View.INVISIBLE);
       etAlto.setEnabled(false);
       etAncho.setEnabled(false);
    }


    private void configurarBtnDatePicker(ImageButton btnDataPiker, TextView txtDataPicker, String seleccion) {

        btnDataPiker.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Fecha")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();

            datePicker.addOnPositiveButtonClickListener(selection -> {
                String date=  new SimpleDateFormat("dd-MM-yyy", Locale.getDefault()).format(new Date(selection));
                String[] separarFecha = date.split("-");
                LocalDate date2 = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    date2 = of(Integer.parseInt(separarFecha[2]),Integer.parseInt(separarFecha[1]),Integer.parseInt(separarFecha[0]));
                }
                assert date2 != null;
                txtDataPicker.setText(date2.toString());
                if(seleccion.equals("REALIZAR")) {
                    fechaArealizar = formarFecha(txtDataPicker);

                }

                if (fechaArealizar != null){
                    System.out.println(fechaArealizar);
                }else {
                    System.out.println("ES NULO");
                }


            });
            datePicker.show(getSupportFragmentManager(),"tag");


        });



    }

    private LocalDate formarFecha(TextView txtDataPicker) {
        String[] separarFecha = txtDataPicker.getText().toString().split("-");

        LocalDate date = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                date = of(Integer.parseInt(separarFecha[0]),Integer.parseInt(separarFecha[1]),Integer.parseInt(separarFecha[2]));
                if(date != null){
                    System.out.println(date);
                }
            }
        }catch (Exception e){
            System.out.println("NO SE PUEDE CONVERTIR LA FECHA");
        }
        if (date != null){
            System.out.println(date);
        }

        return date;
    }


    private void initComponents() {
        btnFechaRelizar = findViewById(R.id.btnFechaRealizar);
        btnBack = findViewById(R.id.btnBack);
        txtFechaRealizar = findViewById(R.id.txtFechaRealizar);
        btnAnadirServicio = findViewById(R.id.btnAnadirServicio);
        etDescripcion = findViewById(R.id.editTextDescripcion);
        cbAnimado = findViewById(R.id.cbAnimado);
        etAlto = findViewById(R.id.etAlto);
        etAncho = findViewById(R.id.etAncho);


        //INCIAR FECHAS AL DIA DE ACTUAL:

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fechaArealizar = LocalDate.now();
        }

    }

}
