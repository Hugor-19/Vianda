package com.example.vianda;

import android.os.Parcel;
import android.os.Parcelable;

public class Producto implements Parcelable {
    private String nombre;
    private String imagen;
    private String descripcion;
    private int precio;

    // Constructor
    public Producto(String nombre, String imagen, String descripcion, int precio) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    // Métodos Parcelable
    protected Producto(Parcel in) {
        nombre = in.readString();
        imagen = in.readString();
        descripcion = in.readString();
        precio = in.readInt();
    }

    public static final Creator<Producto> CREATOR = new Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(imagen);
        dest.writeString(descripcion);
        dest.writeInt(precio);
    }
}