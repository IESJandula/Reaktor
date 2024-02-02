package es.monitoringserver.monitoringserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.monitoringserver.models.Ram;
import es.monitoringserver.models.Id.RamId;

public interface IRamRepository extends JpaRepository <Ram, RamId>
{
}
