package com.example.application;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "flowcrmtutorial")
//A @PWA anotação diz ao Vaadin para criar um ServiceWorker arquivo de manifesto.
@PWA(
        //Nome completo do sistena
        name = "New Standard Software CRM",
        //Deve ser curto o suficiente para caber em um ícone quando instalado e não deve exceder 12 caracteres.
        shortName = "MyProjectCRM",
        //offlineResources é uma lista de arquivos que Vaadin disponibiliza offline através do ServiceWorker.
        offlinePath="offline.html",
        offlineResources = { "./images/offline.png"}
)
public class Application implements AppShellConfigurator {

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }

}
