package com.example.vianda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.bumptech.glide.Glide;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private Context context;
    private List<Producto> listaProductos;

    public ProductoAdapter(Context context, List<Producto> listaProductos) {
        this.context = context;
        this.listaProductos = listaProductos;
    }

    @Override
    public ProductoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductoViewHolder holder, int position) {
        Producto producto = listaProductos.get(position);
        holder.txtNombre.setText(producto.getNombre());
        holder.txtPrecio.setText("$" + producto.getPrecio());

        // Validar si la URL de la imagen es correcta antes de intentar cargarla
        String imageUrl = producto.getImagen();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(producto.getImagen())
                    .placeholder(R.drawable.hamburguesas)
                    .error(R.drawable.hamburguesas)
                    .into(holder.imgProducto);
        } else {
            holder.imgProducto.setImageResource(R.drawable.hamburguesas);  // Si no hay imagen, se muestra una predeterminada
        }
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtPrecio;
        ImageView imgProducto;

        public ProductoViewHolder(View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);
            imgProducto = itemView.findViewById(R.id.imgProducto);
        }
    }
}