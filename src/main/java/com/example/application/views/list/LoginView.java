package com.example.application.views.list;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

//Mapeie a visualização para o "login" caminho. LoginView deve abranger toda a janela do navegador, portanto não use MainLayout como pai.
@Route("login") 
@PageTitle("Login | My Project Software")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

	//Instancie um LoginForm componente para capturar nome de usuário e senha.
	private final LoginForm login = new LoginForm(); 

	public LoginView(){
		addClassName("login-view");
		//Aumente LoginView o tamanho e centralize seu conteúdo - tanto horizontal quanto verticalmente - chamando setAlignItems(`Alignment.CENTER)` e setJustifyContentMode(`JustifyContentMode.CENTER)`.
		setSizeFull(); 
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);

		//Defina a LoginForm ação para "login" postar o formulário de login no Spring Security.
		login.setAction("login"); 

		add(new H1("My Project Software CRM"), login);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

		//Leia os parâmetros de consulta e mostre um erro se uma tentativa de login falhar.
		if(beforeEnterEvent.getLocation()
        .getQueryParameters()
        .getParameters()
        .containsKey("error")) {
            login.setError(true);
        }
	}
}