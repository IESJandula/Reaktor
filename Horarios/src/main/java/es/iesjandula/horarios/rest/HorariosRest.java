package es.iesjandula.horarios.rest;

import java.io.File;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

/**
 * @author David Martinez
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/horarios")
@Slf4j
public class HorariosRest
{
	/**
	 * Method sendXmlToObjects
	 * @param xmlFile
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value = "/send/xml",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> sendXmlToObjects(
			@RequestPart MultipartFile xmlFile)
	{
		try {
			File xml = new File(xmlFile.getOriginalFilename());
			
			
		if(xml.getName().endsWith(".xml")) 
		{
			// ES UN XML
			return ResponseEntity.ok("OK");
		}
		else 
		{
			// NO ES UN XML
			return ResponseEntity.status(400).body("error 400");
		}
		
		
		}catch(Exception exception) 
		{
			return ResponseEntity.status(500).body("error 500");
		}
		
	}
}
