package com.example.tarea7usodialogos;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FormularioAnadir.OnTareaSavedListener{

    private RecyclerView rvTareas;
    private ArrayList<Tarea> tareas = new ArrayList<>();
    public Adaptador adaptador;
    private boolean onCreateFinished = false;


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

        adaptador = new Adaptador(tareas, this::showTareaBottomSheet, this::onTareaLongClick);

        rvTareas = findViewById(R.id.recyclerView);
        rvTareas.setLayoutManager(new LinearLayoutManager(this));
        rvTareas.setAdapter(adaptador);

        tareas.add(new Tarea("AD", "Tarea 1", "Descripcion", "12/12/2022", "12:00", 0));

        //adaptador = new Adaptador(tareas);
        adaptador.notifyDataSetChanged();



        onCreateFinished = true;


        FloatingActionButton btn = findViewById(R.id.boton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormularioAnadir formulario = new FormularioAnadir();
                formulario.show(getSupportFragmentManager(), "Formulario");
            }
        });


    }

    @Override
    public void onTareaSaved(Tarea tarea, boolean isEdit) {
        if (isEdit) {
            int position = tareas.indexOf(tarea);
            tareas.set(position, tarea);
            adaptador.notifyItemChanged(position);
            Toast.makeText(this, "Tarea actualizada", Toast.LENGTH_SHORT).show();
        } else {
            tareas.add(tarea);
            adaptador.notifyItemInserted(tareas.size() - 1);
            rvTareas.scrollToPosition(tareas.size() - 1);
            Toast.makeText(this, "Tarea guardada", Toast.LENGTH_SHORT).show();
        }
    }

    // Mostrar el BottomSheet con opciones
    private void showTareaBottomSheet(Tarea tarea, int position) {
        MenuEditar bottomSheetDialog = new MenuEditar();
        bottomSheetDialog.setTarea(tarea);
        bottomSheetDialog.setOnOptionSelectedListener(new MenuEditar.OnBottomSheetOptionSelectedListener() {
            @Override
            public void onEditSelected(Tarea tarea) {
                showNuevaTareaDialog(tarea);
            }

            @Override
            public void onDeleteSelected(Tarea tarea) {
                showDeleteConfirmationDialog(tarea, position);
            }

            @Override
            public void onCompleteSelected(Tarea tarea) {
                tarea.setCompletada(1);
                adaptador.notifyItemChanged(position);
                Toast.makeText(MainActivity.this, "Tarea completada", Toast.LENGTH_SHORT).show();
            }
        });
        bottomSheetDialog.show(getSupportFragmentManager(), "TareaBottomSheet");
    }

    // Mostrar el cuadro de dialogo para crear o editar una tarea
    private void showNuevaTareaDialog(Tarea tarea) {
        FormularioAnadir formularioAnadir = new FormularioAnadir();
        if (tarea != null) {
            formularioAnadir.setTarea(tarea);
        }
        formularioAnadir.setOnTareaSavedListener(this);
        formularioAnadir.show(getSupportFragmentManager(), "NuevaTareaDialogo");
    }

    // Mostrar confirmación de eliminación
    private void showDeleteConfirmationDialog(Tarea tarea, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar tarea")
                .setMessage("¿Estás seguro de que deseas eliminar esta tarea?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    if (position >= 0 && position < tareas.size()) {
                        tareas.remove(position);
                        adaptador.notifyItemRemoved(position);
                        Toast.makeText(this, "Tarea eliminada", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }


    // Método para manejar clic largo en la tarea
    private void onTareaLongClick(Tarea tarea, int position) {
    }


//    public void addTarea(Tarea t){
////        tareas.add(t);
//////        adaptador.notifyDataSetChanged();
////        adaptador = new Adaptador(tareas);
////        rvTareas = findViewById(R.id.recyclerView);
////        rvTareas.setLayoutManager(new LinearLayoutManager(MainActivity.this));
////        rvTareas.setAdapter(adaptador);
////        runOnUiThread(() -> {
////            tareas.add(t);
////            adaptador.notifyDataSetChanged();
////        });
//        runOnUiThread(() -> {
//            tareas.add(t);
//            rvTareas.post(() -> adaptador.notifyItemInserted(tareas.size() - 1));
//        });
//
//    }
//    public void addTarea(Tarea t) {
//        if (!onCreateFinished) {
//            new android.os.Handler().postDelayed(() -> addTarea(t), 100);
//            return;
//        }
//
//        // Agrega la nueva tarea a la lista
//        tareas.add(t);
//
//        // Notifica al adaptador que se ha insertado un nuevo item
//        runOnUiThread(() -> adaptador.notifyItemInserted(tareas.size() - 1));
//    }

}