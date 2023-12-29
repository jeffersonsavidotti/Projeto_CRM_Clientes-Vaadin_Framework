package com.example.application.views.list;

import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.Component;
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
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@PermitAll
@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard | My Project Software CRM")
public class DashboardView extends VerticalLayout {
    private final CrmService service;

    public DashboardView(CrmService service) {
        this.service = service;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(getContactStats(), createChartsLayout());
    }

    private Component getContactStats() {
        Span stats = new Span(service.countContacts() + " contacts");
        stats.addClassNames(
                LumoUtility.FontSize.XLARGE,
                LumoUtility.Margin.Top.MEDIUM);
        return stats;
    }

    private Component createChartsLayout() {
        HorizontalLayout chartsLayout = new HorizontalLayout();
        chartsLayout.setWidthFull();
        chartsLayout.setSpacing(true);

        chartsLayout.add(createChartLayout("Companies Chart", getCompaniesDataset()));
        chartsLayout.add(createChartLayout("Monthly Sales", getSalesDataset()));
        chartsLayout.add(createChartLayout("Monthly Leads", getLeadsDataset()));
        chartsLayout.add(createChartLayout("Activity Breakdown", getActivityDataset()));

        return chartsLayout;
    }

    private Component createChartLayout(String chartTitle, PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                chartTitle, // título do gráfico
                dataset,
                true, // incluir legenda
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        byte[] chartImageBytes = convertChartToImage(chartPanel);
        Image chartImage = new Image("data:image/png;base64," + Base64.getEncoder().encodeToString(chartImageBytes), chartTitle);

        VerticalLayout chartLayout = new VerticalLayout();
        chartLayout.add(chartImage);
        chartLayout.add(new Span("Additional Information")); // Adicione informações adicionais aqui

        return chartLayout;
    }

    // Métodos para criar conjuntos de dados fictícios (ajuste conforme necessário)
    private PieDataset getCompaniesDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        service.findAllCompanies().forEach(company ->
                dataset.setValue(company.getName(), company.getEmployeeCount()));
        return dataset;
    }

    private PieDataset getSalesDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("January", 1000);
        dataset.setValue("February", 1500);
        dataset.setValue("March", 800);
        return dataset;
    }

    private PieDataset getLeadsDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("January", 50);
        dataset.setValue("February", 80);
        dataset.setValue("March", 60);
        return dataset;
    }

    private PieDataset getActivityDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Emails", 30);
        dataset.setValue("Calls", 20);
        dataset.setValue("Meetings", 10);
        return dataset;
    }

    private byte[] convertChartToImage(ChartPanel chartPanel) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            BufferedImage chartImage = chartPanel.getChart().createBufferedImage(400, 300); // Ajuste o tamanho conforme necessário
            ImageIO.write(chartImage, "png", outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
