package es.reaktor.reaktor.repository;

import es.reaktor.models.Id.RamId;
import es.reaktor.models.Ram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RamRepository extends JpaRepository <Ram, RamId>
{
}
