# 🚀 My Project CRM Tutorial - Vaadin

## 🖼️ Algumas imagens do sistema:

![Imagem 1](https://i.imgur.com/gaDM794.png)
![Imagem 2](https://i.imgur.com/0vGSsXW.png)
![Imagem 3](https://i.imgur.com/Kvm5rOb.png)
![Imagem 4](https://i.imgur.com/YiyUTqq.png)
![Imagem 5](https://i.imgur.com/Z7EfyoH.png)
![Imagem 6](https://i.imgur.com/UqnSC9j.png)
![Imagem 7](https://i.imgur.com/4OG6FIA.png)

## 🏃‍♂️ Executando a Aplicação

O projeto é um projeto Maven padrão. Para executá-lo a partir da linha de comando, digite o seguinte comando:

```bash
mvnw # (Windows)
./mvnw # (Mac & Linux)
```

Em seguida, abra http://localhost:8080 em seu navegador.

Você também pode importar o projeto para sua IDE de escolha como faria com qualquer projeto Maven. Saiba mais sobre como importar projetos Vaadin em diferentes IDEs (Eclipse, IntelliJ IDEA, NetBeans e VS Code).

## 🚀 Implantando em Produção

Para criar uma versão de produção, chame:

```bash
mvnw clean package -Pproduction # (Windows)
./mvnw clean package -Pproduction # (Mac & Linux)
```

Isso criará um arquivo JAR com todas as dependências e recursos front-end, pronto para ser implantado. O arquivo pode ser encontrado na pasta `target` após a conclusão da construção.

Uma vez que o arquivo JAR é construído, você pode executá-lo usando:

```bash
java -jar target/flowcrmtutorial-1.0-SNAPSHOT.jar
```

## 📁 Estrutura do Projeto

- `MainLayout.java` em `src/main/java` contém a configuração de navegação (barra lateral/superior e menu principal), utilizando o App Layout.
- O pacote `views` em `src/main/java` contém as visões Java do lado do servidor da aplicação.
- A pasta `views` em `frontend/` contém as visões de JavaScript do lado do cliente.
- A pasta `themes` em `frontend/` contém os estilos CSS personalizados.

## 🔗 Links Úteis

- Leia a [documentação](https://vaadin.com/docs).
- Siga o [tutorial](https://vaadin.com/docs/latest/tutorial/overview).
- Crie novos projetos em [start.vaadin.com](https://start.vaadin.com/).
- Pesquise componentes de UI e seus exemplos de uso em [vaadin.com/docs/latest/components](https://vaadin.com/docs/latest/components).
- Veja aplicações de casos de uso em [vaadin.com/examples-and-demos](https://vaadin.com/examples-and-demos).
- Construa UIs sem CSS personalizado com [conjunto de classes de utilidade CSS do Vaadin](https://vaadin.com/docs/latest/directory).
- Encontre soluções para casos de uso comuns em [cookbook.vaadin.com](https://cookbook.vaadin.com/).
- Encontre complementos em [vaadin.com/directory](https://vaadin.com/directory).
- Faça perguntas no [Stack Overflow](https://stackoverflow.com/) ou junte-se ao nosso canal [Discord](https://discord.com/invite/vaadin).
- Relate problemas, crie pull requests no [GitHub](https://github.com/vaadin/flow-crud).

## 🐳 Implantação usando Docker

Para construir a versão Dockerizada do projeto, execute:

```bash
mvn clean package -Pproduction
docker build . -t flowcrmtutorial:latest
```

Depois que a imagem Docker for construída corretamente, você pode testá-la localmente usando:

```bash
docker run -p 8080:8080 flowcrmtutorial:latest
```

---
