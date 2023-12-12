package com.microservice.carritos.repository;

import es.reaktor.models.carritos.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository of the Profesor
 */
public interface IProfesorRepository extends JpaRepository<Profesor, Long>
{

}
