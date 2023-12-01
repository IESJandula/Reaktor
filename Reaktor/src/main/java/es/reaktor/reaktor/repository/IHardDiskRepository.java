package es.reaktor.reaktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.reaktor.models.HardDisk;
import es.reaktor.models.Id.HardDiskId;

public interface IHardDiskRepository extends JpaRepository<HardDisk, HardDiskId>
{
}
