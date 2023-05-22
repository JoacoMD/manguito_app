package app.manguito.backend.repositories;

import app.manguito.backend.entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}