package com.arico.aplicacioncocina.ui;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.arico.aplicacioncocina.modelos.Artículo;
import com.arico.aplicacioncocina.modelos.FilaOrden;
import com.arico.aplicacioncocina.util.ClienteApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallesOrdenViewModel extends AndroidViewModel {
    public MutableLiveData<List<FilaOrden>> Filas;
    public MutableLiveData<List<Artículo>> MutableArticulo;
    public MutableLiveData<Integer> ImporteOrden;
    private List <Artículo> artículos;
    private int importe;
    private int cantidad;
    private Context ContextoAplicacion;
    public DetallesOrdenViewModel(@NonNull Application application) {
        super(application);
        this.ContextoAplicacion = application.getApplicationContext ();
        this.artículos = new ArrayList<>();
    }

    public MutableLiveData<List<FilaOrden>> LeerFilas () {
        if (Filas == null) {
            Filas = new MutableLiveData<>();
        }
        return Filas;
    }

    public MutableLiveData<List<Artículo>> LeerArticulo () {
        if (MutableArticulo == null) {
            MutableArticulo = new MutableLiveData<>();
        }
        return MutableArticulo;
    }

    public MutableLiveData<Integer> getImporteOrden() {
        if (ImporteOrden == null) {
            ImporteOrden = new MutableLiveData<>();
        }
        return ImporteOrden;
    }

    public void ConseguirFilas (int orden) {
        ClienteApi.InterfazApi api = ClienteApi.ConseguirApi();
        String token = ClienteApi.LeerToken(ContextoAplicacion);

        Call<List<FilaOrden>> llamada = api.LeerFilas(token, orden);
        llamada.enqueue(new Callback<List<FilaOrden>>() {
            @Override
            public void onResponse(Call<List<FilaOrden>> call, Response<List<FilaOrden>> response) {
                if (response.code() == 200) {
                    LeerFilas().postValue(response.body());
                    response.body().forEach(fila -> {
                        cantidad = fila.getCantidad();
                        CargarArticulo(fila.getArticulo(), cantidad, ContextoAplicacion);
                    });
                } else if (response.code() == 404) {
                    Toast.makeText(ContextoAplicacion, "Ésa orden fue borrada o entregada.", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<List<FilaOrden>> call, Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        });
    }

    public void CargarArticulo (int id, int cantidad, Context contexto) {
        if (LeerArticulo().getValue() != null) {
            artículos = LeerArticulo().getValue();
        }
        ClienteApi.InterfazApi api = ClienteApi.ConseguirApi();
        String token = ClienteApi.LeerToken(contexto);
        Call <Artículo> llamada = api.DetallesArticulo(token, id);
        llamada.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Artículo> call, Response<Artículo> response) {
                Log.i("RespuestaHTTP", response.toString());
                artículos.add(response.body());
                LeerArticulo().postValue(artículos);
                importe += response.body().getPrecio() * cantidad;
                getImporteOrden().postValue(importe);
            }

            @Override
            public void onFailure(Call<Artículo> call, Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        });
    }

    public void DescartarOrden (int id) {
        ClienteApi.InterfazApi api = ClienteApi.ConseguirApi();
        String token = ClienteApi.LeerToken(ContextoAplicacion);
        Call<String> llamada = api.DescartarOrden(token, id);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(ContextoAplicacion, response.body().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        });
    }

    public void EntregarOrden (int id) {
        ClienteApi.InterfazApi api = ClienteApi.ConseguirApi();
        String token = ClienteApi.LeerToken(ContextoAplicacion);
        Call<String> llamada = api.EntregarOrden(token, id);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(ContextoAplicacion, response.body().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        });
    }
}