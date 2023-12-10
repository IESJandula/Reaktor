package es.reaktor.horarios.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HorariosRest {

    @PostMapping("/procesar-archivo")
    public List<String> procesarArchivoCSV(@RequestParam("archivo.csv") MultipartFile archivo) {
        List<String> roles = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(archivo.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> rolesObjeto = parsearLineaCSV(line);
                roles.addAll(rolesObjeto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return roles;
    }

    private List<String> parsearLineaCSV(String linea) {
        List<String> roles = new ArrayList<>();

        try {
            String[] campos = linea.split(",");
            if (campos.length >= 4) {
                List<String> rolesObjeto = List.of(campos[3].trim().split(";"));
                roles.addAll(rolesObjeto);
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Datos de CSV incompletos o incorrectos: " + linea);
        }

        return roles;
    }
}
