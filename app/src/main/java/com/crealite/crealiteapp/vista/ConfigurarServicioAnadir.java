package com.crealite.crealiteapp.vista;
import static java.time.LocalDate.of;

import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.crealite.crealiteapp.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class ConfigurarServicioAnadir extends AppCompatActivity {

    ImageButton btnFechaRelizar, btnFechaEntrega;
    TextView txtFechaRealizar, txtFechaEntrega;
    Spinner spinnerHora, spinnerMinutos, spinnerProvincia, spinnerHorasContratar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.configurar_servicio_anadir);

        initComponents();
        configurarBtnDatePicker(btnFechaRelizar,txtFechaRealizar);
        configurarHoraARelizar();
        configurarBtnDatePicker(btnFechaEntrega,txtFechaEntrega);
        configurarProvincias();


    }

    private void configurarProvincias() {
        String [] provincias = {"Alava","Albacete","Alicante","Almería","Asturias","Avila","Badajoz","Barcelona","Burgos","Cáceres",
                "Cádiz","Cantabria","Castellón","Ciudad Real","Córdoba","La Coruña","Cuenca","Gerona","Granada","Guadalajara",
                "Guipúzcoa","Huelva","Huesca","Islas Baleares","Jaén","León","Lérida","Lugo","Madrid","Málaga","Murcia","Navarra",
                "Orense","Palencia","Las Palmas","Pontevedra","La Rioja","Salamanca","Segovia","Sevilla","Soria","Tarragona2",
                "Santa Cruz de Tenerife","Teruel","Toledo","Valencia","Valladolid","Vizcaya","Zamora","Zaragoza"};

        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(provincias));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.style_spinner,arrayList);
        spinnerProvincia.setAdapter(arrayAdapter);
    }


    private void configurarHoraARelizar() {
        String [] horas = new String[24];
        String [] minutos = new String[6];
        String [] horasContratar = new String[8];
        for (int i = 0; i < 24; i++) {
            if (String.valueOf(i).length()==1){
                horas[i] = "0" + i;
            }else {
                horas[i] = String.valueOf(i);
            }
        }
        minutos[0] = "00";

        for (int i = 1; i < 6; i++) {
            minutos[i] = String.valueOf(i*10);
        }

        for (int i = 1; i <= 8; i++) {
            horasContratar[i-1] = String.valueOf(i);
        }

        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(horas));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.style_spinner,arrayList);
        spinnerHora.setAdapter(arrayAdapter);
        arrayList = new ArrayList<>(Arrays.asList(minutos));
        arrayAdapter = new ArrayAdapter<>(this,R.layout.style_spinner,arrayList);
        spinnerMinutos.setAdapter(arrayAdapter);
        arrayList = new ArrayList<>(Arrays.asList(horasContratar));
        arrayAdapter = new ArrayAdapter<>(this,R.layout.style_spinner,arrayList);
        spinnerHorasContratar.setAdapter(arrayAdapter);
    }

    private LocalDate configurarBtnDatePicker(ImageButton btnDataPiker, TextView txtDataPicker) {
        LocalDate localDate = null;

        btnDataPiker.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Fecha")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();

            datePicker.addOnPositiveButtonClickListener(selection -> {
                String date=  new SimpleDateFormat("dd-MM-yyy", Locale.getDefault()).format(new Date(selection));
                String[] separarFecha = date.split("-");
                LocalDate date2 = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    date2 = of(Integer.parseInt(separarFecha[2]),Integer.parseInt(separarFecha[1]),Integer.parseInt(separarFecha[0]));
                }
                assert date2 != null;
                txtDataPicker.setText(date2.toString());

            });
            datePicker.show(getSupportFragmentManager(),"tag");
        });

        return formarFecha(txtDataPicker);

    }

    private LocalDate formarFecha(TextView txtDataPicker) {
        String[] separarFecha = txtDataPicker.getText().toString().split("-");
        LocalDate date = null;
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                date = of(Integer.parseInt(separarFecha[2]),Integer.parseInt(separarFecha[1]),Integer.parseInt(separarFecha[0]));
            }
        }catch (Exception e){

        }


        return date;
    }


    private void initComponents() {
        btnFechaRelizar = findViewById(R.id.btnFechaRealizar);
        txtFechaRealizar = findViewById(R.id.txtFechaRealizar);
        btnFechaEntrega = findViewById(R.id.btnFechaEntrega);
        txtFechaEntrega = findViewById(R.id.txtFechaEntrega);
        spinnerHora = findViewById(R.id.spinnerHora);
        spinnerMinutos = findViewById(R.id.spinnerMinutos);
        spinnerProvincia = findViewById(R.id.spinnerProvincia);
        spinnerHorasContratar = findViewById(R.id.spinnerHorasContratar);

        //INCIAR FECHAS AL DIA DE ACTUAL:
        LocalDate fechaActual = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            fechaActual = LocalDate.now();
        }
        txtFechaEntrega.setText(fechaActual.toString());
        txtFechaRealizar.setText(fechaActual.toString());

    }

}
