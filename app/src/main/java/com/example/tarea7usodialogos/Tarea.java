package com.example.tarea7usodialogos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Tarea implements Parcelable {
    private String asignatura;
    private String titulo;
    private String descripcion;
    private String fecha;
    private String hora;
    private Integer completada;

    // Constructor principal
    public Tarea(String asignatura, String titulo, String descripcion, String fecha, String hora, int completada) {
        this.asignatura = asignatura;
        this.fecha = fecha;
        this.hora = hora;
        this.completada = completada;
        this.descripcion = descripcion;
        this.titulo = titulo;
    }

    // Constructor para recrear un objeto desde un Parcel
    protected Tarea(Parcel in) {
        asignatura = in.readString();
        titulo = in.readString();
        descripcion = in.readString();
        fecha = in.readString();
        hora = in.readString();
        completada = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(asignatura);
        parcel.writeString(titulo);
        parcel.writeString(descripcion);
        parcel.writeString(fecha);
        parcel.writeString(hora);
        parcel.writeInt(completada);

    }

    public static final Creator<Tarea> CREATOR = new Creator<Tarea>() {
        @Override
        public Tarea createFromParcel(Parcel in) {
            return new Tarea(in);
        }

        @Override
        public Tarea[] newArray(int size) {
            return new Tarea[size];
        }
    };

    // Getters y Setters
    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getCompletada() {
        return completada;
    }

    public void setCompletada(int completada) {
        this.completada = completada;
    }

    @Override
    public String toString() {
        return "Tarea{" +
                "asignatura=" + asignatura +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", completada=" + completada +
                '}';
    }
}

