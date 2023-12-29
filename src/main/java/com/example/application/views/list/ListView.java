package com.example.application.views.list;

import com.example.application.data.entity.Contact;
import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;


@PermitAll
@Route(value="", layout = MainLayout.class) //ListViewainda corresponde ao caminho vazio, mas agora é usado MainLayoutcomo pai.
@PageTitle("Contatos | My Project Software")
public class ListView extends VerticalLayout //A visualização extends VerticalLayout, que coloca todos os componentes filhos verticalmente.
{
    Grid<Contact> grid = new Grid<>(Contact.class); //O componente Grid é digitado com Contact
    TextField filterText = new TextField();

    //Criando uma referência ao formulário para que você tenha acesso a ele por outros métodos.
    ContactForm form;
    CrmService service;

    //Autowire CrmServiceatravés do construtor. Salve-o em um campo para poder acessá-lo por outros métodos.
    public ListView(CrmService service) {
        addClassName("list-view");
        this.service = service;
        setSizeFull();
        //A configuração da grade é extraída para um método separado para manter o construtor mais fácil de ler.
        configureGrid();
        //Adicione a barra de ferramentas e a grade ao arquivo VerticalLayout
        add(getToolbar(), grid);
        //Crie um método para inicializar o formulário.
        configureForm();
        //Altere o add() método para chamar getContent(). O método retorna um HorizontalLayoutque envolve o formulário e a grade, mostrando-os um ao lado do outro.
        add(getContent());
        //Chame updateList() depois de construir a visualização.
        updateList();
        //A closeEditor() chamada no final do construtor: define o formulário contact como null, limpando valores antigos; oculta o formulário; e remove a "editing"classe CSS da visualização.
        closeEditor();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        //Utilize setFlexGrow()para especificar que a Grade deve ter o dobro do espaço do formulário.
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void saveContact(ContactForm.SaveEvent event) {
        service.saveContact(event.getContact());
        updateList();
        closeEditor();
    }

    private void deleteContact(ContactForm.DeleteEvent event) {
        service.deleteContact(event.getContact());
        updateList();
        closeEditor();
    }

    private void configureForm() {
        //Inicialize o formulário com listas de empresas e status:
        //Use o serviço para buscar empresas e status.
        form = new ContactForm(service.findAllCompanies(), service.findAllStatuses());
        form.setWidth("25em");
        //O ouvinte do evento save chama saveContact(). Serve contactServicepara salvar o contato do evento no banco de dados, atualizar a lista e fechar o editor.
        form.addSaveListener(this::saveContact);
        //O ouvinte de evento delete chama deleteContact(). No processo, ele também contactServiceapaga o contato do banco de dados, atualiza a lista e fecha o editor.
        form.addDeleteListener(this::deleteContact);
        //O ouvinte de evento close fecha o editor.
        form.addCloseListener(e -> closeEditor());
    }

    public class ColumnConfig{
        private String headerName;
        private String fieldName;
        public  ColumnConfig(String headerName, String fieldName){
            this.headerName = headerName;
            this.fieldName = fieldName;
        }
        public String getHeaderName(){
            return headerName;
        }
        public String getFieldName(){
            return fieldName;
        }
    }
    private void configureGrid() {
        grid.addClassNames("contact-grid"); //Adicionar alguns nomes de classes aos componentes facilita o estilo do aplicativo posteriormente usando CSS.
        grid.setSizeFull();
        //grid.setColumns(new ColumnConfig("Nome","firstName"), new ColumnConfig("Sobrenome","lastName"), new ColumnConfig("Email","email")); //Defino aqui quais propriedades Contact da grade devem ser exibidas.
        grid.setColumns("firstName", "lastName", "email"); //Defino aqui quais propriedades Contact da grade devem ser exibidas.
        grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status"); //Defina colunas personalizadas para objetos aninhados.
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true)); //Configure as colunas para ajustar automaticamente seu tamanho de acordo com seu conteúdo.
        //addValueChangeListener() adiciona um ouvinte à grade. O Grid componente suporta modos de seleção múltipla e única. Você só precisa selecionar um único Contact, para poder usar o asSingleSelect()método. O getValue()método retorna the Contactna linha selecionada ou null se não houver seleção.
        grid.asSingleSelect().addValueChangeListener(event -> editContact(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Busque por um nome...");
        filterText.setClearButtonVisible(true);
        //Configure o campo de pesquisa para disparar eventos de alteração de valor somente quando o usuário parar de digitar. Dessa forma você evita chamadas desnecessárias ao banco de dados, mas o listener ainda é acionado sem que o usuário saia do foco do campo.
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        //Ligue updateList()sempre que o filtro mudar.
        filterText.addValueChangeListener(e -> updateList());
        //Cliando um botão
        Button addContactButton = new Button("Adicionar Cliente");
        //Ligue addContact() quando o usuário clicar no botão "Adicionar contato".
        addContactButton.addClickListener(click -> addContact());

        //A barra de ferramentas usa um HorizontalLayoutpara colocar TextFielde um Buttonao lado do outro
        var toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar"); //Adicionar alguns nomes de classes aos componentes facilita o estilo do aplicativo posteriormente usando CSS.
        return toolbar;
    }
    //editContact() define o contact selecionado no ContactForme oculta ou mostra o formulário, dependendo da seleção. Também define o "editing"nome da classe CSS durante a edição.
    public void editContact(Contact contact) {
        if (contact == null) {
            closeEditor();
        } else {
            form.setContact(contact);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");
    }
    //addContact() limpa a seleção da grade e cria um novo arquivo Contact.
    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Contact());
    }
    //updateList() define os itens da grade chamando o serviço com o valor do campo de texto do filtro.
    private void updateList() {
        grid.setItems(service.findAllContacts(filterText.getValue()));
    }
}