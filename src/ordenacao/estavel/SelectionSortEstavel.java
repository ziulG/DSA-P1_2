package ordenacao.estavel;

import modelo.Commit;
import estruturas.TabelaHashCommits;
import java.util.*;

/**
 * Implementação ESTÁVEL do SelectionSort usando Tabela Hash como estrutura auxiliar.
 * 
 * Estratégia de estabilização em 3 fases:
 * 1. Agrupar commits por timestamp usando Tabela Hash
 * 2. Ordenar apenas os timestamps únicos com SelectionSort
 * 3. Reconstruir lista concatenando grupos ordenados
 * 
 * Complexidade: O(n²) - dominada pela ordenação dos timestamps
 * Estabilidade: SIM - preserva ordem original dentro de cada grupo
 */
public class SelectionSortEstavel {
    private int comparacoes = 0;
    private TabelaHashCommits tabelaHash;
    
    public SelectionSortEstavel(int capacidadeHash) {
        this.tabelaHash = new TabelaHashCommits(capacidadeHash);
    }
    
    public List<Commit> ordenar(List<Commit> commits) {
        comparacoes = 0;
        tabelaHash.limpar();
        
        if (commits.isEmpty()) {
            return new ArrayList<>();
        }
        
        // FASE 1: Agrupar commits por timestamp (mantém ordem de inserção)
        for (Commit commit : commits) {
            tabelaHash.inserir(commit.getTimestamp(), commit);
        }
        
        // FASE 2: Obter timestamps únicos e ordenar com SelectionSort
        List<Date> timestamps = tabelaHash.obterTimestamps();
        timestamps = ordenarTimestamps(timestamps);
        
        // FASE 3: Reconstruir lista preservando ordem dentro dos grupos
        List<Commit> resultado = new ArrayList<>();
        for (Date timestamp : timestamps) {
            List<Commit> grupo = tabelaHash.buscar(timestamp);
            resultado.addAll(grupo);  // Mantém ordem original do grupo
        }
        
        return resultado;
    }
    
    /**
     * SelectionSort aplicado apenas aos timestamps únicos
     */
    private List<Date> ordenarTimestamps(List<Date> timestamps) {
        List<Date> lista = new ArrayList<>(timestamps);
        int n = lista.size();
        
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            
            for (int j = i + 1; j < n; j++) {
                comparacoes++;
                if (lista.get(j).compareTo(lista.get(minIdx)) < 0) {
                    minIdx = j;
                }
            }
            
            if (minIdx != i) {
                Collections.swap(lista, i, minIdx);
            }
        }
        
        return lista;
    }
    
    public int getComparacoes() {
        return comparacoes;
    }
    
    public void resetarComparacoes() {
        comparacoes = 0;
    }
}

