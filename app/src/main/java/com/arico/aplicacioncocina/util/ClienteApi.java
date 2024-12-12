package com.arico.aplicacioncocina.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arico.aplicacioncocina.modelos.Artículo;
import com.arico.aplicacioncocina.modelos.FilaOrden;
import com.arico.aplicacioncocina.modelos.Orden;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Las operaciones de API del lado del cliente se hacen aquí.
 */

public class ClienteApi {

    public static final String UrlBase = "http://192.168.1.150:5179";

    /**
     * Ésto crea una interfaz Retrofit para poder hacer pedidos al API.
     *
     * @return Instancia de la clase InterfazApi
     * @see InterfazApi
     */
    public static InterfazApi ConseguirApi () {
        Gson NotAJson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.150:5179/Api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(NotAJson))
                .build();
        return retrofit.create(InterfazApi.class);
    }

    /**
     * Ésto guarda el JSON Web Token traido del API.
     *
     * @param token JWT Bearer token guardado en las preferencias compartidas.
     * @param applicationContext Contexto de la aplicación, traido de la vista principal.
     */
    public static void GuardarToken (String token, Context applicationContext) {
        SharedPreferences preferences = applicationContext.getSharedPreferences("PreferenciasCompartidas", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("TokenAcceso", token);
        editor.apply();
    }

    /**
     * Ésto lee el JSON Web Token del archivo de preferencias compartidas.
     *
     * @param applicationContext Contexto de la aplicación, traido de la vista principal
     * @return El JSON Web Token, si no hay uno, retorna null.
     * @see SharedPreferences
     */
    @Nullable
    public static String LeerToken (@NonNull Context applicationContext) {
        SharedPreferences preferences = applicationContext.getSharedPreferences("PreferenciasCompartidas", Context.MODE_PRIVATE);
        return preferences.getString("TokenAcceso", null);
    }

    public static void BorrarToken (Context applicationContext) {
        SharedPreferences preferences = applicationContext.getSharedPreferences("PreferenciasCompartidas", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("TokenAcceso");
        editor.apply();
    }

    public interface InterfazApi {
        /**
         * Ésto conecta al servidor, permitiendo hacer otros pedidos al API.
         *
         * @param token JWT Bearer para la autorización, si es aplicable.
         * @return Llamada retrofit.
         * @see retrofit2.Call
         */
        @GET("Conectarse/ConexionCocina")
        Call<String> HabilitarCocina (@Nullable @Header("Authorization") String token);

        @GET("Articulos/Detalles/{id}")
        Call<Artículo> DetallesArticulo (@Header("Authorization") String token, @Path(value = "id") int id);

        @GET("Ordenes/Todas")
        Call<List<Orden>> TodasLasOrdenes (@Header("Authorization") String token);

        /** Ésto hace un borrado lógico en la base de datos; la orden sigue allí.
         *
         * @param token JWT Bearer para la autorización.
         * @param idOrden Orden a entregar.
         * @return Llamada Retrofit.
         */
        @DELETE("Ordenes/Entregar/{id}")
        Call<String> EntregarOrden (@Header("Authorization") String token, @Path(value="id") int idOrden);

        /** Ésto hace un borrado duro en la base de datos; la orden es eliminada permanentemente.
         *
         * @param token JWT Bearer para la autorización.
         * @param idOrden Orden a borrar.
         * @return Llamada Retrofit.
         */
        @DELETE("Ordenes/Descartar/{id}")
        Call<String> DescartarOrden (@Header("Authorization") String token, @Path(value="id") int idOrden);

        @GET("Ordenes/Detalles/{id}")
        Call<List<FilaOrden>> LeerFilas (@Header("Authorization") String token, @Path(value="id") int idOrden);
    }
}
