package coc.manager.clanmanager;

import coc.manager.clanmanager.model.UpdateSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UpdateScheduleRepository extends JpaRepository<UpdateSchedule, Long> {
    @Query("select a from UpdateSchedule a where a.nextLogUpdate<sysdate and status='P'")
    List<UpdateSchedule> findAllActive();
}
