package com.example.application.views.list;

import com.example.application.security.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

//AppLayout é um layout Vaadin com cabeçalho e gaveta responsiva.
public class MainLayout extends AppLayout {

    private final SecurityService securityService;

    //Autowire SecurityService e salve-o em um campo.
    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    //Em vez de estilizar o texto com CSS bruto, use Lumo Utility Classes fornecidas com o tema padrão.
    private void createHeader() {
        H1 logo = new H1("My Project Software CRM");
        logo.addClassNames(
            LumoUtility.FontSize.LARGE,
            LumoUtility.Margin.MEDIUM);

        String u = securityService.getAuthenticatedUser().getUsername();
        //Crie um botão de logout que chame o logout() método no serviço.
        Button logout = new Button("Logout " + u, e -> securityService.logout());

        //Adicione o botão ao layout do cabeçalho.
        var header = new HorizontalLayout(new DrawerToggle(), logo, logout);


        //Centraliza os componentes ao header longo do eixo vertical.
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        //Ligue header.expand(logo) para fazer o logotipo ocupar todo o espaço extra do layout. Isso pode empurrar o botão de logout para a extrema direita.
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames(
            LumoUtility.Padding.Vertical.NONE,
            LumoUtility.Padding.Horizontal.MEDIUM);

        //Adiciona o headerlayout à barra de navegação do layout do aplicativo, a seção na parte superior da tela.
        addToNavbar(header); 

    }

    private void createDrawer() {
        Icon ico_cliente = VaadinIcon.BOOK.create();
        Icon ico_dashborad = VaadinIcon.DASHBOARD.create();
        Icon ico_dashborad2 = VaadinIcon.DASHBOARD.create();
        Icon ico_novidades = VaadinIcon.BELL.create();
        Icon ico_novidades2 = VaadinIcon.BELL.create();
        Icon ico_novidades3 = VaadinIcon.BELL.create();
        Icon ico_novidades4 = VaadinIcon.BELL.create();


        //Envolve o link do roteador em um VerticalLayoute o adiciona à AppLayout gaveta.
        addToDrawer(new VerticalLayout(
                ico_cliente, new RouterLink("Clientes", ListView.class),
                ico_dashborad, new RouterLink("Dashboard", DashboardView.class),
                ico_dashborad2, new RouterLink("Dashboard 2", DashboardView2.class),
                ico_novidades, new RouterLink("Etapa 2", E2View.class),
                ico_novidades2, new RouterLink("Etapa 3", E3View.class),
                ico_novidades3, new RouterLink("Etapa 4", E4View.class),
                ico_novidades4, new RouterLink("Etapa 5", E5View.class)
        ));
    }

    private Tab createTab(VaadinIcon viewIcon, String viewName) {
        Icon icon = viewIcon.create();
        icon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("margin-inline-start", "var(--lumo-space-xs)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink link = new RouterLink();
        link.add(icon, new Span(viewName));
        // Demo has no routes
        // link.setRoute(viewClass.java);
        link.setTabIndex(-1);

        return new Tab(link);
    }
}