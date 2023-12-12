package com.microservice.carritos.repository;

import es.reaktor.models.carritos.AulasInformatica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAulasInformaticaRepository extends JpaRepository<AulasInformatica, Long>
{
    //AulasInformatica findAulasInformaticaById(Long AulaInformaticaId);
}
