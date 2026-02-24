package com.shottz;

import java.time.LocalDate;
import java.util.List; // Import crucial para receber os dados do banco

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SistemaApp extends Application {

    private ObservableList<Transacao> transacoes = FXCollections.observableArrayList();
    private PieChart pieChart = new PieChart();
    private Stage primaryStage;
    
    
    private TransacaoDAO dao = new TransacaoDAO();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        exibirTelaLogin();
    }

    private void exibirTelaLogin() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));

        Label lblTitulo = new Label("Login do Sistema");
        lblTitulo.setStyle("-fx-font-size: 33px; -fx-font-weight: bold;");

        TextField txtUser = new TextField();
        txtUser.setPromptText("Usuário");
        txtUser.setMaxWidth(250);

        PasswordField txtPass = new PasswordField();
        txtPass.setPromptText("Senha (até 4 caracteres)");
        txtPass.setMaxWidth(250);

        Button btnEntrar = new Button("Entrar");
        btnEntrar.setPrefWidth(250);
        btnEntrar.setStyle("-fx-background-color: #349edb; -fx-text-fill: white; -fx-font-size: 16px;");

        btnEntrar.setOnAction(e -> {
            String senha = txtPass.getText();
            if (senha.length() > 0 && senha.length() <= 4) {
                exibirTelaPrincipal();
            } else {
                new Alert(Alert.AlertType.ERROR, "A senha deve ter entre 1 e 4 caracteres!").show();
            }
        });

        root.getChildren().addAll(lblTitulo, txtUser, txtPass, btnEntrar);
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("Sistema de Gestão Financeira - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void exibirTelaPrincipal() {
        TableView<Transacao> table = new TableView<>();
        
        TableColumn<Transacao, String> colDesc = new TableColumn<>("Descrição");
        colDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        TableColumn<Transacao, Double> colValor = new TableColumn<>("Valor");
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

        TableColumn<Transacao, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        table.getColumns().addAll(colDesc, colValor, colTipo);
        
        
        try {
            List<Transacao> dadosDoBanco = dao.listarTodas();
            transacoes.setAll(dadosDoBanco);
        } catch (Exception e) {
            System.out.println("Aviso: Não foi possível carregar os dados iniciais do banco.");
        }
        
        table.setItems(transacoes);
        atualizarGrafico();

        TextField txtDesc = new TextField();
        txtDesc.setPromptText("Descrição");
        
        TextField txtValor = new TextField();
        txtValor.setPromptText("Valor (Ex: 1000)");

        ComboBox<String> cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("Receita", "Despesa");
        cbTipo.setValue("Receita");

        Button btnAdd = new Button("Adicionar");
        btnAdd.setOnAction(e -> {
            try {
                String desc = txtDesc.getText();
                double valor = Double.parseDouble(txtValor.getText());
                
                Transacao nova = cbTipo.getValue().equals("Receita") 
                                 ? new Receita(desc, valor, LocalDate.now()) 
                                 : new Despesa(desc, valor, LocalDate.now());

                
                dao.salvar(nova);

                
                transacoes.add(nova);
                atualizarGrafico();
                
                txtDesc.clear(); 
                txtValor.clear();
                
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Por favor, digite um valor numérico válido.").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao processar a transação.").show();
            }
        });

        Button btnRelatorios = new Button("Relatórios");
        btnRelatorios.setOnAction(e -> exibirTelaRelatorio());
        btnRelatorios.setStyle("-fx-background-color: #12e92b; -fx-text-fill: white; -fx-font-size: 16px;");

        HBox menu = new HBox(10, btnRelatorios);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER_LEFT);
        
        HBox form = new HBox(10, txtDesc, txtValor, cbTipo, btnAdd);
        form.setPadding(new Insets(10));

        VBox root = new VBox(10, menu, pieChart, table, form);
        root.setPadding(new Insets(15));
        
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("Sistema de Gestão Financeira");
        primaryStage.setScene(scene);
    }

    private void atualizarGrafico() {
        double r = transacoes.stream().filter(t -> t instanceof Receita).mapToDouble(Transacao::getValor).sum();
        double d = transacoes.stream().filter(t -> t instanceof Despesa).mapToDouble(Transacao::getValor).sum();

        PieChart.Data fatiaReceita = new PieChart.Data("Receitas", r);
        PieChart.Data fatiaDespesa = new PieChart.Data("Despesas", d);

        pieChart.setData(FXCollections.observableArrayList(fatiaReceita, fatiaDespesa));

        if (fatiaReceita.getNode() != null) {
            fatiaReceita.getNode().setStyle("-fx-pie-color: #2ecc71;"); 
        }
        if (fatiaDespesa.getNode() != null) {
            fatiaDespesa.getNode().setStyle("-fx-pie-color: #e74c3c;"); 
        }
    }

    private void exibirTelaRelatorio() {
        Label lblTitulo = new Label("Relatórios Financeiros");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        double totalReceitas = transacoes.stream()
                .filter(t -> t instanceof Receita)
                .mapToDouble(Transacao::getValor)
                .sum();

        double totalDespesas = transacoes.stream()
                .filter(t -> t instanceof Despesa)
                .mapToDouble(Transacao::getValor)
                .sum();

        Label lblReceita = new Label("Total de Receitas: R$ " + totalReceitas);
        Label lblDespesa = new Label("Total de Despesas: R$ " + totalDespesas);
        Label lblSaldo = new Label("Saldo: R$ " + (totalReceitas - totalDespesas));

        lblReceita.setStyle("-fx-text-fill: green;");
        lblDespesa.setStyle("-fx-text-fill: red;");
        lblSaldo.setStyle("-fx-font-weight: bold;");

        PieChart.Data fatiaReceita = new PieChart.Data("Receitas", totalReceitas);
        PieChart.Data fatiaDespesa = new PieChart.Data("Despesas", totalDespesas);

        PieChart graficoRelatorio = new PieChart(
            FXCollections.observableArrayList(fatiaReceita, fatiaDespesa)
        );

        graficoRelatorio.applyCss();

        if (fatiaReceita.getNode() != null) {
            fatiaReceita.getNode().setStyle("-fx-pie-color: #2ecc71;");
        }
        if (fatiaDespesa.getNode() != null) {
            fatiaDespesa.getNode().setStyle("-fx-pie-color: #e74c3c;");
        }

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> exibirTelaPrincipal());
        btnVoltar.setStyle("-fx-background-color: #349edb; -fx-text-fill: white; -fx-font-size: 16px;");

        VBox root = new VBox(15, lblTitulo, lblReceita, lblDespesa, lblSaldo, graficoRelatorio, btnVoltar);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("Relatórios");
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) { launch(args); }
}