package es.monitoringserver.monitoringclient.utils;

import org.springframework.stereotype.Component;
import oshi.software.os.OSProcess;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Neil Hdez
 * @version 1.0.0
 * @since 24/03/2023
 */
@Component
public class ListUtils
{

    public <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public List<OSProcess> removeDuplicates(List<OSProcess> processes)
    {
        processes = processes.stream()
                .filter(distinctByKey(OSProcess::getName))
                .collect(Collectors.toList());
        return processes;
    }
}
