package al.rouin.common

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass


@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class AuditEntity {
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.MIN
        private set

    @Column(name = "modified_date")
    @LastModifiedDate
    var modifiedAt: LocalDateTime = LocalDateTime.MAX
        private set
}