package io.github.gcaixeta.hok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.gcaixeta.hok.service.ProjectService;

@Controller
@RequestMapping("/project")
public class ProjectController {

  @Autowired
  private ProjectService projectService;

  @GetMapping
  public String index(Model model) {
    model.addAttribute("projectList", projectService.getAllProjects());
    return "project/index";
  }
}
