package coc.manager.clanmanager.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class BadgeUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long badgeid;
    private String small;
    private String medium;
    private String large;
}
