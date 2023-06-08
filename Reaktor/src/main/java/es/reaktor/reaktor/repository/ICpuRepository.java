package es.reaktor.reaktor.repository;

import es.reaktor.models.Cpu;
import es.reaktor.models.Id.CpuId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICpuRepository extends JpaRepository<Cpu, CpuId>
{
    Cpu findCpuById_Motherboard_SerialNumber(String serialNumber);
}
