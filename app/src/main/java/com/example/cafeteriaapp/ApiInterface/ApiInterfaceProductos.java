package com.example.cafeteriaapp.ApiInterface;


import com.example.cafeteriaapp.Entidades.Producto;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterfaceProductos {

    @GET("getDataProductos.php")
    Call<List<Producto>> getProductos(
            @Query("item_type") String item_type,
            @Query("idCat") int id
    );
}
