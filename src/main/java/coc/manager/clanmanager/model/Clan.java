package coc.manager.clanmanager.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Clan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long clanid;
    private String tag;
    private String name;
    @OneToOne(cascade = {CascadeType.ALL})
    private BadgeUrl badgeUrls;
    private int clanLevel;
    private int attacks;
    private int stars;
    private int destructionPercentage;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Members> members;
}
