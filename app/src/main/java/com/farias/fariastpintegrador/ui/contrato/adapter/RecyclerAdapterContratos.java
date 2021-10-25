package com.farias.fariastpintegrador.ui.contrato.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.farias.fariastpintegrador.R;
import com.farias.fariastpintegrador.modelo.Contrato;
import com.farias.fariastpintegrador.modelo.Inmueble;
import com.farias.fariastpintegrador.modelo.Inquilino;
import com.farias.fariastpintegrador.ui.inquilino.adapter.RecyclerAdapterInquilinos;

import java.util.ArrayList;

public class RecyclerAdapterContratos extends RecyclerView.Adapter<RecyclerAdapterContratos.ContratoViewHolder>{

    // Declaraciones
    private Context context;
    private ArrayList<Inmueble> lista;
    private Contrato contrato;

    // ######################################
    //           implemnto el holder
    // ######################################
    public static class ContratoViewHolder extends RecyclerView.ViewHolder {
        private ImageView IV_foto;
        private TextView TV_Direccion, TV_Precio;

        public ContratoViewHolder(@NonNull View itemView) {
            super(itemView);

            IV_foto = itemView.findViewById(R.id.IV_foto_contrato);
            TV_Direccion = itemView.findViewById(R.id.TV_Direccion_contrato);
            TV_Precio = itemView.findViewById(R.id.TV_Precio_contrato);
        }

        public ImageView getIV_foto() {
            return IV_foto;
        }

        public TextView getTV_Direccion() {
            return TV_Direccion;
        }

        public TextView getTV_Precio() {
            return TV_Precio;
        }
    }

    // ####################################
    //             Constructor
    // ####################################
    // Se inicializa la informacion del adapter
    public RecyclerAdapterContratos(Context context, ArrayList<Inmueble> objetos) {
        this.lista = objetos;
        this.context = context;
        this.contrato = contrato;

    }

    // #################################
    //         OnCreateViewHolder
    // #################################

    @NonNull
    @Override
    public RecyclerAdapterContratos.ContratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layoutIdParaListItem = R.layout.fragment_contrato_fila;
        boolean attachToParentRapido = false;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutIdParaListItem,parent,attachToParentRapido);

        RecyclerAdapterContratos.ContratoViewHolder viewHolder = new RecyclerAdapterContratos.ContratoViewHolder(view);

        return viewHolder;
    }

    // #################################
    //         OnBindViewHolder
    // #################################

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterContratos.ContratoViewHolder holder, int position) {

        Log.d("mensaje", "En Inquilinos en la posicion " + position );

        Inmueble inmueble = lista.get(position);

        holder.TV_Direccion.setText(inmueble.getDireccion());
        holder.TV_Precio.setText("$ "+inmueble.getmontoAlquilerPropuesto()+".00");
        Glide.with(context)
                .load(inmueble.getImagen())
                .diskCacheStrategy(DiskCacheStrategy.ALL)       // Llama la imagen remota y la carga en el cache,
                .into(holder.IV_foto);                          // despues la busca de ahi y es mas rapido
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();                                           // Instancio el bundle a enviar
                bundle.putSerializable("inmueble", inmueble );                           // Meto la inmueble en el bundle
                Log.d("mensajeViewHolder",inmueble.getDireccion());
                Navigation.findNavController(view).navigate(R.id.contratoDetalleFragment,bundle);       // Meto el bundle clave budle en el navigation
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


}
