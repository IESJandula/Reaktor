package es.reaktor.reaktor.repository;

import es.reaktor.models.HardDisk;
import es.reaktor.models.Id.HardDiskId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HardDiskRepository extends JpaRepository<HardDisk, HardDiskId>
{
}
