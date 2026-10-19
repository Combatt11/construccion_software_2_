package com.nexora.application.ports.out;

public interface AuditPort {
    void record(String requestId, Long userId, String action, String entityType, Long entityId, Object payload, String result);
}
