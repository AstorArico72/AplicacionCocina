package com.arico.aplicacioncocina.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arico.aplicacioncocina.modelos.Artículo;
import com.arico.aplicacioncocina.modelos.Orden;
import com.arico.aplicacioncocina.modelos.FilaOrden;
import com.arico.aplicacioncocina.databinding.FragmentDetallesOrdenBinding;
import com.arico.aplicacioncocina.util.AdaptadorFilaOrden;

import java.util.List;

public class DetallesOrden extends Fragment {
    private FragmentDetallesOrdenBinding binder;
    private DetallesOrdenViewModel mViewModel;
    private Orden orden;

    public static DetallesOrden newInstance() {
        return new DetallesOrden();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binder = FragmentDetallesOrdenBinding.inflate (inflater);
        this.mViewModel = new ViewModelProvider(this).get(DetallesOrdenViewModel.class);
        assert getArguments() != null;
        this.orden = (Orden) getArguments().getSerializable("Orden");

        binder.NumOrdenD.append (orden.getId() + "");
        binder.NumClienteD.append (orden.getCliente() + "");

        mViewModel.LeerArticulo().observe(getViewLifecycleOwner(), new Observer<List<Artículo>>() {
            @Override
            public void onChanged(List<Artículo> artículos) {
                CargarFilas(mViewModel.LeerFilas().getValue(), mViewModel.LeerArticulo().getValue());
            }
        });

        mViewModel.getImporteOrden().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binder.EtiquetaImporte.setText("Importe: $" + integer);
            }
        });

        CargarFilas(null, null);

        mViewModel.ConseguirFilas(orden.getId());
        getActivity().setContentView(binder.getRoot());

        binder.BotonVerListado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.MutableArticulo.setValue(null);
                mViewModel.Filas.setValue(null);
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });

        binder.BotonDescartar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.DescartarOrden(orden.getId());
                mViewModel.MutableArticulo.setValue(null);
                mViewModel.Filas.setValue(null);
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });

        binder.BotonEntregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.EntregarOrden(orden.getId());
                mViewModel.MutableArticulo.setValue(null);
                mViewModel.Filas.setValue(null);
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });

        return binder.getRoot();
    }

    private void CargarFilas (@Nullable List <FilaOrden> filas, @Nullable List<Artículo> articulos) {
        AdaptadorFilaOrden adaptador = new AdaptadorFilaOrden(getActivity().getApplicationContext(), filas, articulos);
        binder.ListaFilas.setAdapter(adaptador);
        binder.ListaFilas.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}