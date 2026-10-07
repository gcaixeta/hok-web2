package io.github.gcaixeta.hok.config;

import io.github.gcaixeta.hok.service.ProjectService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public ProjectService projectService() {
        return Mockito.mock(ProjectService.class);
    }
}
