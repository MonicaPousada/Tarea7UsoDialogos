package com.example.tarea7usodialogos;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class FormularioAnadir extends DialogFragment {
    private Tarea tarea;
    private Spinner spinnerAsignatura;
    private EditText etTitulo;
    private EditText etDescripcion;
    private EditText etFecha;
    private EditText etHora;
    private Button btnCancelar;
    private Button btnGuardar;
    private OnTareaSavedListener listener;

    public interface OnTareaSavedListener {
        void onTareaSaved(Tarea tarea, boolean isEdit);
    }

    public void setOnTareaSavedListener(OnTareaSavedListener listener) {
        this.listener = listener;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View vista = inflater.inflate(R.layout.nueva_tarea, null);

        spinnerAsignatura = vista.findViewById(R.id.spinnerAsignatura);
        etTitulo = vista.findViewById(R.id.etTitulo);
        etDescripcion = vista.findViewById(R.id.etDescripcion);
        etFecha = vista.findViewById(R.id.etFecha);
        etHora = vista.findViewById(R.id.etHora);
        btnCancelar = vista.findViewById(R.id.btnCancelar);
        btnGuardar = vista.findViewById(R.id.btnGuardar);

        if (tarea != null) {
            etTitulo.setText(tarea.getTitulo());
            etDescripcion.setText(tarea.getDescripcion());
            etFecha.setText(tarea.getFecha());
            etHora.setText(tarea.getHora());

        }

        etFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendario = Calendar.getInstance();
                int year = calendario.get(Calendar.YEAR);
                int mes = calendario.get(Calendar.MONTH);
                int dia = calendario.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogoFecha = new DatePickerDialog(view.getContext());
                dialogoFecha.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int mes, int dia) {
                        String fechaSeleccionada = dia + "/" + (mes + 1) + "/" + year;
                        etFecha.setText(fechaSeleccionada);
                    }
                });
                dialogoFecha.show();
            }
        });

        etHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener la hora actual
                Calendar calendario = Calendar.getInstance();
                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                int minuto = calendario.get(Calendar.MINUTE);

                // Crear el TimePickerDialog con la hora actual como predeterminada
                TimePickerDialog dialogoHora = new TimePickerDialog(
                        view.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                // Formatear la hora seleccionada y establecerla en el EditText
                                String horaSeleccionada = String.format("%02d:%02d", selectedHour, selectedMinute);
                                etHora.setText(horaSeleccionada);
                            }
                        },
                        hora, minuto, true // El último parámetro define el formato: true para formato 24 horas
                );

                dialogoHora.show();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cierra el diálogo
                dismiss();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar();
                dismiss();
            }
        });


        builder.setView(vista);
        return builder.create();


    }

    public void guardar() {
        String nombre = etTitulo.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        String fecha = etFecha.getText().toString();
        String hora = etHora.getText().toString();
        String asignatura = spinnerAsignatura.getSelectedItem().toString();

        if (nombre.isEmpty() || descripcion.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
            Toast.makeText(getActivity(), "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (tarea == null) {
            // Si tarea es nula, es una nueva tarea
            tarea = new Tarea(nombre, descripcion, fecha, hora, asignatura, 0);
            if (listener != null) {
                listener.onTareaSaved(tarea, false);
            }
        } else {
            // Si tarea no es nula, es una edición
            tarea.setTitulo(nombre);
            tarea.setDescripcion(descripcion);
            tarea.setFecha(fecha);
            tarea.setHora(hora);
            tarea.setAsignatura(asignatura);
            if (listener != null) {
                listener.onTareaSaved(tarea, true);
            }
        }

        // Limpiar los campos
        etTitulo.setText("");
        etDescripcion.setText("");
        etFecha.setText("");
        etHora.setText("");
        spinnerAsignatura.setSelection(0);
        dismiss();



    }


}
