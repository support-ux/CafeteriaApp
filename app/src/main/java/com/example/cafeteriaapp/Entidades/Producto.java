package com.example.cafeteriaapp.Entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.annotations.SerializedName;

public class Producto {
    @SerializedName("Id") private int Id;
    @SerializedName("Producto") private String Producto;
    @SerializedName("Descripcion") private String Descripcion;
    @SerializedName("Stock") private String Stock;
    @SerializedName("Precio") private String Precio;
    @SerializedName("Foto") private String Foto;
    @SerializedName("imagen") private Bitmap imagen;

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

    public Bitmap getImagen() {
        return imagen;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setProducto(String producto) {
        Producto = producto;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public void setStock(String stock) {
        Stock = stock;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public void setFoto(String foto) {
        Foto = foto;
        try{
            byte[] byteCode = Base64.decode(Foto,Base64.DEFAULT);
            this.imagen = BitmapFactory.decodeByteArray(byteCode,0,byteCode.length);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
