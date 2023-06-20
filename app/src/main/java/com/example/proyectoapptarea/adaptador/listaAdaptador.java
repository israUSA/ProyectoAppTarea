package com.example.proyectoapptarea.adaptador;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoapptarea.R;
import com.example.proyectoapptarea.activity_ver_tarea;
import com.example.proyectoapptarea.entidades.listaTareas;

import java.util.ArrayList;
import java.util.List;

public class listaAdaptador extends RecyclerView.Adapter<listaAdaptador.ViewHolder> {

    private Context context;
    ArrayList<listaTareas> listaTareas;
    private LayoutInflater mInflater;
    private List<listaTareas> mData;


    public listaAdaptador(List<listaTareas> tareaList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context= context;
        this.mData= tareaList;

    }


    @Override
    public void onBindViewHolder(final listaAdaptador.ViewHolder holder,final int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public listaAdaptador.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
/*        View view = mInflater.inflate(R.layout.lista_tarea,null);
        return new listaAdaptador.ViewHolder(view);*/

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_tarea, null, false);
        return new listaAdaptador.ViewHolder(view);
    }

    public void setItems(List<listaTareas>items){
        mData= items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ViuwTitulo, ViuwDescripcion, ViuwFechaLimite,horaLimite;

        TextView viewNombre, viewFecha, viewDescripcion;

        ViewHolder(View itemView){
            super(itemView);
            ViuwTitulo = itemView.findViewById(R.id.ViuwTitulo);
            ViuwDescripcion = itemView.findViewById(R.id.ViuwDescripcion);
            ViuwFechaLimite = itemView.findViewById(R.id.ViuwFechaLimite);
            horaLimite = itemView.findViewById(R.id.horaLimite);

            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewFecha = itemView.findViewById(R.id.viewFecha);
            viewDescripcion = itemView.findViewById(R.id.viewDescripcion);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        Context context = view.getContext();
                        Intent intent = new Intent(context, activity_ver_tarea.class);
                        intent.putExtra("ID", position+1);
                        context.startActivity(intent);
                    }

                }
            });
        }

        void bindData (final listaTareas item){
            ViuwTitulo.setText(item.getTitulo_tarea());
            ViuwDescripcion.setText(item.getDescripcion());
            ViuwFechaLimite.setText(item.getFechaVencimiento());
            horaLimite.setText(item.gethora());
        }
    }

}