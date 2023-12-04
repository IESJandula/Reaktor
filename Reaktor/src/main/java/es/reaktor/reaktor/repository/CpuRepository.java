package es.reaktor.reaktor.repository;

import es.reaktor.models.Cpu;
import es.reaktor.models.Id.CpuId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpuRepository extends JpaRepository<Cpu, CpuId>
{
    Cpu findCpuById_Motherboard_MotherBoardSerialNumber(String motherBoardSerialNumber);
}
