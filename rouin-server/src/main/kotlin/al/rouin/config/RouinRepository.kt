package al.rouin.config

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface RouinRepository<T, ID> : JpaRepository<T, ID>