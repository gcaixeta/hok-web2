package io.github.gcaixeta.hok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import io.github.gcaixeta.hok.model.Project;
import io.github.gcaixeta.hok.service.ProjectService;

@Controller
public class ProjectController {

  @Autowired
  private ProjectService projectService;

  @GetMapping("project/create")
  public String create(Model model) {
    model.addAttribute("project", new Project());
    return "project/create";
  }

  @GetMapping("project/edit/{id}")
  public String edit(@PathVariable Long id, Model model) {
    System.out.println("Requesting page: editing project: " + id);
    Project projectToEdit = projectService.getProjectById(id);
    model.addAttribute("project", projectToEdit);
    return "project/edit";
  }

  @PostMapping("project/save")
  public String save(Project project, Model model) {
    System.out.println(project.getName());
    projectService.saveProject(project);
    model.addAttribute("projectList", projectService.getAllProjects());
    return "redirect:/project";
  }

  @GetMapping("project")
  public String index(Model model) {
    model.addAttribute("projectList", projectService.getAllProjects());
    return "project/index";
  }

  @GetMapping("project/delete/{id}")
  public String delete(@PathVariable Long id) {
    projectService.deleteProjectById(id);
    return "redirect:/project";
  }
}
