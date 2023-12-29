package com.example.application.views.list;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Status;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

//ContactForm estende FormLayout: um layout responsivo que mostra campos de formulário em uma ou duas colunas, dependendo da largura da janela de visualização.
public class ContactForm extends FormLayout
{
  //Chamadas binder.setBean() para vincular os valores do contact aos campos da interface do usuário. O método também adiciona ouvintes de alteração de valor para atualizar as alterações na IU de volta ao objeto de domínio.
  public void setContact(Contact contact) {
    binder.setBean(contact);
  }
  //Cria todos os componentes da UI como campos no componente.
  TextField firstName = new TextField("First name");
  TextField lastName = new TextField("Last name");
  EmailField email = new EmailField("Email");
  ComboBox<Status> status = new ComboBox<>("Status");
  ComboBox<Company> company = new ComboBox<>("Company");

  Button save = new Button("Salvar");
  Button delete = new Button("Deletar");
  Button close = new Button("Cancelar");

  //BeanValidationBinder é um Binderque conhece as anotações de validação do bean. Ao passá-lo no Contact.class, você define o tipo de objeto ao qual está vinculando.
  Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);
  public ContactForm(List<Company> companies, List<Status> statuses) {
    //Dá ao componente um nome de classe CSS, para que você possa estilizá-lo mais tarde.
    addClassName("contact-form");
    //bindInstanceFields() corresponde aos campos Contacte ContactFormcom base em seus nomes.
    binder.bindInstanceFields(this);

    company.setItems(companies);
    company.setItemLabelGenerator(Company::getName);
    status.setItems(statuses);
    status.setItemLabelGenerator(Status::getName);

    //Adiciona todos os componentes da UI ao layout. Os botões requerem um pouco de configuração extra. Crie e chame um novo método, createButtonsLayout()
    add(firstName, 
        lastName,
        email,
        company,
        status,
        createButtonsLayout());
  }

  private HorizontalLayout createButtonsLayout() {
    //Torna os botões visualmente distintos uns dos outros usando variantes de tema integradas .
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY); 
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    //Define atalhos de teclado: Enterpara salvar e Escapepara fechar o editor.
    save.addClickShortcut(Key.ENTER); 
    close.addClickShortcut(Key.ESCAPE);

    //O botão salvar chama o validateAndSave()métod
    save.addClickListener(event -> validateAndSave());
    //O botão excluir aciona um evento de exclusão e passa o contact ativo.
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
    //O botão cancelar dispara um evento de fechamento.
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));

    //Valida o formulário sempre que ele é alterado. Se for inválido, desativa o botão salvar para evitar envios inválidos.
    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

    //Retorna um HorizontalLayoutcontendo os botões para colocá-los um ao lado do outro.
    return new HorizontalLayout(save, delete, close); 
  }
  private void validateAndSave() {
    if (binder.isValid()) {
      //Dispare um evento save, para que o componente pai possa manipular a ação.
      fireEvent(new SaveEvent(this, binder.getBean()));
    }
  }
  // Events
  public static abstract class ContactFormEvent extends ComponentEvent<ContactForm> {
    private Contact contact;
    //ContactFormEvent é uma superclasse comum para todos os eventos. Ele contém o contactque foi editado ou excluído.
    protected ContactFormEvent(ContactForm source, Contact contact) {
      super(source, false);
      this.contact = contact;
    }

    public Contact getContact() {
      return contact;
    }
  }

  public static class SaveEvent extends ContactFormEvent {
    SaveEvent(ContactForm source, Contact contact) {
      super(source, contact);
    }
  }

  public static class DeleteEvent extends ContactFormEvent {
    DeleteEvent(ContactForm source, Contact contact) {
      super(source, contact);
    }

  }

  public static class CloseEvent extends ContactFormEvent {
    CloseEvent(ContactForm source) {
      super(source, null);
    }
  }

  //Os add*Listener()métodos que passam o tipo de evento bem digitado para o barramento de eventos do Vaadin para registrar os tipos de eventos personalizados.
  public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
    return addListener(DeleteEvent.class, listener);
  }

  public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
    return addListener(SaveEvent.class, listener);
  }
  public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
    return addListener(CloseEvent.class, listener);
  }
}