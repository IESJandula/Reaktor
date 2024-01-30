package es.monitoringserver.monitoringclient.windows;

import es.monitoringserver.models.Configuration;
import es.monitoringserver.models.Motherboard;
import es.monitoringserver.monitoringclient.utils.CommandExecutor;
import es.monitoringserver.monitoringclient.utils.Constants;
import es.monitoringserver.monitoringclient.utils.HttpCommunicationSender;
import es.monitoringserver.monitoringclient.utils.ReadFiles;
import es.monitoringserver.monitoringclient.utils.exceptions.ConstantsErrors;
import es.monitoringserver.monitoringclient.utils.exceptions.ReaktorClientException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.platform.windows.WindowsHardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.util.Date;
import java.util.HashSet;

/**
 * - Class -
 * This class is used to get the hardware information of the computer in Windows OS
 */
@Service
@Slf4j
public final class WindowsMotherboard
{

    /**
     * - Attribute -
     * this class is used to read the files of the application
     */
    @Autowired
    private ReadFiles readFiles;

    /**
     * - Attribute -
     * this class is used to execute the commands of the application
     */
    @Autowired
    private CommandExecutor commandExecutor;

    /**
     * - Attribute -
     * this class is used to send the information to the server
     */
    @Autowired
    private HttpCommunicationSender httpCommunicationSender;

    /**
     * - Attribute -
     * this Attribute is used to get the URL of the server
     */
    @Value("${reaktor.server.url}")
    private String reaktorServerUrl;

    /**
     * - Attribute -
     * this class is used to get the hardware information of the computer
     */
    private final WindowsHardwareAbstractionLayer hardwareAbstractionLayer;

    /**
     * - Attribute -
     * this class is used to get information of the Operating System
     */
    private final OperatingSystem operatingSystem;

    /**
     * - Attribute -
     * this class is used to get information of the configuration introduced by the user
     */
    private Configuration configuration;


    /**
     * - Constructor -
     * Constructor by default, is used to initialize the hardwareAbstractionLayer attribute
     */
    public WindowsMotherboard()
    {
        this.hardwareAbstractionLayer   = new WindowsHardwareAbstractionLayer();
        this.operatingSystem            = new SystemInfo().getOperatingSystem();
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
     * This method is used to get the information of the motherboard
     * @return Motherboard with the information
     */
    public Motherboard getMotherboard()
    {
        Motherboard motherboard = new Motherboard();

        motherboard.setMotherBoardSerialNumber(this.getHardwareUUID());
        motherboard.setModel(this.getModel());
        motherboard.setLastConnection(this.getLastConnection());
        motherboard.setLastUpdateComputerOn(this.getLastUpdateComputerOn(motherboard));
        motherboard.setComputerOn(true);
        motherboard.setMalware(new HashSet<>());
        this.setLocationAndProfessorName(motherboard);

        return motherboard;
    }

    /**
     * - Method -
     * This method is used to set the location and the teacher name
     */
    private void setLocationAndProfessorName(Motherboard motherboard)
    {
        motherboard.setTeacher(this.configuration.getTeacher());
        motherboard.setComputerSerialNumber(this.configuration.getComputerSerialNumber());
        motherboard.setClassroom(this.configuration.getClassroom());
        motherboard.setTrolley(this.configuration.getTrolley());
        motherboard.setIsAdmin(this.configuration.getIsAdmin());
        motherboard.setAndaluciaId(this.configuration.getAndaluciaId());
        motherboard.setComputerNumber(this.configuration.getComputerNumber());
    }

    /**
     * - Method -
     * This method is used to get the last update of the computer
     * @param motherboard Motherboard with the information of the computer
     * @return Date with the last update of the computer
     */
    private Date getLastUpdateComputerOn(Motherboard motherboard)
    {
        try
        {
            this.httpCommunicationSender.sendPost(this.httpCommunicationSender.createHttpPostWithHeader(this.reaktorServerUrl + "/computer-on", "motherBoardSerialNumber", motherboard.getMotherBoardSerialNumber()));
        }
        catch (ReaktorClientException reaktorClientException)
        {
            log.warn(reaktorClientException.getMessage());
            log.warn(ConstantsErrors.ERROR_COMMUNICATION_TO_SERVER, reaktorClientException);
            reaktorClientException.printStackTrace();
        }
        return new Date();
    }

    /**
     * - Method -
     * This method is used to get the UUID of the computer
     * @return String with the UUID of the computer
     */
    public String getHardwareUUID()
    {
        return this.hardwareAbstractionLayer.getComputerSystem().getHardwareUUID();
    }

    /**
     * - Method -
     * This method is used to get the Last connection
     * @return Date with the Last connection
     */
    public Date getLastConnection()
    {
        long lastBootTime = operatingSystem.getSystemUptime();
        long lastConnectionTime = System.currentTimeMillis() - lastBootTime;
        return new Date(lastConnectionTime);

    }

    /**
     * - Method -
     * This method is used to get the motherboard model
     * @return String with the motherboard model of the computer
     */
    public String getModel()
    {
        String model;
        try
        {
            model = this.commandExecutor.executeCommand(CommandConstantsWindows.GET_MODEL_MOTHERBOARD_WINDOWS).replace(Constants.OUTPUT_MODEL_CONSOLE, Constants.EMPTY_STRING).trim();
        }
        catch (ReaktorClientException reaktorClientException)
        {
            model = Constants.UNKNOWN;
            log.warn(ConstantsErrors.ERROR_GETTING_HARDWARE_MODEL_MOTHERBOARD, reaktorClientException);
            reaktorClientException.printStackTrace();
        }

        return model;
    }

}
