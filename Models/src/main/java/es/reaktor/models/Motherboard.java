package es.reaktor.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

/**
 * @author Neil Hdez
 *
 * Class - Motherboard of computer
 */
@Entity
@Table(name = "motherboard")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class Motherboard
{
    /**
     * Attribute - serial number ID
     */
    @Id
    @Column(name = "serial_number")
    private String serialNumber;

    /**
     * Attribute - model of motherboard
     */
    @Column(nullable = false)
    private String model;

    /**
     * Attribute - classroom of motherboard
     */
    @Column(nullable = false)
    private String classroom;

    /**
     * Attribute - trolley of motherboard
     */
    @Column(nullable = false)
    private String trolley;
    
    /** Attribute andaluciaId*/
    @Column(nullable = false)
    private String andaluciaId;
    
    /** Attribute computerNumber*/
    @Column(nullable = false)
    private String computerNumber;

    /**
     * Attribute - teacher of motherboard
     */
    @Column(nullable = false)
    private String teacher;

    /**
     * Attribute - last connection of motherboard
     */
    @Column(nullable = false)
    private Date lastConnection;

    /**
     * Attribute - last update computer on
     */
    @Column(nullable = false)
    private Date lastUpdateComputerOn;

    /**
     * Attribute - computer on
     */
    @Column(nullable = false)
    private Boolean computerOn;

    /**
     * Attribute - is admin
     */
    @Column(nullable = false)
    private Boolean isAdmin;

    /**
     * Attribute - Malware
     */
    @OneToMany(mappedBy = "serialNumber", cascade = CascadeType.ALL)
    private Set<MotherboardMalware> malware;

}
