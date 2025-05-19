package br.villes.aplicativoplacas.services;

import br.villes.aplicativoplacas.moldels.Placa;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PlacaService {
    private static final String JSON_FILE = "placas.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File file = new File(JSON_FILE);
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private long ultimaModificacao = 0;
    private Runnable listener;

    public PlacaService() {
        iniciarMonitoramento();
    }

    private void iniciarMonitoramento() {
        scheduler.scheduleAtFixedRate(() -> {
            if (file.exists()) {
                long ultimaAlteracao = file.lastModified();
                if (ultimaAlteracao > ultimaModificacao) {
                    ultimaModificacao = ultimaAlteracao;
                    System.out.println("Arquivo atualizado. Recarregando tabela...");
                    if (listener != null) {
                        listener.run();
                    }
                }
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    public void setOnDataChanged(Runnable listener) {
        this.listener = listener;
    }

    public List<Placa> lerPlacas() {
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void salvarPlacas(List<Placa> placas) {
        try {
            objectMapper.writeValue(file, placas);
        } catch (IOException e) {
            System.err.println("Erro ao salvar no arquivo JSON: " + e.getMessage());
        }
    }

    public int obterProximoId(List<Placa> placas) {
        Set<Integer> idsExistentes = placas.stream()
                .map(Placa::getId)
                .collect(Collectors.toSet());

        return IntStream.rangeClosed(1, idsExistentes.size() + 1)
                .filter(id -> !idsExistentes.contains(id))
                .findFirst()
                .orElse(1);
    }

    public void encerrarMonitoramento() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }
}
