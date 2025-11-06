package ordenacao.instavel;

import modelo.Commit;
import java.util.*;

/**
 * Implementação do algoritmo SelectionSort INSTÁVEL para ordenação de commits.
 * 
 * Complexidade: O(n²)
 * Estabilidade: NÃO - trocas de elementos distantes podem alterar ordem relativa
 */
public class SelectionSortInstavel {
    private int comparacoes = 0;
    
    public List<Commit> ordenar(List<Commit> commits) {
        List<Commit> lista = new ArrayList<>(commits);
        int n = lista.size();
        comparacoes = 0;
        
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            
            // Encontrar o menor elemento no restante do array
            for (int j = i + 1; j < n; j++) {
                comparacoes++;
                if (lista.get(j).getTimestamp().compareTo(lista.get(minIdx).getTimestamp()) < 0) {
                    minIdx = j;
                }
            }
            
            // Troca (causa instabilidade quando há timestamps iguais)
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

