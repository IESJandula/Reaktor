package es.reaktor.horarios.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/horarios")
@Slf4j
public class HorariosRest {

    @RequestMapping(method = RequestMethod.POST, value = "/send/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<String> sendCsvTo(@RequestPart MultipartFile archivo) {
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
