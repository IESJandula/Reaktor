package es.monitoringserver.monitoringserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.monitoringserver.models.HardDisk;
import es.monitoringserver.models.Id.HardDiskId;

public interface IHardDiskRepository extends JpaRepository<HardDisk, HardDiskId>
{
}
