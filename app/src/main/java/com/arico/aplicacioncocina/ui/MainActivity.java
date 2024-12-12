package com.arico.aplicacioncocina.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.arico.aplicacioncocina.databinding.ActivityMainBinding;
import com.arico.aplicacioncocina.modelos.Orden;
import com.arico.aplicacioncocina.util.AdaptadorOrden;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binder;
    private MainViewModel ViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binder = ActivityMainBinding.inflate(getLayoutInflater());
        this.ViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        SolicitarPermisos();
        ViewModel.Conectarse();

        ViewModel.LeerOrdenes().observe(this, new Observer<List<Orden>>() {
            @Override
            public void onChanged(List<Orden> ordenes) {
                CargarOrdenes(ordenes);
            }
        });

        ViewModel.RecibirOrdenes();

        setContentView(binder.getRoot());
    }

    private void CargarOrdenes (List <Orden> ordenes) {
        AdaptadorOrden adaptador = new AdaptadorOrden(getApplicationContext(), ordenes, getSupportFragmentManager());
        binder.ListaOrdenes.setAdapter(adaptador);
        binder.ListaOrdenes.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
    }
    private void SolicitarPermisos(){
        if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.INTERNET}, 1000);
        }
    }
}