package com.jarsolutions.fitness.muscle.domain.repository;

import com.jarsolutions.fitness.muscle.domain.entity.Muscle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuscleRepository extends JpaRepository<Muscle, Long> {}
