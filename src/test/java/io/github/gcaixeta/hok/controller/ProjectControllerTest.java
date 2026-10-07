package io.github.gcaixeta.hok.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import io.github.gcaixeta.hok.config.TestConfig;
import io.github.gcaixeta.hok.model.Project;
import io.github.gcaixeta.hok.service.ProjectService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(ProjectController.class)
@Import(TestConfig.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectService projectService;

    @AfterEach
    void resetMocks() {
        reset(projectService);
    }

    private List<Project> testCreateProjectList(){
        Project projectB = new Project();
        projectB.setId(1L);
        projectB.setDescription("Descricao");
        projectB.setName("Projeto B");
        projectB.setSymbol("SAA");

        return List.of(projectB);
    }

    @Test
    @DisplayName("GET /project - Listar projetos na tela index sem usuário autenticado")
    void testIndexNotAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/project"))
                .andExpect(status().isUnauthorized()); // Correção aqui
    }

    @Test
    @WithMockUser
    @DisplayName("GET /project - Listar projetos na tela index com usuário logado")
    void testIndexAuthenticatedUser() throws Exception {
        when(projectService.getAllProjects()).thenReturn(testCreateProjectList());

        mockMvc.perform(get("/project"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/index"))
                .andExpect(model().attributeExists("projectList"))
                .andExpect(content().string(containsString("Listagem de Projeto")))
                .andExpect(content().string(containsString("Projeto B")));
    }

    @Test
    @WithMockUser(username = "aluno@iftm.edu.br", authorities = { "Admin" })
    @DisplayName("GET /project/create - Exibe formulário de criação")
    void testCreateFormAuthorizedUser() throws Exception {
        mockMvc.perform(get("/project/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/create"))
                .andExpect(model().attributeExists("project"))
                .andExpect(content().string(containsString("Cadastrar Projeto")));
    }

    @Test
    @WithMockUser(username = "aluno2@iftm.edu.br", authorities = { "Manager" })
    @DisplayName("GET /project - Verificar o link de cadastrar para um usuario não admin logado")
    void testCreateFormNotAuthorizedUser() throws Exception {
        when(projectService.getAllProjects()).thenReturn(testCreateProjectList());
        // Obter o HTML da página renderizada pelo controlador
        mockMvc.perform(get("/project/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/create"))
                .andExpect(model().attributeExists("project"))
                .andExpect(content().string(not(containsString("<a class=\"dropdown-item\" href=\"/project/create\">Cadastrar</a>"))));
    }

    @Test
    @WithMockUser
    @DisplayName("POST /project/save - Falha na validação e retorna para o formulário")
    void testSaveProjectValidationError() throws Exception {
        Project project = new Project(); // Projeto sem nome, o que causará erro de validação

        mockMvc.perform(post("/project/save")
                        .with(csrf())
                        .flashAttr("project", project))
                .andExpect(status().isOk())
                .andExpect(view().name("project/create"))
                .andExpect(model().attributeHasErrors("project"));

        verify(projectService, never()).saveProject(any(Project.class));
    }

    @Test
    @WithMockUser(username = "aluno@iftm.edu.br", authorities = { "Admin" })
    @DisplayName("POST /project/save - Projeto válido é salvo com sucesso")
    void testSaveValidProject() throws Exception {
        Project project = new Project();
        project.setName("Novo Projeto");
        project.setDescription("Descrição");
        project.setSymbol("SAA");

        mockMvc.perform(post("/project/save")
                        .with(csrf())
                        .flashAttr("project", project))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/project"));

        verify(projectService).saveProject(any(Project.class));
    }

}