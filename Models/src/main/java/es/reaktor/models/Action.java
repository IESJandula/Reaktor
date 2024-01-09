package es.reaktor.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action
{

	private String actionName;
	
	private String info;
	
	private String ConfiguracionWifi;
	
}