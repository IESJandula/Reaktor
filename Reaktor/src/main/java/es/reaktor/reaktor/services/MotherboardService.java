package es.reaktor.reaktor.services;

import es.reaktor.reaktor.repository.MotherboardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MotherboardService
{

    private final MotherboardRepository motherboardRepository;


    public List<String> getAllMotherboardIds() {

        return motherboardRepository.getAllMotherboardIds();

    }


}
