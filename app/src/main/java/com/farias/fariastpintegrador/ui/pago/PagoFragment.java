package com.farias.fariastpintegrador.ui.pago;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.farias.fariastpintegrador.R;
import com.farias.fariastpintegrador.modelo.Contrato;
import com.farias.fariastpintegrador.ui.pago.adapter.RecyclerAdapterPagos;

public class PagoFragment extends Fragment {

    private RecyclerView recyclerView;
    private PagoViewModel model;

    public static PagoFragment newInstance() {
        return new PagoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pago, container, false);
        model = new ViewModelProvider(this).get(PagoViewModel.class);

        // Inicializo el recycler view
        recyclerView = view.findViewById(R.id.RV_Pagos);    // Asi llamo a rv desde fragmnet

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),       // cambie el getContext por el getActivity y lo volvi atras
                LinearLayoutManager.VERTICAL,
                false);

        model.getPagos().observe( getViewLifecycleOwner(), pagos -> {               // cambie el this.getActivity por un getViewLifeycleOwner porqu es fragment
            {
                RecyclerAdapterPagos mAdapter = new RecyclerAdapterPagos(getContext(),pagos);
                recyclerView.setLayoutManager((linearLayoutManager));
                recyclerView.setAdapter(mAdapter);
            }
        });

        Contrato c = (Contrato) getArguments().getSerializable("elContrato");
        model.setPagos(c);

        return view;
    }

}