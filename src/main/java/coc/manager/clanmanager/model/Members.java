package coc.manager.clanmanager.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Members {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long memberid;
    private long clanid;
    private String tag;
    private String name;
    private int townhallLevel;
    private int mapPosition;
    private int opponentAttacks;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Attacks> attacks;
}
