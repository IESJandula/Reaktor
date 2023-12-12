package com.microservice.carritos.repository;

import es.reaktor.models.carritos.Id.ReservaCarritoPcsId;
import es.reaktor.models.carritos.ReservaCarritoPcs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IREservaCarritoPcsRepository extends JpaRepository<ReservaCarritoPcs, ReservaCarritoPcsId> {
}
