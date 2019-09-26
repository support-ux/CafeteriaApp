package com.example.cafeteriaapp.ApiInterface;


import com.example.cafeteriaapp.Entidades.VentaDetalle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterfaceDetallePedidoUsuario {

    @GET("getDataDetallePedidoUsuario.php")
    Call<List<VentaDetalle>> getDetallePedidoUsuario(
            @Query("idAlumno") int idAlumno
    );
}

