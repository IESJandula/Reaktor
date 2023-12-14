package com.microservice.carritos.service;

import com.microservice.carritos.repository.IREservaCarritoPcsRepository;
import com.microservice.carritos.repository.IReservaAulaRepository;
import com.microservice.carritos.repository.IReservaCarritoTabletsRepository;
import es.reaktor.models.carritos.ReservaAula;
import es.reaktor.models.carritos.ReservaCarritoPcs;
import es.reaktor.models.carritos.ReservaCarritoTablets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Clase encargada de llamar a los repositorios de reservas y los busca en la base de datos
 */

@Service
@Transactional
public class AllReservasService
{

    @Autowired
    private IReservaAulaRepository iReservaAulaRepository;

    @Autowired
    private IREservaCarritoPcsRepository irReservaCarritoPcsRepository;

    @Autowired
    IReservaCarritoTabletsRepository iReservaCarritoTabletsRepository;

    public List<ReservaAula> listaReservaAula()
    {
        return iReservaAulaRepository.findAll();
    }

    public List<ReservaCarritoPcs> listaReservaCarritoPc()
    {
        return irReservaCarritoPcsRepository.findAll();
    }

    public List<ReservaCarritoTablets> listaReservaCarritoTablets(){return iReservaCarritoTabletsRepository.findAll();}

}
