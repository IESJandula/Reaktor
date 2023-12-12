package com.microservice.carritos.repository;

import es.reaktor.models.carritos.CarritoTablets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICarritoTabletsRepository extends JpaRepository<CarritoTablets, Long>
{

}
