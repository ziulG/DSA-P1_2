package estruturas;

import modelo.CommitModel;
import java.util.*;

/**
 * Tabela Hash com encadeamento para armazenar commits agrupados por timestamp.
 */
public class TabelaHashCommits {
    
    private static class Entrada {
        Date timestamp;
        List<CommitModel> commits;
        Entrada proximo;
        
        Entrada(Date timestamp) {
            this.timestamp = timestamp;
            this.commits = new ArrayList<>();
        }
    }
    
    private Entrada[] tabela;
    private int tamanho;
    private int capacidade;
    
    public TabelaHashCommits(int capacidade) {
        this.capacidade = capacidade;
        this.tabela = new Entrada[capacidade];
        this.tamanho = 0;
    }
    
    // função hash para Date
    private int hash(Date timestamp) {
        return Math.abs(timestamp.hashCode()) % capacidade;
    }
    
    // insere commit, agrupado por timestamp
    public void inserir(Date timestamp, CommitModel commit) {
        int indice = hash(timestamp);
        Entrada atual = tabela[indice];
        
        // busca timestamp na lista encadeada
        while (atual != null) {
            if (atual.timestamp.equals(timestamp)) {
                atual.commits.add(commit);
                return;
            }
            atual = atual.proximo;
        }
        
        // cria nova entrada
        Entrada nova = new Entrada(timestamp);
        nova.commits.add(commit);
        nova.proximo = tabela[indice];
        tabela[indice] = nova;
        tamanho++;
    }
    
    // busca commits por timestamp
    public List<CommitModel> buscar(Date timestamp) {
        int indice = hash(timestamp);
        Entrada atual = tabela[indice];
        
        while (atual != null) {
            if (atual.timestamp.equals(timestamp)) {
                return atual.commits;
            }
            atual = atual.proximo;
        }
        
        return new ArrayList<>();
    }
    
    // percurso in-order
    public List<Date> obterTimestamps() {
        List<Date> timestamps = new ArrayList<>();
        for (Entrada entrada : tabela) {
            Entrada atual = entrada;
            while (atual != null) {
                timestamps.add(atual.timestamp);
                atual = atual.proximo;
            }
        }
        return timestamps;
    }
    
    public void limpar() {
        Arrays.fill(tabela, null);
        tamanho = 0;
    }
    
    public int getTamanho() {
        return tamanho;
    }
}

