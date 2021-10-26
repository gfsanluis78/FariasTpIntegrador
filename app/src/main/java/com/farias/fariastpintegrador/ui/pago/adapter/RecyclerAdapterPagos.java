package com.farias.fariastpintegrador.ui.pago.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.farias.fariastpintegrador.R;
import com.farias.fariastpintegrador.modelo.Inmueble;
import com.farias.fariastpintegrador.modelo.Pago;
import com.farias.fariastpintegrador.ui.inmuebles.adapter.RecyclerAdapterInmuebles;

import java.util.ArrayList;

public class RecyclerAdapterPagos extends RecyclerView.Adapter<RecyclerAdapterPagos.PagosViewHolder> { // Hago el extends e implemento los meotodos

    //  Declaraciones
    private Context context;
    private ArrayList<Pago> lista;

    // ######################################
    //           implemnto el holder
    // ######################################
    public static class PagosViewHolder extends RecyclerView.ViewHolder {
        private TextView codigoPago, numPago, codigoContrato, importe, fechaPago;

        public PagosViewHolder(@NonNull View itemView) {
            super(itemView);

            codigoPago = itemView.findViewById(R.id.TV_codigo_pago);
            numPago = itemView.findViewById(R.id.TV_numero_pago);
            codigoContrato = itemView.findViewById(R.id.TV_codigo_contrato);
            importe = itemView.findViewById(R.id.TV_importe_pago);
            fechaPago = itemView.findViewById(R.id.TV_fecha_pago);
          }

          // Implemento los geters

        public TextView getCodigoPago() {
            return codigoPago;
        }

        public TextView getNumPago() {
            return numPago;
        }

        public TextView getCodigoContrato() {
            return codigoContrato;
        }

        public TextView getImporte() {
            return importe;
        }

        public TextView getFechaPago() {
            return fechaPago;
        }
    }

    // ####################################
    //             Constructor
    // ####################################
    // Se inicializa la informacion del adapter
    public RecyclerAdapterPagos(Context context, ArrayList<Pago> objetos) {
        this.lista = objetos;
        this.context = context;

    }

    // #################################
    //         OnCreateViewHolder
    // #################################
    @NonNull
    @Override
    public PagosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layoutIdParaListItem = R.layout.fragment_pago_fila;
        boolean attachToParentRapido = false;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutIdParaListItem,parent,attachToParentRapido);

        return new PagosViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PagosViewHolder holder, int position) {

        Log.d("mensaje", "En la posicion " + position );

        Pago pago = lista.get(position);
        holder.numPago.setText("Pago numero "+pago.getNumero());
        holder.codigoPago.setText(pago.getIdPago()+"");
        holder.codigoContrato.setText(pago.getIdContrato()+"");
        holder.importe.setText("$ " + pago.getImporte()+".00");
        holder.fechaPago.setText(pago.getFechaFormateada());

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

}
