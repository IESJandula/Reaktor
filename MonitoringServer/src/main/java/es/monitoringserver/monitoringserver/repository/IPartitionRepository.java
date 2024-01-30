package es.reaktor.reaktor.repository;

import es.reaktor.models.Id.PartitionId;
import es.reaktor.models.Partition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPartitionRepository extends JpaRepository<Partition, PartitionId>
{
}
