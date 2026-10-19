package com.nexora.adapters.out.persistence.mongo;

import com.nexora.adapters.out.persistence.document.AuditLog;
import com.nexora.application.ports.out.AuditPort;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class AuditMongoAdapter implements AuditPort {
    private final AuditLogMongoRepository repo;

    public AuditMongoAdapter(AuditLogMongoRepository repo) { this.repo = repo; }

    @Override
    public void record(String requestId, Long userId, String action, String entityType, Long entityId, Object payload, String result) {
        AuditLog log = new AuditLog(null, LocalDateTime.now(), requestId, userId, action, entityType, entityId,
                payload instanceof Map ? (Map<String, Object>) payload : Map.of("value", payload), result);
        repo.save(log);
    }
}
