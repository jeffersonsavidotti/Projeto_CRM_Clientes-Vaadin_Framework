package com.example.application.views.list;

import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

//DashboardView é mapeado para o "dashboard" caminho e usado MainLayout como layout pai.
@PermitAll
@Route(value = "e4", layout = MainLayout.class)
@PageTitle("Etapa 4 | My Project Software")
public class E4View extends VerticalLayout {
    private final CrmService service;


    //Toma CrmService como parâmetro do construtor e salva como um campo.
    public E4View(CrmService service) {
        this.service = service;
        addClassName("dashboard-view");
        // Centraliza o conteúdo do layout.
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(getUpdateMessage());
    }

    private H2 getUpdateMessage() {
        H2 message = new H2("Atualizações futuras estão chegando!");
        message.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.Margin.Top.MEDIUM);
        return message;
    }
}