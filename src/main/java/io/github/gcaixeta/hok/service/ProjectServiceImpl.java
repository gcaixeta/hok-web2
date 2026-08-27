package io.github.gcaixeta.hok.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import io.github.gcaixeta.hok.exception.ProjectNotFoundException;
import io.github.gcaixeta.hok.model.Project;
import io.github.gcaixeta.hok.repository.ProjectRepository;

public class ProjectServiceImpl implements ProjectService {

  @Autowired
  private ProjectRepository projects;

  @Override
  public List<Project> getAllProjects() {
    return projects.findAll();
  }

  @Override
  public void saveProject(Project project) {
    projects.save(project);
  }

  @Override
  public Project getProjectById(Long id) {
    return projects.findById(id)
        .orElseThrow(() -> new ProjectNotFoundException("Could not find project with id: " + id));
  }

  @Override
  public void deleteProjectById(Long id) {
    projects.deleteById(id);
  }
}
