package es.iesjandula.reaktor.monitoring_client.scheduled_task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.iesjandula.reaktor.monitoring_client.utils.HttpCommunicationSender;
import es.iesjandula.reaktor.monitoring_client.utils.exceptions.ConstantsErrors;
import es.iesjandula.reaktor.monitoring_client.utils.exceptions.ReaktorClientException;
import es.iesjandula.reaktor.monitoring_client.windows.WindowsMotherboard;

@Component
@Slf4j
public class ComputerOnReport
{

    @Autowired
    private HttpCommunicationSender httpCommunicationSender;

    @Autowired
    private WindowsMotherboard windowsMotherboard;

    @Value("${reaktor.server.url}")
    private String reaktorServerUrl;

    @Scheduled(fixedDelayString = "${reaktor.computerOnReport}", initialDelay = 2000)
    public void computerOnReport()
    {
        try
        {
            this.httpCommunicationSender.sendPost(this.httpCommunicationSender.createHttpPostWithHeader(this.reaktorServerUrl + "/computer-on", "motherBoardSerialNumber", this.windowsMotherboard.getHardwareUUID()));
            log.info("Computer on report sent");
        }
        catch (ReaktorClientException reaktorClientException)
        {
            log.warn(reaktorClientException.getMessage());
            log.warn(ConstantsErrors.ERROR_COMMUNICATION_TO_SERVER, reaktorClientException);
            reaktorClientException.printStackTrace();
        }
    }
}
