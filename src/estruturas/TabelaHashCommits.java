package estruturas;

import modelo.Commit;
import java.util.*;

/**
 * Tabela Hash com encadeamento para armazenar commits agrupados por timestamp.
 * Usada para garantir estabilidade em algoritmos de ordenação.
 */
public class TabelaHashCommits {
    
    private static class Entrada {
        Date timestamp;
        List<Commit> commits;
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
    
    /**
     * Função hash para Date
     */
    private int hash(Date timestamp) {
        return Math.abs(timestamp.hashCode()) % capacidade;
    }
    
    /**
     * Insere um commit na tabela, agrupado por timestamp
     */
    public void inserir(Date timestamp, Commit commit) {
        int indice = hash(timestamp);
        Entrada atual = tabela[indice];
        
        // Buscar timestamp existente na lista encadeada
        while (atual != null) {
            if (atual.timestamp.equals(timestamp)) {
                atual.commits.add(commit);
                return;
            }
            atual = atual.proximo;
        }
        
        // Criar nova entrada (inserção no início da lista)
        Entrada nova = new Entrada(timestamp);
        nova.commits.add(commit);
        nova.proximo = tabela[indice];
        tabela[indice] = nova;
        tamanho++;
    }
    
    /**
     * Busca todos os commits com um determinado timestamp
     */
    public List<Commit> buscar(Date timestamp) {
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
    
    /**
     * Retorna todos os timestamps armazenados na tabela
     */
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
    
    /**
     * Limpa toda a tabela
     */
    public void limpar() {
        Arrays.fill(tabela, null);
        tamanho = 0;
    }
    
    public int getTamanho() {
        return tamanho;
    }
}

