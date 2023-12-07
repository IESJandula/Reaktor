package es.reaktor.horarios.models;

import java.util.List;

public class ObjetoCsv {
	private String nombre;
    private String apellidos;
    private String correo;
    private List<String> roles;

    public ObjetoCsv(String nombre, String apellidos, String correo, List<String> roles) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.roles = roles;
    }

    // Agrega getters y setters según sea necesario

    @Override
    public String toString() {
        return "ObjetoCsv{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", correo='" + correo + '\'' +
                ", roles=" + roles +
                '}';
    }
}
