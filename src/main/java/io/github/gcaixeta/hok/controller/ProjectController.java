package io.github.gcaixeta.hok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
