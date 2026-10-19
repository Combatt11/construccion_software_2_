package com.nexora.adapters.out.persistence.mongo;

import com.nexora.adapters.out.persistence.document.AuditLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuditLogMongoRepository extends MongoRepository<AuditLog, String> {
}
