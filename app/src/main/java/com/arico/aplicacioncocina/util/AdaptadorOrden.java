package com.arico.aplicacioncocina.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.arico.aplicacioncocina.R;
import com.arico.aplicacioncocina.modelos.Orden;
import com.arico.aplicacioncocina.ui.DetallesOrden;

import java.util.List;

public class AdaptadorOrden extends RecyclerView.Adapter <AdaptadorOrden.Holder> {

    private Context ContextoAplicacion;
    @Nullable private List<Orden> ListaOrdenes;
    private FragmentManager gestor;

    public AdaptadorOrden (@NonNull Context contextoAplicacion, @Nullable List<Orden> items, @NonNull FragmentManager gestorFragmentos) {
        this.ContextoAplicacion = contextoAplicacion;
        this.ListaOrdenes = items;
        this.gestor = gestorFragmentos;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(ContextoAplicacion).inflate(R.layout.item_orden, parent, false);
        return new Holder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (ListaOrdenes != null) {
            Orden orden = ListaOrdenes.get(position);
            holder.NumOrden.setText("Orden #" + orden.getId());
            holder.NumCliente.setText("Mesa #" + orden.getCliente());
            holder.TarjetaOrden.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle atado = new Bundle ();
                    assert orden != null;
                    atado.putSerializable("Orden", orden);
                    FragmentTransaction transaction = gestor.beginTransaction();
                    transaction.add (DetallesOrden.class, atado, null);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (ListaOrdenes == null) {
            return 0;
        } else {
            return ListaOrdenes.size();
        }
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView NumOrden;
        private TextView NumCliente;
        private CardView TarjetaOrden;

        public Holder(@NonNull View itemView) {
            super(itemView);
            this.NumOrden = itemView.findViewById(R.id.NumOrden);
            this.NumCliente = itemView.findViewById(R.id.NumCliente);
            this.TarjetaOrden = itemView.findViewById(R.id.TarjetaOrden);
        }
    }
}
