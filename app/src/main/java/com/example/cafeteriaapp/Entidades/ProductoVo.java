package com.example.cafeteriaapp.Entidades;

public class ProductoVo {

    private String id;
    private String nomProducto;
    private String precio;

    public ProductoVo(String id, String nomProducto, String precio) {
        this.id = id;
        this.nomProducto = nomProducto;
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomProducto() {
        return nomProducto;
    }

    public void setNomProducto(String nomProducto) {
        this.nomProducto = nomProducto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
