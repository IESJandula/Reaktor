package es.reaktor.reaktor.rest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import es.reaktor.models.Location;

@RestController
@CrossOrigin(origins = "*")
public class ReaktorWebRest {

	
	// Get any computer by serialNumber, andaluciaId, computerNumber, clasroom , trolley , plant , professor, any type of HardwareComponent or empty to get all computers
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

    // Download all screenshots on a zip file by classroom, trolley, plant, professor    
    
    @RequestMapping(method = RequestMethod.POST, value = "/computers/web", consumes = "application/json")
    public ResponseEntity<?> getComputersScreens(
             @RequestHeader(required = false) String classroom,
             @RequestHeader(required = false) String trolley,
             @RequestHeader(required = false) String plant,
             @RequestHeader(required = false) String profesor
             ) 
    {
    	try {
            // Lógica para obtener las capturas de pantalla y guardarlas en archivos

            
            if (classroom != null) {
                guardarCaptura(classroom, "captura_clase.png");
            }
            if (trolley != null) {
                guardarCaptura(trolley, "captura_carrito.png");
            }
            if (plant != null) {
                guardarCaptura(plant, "captura_planta.png");
            }
            if (profesor != null) {
                guardarCaptura(profesor, "captura_profesor.png");
            }

            return ResponseEntity.ok("Descarga de capturas completada.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    // Método para guardar capturas en archivos
    private void guardarCaptura(String classroom, String nombreArchivo) throws IOException {
        
        String contenido = "Datos de la captura en " + classroom.toString(); 

        // Ruta donde se guardarán los archivos
        String ruta = "ruta/donde/guardar/";

        File archivo = new File(ruta + nombreArchivo);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            writer.write(contenido);
        }
    }
    
    
    
    
}



