package coc.manager.clanmanager.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Attacks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long attackid;
    private String attackerTag;
    private String defenderTag;
    private int stars;
    private int destructionPercentage;
    @Column(name = "ordr")
    private int order;
}
