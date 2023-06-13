package com.example.proyectoapptarea.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoapptarea.R;
import com.example.proyectoapptarea.entidades.listaTareas;

import java.util.List;

public class listaAdaptador extends RecyclerView.Adapter<listaAdaptador.ViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<listaTareas> mData;

    public listaAdaptador(List<listaTareas> tareaList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context= context;
        this.mData= tareaList;
    }

    @Override
    public listaAdaptador.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.lista_tarea,null);
        return new listaAdaptador.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final listaAdaptador.ViewHolder holder,final int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setItems(List<listaTareas>items){
        mData= items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ViuwTitulo, ViuwDescripcion, ViuwFechaLimite;

        ViewHolder(View itemView){
            super(itemView);

            ViuwTitulo = itemView.findViewById(R.id.ViuwTitulo);
            ViuwDescripcion = itemView.findViewById(R.id.ViuwDescripcion);
            ViuwFechaLimite = itemView.findViewById(R.id.ViuwFechaLimite);
        }

        void bindData (final listaTareas item){
            ViuwTitulo.setText(item.getTitulo_tarea());
            ViuwDescripcion.setText(item.getDescripcion());
            ViuwFechaLimite.setText((CharSequence) item.getFechaVencimiento());

        }

    }

}