package es.reaktor.reaktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.reaktor.models.Partition;
import es.reaktor.models.Id.PartitionId;

public interface IPartitionRepository extends JpaRepository<Partition, PartitionId>
{
}
