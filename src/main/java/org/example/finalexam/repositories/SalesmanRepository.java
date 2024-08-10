package org.example.finalexam.repositories;

import org.example.finalexam.entities.Salesman;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesmanRepository extends JpaRepository<Salesman,Long> {
}
