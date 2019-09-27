package com.example.cafeteriaapp.Entidades;

import com.google.gson.annotations.SerializedName;

public class Venta {

    @SerializedName("ID") private int Id;
    @SerializedName("IDAL") private String IdAl;
    @SerializedName("NOMBRES") private String Nombres;
    @SerializedName("APELLIDOS") private String Apellidos;
    @SerializedName("FECHA") private String Fecha;
    @SerializedName("ESTADO") private String Estado;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getIdAl() {
        return IdAl;
    }

    public void setIdAl(String idAl) {
        IdAl = idAl;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }
}
