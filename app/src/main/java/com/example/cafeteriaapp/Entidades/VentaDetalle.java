package com.example.cafeteriaapp.Entidades;

import com.google.gson.annotations.SerializedName;

public class VentaDetalle {

    @SerializedName("PRODUCTO") private String Producto;
    @SerializedName("CANTIDAD") private String Cantidad;
    @SerializedName("PRECIO") private String Precio;
    @SerializedName("TOTAL") private String Total;

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String producto) {
        Producto = producto;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }
}
