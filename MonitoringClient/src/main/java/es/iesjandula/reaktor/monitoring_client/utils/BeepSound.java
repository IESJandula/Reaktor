package es.iesjandula.reaktor.monitoring_client.utils;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BeepSound extends Thread
{
	/** SO - Path String Prefix */
	private static final String SO_PATH_STRING_PREFIX  = "file://" ;
	
	/** Beep - Path - 01 */
	private static final String BEEP_PATH_1 = "src" + File.separator 	 + "main"   + File.separator + "resources" + File.separator + 
											"Debug" + File.separator + "net6.0" + File.separator + "BeepReaktor.exe" ;
	
	/** Beep - Path - 02 */
	private static final String BEEP_PATH_2 = "Debug/net6.0/BeepReaktor.exe" ;

    /** Beep - Path - 03 */
    private static final String BEEP_PATH_3 = "Debug" + File.separator + "net6.0" + File.separator + "BeepReaktor.exe" ;

	
	
    private final CommandExecutor commandExecutor;

    public BeepSound(CommandExecutor commandExecutor)
    {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void run()
    {
        try
        {
            String command = "C:" + File.separator + BEEP_PATH_3;
            this.commandExecutor.executeCommand(command);
        }
        catch (Exception reaktorClientException)
        {
            log.warn("Error this comand doesn`t work", reaktorClientException);
        }
    }
    
    /**
     * Load file from wherever place
     * 
     * @return the file
     * @throws Exception 
     */
    private File getFile() throws Exception
    {
        Path path = null;

        if (BEEP_PATH_1.toLowerCase().startsWith(BeepSound.SO_PATH_STRING_PREFIX))
        {
            path = Paths.get(URI.create(BEEP_PATH_1));
        }
        else
        {
            path = Paths.get(BEEP_PATH_1, new String[0]);
        }

        File file = null;

        if (Files.exists(path, new LinkOption[0]))
        {
        	file = path.toFile() ;
        }
        else
        {
        	file = this.searchFile();
        }

        return file;
    }
    
	/**
	 * Search file
	 * 
	 * @return the file
	 * @throws Exception with an occurred exception
	 */
	private File searchFile() throws Exception
	{
		URL urlResource =  Thread.currentThread().getContextClassLoader().getResource(BEEP_PATH_2);
		
        if (urlResource == null)
        {
        	throw new Exception("Beep file not found") ;
        }
        
        return new File(urlResource.getFile());
	}
}
