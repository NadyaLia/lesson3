package de.telran.g240123mbelesson331082023.repository.jpa;

import de.telran.g240123mbelesson331082023.domain.entity.Product;
import de.telran.g240123mbelesson331082023.domain.entity.jpa.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaTaskRepository extends JpaRepository<Task, Integer> {

    @Query(value = "SELECT * FROM task ORDER BY executed_at DESC LIMIT 5;", nativeQuery = true)
    List<Task> displayLastFiveTasks();

    @Query(value = "SELECT * FROM product ORDER BY id DESC LIMIT 1;", nativeQuery = true)
    Product displayLastAddedProduct();
}
