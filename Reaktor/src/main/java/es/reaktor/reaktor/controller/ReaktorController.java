package es.reaktor.reaktor.controller;

import es.reaktor.models.DTO.MalwareDTOWeb;
import es.reaktor.models.DTO.ReaktorDTO;
import es.reaktor.models.DTO.SimpleComputerDTO;
import es.reaktor.models.Malware;
import es.reaktor.models.Motherboard;
import es.reaktor.models.Reaktor;
import es.reaktor.reaktor.reaktor_actions.ReaktorActions;
import es.reaktor.reaktor.services.ReaktorService;
import es.reaktor.reaktor.repository.MalwareRepository;
import es.reaktor.reaktor.repository.MotherboardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class ReaktorController
{

    @Autowired
    private ReaktorActions reaktorActions;

    @Autowired
    private MotherboardRepository motherboardRepository;

    @Autowired
    private MalwareRepository malwareRepository;

    @Autowired
    private ReaktorService reaktorService;


    @RequestMapping(method = RequestMethod.POST, value = "/reaktor")
    public ResponseEntity<?> sendInformation(
            @RequestBody Reaktor reaktor
            )
    {
        log.info("Receiving information from reaktor {}", reaktor);
        this.reaktorActions.saveReaktor(reaktor);
        return ResponseEntity.ok("Reaktor Server is running");
    }

    /**
     * This Method is used to obtain the malware list
     * @return the malware list
     */
    @RequestMapping(method = RequestMethod.POST, value = "/computer-on")
    public ResponseEntity<?> computerOn(
            @RequestHeader String motherBoardSerialNumber
    )
    {
        // Comprobamos que existe la placa base en la base de datos
        if (!this.motherboardRepository.existsById(motherBoardSerialNumber))
        {
            return ResponseEntity.ok("Motherboard with serial number " + motherBoardSerialNumber + " does not exist");
        }

        // we get the motherboard
        Motherboard motherboard = this.motherboardRepository.findByMotherBoardSerialNumber(motherBoardSerialNumber).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Motherboard with serial number " + motherBoardSerialNumber + " does not exist")
        );

        // we set the computer on
        motherboard.setComputerOn(true);

        // we set the last update computer on
        motherboard.setLastUpdateComputerOn(new Date());

        // we save the motherboard with the new status
        this.motherboardRepository.save(motherboard);

        return ResponseEntity.ok("Computer with serial number " + motherBoardSerialNumber + " is on");
    }

    @GetMapping("/computer")
    public ResponseEntity<List<String>> getComputer() {
        return ResponseEntity.ok(this.reaktorService.getAllIds());
    }

    @GetMapping("/computer/{computerId}")
    public ResponseEntity<SimpleComputerDTO> getComputer(@PathVariable String computerId) {
        return ResponseEntity.ok(this.reaktorService.getSimpleComputerDTO(computerId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/reaktor/{idComputer}")
    public ResponseEntity<ReaktorDTO> getReaktor(@PathVariable String idComputer)
    {
        return ResponseEntity.ok(reaktorService.getInformationReaktor(idComputer));
    }
}
