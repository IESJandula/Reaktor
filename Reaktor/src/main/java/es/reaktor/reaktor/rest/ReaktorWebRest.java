package es.reaktor.reaktor.rest;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.reaktor.models.Computer;
import es.reaktor.models.HardwareComponent;

@RestController
@CrossOrigin(origins = "*")
public class ReaktorWebRest {

    @RequestMapping(method = RequestMethod.POST, value = "/computers/web", consumes = "application/json")
    public ResponseEntity<?> getComputersByAny(
             @RequestHeader(required = false) String serialNumber,
             @RequestHeader(required = false) String andaluciaId,
             @RequestHeader(required = false) String computerNumber,
             @RequestHeader(required = false) String classroom,
             @RequestHeader(required = false) String trolley,
             @RequestHeader(required = false) int plant,
             @RequestHeader(required = false) String teacher,
             @RequestBody(required = false) List<HardwareComponent> hardwareList             
             ) 
    {
        try {        	
            List<Computer> computers = buscarOrdenador(serialNumber, andaluciaId, computerNumber, classroom, trolley, plant, hardwareList);
            return ResponseEntity.ok(computers);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    private List<Computer> buscarOrdenador(String serialNumber, String andaluciaId, String computerNumber, String classroom, String trolley, int plant, List<HardwareComponent> hardwareList) {
        
    	
        List<Computer> computers = new ArrayList<>();

        List<Computer> filtrarOrdenadores = new ArrayList<>();

        // Filtrar los ordenadores basados en los parámetros proporcionados
        for (Computer computer : computers) {
            if ((serialNumber == null || computer.getSerialNumber().equals(serialNumber)) &&
                (andaluciaId == null || computer.getAndaluciaId().equals(andaluciaId)) &&
                (computerNumber == null || computer.getComputerNumber().equals(computerNumber)) &&
                (classroom == null || computer.getLocation().getClassroom().equals(classroom)) &&
                (trolley == null || computer.getLocation().getTrolley().equals(trolley)) &&
                (plant == 0 || computer.getLocation().getPlant() == plant) &&
                (hardwareList == null || computer.getHardwareList().equals(hardwareList))) {

                filtrarOrdenadores.add(computer);
            }
        }

        return filtrarOrdenadores;
    }

    // Descargue todas las capturas de pantalla en un archivo zip por aula, carrito, planta, profesor
    // Este método aún no está implementado, puedes desarrollarlo aquí o en otro lugar del controlador.
}



