package com.example.cafeteriaapp.Entidades;

import com.google.gson.annotations.SerializedName;

public class Producto {
    @SerializedName("Id") private int Id;
    @SerializedName("Producto") private String Producto;
    @SerializedName("Descripcion") private String Descripcion;
    @SerializedName("Stock") private String Stock;
    @SerializedName("Precio") private String Precio;
    @SerializedName("Foto") private String Foto;

    public int getId() {
        return Id;
    }

    public String getProducto() {
        return Producto;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public String getStock() {
        return Stock;
    }

    public String getPrecio() {
        return Precio;
    }

    public String getFoto() {
        return Foto;
    }
}
