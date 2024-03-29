package com.example.proyectoapptarea.entidades;


import android.widget.DatePicker;
import android.widget.TextView;

import java.sql.Date;

public class listaTareas {
    private int id, completada;
    private String fechaCreacion;
    private String titulo_tarea;
    private String descripcion;
    private String fechaVencimiento;
    private String hora;

    public listaTareas(String titulo_tarea, String descripcion, String fechaVencimiento, String hora, int completada) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.titulo_tarea = titulo_tarea;
        this.descripcion = descripcion;
        this.fechaVencimiento = fechaVencimiento;
        this.hora = hora;
        this.completada = completada;
    }

    public listaTareas(int id,  String titulo_tarea, String descripcion, String fechaVencimiento, String hora) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.titulo_tarea = titulo_tarea;
        this.descripcion = descripcion;
        this.fechaVencimiento = fechaVencimiento;
        this.hora = hora;
    }

    public listaTareas() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTitulo_tarea() {
        return titulo_tarea;
    }

    public void setTitulo_tarea(String titulo_tarea) {
        this.titulo_tarea = titulo_tarea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String gethora() {
        return hora;
    }
    public void sethora(String hora) {
        this.hora = hora;
    }

    public int getCompletada() {
        return completada;
    }

    public void setCompletada(int completada) {
        this.completada = completada;
    }
}
