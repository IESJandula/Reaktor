package es.reaktor.reaktorclient.utils;

import es.reaktor.reaktorclient.utils.exceptions.ReaktorClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

@Service
@Slf4j
public class BeepSound extends Thread
{

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
            String command = Paths.get("ReaktorClient/src/main/resources/Debug/net6.0/BeepReaktor.exe").toFile().getAbsolutePath();
            this.commandExecutor.executeCommand(command);
        }
        catch (ReaktorClientException reaktorClientException)
        {
            log.warn("Error this comand doesn`t work", reaktorClientException);
        }
    }
}
