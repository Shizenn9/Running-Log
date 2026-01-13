package com.nel.workoutlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nel.workoutlog.entity.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
}
