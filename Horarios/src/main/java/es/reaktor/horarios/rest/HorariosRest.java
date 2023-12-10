package es.reaktor.horarios.rest;

import es.reaktor.horarios.models.ObjetoCsv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@RestController
public class HorariosRest {

    @PostMapping("/procesar-archivo")
    public List<ObjetoCsv> procesarArchivoCSV(@RequestParam("archivo.csv") MultipartFile archivo) {
        List<ObjetoCsv> objetosCsv = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(archivo.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                ObjetoCsv objeto = parsearLineaCSV(line);
                if (objeto != null) {
                    objetosCsv.add(objeto);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return objetosCsv;
    }

    private ObjetoCsv parsearLineaCSV(String linea) {
        String[] campos = linea.split(",");
        ObjetoCsv objeto = null;

        try {
            if (campos.length >= 4) {
                String nombre = campos[0].trim();
                String apellidos = campos[1].trim();
                String correo = campos[2].trim();

                List<String> roles = List.of(campos[3].trim().split(";"));

                objeto = new ObjetoCsv(nombre, apellidos, correo, roles);
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            String error = "Datos de CSV incompletos o incorrectos: " + linea;
            System.out.println(error);
        }

        return objeto;
    }
}
