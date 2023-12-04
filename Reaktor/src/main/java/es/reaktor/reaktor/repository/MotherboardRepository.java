package es.reaktor.reaktor.repository;

import es.reaktor.models.DTO.SimpleComputerDTO;
import es.reaktor.models.Motherboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MotherboardRepository extends JpaRepository<Motherboard, String>
{
    Optional<Motherboard> findByMotherBoardSerialNumber(String motherBoardSerialNumber);

    @Query("SELECT m.motherBoardSerialNumber FROM Motherboard m")
    List<String> getAllMotherboardIds();
}
