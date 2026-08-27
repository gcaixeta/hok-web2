package io.github.gcaixeta.hok.service;

import java.util.List;

import io.github.gcaixeta.hok.model.Project;

public interface ProjectService {
  List<Project> getAllProjects();
  void saveProject(Project project);
  Project getProjectById(Long id);
  void deleteProjectById(Long id);
}
