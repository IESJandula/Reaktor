package es.reaktor.reaktor.scheduled_task;

import es.reaktor.models.Motherboard;
import es.reaktor.reaktor.repository.MotherboardRepository;
import es.reaktor.reaktor.utils.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * - Class -
 * This class is used to schedule the tasks of the application
 */
@Slf4j
@Component
public class ScheduledTask
{

    @Autowired
    private MotherboardRepository motherboardRepository;

    @Autowired
    private AppConfig appConfig;

    @Scheduled(fixedDelayString = "${reaktor.cronComputerOn}", initialDelay = 2000)
    public void checkComputerOn()
    {
        log.info("Checking if the computer is on");

        List<Motherboard> motherboards = this.motherboardRepository.findAll();

        for (Motherboard motherboard : motherboards)
        {
            if (motherboard.getComputerOn())
            {
                if (System.currentTimeMillis() - motherboard.getLastUpdateComputerOn().getTime() > this.appConfig.getCronComputerOnDifference())
                {
                    motherboard.setComputerOn(false);
                    this.motherboardRepository.save(motherboard);
                }
            }
        }
    }
}
