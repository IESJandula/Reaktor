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
	
	private String fileName;
	
	private byte[] file;

	public Action(String actionName, String info) {
		super();
		this.actionName = actionName;
		this.info = info;
	}
	
	
}
