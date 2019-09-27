package com.example.cafeteriaapp.ApiClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiGetDataVentaAdmin {

    public static final String BASE_URL = "https://support-ux.000webhostapp.com/wscafeteria/Datos/";
    public static Retrofit retrofit;

    public static Retrofit getApiListaDetalleVentaAdmin(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
