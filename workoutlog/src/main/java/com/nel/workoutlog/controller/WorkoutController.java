package com.nel.workoutlog.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.nel.workoutlog.entity.Workout;
import com.nel.workoutlog.repository.WorkoutRepository;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    private final WorkoutRepository repository;

    public WorkoutController(WorkoutRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Workout> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Workout create(@RequestBody Workout workout) {
        return repository.save(workout);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
    repository.deleteById(id);
    }

    @PutMapping("/{id}")
        public Workout update(@PathVariable Long id, @RequestBody Workout updated) {
            return repository.findById(id)
            .map(w -> {
                w.setDate(updated.getDate());
                w.setDistance(updated.getDistance());
                w.setPace(updated.getPace());
                return repository.save(w);
        })
        .orElseThrow();
}


}
