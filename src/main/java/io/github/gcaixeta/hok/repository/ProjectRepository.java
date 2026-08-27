package io.github.gcaixeta.hok.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.gcaixeta.hok.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
