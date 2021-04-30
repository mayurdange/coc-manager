package coc.manager.clanmanager;

import coc.manager.clanmanager.model.UpdateSchedule;
import coc.manager.clanmanager.model.WarLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api")
public class StrikeController {

    @Autowired
    COCService cocService;
//
//	@RequestMapping("/load")
//	public boolean fetchWarDetailIntoDB(@RequestParam String token,@RequestParam String clanToken){
//		return cocService.getClanReqIntoDB(token,"#"+clanToken);
//	}

    @RequestMapping("/warlogs")
    public List<WarLog> fetchWarDetailIntoDB() {
        return cocService.getWarLogs();
    }

    @RequestMapping("/schedules")
    public List<UpdateSchedule> fetchschedules() {
        return cocService.getSchedules();
    }

    @RequestMapping("/manualRefresh")
    public List<UpdateSchedule> triggerManualRefresh() {
        return cocService.getManualRefresh();
    }
}
