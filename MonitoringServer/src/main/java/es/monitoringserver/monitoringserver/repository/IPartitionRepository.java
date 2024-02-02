package es.monitoringserver.monitoringserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.monitoringserver.models.Partition;
import es.monitoringserver.models.Id.PartitionId;

public interface IPartitionRepository extends JpaRepository<Partition, PartitionId>
{
}
