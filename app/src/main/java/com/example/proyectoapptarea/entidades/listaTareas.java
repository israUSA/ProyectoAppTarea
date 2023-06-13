package com.example.proyectoapptarea.entidades;


import android.widget.TextView;

import java.sql.Date;

public class listaTareas {
    private int id;
    private Date fechaCreacion;
    private String titulo_tarea;
    private String descripcion;
    private Date fechaVencimiento;

    public listaTareas(TextView titulo, TextView descripcion, TextView fechaLimite) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.titulo_tarea = titulo_tarea;
        this.descripcion = this.descripcion;
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
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

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}
