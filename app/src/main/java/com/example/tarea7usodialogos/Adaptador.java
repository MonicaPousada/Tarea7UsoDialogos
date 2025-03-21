package com.example.tarea7usodialogos;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.TareaViewHolder>{
    //Lista que almacena las prendas que se mostrarán en el RecyclerView
    ArrayList<Tarea> tareas;
    private final OnTareaClickListener clickListener;
    private final OnTareaLongClickListener longClickListener;

    //Constructor que recibe la lista de prendas como parámetro
    public Adaptador(ArrayList<Tarea> tareas, OnTareaClickListener clickListener, OnTareaLongClickListener longClickListener) {
        this.tareas = tareas;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    // Interfaz para manejar clics simples
    public interface OnTareaClickListener {
        void onTareaClick(Tarea tarea, int position);
    }

    // Interfaz para manejar clics largos
    public interface OnTareaLongClickListener {
        void onTareaLongClick(Tarea tarea, int position);
    }

    //Método que se llama al crear una nueva vista (ViewHolder) para un elemento del RecyclerView
    @NonNull
    @Override
    public Adaptador.TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//Inflar la vista desde el layout 'vista.xml' y crear un nuevo ViewHolder con ella
        Adaptador.TareaViewHolder tareaViewHolder =
                new TareaViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_tarea,parent,false)
                );
        return tareaViewHolder;
    }

    //Método que se llama para enlazar los datos de un elemento con su ViewHolder
    @Override
    public void onBindViewHolder(@NonNull Adaptador.TareaViewHolder holder, int position) {
//Obtener la prenda correspondiente a la posición actual
        Tarea tarea = tareas.get(position);

        holder.tvAsignatura.setText(tarea.getAsignatura());
        holder.tvTitulo.setText(tarea.getTitulo());
        holder.tvDescripcion.setText(tarea.getDescripcion());
        holder.tvFecha.setText(tarea.getFecha());
        holder.tvHora.setText(tarea.getHora());

        SpannableString completada;
        if(tarea.getCompletada() == 0){
            completada = new SpannableString("No completada");
            completada.setSpan(new StyleSpan(Typeface.BOLD), 0, completada.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            completada.setSpan(new ForegroundColorSpan(Color.RED), 0, completada.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        else{
            completada = new SpannableString("Completada");
            completada.setSpan(new StyleSpan(Typeface.BOLD), 0, completada.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            completada.setSpan(new ForegroundColorSpan(Color.GREEN), 0, completada.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        holder.tvCompletada.setText(completada);

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onTareaClick(tarea, position);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onTareaLongClick(tarea, position);
            }
            return true;
        });

    }

    //Método que devuelve la cantidad de elementos en la lista
    @Override
    public int getItemCount() {
        return tareas.size();
    }

    //Clase interna que define el ViewHolder para la lista de prendas
    public class TareaViewHolder extends RecyclerView.ViewHolder{
        TextView tvAsignatura;
        TextView tvTitulo;
        TextView tvDescripcion;
        TextView tvFecha;
        TextView tvHora;
        TextView tvCompletada;

        //Constructor que inicializa los componentes de la vista
        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAsignatura = itemView.findViewById(R.id.tvAsignatura);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvHora = itemView.findViewById(R.id.tvHora);
            tvCompletada = itemView.findViewById(R.id.tvCompletada);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    BottomSheetDialog menuInferior = new BottomSheetDialog(itemView.getContext());
//                    menuInferior.setContentView(R.layout.menu_inferior);
//                    menuInferior.show();
//
//                    LinearLayout llEditar = itemView.findViewById(R.id.llEditar);
//                    llEditar.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            //editar tarea
//                        }
//                    });
//
//                    LinearLayout llEliminar = itemView.findViewById(R.id.llEliminar);
//                    llEliminar.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            //eliminar tarea
//                        }
//                    });
//
//                    LinearLayout llCompletar = itemView.findViewById(R.id.llCompletar);
//                    llCompletar.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            //completar tarea
//                        }
//                    });
//
//
//
//                }
//            });
        }
    }
}
