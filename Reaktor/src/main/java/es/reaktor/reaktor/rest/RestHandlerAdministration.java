package es.reaktor.reaktor.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.reaktor.reaktor.exceptions.ComputerError;
import es.reaktor.reaktor.models.Peripheral;


@RequestMapping(value = "/computers/admin/peripheral", produces = {"application/json"})
@RestController
public class RestHandlerAdministration
{
	public RestHandlerAdministration(){}
	
	@RequestMapping(method = RequestMethod.POST, value = "/by_body/", consumes = {"application/json"})
	public ResponseEntity<?> addByBody(
			@RequestHeader(value="classroom", required = false) final String classroom,
			@RequestHeader(value="trolley", required = false) final String trolley,
			@RequestBody(required=true) Peripheral[] peripherals)
	//TUDU...
	{
		try
		{	
			this.checkParams(classroom, trolley, peripherals);
			return ResponseEntity.ok().body("All OK");
		}
		catch (ComputerError ce)
		{
			return ResponseEntity.status(400).body(ce.getBodyExceptionMessage()) ;
		}
		catch(Exception e) 
		{
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	
	private void checkParams(String classroom,String trolley, Peripheral peripheral) throws ComputerError
    {
        if(classroom.isEmpty() && trolley.isEmpty())
        {
            throw new ComputerError(2, "All params can't be null");
        }
        else if(peripheral==null) 
        {
        	throw new ComputerError(5, "The Pheriferal can't be null");
        }
    }
	
}
