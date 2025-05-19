package br.villes.aplicativoplacas.controllers;

import br.villes.aplicativoplacas.moldels.Placa;
import br.villes.aplicativoplacas.services.PlacaService;
import br.villes.aplicativoplacas.utils.Toast;
import br.villes.aplicativoplacas.utils.Validadores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.util.List;

public class TableController {
    public static PlacaService placaService = new PlacaService();
    public static ObservableList<Placa> placasList;

    @FXML
    private TableView<Placa> tabela;
    @FXML
    private TableColumn<Placa, Integer> id;
    @FXML
    private TableColumn<Placa, String> fabricante, codigo, tipo, modelo, preco, dataPostagem;
    @FXML
    private TextField fabricanteField, codigoField, tipoField, modeloField, precoField, idField, attFabricante, attCodigo, attTipo, attModelo, attPreco, pesquisaCodigoField;
    @FXML
    private AnchorPane root;

    @FXML
    public void initialize() {
        configurarColunas();
        carregarPlacas();
        placaService.setOnDataChanged(this::carregarPlacas);
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                preencherCamposComPlaca(newSelection);
                habilitarCamposEdicao(false);
            }
        });
    }

    private void carregarPlacas() {
        List<Placa> placas = placaService.lerPlacas();
        placasList = FXCollections.observableArrayList(placas);
        tabela.setItems(placasList);
    }


    @FXML
    public void adicionarPlaca() {
        if (Validadores.isTextFieldEmpty(fabricanteField, codigoField, tipoField, modeloField, precoField)) {
            mostrarAlerta("Todos os campos devem ser preenchidos!", Toast.tipoToast.ERRO);
            return;
        }

        List<Placa> placas = placaService.lerPlacas();
        int novoId = placaService.obterProximoId(placas);

        String data = LocalDate.now().toString();
        Placa novaPlaca = new Placa(novoId, fabricanteField.getText(), codigoField.getText(), tipoField.getText(),
                modeloField.getText(), Validadores.formatPrice(precoField.getText()), data);

        placas.add(novaPlaca);
        placaService.salvarPlacas(placas);

        carregarPlacas();

        Placa placaAdicionada = placasList.stream()
                .filter(p -> p.getId() == novoId)
                .findFirst()
                .orElse(null);

        if (placaAdicionada != null) {
            tabela.getSelectionModel().select(placaAdicionada);
            tabela.scrollTo(placaAdicionada);
        }

        mostrarAlerta("Placa adicionada com sucesso!", Toast.tipoToast.SUCESSO);
        limparCamposCadastro();
    }



    @FXML
    public void removerPlaca() {
        if (idField.getText().trim().isEmpty() || !idField.getText().matches("\\d+")) {
            mostrarAlerta("ID inválido!", Toast.tipoToast.ERRO);
            return;
        }

        int id = Integer.parseInt(idField.getText());
        boolean removed = placasList.removeIf(placa -> placa.getId() == id);

        if (!removed) {
            mostrarAlerta("Placa não encontrada!", Toast.tipoToast.ERRO);
            return;
        }

        placaService.salvarPlacas(placasList);
        carregarPlacas();
        mostrarAlerta("Placa removida com sucesso!", Toast.tipoToast.SUCESSO);

        limparCamposAtualizar();
    }

    @FXML
    public void atualizarPlaca() {
        if (Validadores.isTextFieldDisabled(attFabricante, attCodigo, attTipo, attModelo, attPreco)) {
            mostrarAlerta("Selecione uma placa!", Toast.tipoToast.ERRO);
            return;
        }
        if (Validadores.isTextFieldEmpty(idField, attFabricante, attCodigo, attTipo, attModelo, attPreco) || !idField.getText().matches("\\d+")) {
            mostrarAlerta("Todos os campos devem ser preenchidos corretamente!", Toast.tipoToast.ERRO);
            return;
        }

        List<Placa> placas = placaService.lerPlacas();
        int id = Integer.parseInt(idField.getText());
        boolean atualizado = false;

        for (Placa placa : placas) {
            if (placa.getId() == id) {
                placa.setFabricante(attFabricante.getText());
                placa.setCodigo(attCodigo.getText());
                placa.setTipo(attTipo.getText());
                placa.setModelo(attModelo.getText());
                placa.setPreco(Validadores.formatPrice(attPreco.getText()));
                placa.setDataPostagem(LocalDate.now().toString());
                atualizado = true;
                break;
            }
        }

        if (!atualizado) {
            mostrarAlerta("Placa não encontrada!", Toast.tipoToast.ERRO);
            return;
        }

        placaService.salvarPlacas(placas);
        carregarPlacas();
        mostrarAlerta("Placa atualizada com sucesso!", Toast.tipoToast.SUCESSO);
        habilitarCamposEdicao(false);
    }

    @FXML
    public void pesquisarPlaca() {
        String termoPesquisa = pesquisaCodigoField.getText().trim().toLowerCase();
        List<Placa> placas = placaService.lerPlacas();

        if (termoPesquisa.isEmpty()) {
            placasList.setAll(placas);
        } else {
            List<Placa> resultado = placas.stream()
                    .filter(placa -> placa.getFabricante().toLowerCase().contains(termoPesquisa) ||
                            placa.getCodigo().toLowerCase().contains(termoPesquisa) ||
                            placa.getTipo().toLowerCase().contains(termoPesquisa) ||
                            placa.getModelo().toLowerCase().contains(termoPesquisa))
                    .toList();
            placasList.setAll(resultado);
        }
    }


    @FXML
    private void preencherCampos() {
        Placa placa;

        if (Validadores.foiChamadoPorBotao(root)) {
            String idTexto = idField.getText().trim();
            if (!idTexto.matches("\\d+")) {
                mostrarAlerta("ID inválido!", Toast.tipoToast.ERRO);
                return;
            }

            int idBusca = Integer.parseInt(idTexto);
            placa = placasList.stream().filter(pl -> pl.getId() == idBusca).findFirst().orElse(null);

            if (placa == null) {
                mostrarAlerta("Placa não encontrada!", Toast.tipoToast.ERRO);
                return;
            }

            tabela.getSelectionModel().select(placa);
            tabela.scrollTo(tabela.getSelectionModel().getSelectedIndex());

            habilitarCamposEdicao(true);

        } else {
            placa = tabela.getSelectionModel().getSelectedItem();
        }
        preencherCamposComPlaca(placa);
    }
    private void habilitarCamposEdicao(boolean habilitar) {
        attFabricante.setDisable(!habilitar);
        attCodigo.setDisable(!habilitar);
        attTipo.setDisable(!habilitar);
        attModelo.setDisable(!habilitar);
        attPreco.setDisable(!habilitar);
    }
    private void preencherCamposComPlaca(Placa placa) {
        idField.setText(String.valueOf(placa.getId()));
        attFabricante.setText(placa.getFabricante());
        attCodigo.setText(placa.getCodigo());
        attTipo.setText(placa.getTipo());
        attModelo.setText(placa.getModelo());
        attPreco.setText(placa.getPreco());
    }


    private void mostrarAlerta(String mensagem, Toast.tipoToast tipo) {
        Toast.mostrarToast(root, mensagem, tipo);
    }

    private void configurarColunas() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        dataPostagem.setCellValueFactory(new PropertyValueFactory<>("dataPostagem"));
        fabricante.setCellValueFactory(new PropertyValueFactory<>("fabricante"));
        codigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        modelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        preco.setCellValueFactory(new PropertyValueFactory<>("preco"));
    }

    private void limparCamposCadastro() {
        fabricanteField.clear();
        codigoField.clear();
        tipoField.clear();
        modeloField.clear();
        precoField.clear();
    }

    private void limparCamposAtualizar() {
        idField.clear();
        attFabricante.clear();
        attCodigo.clear();
        attTipo.clear();
        attModelo.clear();
        attPreco.clear();
    }
}
