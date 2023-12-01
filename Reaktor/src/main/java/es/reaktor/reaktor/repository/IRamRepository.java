package es.reaktor.reaktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.reaktor.models.Ram;
import es.reaktor.models.Id.RamId;

public interface IRamRepository extends JpaRepository <Ram, RamId>
{
}
