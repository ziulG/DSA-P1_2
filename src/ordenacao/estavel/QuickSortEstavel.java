package ordenacao.estavel;

import modelo.Commit;
import estruturas.ArvoreAVLCommits;
import java.util.*;

/**
 * Implementação ESTÁVEL do QuickSort usando Árvore AVL como estrutura auxiliar.
 * 
 * Estratégia de estabilização em 3 fases:
 * 1. Agrupar commits por timestamp usando Árvore AVL
 * 2. Ordenar timestamps únicos com QuickSort
 * 3. Reconstruir lista concatenando grupos ordenados
 * 
 * Complexidade: O(n log n) médio
 * Estabilidade: SIM - preserva ordem original dentro de cada grupo
 */
public class QuickSortEstavel {
    private int comparacoes = 0;
    private ArvoreAVLCommits arvoreAVL;
    
    public QuickSortEstavel() {
        this.arvoreAVL = new ArvoreAVLCommits();
    }
    
    public List<Commit> ordenar(List<Commit> commits) {
        comparacoes = 0;
        arvoreAVL.limpar();
        
        if (commits.isEmpty()) {
            return new ArrayList<>();
        }
        
        // FASE 1: Agrupar commits por timestamp usando AVL
        for (Commit commit : commits) {
            arvoreAVL.inserir(commit.getTimestamp(), commit);
        }
        
        // FASE 2: Obter timestamps já ordenados da AVL (percurso in-order)
        // A AVL mantém os elementos ordenados naturalmente
        List<Date> timestamps = arvoreAVL.obterTimestampsOrdenados();
        
        // Opcional: aplicar QuickSort nos timestamps (já estão ordenados pela AVL)
        // Mas vamos fazer para demonstrar o algoritmo
        timestamps = ordenarTimestamps(timestamps);
        
        // FASE 3: Reconstruir lista preservando ordem dentro dos grupos
        List<Commit> resultado = new ArrayList<>();
        for (Date timestamp : timestamps) {
            List<Commit> grupo = arvoreAVL.buscar(timestamp);
            resultado.addAll(grupo);
        }
        
        return resultado;
    }
    
    /**
     * QuickSort aplicado aos timestamps únicos
     */
    private List<Date> ordenarTimestamps(List<Date> timestamps) {
        List<Date> lista = new ArrayList<>(timestamps);
        quicksort(lista, 0, lista.size() - 1);
        return lista;
    }
    
    private void quicksort(List<Date> lista, int esq, int dir) {
        if (esq < dir) {
            int p = particionar(lista, esq, dir);
            quicksort(lista, esq, p - 1);
            quicksort(lista, p + 1, dir);
        }
    }
    
    private int particionar(List<Date> lista, int esq, int dir) {
        int meio = (esq + dir) / 2;
        Date pivo = lista.get(meio);
        int i = esq - 1;
        int j = dir + 1;
        
        while (true) {
            do {
                i++;
                comparacoes++;
            } while (i <= dir && lista.get(i).compareTo(pivo) < 0);
            
            do {
                j--;
                comparacoes++;
            } while (j >= esq && lista.get(j).compareTo(pivo) > 0);
            
            if (i >= j) return j;
            
            Collections.swap(lista, i, j);
        }
    }
    
    public int getComparacoes() {
        return comparacoes;
    }
    
    public void resetarComparacoes() {
        comparacoes = 0;
    }
}

