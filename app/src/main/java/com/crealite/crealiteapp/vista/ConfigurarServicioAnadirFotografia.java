package com.crealite.crealiteapp.vista;

import static java.time.LocalDate.of;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.controlador.Constantes;
import com.crealite.crealiteapp.modelo.Fotografia;
import com.crealite.crealiteapp.modelo.Proyecto;
import com.crealite.crealiteapp.modelo.Servicio;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class ConfigurarServicioAnadirFotografia extends AppCompatActivity {

    private ImageButton btnFechaRelizar, btnBack;
    private Button btnAnadirServicio;
    private TextView txtFechaRealizar;
    private TextInputEditText etLocalidad, etDescripcion;
    private Spinner spinnerHora, spinnerMinutos, spinnerProvincia, spinnerHorasContratar, spinnerCantidaFotos;
    private LocalDate fechaArealizar;
    private LocalTime hora;
    private String provincia,localidad,descripcion;
    private int horasAContratar, cantidadFotos;
    private Fotografia foto;
    private Proyecto nuevoProyecto;
    private ArrayList<Servicio> servicios;
    private ArrayAdapter<String> arrayAdapterProvincia;
    private String [] provincias;
    private ArrayList<String> arrayListHorasAcontratar;
    private ArrayList<String> arrayListDuracionVideo;
    private String [] cantidadFotosArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.configurar_servicio_anadir_fotografia);
        initComponents();
        configurarProvincias();
        configurarSpinners();

        Bundle extras = getIntent().getExtras();
        foto= (Fotografia) extras.get(Constantes.EXTRA_SERVICIO);
        System.out.println("ESTA ES LA DURACION QUE LE LLEGA: " + foto.getDuracion());

        if (foto.getProyecto()!= null){
            rellenarCampos();
            desabilitarCampos();
            if (foto.getProyecto().isPagado()){
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
                if (video != null){
                    onBackPressed();
                }else{
                    onBackPressed();
                }
            }
        });*/

        //PULSAR AÑADIR SERVICIO
        btnAnadirServicio.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (fechaArealizar.isBefore(LocalDate.now())){
                    Toast.makeText(ConfigurarServicioAnadirFotografia.this, "Selecionne una feca Correcta", Toast.LENGTH_SHORT).show();
                }else if (etLocalidad.getText().toString().isEmpty()){
                    Toast.makeText(ConfigurarServicioAnadirFotografia.this, "Introduce una localidad", Toast.LENGTH_SHORT).show();
                }else if (etDescripcion.getText().toString().isEmpty()){
                    Toast.makeText(ConfigurarServicioAnadirFotografia.this, "INTRODUCE UNA DESCRIPCION", Toast.LENGTH_SHORT).show();
                } else{
                    hora = LocalTime.of(Integer.parseInt(spinnerHora.getSelectedItem().toString()),Integer.parseInt(spinnerMinutos.getSelectedItem().toString()));
                    provincia = (String) spinnerProvincia.getSelectedItem();
                    localidad = etLocalidad.getText().toString();
                    horasAContratar = Integer.parseInt(spinnerHorasContratar.getSelectedItem().toString());
                    descripcion = etDescripcion.getText().toString();
                    cantidadFotos = Integer.parseInt(spinnerCantidaFotos.getSelectedItem().toString());
                    foto.setFechaRealizar(fechaArealizar);
                    foto.setDuracion(horasAContratar);
                    foto.setDescripcion(descripcion);
                    foto.setLocalidad(localidad);
                    foto.setProvincia(provincia);
                    foto.setProyecto(nuevoProyecto);
                    foto.setCantidadFotos(cantidadFotos);
                    servicios.add(foto);
                    Intent intent = new Intent(ConfigurarServicioAnadirFotografia.this,NewProyectActivity.class);
                    intent.putExtra(Constantes.EXTRA_PRYECTO,nuevoProyecto);
                    intent.putExtra(Constantes.EXTRA_LISTA_SERVICIO,servicios);
                    startActivity(intent);
                }
            }
        });

    }

    private void rellenarCampos() {
        txtFechaRealizar.setText(foto.getFechaRealizar().toString());
        etLocalidad.setText(foto.getLocalidad());
        etDescripcion.setText(foto.getDescripcion());

        for (int i = 0; i < provincias.length; i++) {

            if (foto.getProvincia().equalsIgnoreCase(provincias[i])){
                spinnerProvincia.setSelection(i);
                break;
            }
        }

        for (int i = 0; i < arrayListHorasAcontratar.size(); i++) {
            if (String.valueOf(foto.getDuracion()).equalsIgnoreCase(arrayListHorasAcontratar.get(i))){
                spinnerHorasContratar.setSelection(i);
                break;
            }
        }

        for (int i = 0; i < cantidadFotosArray.length; i++) {
            if (String.valueOf(foto.getCantidadFotos()).equalsIgnoreCase(cantidadFotosArray[i])){
               spinnerCantidaFotos.setSelection(i);
                break;
            }
        }


    }

    private void desabilitarCampos() {
        btnFechaRelizar.setEnabled(false);
        spinnerHorasContratar.setEnabled(false);
        spinnerHora.setEnabled(false);
        spinnerMinutos.setEnabled(false);
        etDescripcion.setEnabled(false);
        btnAnadirServicio.setEnabled(false);
        btnAnadirServicio.setActivated(false);
        btnAnadirServicio.setVisibility(View.INVISIBLE);
        spinnerProvincia.setEnabled(false);
        spinnerCantidaFotos.setEnabled(false);
        etLocalidad.setEnabled(false);

    }

    private void configurarProvincias() {
        provincias = new String [] {"Alava","Albacete","Alicante","Almería","Asturias","Avila","Badajoz","Barcelona","Burgos","Cáceres",
                "Cádiz","Cantabria","Castellón","Ciudad Real","Córdoba","La Coruña","Cuenca","Gerona","Granada","Guadalajara",
                "Guipúzcoa","Huelva","Huesca","Islas Baleares","Jaén","León","Lérida","Lugo","Madrid","Málaga","Murcia","Navarra",
                "Orense","Palencia","Las Palmas","Pontevedra","La Rioja","Salamanca","Segovia","Sevilla","Soria","Tarragona2",
                "Santa Cruz de Tenerife","Teruel","Toledo","Valencia","Valladolid","Vizcaya","Zamora","Zaragoza"};

        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(provincias));
        arrayAdapterProvincia = new ArrayAdapter<>(this,R.layout.style_spinner,arrayList);
        spinnerProvincia.setAdapter(arrayAdapterProvincia);

    }


    private void configurarSpinners() {
        //ARRAY NECESARIOS
        String [] horas = new String[24];
        String [] minutos = new String[6];
        String [] horasContratar = new String[8];
        cantidadFotosArray = new String[] {"10","20","30","40","50","60","70","80","90"};

        //RELLNENAR HORAS
        for (int i = 0; i < 24; i++) {
            if (String.valueOf(i).length()==1){
                horas[i] = "0" + i;
            }else {
                horas[i] = String.valueOf(i);
            }
        }

        //RELLENAR MINUTOS
        minutos[0] = "00";
        for (int i = 1; i < 6; i++) {
            minutos[i] = String.valueOf(i*10);
        }

        //RELLENAR HORAS

        for (int i = 1; i <= 8; i++) {
            horasContratar[i-1] = String.valueOf(i);
        }



        ArrayList<String> arrayList= new ArrayList<>(Arrays.asList(horas));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.style_spinner,arrayList);
        spinnerHora.setAdapter(arrayAdapter);

        arrayList = new ArrayList<>(Arrays.asList(minutos));
        arrayAdapter = new ArrayAdapter<>(this,R.layout.style_spinner,arrayList);
        spinnerMinutos.setAdapter(arrayAdapter);

        arrayListHorasAcontratar = new ArrayList<>(Arrays.asList(horasContratar));
        arrayAdapter = new ArrayAdapter<>(this,R.layout.style_spinner,arrayListHorasAcontratar);
        spinnerHorasContratar.setAdapter(arrayAdapter);

        arrayListDuracionVideo = new ArrayList<>(Arrays.asList(cantidadFotosArray));
        arrayAdapter = new ArrayAdapter<>(this,R.layout.style_spinner,arrayListDuracionVideo);
        spinnerCantidaFotos.setAdapter(arrayAdapter);
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
        spinnerHora = findViewById(R.id.spinnerHora);
        spinnerMinutos = findViewById(R.id.spinnerMinutos);
        spinnerProvincia = findViewById(R.id.spinnerProvincia);
        spinnerHorasContratar = findViewById(R.id.spinnerHorasContratar);
        spinnerCantidaFotos = findViewById(R.id.spinnerCantidadFotos);
        etLocalidad = findViewById(R.id.editTextLocalidad);
        etDescripcion = findViewById(R.id.editTextDescripcion);



        //INCIAR FECHAS AL DIA DE ACTUAL:

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fechaArealizar = LocalDate.now();
        }

    }

}
