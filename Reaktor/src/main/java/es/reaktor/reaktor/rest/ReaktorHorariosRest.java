package es.reaktor.reaktor.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.reaktor.models.Datos;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class ReaktorHorariosRest
{
	@RequestMapping(method = RequestMethod.DELETE, value = "/malware/{name}")
    public ResponseEntity<?> parseXML(
            @RequestBody MultipartFile xml)
    {
		if(!xml.isEmpty())
		{
			return ResponseEntity.ok(new Datos(null, null, null, null, null));
    	}
		return ResponseEntity.ok("esta vacio");
    }
}
