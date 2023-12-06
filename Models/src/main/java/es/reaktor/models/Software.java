package es.reaktor.models;

import lombok.Data;

@Data
public class Software
{
    /**
     
Attribute*/
  private String application;

    public Software(String application) 
    {
        super();
        this.application = application;
    }

}