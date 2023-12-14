package com.microservice.carritos.controller;

import com.microservice.carritos.repository.*;
import com.microservice.carritos.service.AllReservasService;
import com.microservice.carritos.utils.TotalReservas;
import es.reaktor.models.carritos.ReservaAula;
import es.reaktor.models.carritos.ReservaCarritoPcs;
import es.reaktor.models.carritos.ReservaCarritoTablets;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase controladora encargada de definir y hacer funcionar cada EndPoint
 */
@NoArgsConstructor
@RestController
@RequestMapping(value = "/reservas", produces = {"application/json"})
public class ReservasCarritosController
{

    /**
     * Listas para almacenar las reservas en las sesiones
     */

    List<ReservaAula> listaRservasAulas = new ArrayList<>();

    List<ReservaCarritoTablets> listasReservasCarritoTablets = new ArrayList<>();

    List<ReservaCarritoPcs> listaReservasCarritoPcs = new ArrayList<>();

    /**
     * atributos que referencian los repositorios con autowired
     */

    @Autowired
    private AllReservasService allReservasService;

    @Autowired
    private IAulasInformaticaRepository iAulasInformaticaRepository;

    @Autowired
    private IProfesorRepository iProfesorRepository;

    @Autowired
    private IReservaAulaRepository iReservaAulaRepository;

    @Autowired
    private IReservaCarritoTabletsRepository iReservaCarritoTabletsRepository;

    @Autowired
    private ICarritoTabletsRepository iCarritoTabletsRepository;

    @Autowired
    private IReservaCarritoPcsRepository iReservaCarritoPcsRepository;

    @Autowired
    private ICarritoPcRepository iCarritoPcRepository;

    /**
     * Visualizacion de las reservas
     * @return las reservas que hay almacenadas en la base de datos
     */
    @RequestMapping(method = RequestMethod.GET, value = "/allReservations")
    public ResponseEntity<TotalReservas> listaReservas()
    {
        TotalReservas totalReservas = new TotalReservas();

        List<ReservaAula> reservaAulas = allReservasService.listaReservaAula();
        List<ReservaCarritoPcs> reservaCarritoPcs = allReservasService.listaReservaCarritoPc();
        List<ReservaCarritoTablets> reservaCarritoTablets = allReservasService.listaReservaCarritoTablets();

        totalReservas.setReservasAulas(reservaAulas);
        totalReservas.setReservasCarritoPcs(reservaCarritoPcs);
        totalReservas.setReservasCarritoTablets(reservaCarritoTablets);

        return new ResponseEntity<TotalReservas>(totalReservas, HttpStatus.OK);
    }

}
