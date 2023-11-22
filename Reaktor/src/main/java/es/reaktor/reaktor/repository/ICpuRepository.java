package es.reaktor.reaktor.repository;

import es.reaktor.models.Cpu;
import es.reaktor.models.Id.CpuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

public interface ICpuRepository extends JpaRepository<Cpu, CpuId>
{
    Cpu findCpuById_Motherboard_MotherBoardSerialNumber(String motherBoardSerialNumber);
}
