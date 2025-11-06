package ordenacao.instavel;

import modelo.Commit;
import java.util.*;

/**
 * Implementação do algoritmo QuickSort INSTÁVEL para ordenação de commits.
 * 
 * Complexidade: O(n log n) médio, O(n²) pior caso
 * Estabilidade: NÃO - particionamento pode alterar ordem relativa
 */
public class QuickSortInstavel {
    private int comparacoes = 0;
    
    public List<Commit> ordenar(List<Commit> commits) {
        List<Commit> lista = new ArrayList<>(commits);
        comparacoes = 0;
        quicksort(lista, 0, lista.size() - 1);
        return lista;
    }
    
    private void quicksort(List<Commit> lista, int esq, int dir) {
        if (esq < dir) {
            int p = particionar(lista, esq, dir);
            quicksort(lista, esq, p - 1);
            quicksort(lista, p + 1, dir);
        }
    }
    
    private int particionar(List<Commit> lista, int esq, int dir) {
        // Pivô central para melhorar performance média
        int meio = (esq + dir) / 2;
        Commit pivo = lista.get(meio);
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
            
            // Troca causa instabilidade
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

