package com.microservice.carritos.repository;

import es.reaktor.models.carritos.Id.ReservaCarritoTabletsId;
import es.reaktor.models.carritos.ReservaCarritoTablets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReservaCarritoTabletsRepository extends JpaRepository<ReservaCarritoTablets, ReservaCarritoTabletsId> {
}
