package es.reaktor.reaktor.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "reaktor")
public class AppConfig
{

    private Integer cronComputerOn;

    private Integer cronComputerOnDifference;

}
