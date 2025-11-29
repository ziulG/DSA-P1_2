package ordenacao.estavel;

import modelo.CommitModel;
import estruturas.ArvoreAVLCommits;
import java.util.*;

/**
 * Implementação ESTÁVEL do QuickSort usando Árvore AVL como auxiliar.
 * 
 * 1 - Agrupar commits por timestamp usando Árvore AVL
 * 2 - Ordenar timestamps únicos com QuickSort
 * 3 - Reconstruir lista concatenando grupos ordenados
 * 
 */
public class QuickSortEstavel {
    private int comparacoes = 0;
    private ArvoreAVLCommits arvoreAVL;
    
    public QuickSortEstavel() {
        this.arvoreAVL = new ArvoreAVLCommits();
    }
    
    public List<CommitModel> ordenar(List<CommitModel> commits) {
        comparacoes = 0;
        arvoreAVL.limpar();
        
        if (commits.isEmpty()) {
            return new ArrayList<>();
        }
        
        // agrupa commits por timestamp -> Árvore AVL
        for (CommitModel commit : commits) {
            arvoreAVL.inserir(commit.getTimestamp(), commit);
        }
        
        // obtem timestamps ordenados da AVL (percurso in-order)
        List<Date> timestamps = arvoreAVL.obterTimestampsOrdenados();
        
        // aplica QuickSort nos timestamps (já ordenados pela AVL)
        timestamps = ordenarTimestamps(timestamps);
        
        // reconstrui lista preservando ordem dos grupos
        List<CommitModel> resultado = new ArrayList<>();
        for (Date timestamp : timestamps) {
            List<CommitModel> grupo = arvoreAVL.buscar(timestamp);
            resultado.addAll(grupo);
        }
        
        return resultado;
    }
    
    // QuickSort aplicado aos timestamps únicos
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
        Date pivo = lista.get(meio); // pivo no meio
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

