package es.reaktor.horarios.models;

import lombok.Data;

@Data
public class ObjetoCsv {
    private String nombre;
    private String apellidos;
    private String correo;
    private String rol;

    public ObjetoCsv(String nombre, String apellidos, String correo, String rol) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "ObjetoCsv{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", correo='" + correo + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}
