package ordenacao.estavel;

import modelo.Commit;
import estruturas.ArvoreRubroNegraCommits;
import java.util.*;

/**
 * Implementação ESTÁVEL do HeapSort usando Árvore Rubro-Negra como estrutura auxiliar.
 * 
 * Estratégia de estabilização em 3 fases:
 * 1. Agrupar commits por timestamp usando Árvore Rubro-Negra
 * 2. Ordenar timestamps únicos com HeapSort
 * 3. Reconstruir lista concatenando grupos ordenados
 * 
 * Complexidade: O(n log n) garantido
 * Estabilidade: SIM - preserva ordem original dentro de cada grupo
 */
public class HeapSortEstavel {
    private int comparacoes = 0;
    private ArvoreRubroNegraCommits arvoreRN;
    
    public HeapSortEstavel() {
        this.arvoreRN = new ArvoreRubroNegraCommits();
    }
    
    public List<Commit> ordenar(List<Commit> commits) {
        comparacoes = 0;
        arvoreRN.limpar();
        
        if (commits.isEmpty()) {
            return new ArrayList<>();
        }
        
        // FASE 1: Agrupar commits por timestamp usando Árvore Rubro-Negra
        for (Commit commit : commits) {
            arvoreRN.inserir(commit.getTimestamp(), commit);
        }
        
        // FASE 2: Obter timestamps já ordenados da RN (percurso in-order)
        List<Date> timestamps = arvoreRN.obterTimestampsOrdenados();
        
        // Aplicar HeapSort nos timestamps para demonstrar o algoritmo
        timestamps = ordenarTimestamps(timestamps);
        
        // FASE 3: Reconstruir lista preservando ordem dentro dos grupos
        List<Commit> resultado = new ArrayList<>();
        for (Date timestamp : timestamps) {
            List<Commit> grupo = arvoreRN.buscar(timestamp);
            resultado.addAll(grupo);
        }
        
        return resultado;
    }
    
    /**
     * HeapSort aplicado aos timestamps únicos
     */
    private List<Date> ordenarTimestamps(List<Date> timestamps) {
        List<Date> lista = new ArrayList<>(timestamps);
        int n = lista.size();
        
        // Construir heap máximo
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(lista, n, i);
        }
        
        // Extrair elementos do heap
        for (int i = n - 1; i > 0; i--) {
            Collections.swap(lista, 0, i);
            heapify(lista, i, 0);
        }
        
        return lista;
    }
    
    private void heapify(List<Date> lista, int n, int i) {
        int maior = i;
        int esq = 2 * i + 1;
        int dir = 2 * i + 2;
        
        if (esq < n) {
            comparacoes++;
            if (lista.get(esq).compareTo(lista.get(maior)) > 0) {
                maior = esq;
            }
        }
        
        if (dir < n) {
            comparacoes++;
            if (lista.get(dir).compareTo(lista.get(maior)) > 0) {
                maior = dir;
            }
        }
        
        if (maior != i) {
            Collections.swap(lista, i, maior);
            heapify(lista, n, maior);
        }
    }
    
    public int getComparacoes() {
        return comparacoes;
    }
    
    public void resetarComparacoes() {
        comparacoes = 0;
    }
}

