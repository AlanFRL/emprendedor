package com.parcial1arq.emprendedor.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.parcial1arq.emprendedor.R;
import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {
    private List<String> categorias;

    public CategoriaAdapter(List<String> categorias) {
        this.categorias = categorias;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String descripcion = categorias.get(position);
        holder.textViewCategoria.setText(descripcion);

        // Acción de editar
        holder.btnEditar.setOnClickListener(v -> {
            ((CategoriaActivity) holder.itemView.getContext()).mostrarDialogoEditarCategoria(descripcion);
        });

        // Acción de eliminar
        holder.btnEliminar.setOnClickListener(v -> {
            ((CategoriaActivity) holder.itemView.getContext()).eliminarCategoriaSeleccionada(descripcion);
        });
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCategoria;
        Button btnEditar, btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCategoria = itemView.findViewById(R.id.textViewCategoria);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
