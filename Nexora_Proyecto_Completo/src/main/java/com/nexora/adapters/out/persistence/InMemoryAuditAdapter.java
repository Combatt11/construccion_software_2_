package com.nexora.adapters.out.persistence;

import com.nexora.application.ports.out.AuditPort;
import java.util.ArrayList;
import java.util.List;

public class InMemoryAuditAdapter implements AuditPort {
    private final List<String> records = new ArrayList<>();

    @Override
    public void record(String requestId, Long userId, String action, String entityType, Long entityId, Object payload, String result) {
        records.add(action + ":" + entityType + ":" + entityId);
    }

    public List<String> getRecords() { return records; }
}
