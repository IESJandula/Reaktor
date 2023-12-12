package com.microservice.carritos.repository;

import es.reaktor.models.carritos.Id.ReservaAulaId;
import es.reaktor.models.carritos.ReservaAula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReservaAulaRepository extends JpaRepository<ReservaAula, ReservaAulaId> {
}
