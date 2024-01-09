package es.reaktor.reaktor.rest;
import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import es.reaktor.models.CommandLine;
import es.reaktor.models.Computer;
import es.reaktor.models.ComputerError;
import es.reaktor.models.Peripheral;
import es.reaktor.models.Software;
import lombok.extern.slf4j.Slf4j;
@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class ReaktorAdministrationRest 
{
	/**Author Manuel Martín**/
	@RequestMapping(method = RequestMethod.POST, value = "/computers/admin/commandLine", consumes = "application/json")
    public ResponseEntity<?> postComputerCommandLine(
             @RequestHeader(required = false) String serialNumber,
             @RequestHeader(required = false) String classroom,
             @RequestHeader(required = false) String trolley,
             @RequestHeader(required = false) int plant,
             @RequestBody(required = true)     ArrayList<CommandLine> commandLineInstance
             )
    {
        try 
        {
            controlCommandLine(serialNumber,classroom, trolley, plant, commandLineInstance);
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

    public void controlCommandLine(String serialNumber, String classroom,String trolley, int plant,    ArrayList<CommandLine> commandLineInstance) throws ComputerError
    {
        if(commandLineInstance == null || commandLineInstance.isEmpty()) 
        {

            throw new ComputerError(1, "Nose ha enviado ninguna clase o carrito");
        }
    }
    
    
    /**Author Manuel Martín**/
    @RequestMapping(method = RequestMethod.POST, value = "/computers/admin/shutdown", consumes = "application/json")
    public ResponseEntity<?>  putComputerShutdown(
             @RequestHeader(required = false) String serialNumber,
             @RequestHeader(required = false) String classroom,
             @RequestHeader(required = false) String trolley,
             @RequestHeader(required = false) int plant
             )
    {
        try 
        {
            controlShutdonw(serialNumber,classroom, trolley, plant);
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

    public void controlShutdonw (String serialNumber, String classroom,String trolley, int plant) throws ComputerError
    {
        if(serialNumber == null ||  classroom == null || trolley == null || plant < 0) 
        {

            throw new ComputerError(1, "Nose ha enviado ninguna clase o carrito");
        }
    }
    
    /**Author Manuel Martín**/
    @RequestMapping(method = RequestMethod.POST, value = "/computers/admin/restart", consumes = "application/json")
    public ResponseEntity<?>   putComputerRestart(
             @RequestHeader(required = false) String serialNumber,
             @RequestHeader(required = false) String classroom,
             @RequestHeader(required = false) String trolley,
             @RequestHeader(required = false) int plant
             )
    {
        try 
        {
            controlComputerRestart(serialNumber,classroom, trolley, plant);
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

    public void controlComputerRestart(String serialNumber, String classroom,String trolley, int plant) throws ComputerError
    {
        if(serialNumber == null || classroom == null || trolley == null || plant < 0) 
        {

            throw new ComputerError(1, "Nose ha enviado ninguna clase o carrito");
        }
    } 
    
    /**Author Manuel Belmonte**/
    @RequestMapping(method = RequestMethod.POST, value = "/computers/admin/peripheral", consumes = {"application/json"})
    public ResponseEntity<?> peripheralInfo(
            @RequestHeader(required=false) String Classroom,
            @RequestHeader(required=false) String Trolley,
            @RequestBody Peripheral peripheralInstance[]
    )
    {
        try
        {
            testPeripheral(peripheralInstance);
        }
        catch (ComputerError computerError)
        {
            return ResponseEntity.status(400).body(computerError.getBodyExceptionMessage());

        }
        try 
        {
            //TO DO
        }
        catch(Exception e) 
        {
            return ResponseEntity.status(500).body(e.getMessage());
       }

        return ResponseEntity.ok().build();
    }

    public void testPeripheral(Peripheral peripheralInstance[])throws ComputerError
    {
        if(peripheralInstance == null) 
        {
            ComputerError computerError = new ComputerError(400,"No hay perifericos");

            throw computerError;

        }
    }
    /**Author Manuel Martín**/
    @RequestMapping(method = RequestMethod.POST, value = "/computer/admin/screenshot")
    public ResponseEntity<?> sendScreenshotOrder(
             @RequestHeader (required = false) String classroom,
             @RequestHeader (required = false) String trolley
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
    @RequestMapping(method = RequestMethod.GET, value = "/computer/admin/screenshot", produces ="application/zip")
    public ResponseEntity<?> sendScreenshotOrderGet(
             @RequestHeader (required = false) String classroom,
             @RequestHeader (required = false) String trolley
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
    
    /**Author Manuel Martín**/
    @RequestMapping(method = RequestMethod.POST, value = "/computers/admin/file", consumes= "multipart/form-data")
    public ResponseEntity<?> postComputerExecFile(
    		 @RequestHeader(required = false) String serialNumber,
             @RequestHeader (required = false) String classroom,
             @RequestHeader (required = false) String trolley,
             @RequestHeader(required = true) String execFile
             )
    {
        try 
        {
            controlFile(execFile);
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
    
    public void controlFile(String execFile) throws ComputerError
    {
        if(execFile.isEmpty()) 
        {

            throw new ComputerError(1, "Nose ha enviado ninguna clase o carrito");
        }
    }
    /**Author Manuel Belmonte**/
    
    @RequestMapping(method = RequestMethod.POST, value = "/computers/admin/software", consumes = {"application/json"})
    public ResponseEntity<?> sendSoftware(
            @RequestHeader(required=false) String Classroom,
            @RequestHeader(required=false) String Trolley,
            @RequestHeader(required=false) String Professor,
            @RequestBody Software softwareInstance []
    )
    {
        try
        {
            testSoftware(softwareInstance);
        }
        catch (ComputerError computerError)
        {
            return ResponseEntity.status(400).body(computerError.getBodyExceptionMessage());

        }
        try 
        {
            //TO DO
        }
        catch(Exception e) 
        {
            return ResponseEntity.status(500).body(e.getMessage());
       }

        return ResponseEntity.ok().build();
    }

    public void testSoftware(Software softwareInstance[])throws ComputerError
    {
        if(softwareInstance == null) 
        {
            ComputerError computerError = new ComputerError(400,"No hay ninguna aplicacion");

            throw computerError;

        }
    }
    
    /**Author Manuel Belmonte**/
    
    @RequestMapping(method = RequestMethod.DELETE, value = "/computers/admin/software", consumes = {"application/json"})
    public ResponseEntity<?> peripheralInfo(
            @RequestHeader(required=false) String Classroom,
            @RequestHeader(required=false) String Trolley,
            @RequestHeader(required=false) String Professor,
            @RequestBody Software softwareInstance []
    )
    {
        try
        {
            testSoftware(softwareInstance);
        }
        catch (ComputerError computerError)
        {
            return ResponseEntity.status(400).body(computerError.getBodyExceptionMessage());

        }
        try 
        {
            //TO DO
        }
        catch(Exception e) 
        {
            return ResponseEntity.status(500).body(e.getMessage());
       }
        return ResponseEntity.ok().build();
    }
    
    /**Author Manuel Martin**/
    
    @RequestMapping(method = RequestMethod.PUT, value = " /computer/edit", consumes = {"application/json"})
    public ResponseEntity<?> updateComputer(
            @RequestHeader(required=false) String serialNumber,
            @RequestHeader(required=false) String andaluciaId,
            @RequestHeader(required=false) String computerNumber,
            @RequestBody Computer computerInstance []
    )
    {
        try
        {
            testComputer(computerInstance);
        }
        catch (ComputerError computerError)
        {
            return ResponseEntity.status(400).body(computerError.getBodyExceptionMessage());

        }
        try 
        {
            //TO DO
        }
        catch(Exception e) 
        {
            return ResponseEntity.status(500).body(e.getMessage());
       }
        return ResponseEntity.ok().build();
    }
    
    public void testComputer(Computer computerInstance [])throws ComputerError
    {
        if( computerInstance== null) 
        {
            ComputerError computerError = new ComputerError(400,"No hay ninguna aplicacion");

            throw computerError;

        }
    }

   
    


}
