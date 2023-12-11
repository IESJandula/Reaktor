package es.reaktor.reaktorclient.scheduled_task;

import es.reaktor.reaktorclient.utils.HttpCommunicationSender;
import es.reaktor.reaktorclient.utils.exceptions.ConstantsErrors;
import es.reaktor.reaktorclient.utils.exceptions.ReaktorClientException;
import es.reaktor.reaktorclient.windows.WindowsMotherboard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PendingActions
{
    @Autowired
    private HttpCommunicationSender httpCommunicationSender;

    @Value("${reaktor.server.url}")
    private String reaktorServerUrl;

    @Scheduled(fixedDelayString = "${reaktor.computerOnReport}", initialDelay = 2000)
    public void computerOnReport()
    {
        try
        {
            
        }
        catch (ReaktorClientException reaktorClientException)
        {
            log.warn(reaktorClientException.getMessage());
            log.warn(ConstantsErrors.ERROR_COMMUNICATION_TO_SERVER, reaktorClientException);
            reaktorClientException.printStackTrace();
        }
    }
}