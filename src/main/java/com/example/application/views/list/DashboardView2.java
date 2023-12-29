package com.example.application.views.list;

import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@PermitAll
@Route(value = "e1", layout = MainLayout.class)
@PageTitle("Dashboard 2 | My Project Software CRM")
public class DashboardView2 extends VerticalLayout {
    private final CrmService service;

    public DashboardView2(CrmService service) {
        this.service = service;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(getContactStats());

        // Organize os gráficos lado a lado
        HorizontalLayout chartsLayout = new HorizontalLayout();
        chartsLayout.add(getCompaniesChart(), getSalesChart());
        add(chartsLayout);

        HorizontalLayout chartsLayout2 = new HorizontalLayout();
        chartsLayout2.add(getLeadsChart(), getActivityChart());
        add(chartsLayout2);
    }
    private Component getContactStats() {
        Span stats = new Span(service.countContacts() + " contacts");
        stats.addClassNames(
                LumoUtility.FontSize.XLARGE,
                LumoUtility.Margin.Top.MEDIUM);
        return stats;
    }

    private Component getCompaniesChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        // Adicione dados ao conjunto de dados
        service.findAllCompanies().forEach(company ->
                dataset.setValue(company.getName(), company.getEmployeeCount()));

        // Crie o gráfico de pizza usando JFreeChart
        JFreeChart chart = ChartFactory.createPieChart(
                "Companies Chart", // título do gráfico
                dataset,
                true, // incluir legenda
                true,
                false
        );

        // Verifique se o tipo de plotagem é PiePlot3D antes de realizar o cast
        if (chart.getPlot() instanceof PiePlot3D) {
            PiePlot3D plot3D = (PiePlot3D) chart.getPlot();

            // Ajuste adicional para um gráfico 3D
            plot3D.setStartAngle(290);
            plot3D.setDirection(Rotation.CLOCKWISE);
            plot3D.setForegroundAlpha(0.5f);
        }

        // Crie um painel de gráfico para exibir o gráfico no Vaadin
        ChartPanel chartPanel = new ChartPanel(chart);

        // Converta o gráfico para um formato de imagem
        byte[] chartImageBytes = convertChartToImage(chartPanel);

        // Crie um componente Image do Vaadin com a imagem do gráfico
        Image chartImage = new Image("data:image/png;base64," + Base64.getEncoder().encodeToString(chartImageBytes), "Companies Chart");

        // Crie um Div e adicione o Image a ele
        Div chartDiv = new Div(chartImage);

        // Crie um VerticalLayout e adicione o Div a ele
        VerticalLayout layout = new VerticalLayout();
        layout.add(chartDiv);

        return layout;
    }

    private Component getSalesChart() {
        // Crie um conjunto de dados fictício para representar as vendas
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(1000, "Sales", "January");
        dataset.addValue(1500, "Sales", "February");
        dataset.addValue(800, "Sales", "March");

        // Crie um gráfico de barras usando JFreeChart
        JFreeChart chart = ChartFactory.createBarChart(
                "Monthly Sales", // título do gráfico
                "Month", // rótulo do eixo X
                "Revenue", // rótulo do eixo Y
                dataset
        );

        // Crie um painel de gráfico para exibir o gráfico no Vaadin
        ChartPanel chartPanel = new ChartPanel(chart);

        // Converta o gráfico para um formato de imagem
        byte[] chartImageBytes = convertChartToImage(chartPanel);

        // Crie um componente Image do Vaadin com a imagem do gráfico
        Image chartImage = new Image("data:image/png;base64," + Base64.getEncoder().encodeToString(chartImageBytes), "Monthly Sales");

        // Crie um Div e adicione o Image a ele
        Div chartDiv = new Div(chartImage);

        // Crie um VerticalLayout e adicione o Div a ele
        VerticalLayout layout = new VerticalLayout();
        layout.add(chartDiv);

        return layout;
    }

    private Component getLeadsChart() {
        // Crie um conjunto de dados fictício para representar os leads
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(50, "Leads", "January");
        dataset.addValue(80, "Leads", "February");
        dataset.addValue(60, "Leads", "March");

        // Crie um gráfico de linhas usando JFreeChart
        JFreeChart chart = ChartFactory.createLineChart(
                "Monthly Leads", // título do gráfico
                "Month", // rótulo do eixo X
                "Number of Leads", // rótulo do eixo Y
                dataset
        );

        // Crie um painel de gráfico para exibir o gráfico no Vaadin
        ChartPanel chartPanel = new ChartPanel(chart);

        // Converta o gráfico para um formato de imagem
        byte[] chartImageBytes = convertChartToImage(chartPanel);

        // Crie um componente Image do Vaadin com a imagem do gráfico
        Image chartImage = new Image("data:image/png;base64," + Base64.getEncoder().encodeToString(chartImageBytes), "Monthly Leads");

        // Crie um Div e adicione o Image a ele
        Div chartDiv = new Div(chartImage);

        // Crie um VerticalLayout e adicione o Div a ele
        VerticalLayout layout = new VerticalLayout();
        layout.add(chartDiv);

        return layout;
    }

    private Component getActivityChart() {
        // Crie um conjunto de dados fictício para representar a atividade
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Emails", 30);
        dataset.setValue("Calls", 20);
        dataset.setValue("Meetings", 10);

        // Crie um gráfico de rosca usando JFreeChart
        JFreeChart chart = ChartFactory.createRingChart(
                "Activity Breakdown", // título do gráfico
                dataset,
                true, // incluir legenda
                true,
                false
        );

        // Crie um painel de gráfico para exibir o gráfico no Vaadin
        ChartPanel chartPanel = new ChartPanel(chart);

        // Converta o gráfico para um formato de imagem
        byte[] chartImageBytes = convertChartToImage(chartPanel);

        // Crie um componente Image do Vaadin com a imagem do gráfico
        Image chartImage = new Image("data:image/png;base64," + Base64.getEncoder().encodeToString(chartImageBytes), "Activity Breakdown");

        // Crie um Div e adicione o Image a ele
        Div chartDiv = new Div(chartImage);

        // Crie um VerticalLayout e adicione o Div a ele
        VerticalLayout layout = new VerticalLayout();
        layout.add(chartDiv);

        return layout;
    }

    private byte[] convertChartToImage(ChartPanel chartPanel) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Salve o gráfico em um fluxo de bytes
            BufferedImage chartImage = chartPanel.getChart().createBufferedImage(800, 600);
            ImageIO.write(chartImage, "png", outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
