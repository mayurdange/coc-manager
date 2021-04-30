package coc.manager.clanmanager.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class UpdateSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long schid;
    private String clanTag;
    private LocalDateTime nextLogUpdate;
    private String status;
}
