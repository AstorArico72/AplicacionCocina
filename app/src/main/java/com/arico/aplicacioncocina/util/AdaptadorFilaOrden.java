package com.arico.aplicacioncocina.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.arico.aplicacioncocina.R;
import com.arico.aplicacioncocina.modelos.Artículo;
import com.arico.aplicacioncocina.modelos.FilaOrden;
import java.util.List;

public class AdaptadorFilaOrden extends RecyclerView.Adapter<AdaptadorFilaOrden.Holder> {
    @Nullable private List<FilaOrden> FilasOrden;
    @Nullable private List<Artículo> Articulos;
    private Context ContextoAplicacion;

    public AdaptadorFilaOrden (@NonNull Context contextoAplicacion, @Nullable List<FilaOrden> items, @Nullable List<Artículo> artículos) {
        this.FilasOrden = items;
        this.ContextoAplicacion = contextoAplicacion;
        this.Articulos = artículos;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from (ContextoAplicacion).inflate(R.layout.item_fila_orden, parent, false);
        return new Holder (vista);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (FilasOrden != null && Articulos != null) {
            FilaOrden fila = FilasOrden.get (position);
            holder.CantidadArticulo.setText(fila.getCantidad() + "x");
            if (position < Articulos.size()) {
                Artículo articulo = Articulos.get(position);
                holder.NombreArticulo.setText(articulo.getNombre() + " (#" + articulo.getId() + ")");
            }
        }
    }

    @Override
    public int getItemCount() {
        if (FilasOrden == null) {
            return 0;
        } else {
            return FilasOrden.size();
        }
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView CantidadArticulo;
        TextView NombreArticulo;
        public Holder(@NonNull View itemView) {
            super(itemView);
            this.CantidadArticulo = itemView.findViewById(R.id.CantidadOArticulo);
            this.NombreArticulo = itemView.findViewById(R.id.NombreOArticulo);
        }
    }
}
