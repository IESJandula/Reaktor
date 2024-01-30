package es.reaktor.reaktor.repository;

import es.reaktor.models.Motherboard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMotherboardRepository extends JpaRepository<Motherboard, String>
{
    Motherboard findByMotherBoardSerialNumber(String motherBoardSerialNumber);


}
