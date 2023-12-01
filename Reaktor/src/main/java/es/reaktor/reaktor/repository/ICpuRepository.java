package es.reaktor.reaktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.reaktor.models.Cpu;
import es.reaktor.models.Id.CpuId;

public interface ICpuRepository extends JpaRepository<Cpu, CpuId>
{
    Cpu findCpuById_Motherboard_MotherBoardSerialNumber(String motherBoardSerialNumber);
}
