package com.crealite.crealiteapp.vista;

import static java.time.LocalDate.of;

import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.crealite.crealiteapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.registro_activity);

        MaterialButton btnFechaNac = findViewById(R.id.btnFechaNac);


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
                              String date=  new SimpleDateFormat("dd-MM-yyy", Locale.getDefault()).format(new Date(selection));
                              String[] separarFecha = date.split("-");
                        LocalDate date2 = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            date2 = of(Integer.parseInt(separarFecha[2]),Integer.parseInt(separarFecha[1]),Integer.parseInt(separarFecha[0]));
                        }
                        btnFechaNac.setText(date2.toString());
                    }
                });
                datePicker.show(getSupportFragmentManager(),"tag");
            }
        });
    }
}
