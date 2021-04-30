package coc.manager.clanmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class WarLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long warLogid;
    private String state;
    private int teamSize;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyyMMdd'T'HHmmss.SSS'Z'")//20210428T143555.000Z
    private LocalDateTime preparationStartTime;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyyMMdd'T'HHmmss.SSS'Z'")//20210428T143555.000Z
    private LocalDateTime startTime;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyyMMdd'T'HHmmss.SSS'Z'")//20210428T143555.000Z
    private LocalDateTime endTime;
    @OneToOne(cascade = {CascadeType.ALL})
    private Clan clan;
    @OneToOne(cascade = {CascadeType.ALL})
    private Clan opponent;
}
