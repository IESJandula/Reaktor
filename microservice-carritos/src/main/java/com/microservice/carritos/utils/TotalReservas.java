package com.microservice.carritos.utils;

import es.reaktor.models.carritos.ReservaAula;
import es.reaktor.models.carritos.ReservaCarritoPcs;
import es.reaktor.models.carritos.ReservaCarritoTablets;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Clase TotalReservas encargada de coger en formato de listas todas las reservas, tanto de carritos, como aulas
 */
@NoArgsConstructor
@AllArgsConstructor
public class TotalReservas
{

    /**
     * Atributos en formato listas
     */
    private List<ReservaAula> reservasAulas;

    private List<ReservaCarritoPcs> reservasCarritoPcs;

    private List<ReservaCarritoTablets> reservasCarritoTablets;

    /**
     * Getters and Setters
     */

    public List<ReservaAula> getReservasAulas()
    {
        return reservasAulas;
    }

    public void setReservasAulas(List<ReservaAula> reservasAulas)
    {
        this.reservasAulas = reservasAulas;
    }

    public List<ReservaCarritoPcs> getReservasCarritoPcs()
    {
        return reservasCarritoPcs;
    }

    public void setReservasCarritoPcs(List<ReservaCarritoPcs> reservasCarritoPcs) {this.reservasCarritoPcs = reservasCarritoPcs;}

    public List<ReservaCarritoTablets> getReservasCarritoTablets()
    {
        return reservasCarritoTablets;
    }

    public void setReservasCarritoTablets(List<ReservaCarritoTablets> reservasCarritoTablets) {this.reservasCarritoTablets = reservasCarritoTablets;}

    /**
     * Generated ToString
     * @return devuelve un string con las listas de las reservas
     */
    @Override
    public String toString() {
        return "TotalReservas{" +
                "reservasAulas=" + reservasAulas +
                ", reservasCarritoPcs=" + reservasCarritoPcs +
                ", reservasCarritoTablets=" + reservasCarritoTablets +
                '}';
    }
}
