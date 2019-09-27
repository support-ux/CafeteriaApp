package com.example.cafeteriaapp.ApiInterface;


import com.example.cafeteriaapp.Entidades.Venta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterfaceVentaAdmin {

    @GET("getDataDetallePedidoAdmin.php")
    Call<List<Venta>> getDetallePedidoAdmin(
            @Query("idC") int idC
    );
}

