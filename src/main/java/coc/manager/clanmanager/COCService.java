package coc.manager.clanmanager;

import coc.manager.clanmanager.model.UpdateSchedule;
import coc.manager.clanmanager.model.WarLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class COCService {
    private static final Logger LOG = LoggerFactory.getLogger(COCService.class);
    @Autowired
    WarLogRepository warLogRepository;
    @Autowired
    UpdateScheduleRepository updateScheduleRepository;

    public COCService() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
    }

    public boolean getClanReqIntoDB(String token, String clanToken){
        WarLog response = getCurrentWarInfo(token, clanToken);
        //save to DB!
        saveToDB(response,clanToken);
        return false;
    }

    private void saveToDB(WarLog response, String clanToken) {
        warLogRepository.save(response);
    }

    private WarLog getCurrentWarInfo(String token, String clanToken) {
        RestTemplateBuilder r=new RestTemplateBuilder();
        RestTemplate rt = r.build();
        String encodedTag;
        try {
            encodedTag=URLEncoder.encode(clanToken, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        URI theUrl = URI.create("https://api.clashofclans.com/v1/clans/"+ encodedTag +"/currentwar");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.add("Authorization", "Bearer " + token);
        ResponseEntity<WarLog> responseEntity = rt.exchange(theUrl, HttpMethod.GET, new HttpEntity<String>(headers), WarLog.class);
        LOG.debug("response: {}",responseEntity.getBody());
        return responseEntity.getBody();
    }

    @Scheduled(fixedDelay = 10*60*1000)
    public void udpateIfNeeded(){
        List<UpdateSchedule> allActive = updateScheduleRepository.findAllActive();
        //fetch the warlog for the clan
        allActive.forEach(s->{
            LOG.debug("found active schedule, will fetch warlog {}",s);
            String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6ImU4ODVhNzVlLTM5MjQtNDA0YS1iMGZkLWRmZTBhNThmZGIxYiIsImlhdCI6MTYxOTYyNzU0OSwic3ViIjoiZGV2ZWxvcGVyLzIzZmE4ZjBhLTdlMTktODNkYS0zMGM1LTM0Yjg4MmU0MDcxYSIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjEyNC42Ni4xNjkuMjE0Il0sInR5cGUiOiJjbGllbnQifV19.2OzrqGnAQhUOxJ1B0m16hjinRphFCV2HaPP5K52VQd9FIz4_4QGge0KcnwRGkYOQUuG88M4P3N__s_mXz3dcJg";
            WarLog response = getCurrentWarInfo(token, s.getClanTag());
            saveToDB(response,s.getClanTag());
            UpdateSchedule u = new UpdateSchedule();
            u.setClanTag(s.getClanTag());
            u.setStatus("P");
            switch(response.getState()){
                case "preparation":
                    u.setNextLogUpdate(response.getStartTime().plusHours(12));
                    break;
                case "warEnded":
                case "notInWar":
                default:
                    u.setNextLogUpdate(LocalDateTime.now().plusHours(12));
                    break;
                case "inWar":
                    if(LocalDateTime.now().isAfter(response.getStartTime().plusHours(12))) {
                        u.setNextLogUpdate(response.getEndTime());
                    }else{
                        u.setNextLogUpdate(response.getStartTime().plusHours(12));
                    }
                    break;
            }
            if(u.getNextLogUpdate() != null){
                updateScheduleRepository.save(u);
                s.setStatus("DONE");
                updateScheduleRepository.save(s);
            }
        });
    }

    public List<WarLog> getWarLogs() {
        return warLogRepository.findAll();
    }

    public List<UpdateSchedule> getSchedules() {
        return updateScheduleRepository.findAll();
    }

    public List<UpdateSchedule> getManualRefresh() {
        udpateIfNeeded();
        return getSchedules();
    }
}
