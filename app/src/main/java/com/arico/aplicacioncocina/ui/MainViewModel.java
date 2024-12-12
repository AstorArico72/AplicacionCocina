package com.arico.aplicacioncocina.ui;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.List;
import com.arico.aplicacioncocina.modelos.Orden;
import com.arico.aplicacioncocina.util.ClienteApi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends androidx.lifecycle.AndroidViewModel {
    private Context ContextoAplicacion;
    private MutableLiveData<List<Orden>> ListaOrdenes;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.ContextoAplicacion = application.getApplicationContext();
    }

    public MutableLiveData<List<Orden>> LeerOrdenes () {
        if (ListaOrdenes == null) {
            ListaOrdenes = new MutableLiveData<>();
        }
        return ListaOrdenes;
    }

    public void Conectarse () {
        ClienteApi.InterfazApi api = ClienteApi.ConseguirApi();
        @Nullable String TokenAcceso = ClienteApi.LeerToken(ContextoAplicacion);
        Call<String> llamada = api.HabilitarCocina(TokenAcceso);
        llamada.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i ("RespuestaHTTP", response.toString());
                if (response.code() == 200) {
                    ClienteApi.GuardarToken("Bearer " + response.body(), ContextoAplicacion);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        });
    }

    public void RecibirOrdenes () {
        ClienteApi.InterfazApi api = ClienteApi.ConseguirApi();
        String token = ClienteApi.LeerToken(ContextoAplicacion);
        Call<List<Orden>> llamada = api.TodasLasOrdenes(token);
        llamada.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Orden>> call, Response<List<Orden>> response) {
                if (response.code() == 200) {
                    ListaOrdenes.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Orden>> call, Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        });
    }

}
