package es.reaktor.reaktor.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.reaktor.models.ComputerError;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class ReaktorWebRest 
{
	/**Author Manuel Martín**/
    @RequestMapping(method = RequestMethod.GET, value = "/computers/web/screenshot", produces ="application/zip")
    public ResponseEntity<?> getComputersScreens(
             @RequestHeader (required = false) String classroom,
             @RequestHeader (required = false) String trolley,
             @RequestHeader (required = false) int plant,
             @RequestHeader (required = false) String professor
             )
    {
        try 
        {
            controlScreenShot(classroom, trolley);
            return ResponseEntity.ok().build();
        }
        catch (ComputerError computerError) 
        {
            return ResponseEntity.status(400).body(computerError.getBodyExceptionMessage()) ;
        }
        catch (Exception e )
        {

            return ResponseEntity.status(500).body(e.getMessage()) ;
        } 
    }
    
    /**Author Manuel Martín**/
    @RequestMapping(method = RequestMethod.POST, value = "/computers/web")
    public ResponseEntity<?> getComputersByAny(
    		 @RequestHeader (required = false) String serialNumber,
    		 @RequestHeader (required = false) String andaluciaId,
    		 @RequestHeader (required = false) String computerNumber,
             @RequestHeader (required = false) String classroom,
             @RequestHeader (required = false) String trolley,
             @RequestHeader (required = false) int plant,
             @RequestHeader (required = false) String professor,
             @RequestHeader (required = false) Hardware 
             )
    {
        try 
        {
            controlScreenShot(classroom, trolley);
            return ResponseEntity.ok().build();
        }
        catch (ComputerError computerError) 
        {
            return ResponseEntity.status(400).body(computerError.getBodyExceptionMessage()) ;
        }
        catch (Exception e )
        {

            return ResponseEntity.status(500).body(e.getMessage()) ;
        } 
    }
    
    public void controlScreenShot(String classroom, String trolley) throws ComputerError
    {
        if(classroom.isEmpty() && trolley.isEmpty()) 
        {

            throw new ComputerError(1, "Nose ha enviado ninguna clase o carrito");
        }
    }

}
