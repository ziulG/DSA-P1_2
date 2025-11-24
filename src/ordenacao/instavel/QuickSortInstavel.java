package ordenacao.instavel;

import modelo.CommitModel;
import java.util.*;

/**
 * Implementação do algoritmo QuickSort INSTÁVEL para ordenação de commits.
 */
public class QuickSortInstavel {
    private int comparacoes = 0;
    
    public List<CommitModel> ordenar(List<CommitModel> commits) {
        List<CommitModel> lista = new ArrayList<>(commits);
        comparacoes = 0;
        quicksort(lista, 0, lista.size() - 1);
        return lista;
    }
    
    private void quicksort(List<CommitModel> lista, int esq, int dir) {
        if (esq < dir) {
            int p = particionar(lista, esq, dir);
            quicksort(lista, esq, p - 1);
            quicksort(lista, p + 1, dir);
        }
    }
    
    private int particionar(List<CommitModel> lista, int esq, int dir) {
        // pivo central
        int meio = (esq + dir) / 2;
        CommitModel pivo = lista.get(meio);
        int i = esq - 1;
        int j = dir + 1;
        
        while (true) {
            do {
                i++;
                comparacoes++;
            } while (i <= dir && lista.get(i).getTimestamp().compareTo(pivo.getTimestamp()) < 0);
            
            do {
                j--;
                comparacoes++;
            } while (j >= esq && lista.get(j).getTimestamp().compareTo(pivo.getTimestamp()) > 0);
            
            if (i >= j) return j;
            
            // troca causa instabilidade
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

