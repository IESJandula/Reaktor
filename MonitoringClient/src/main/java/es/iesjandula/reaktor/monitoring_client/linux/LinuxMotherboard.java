package es.iesjandula.reaktor.monitoring_client.linux;

import es.iesjandula.reaktor.models.Configuration;
import es.iesjandula.reaktor.monitoring_client.utils.CommandExecutor;
import es.iesjandula.reaktor.monitoring_client.utils.ReadFiles;
import es.iesjandula.reaktor.monitoring_client.utils.exceptions.ConstantsErrors;
import es.iesjandula.reaktor.monitoring_client.utils.exceptions.ReaktorClientException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public final class LinuxMotherboard
{

    @Autowired
    private ReadFiles readFiles;

    /**
     * - Attribute -
     * this attribute is used to get the name of the classroom
     */
    private String locationAula;

    /**
     * - Attribute -
     * this attribute is used to get the name of the professor
     */
    private String professorName;

    private Configuration configuration;


    @Autowired
    private CommandExecutor commandExecutor;

    public LinuxMotherboard()
    {
        this.locationAula = locationAula;
        this.professorName = professorName;
    }

    /**
     * - Method -
     * This method is used to initialize the configuration attribute
     */
    @PostConstruct
    public void init()
    {
        this.configuration = this.readFiles.readConfiguration();
    }

    /**
     * - Method -
     * This method is used to get the UUID of the computer in Linux
     * <br>
     * - ATTRIBUTE IN DATABASE -
     * serial_number: String with the UUID of the computer in Linux
     *
     * @return String with the UUID of the computer in Linux
     */
    public String getHardwareUUID()
    {
        String hardwareUUIDForLinux;

        try
        {
            hardwareUUIDForLinux = this.commandExecutor.executeCommand(CommandConstantsLinux.GET_UUID_FOR_LINUX);
        }
        catch (ReaktorClientException reaktorClientException)
        {
            hardwareUUIDForLinux = ConstantsErrors.ERROR_GETTING_HARDWARE_UUID_LINUX;
            log.warn(ConstantsErrors.ERROR_GETTING_HARDWARE_UUID_LINUX, reaktorClientException);
            reaktorClientException.printStackTrace();
        }

        return hardwareUUIDForLinux;
    }

    /**
     * - Method -
     * This method is used to get the location of the computer
     * @return String with the location of the computer
     */
    public String getLocation()
    {
        return this.configuration.getTeacher() + " : " + this.configuration.getClassroom();
    }

}
