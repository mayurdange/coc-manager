package coc.manager.clanmanager;

import coc.manager.clanmanager.model.WarLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarLogRepository extends JpaRepository<WarLog, Long> {
}
