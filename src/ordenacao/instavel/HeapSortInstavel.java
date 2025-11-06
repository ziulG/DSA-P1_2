package ordenacao.instavel;

import modelo.Commit;
import java.util.*;

/**
 * Implementação do algoritmo HeapSort INSTÁVEL para ordenação de commits.
 * 
 * Complexidade: O(n log n) garantido
 * Estabilidade: NÃO - estrutura de heap não preserva ordem relativa
 */
public class HeapSortInstavel {
    private int comparacoes = 0;
    
    public List<Commit> ordenar(List<Commit> commits) {
        List<Commit> lista = new ArrayList<>(commits);
        int n = lista.size();
        comparacoes = 0;
        
        // Construir heap máximo
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(lista, n, i);
        }
        
        // Extrair elementos do heap um por um
        for (int i = n - 1; i > 0; i--) {
            // Mover raiz atual para o fim
            Collections.swap(lista, 0, i);
            
            // Chamar heapify na heap reduzida
            heapify(lista, i, 0);
        }
        
        return lista;
    }
    
    private void heapify(List<Commit> lista, int n, int i) {
        int maior = i;
        int esq = 2 * i + 1;
        int dir = 2 * i + 2;
        
        // Se filho esquerdo é maior que a raiz
        if (esq < n) {
            comparacoes++;
            if (lista.get(esq).getTimestamp().compareTo(lista.get(maior).getTimestamp()) > 0) {
                maior = esq;
            }
        }
        
        // Se filho direito é maior que o maior até agora
        if (dir < n) {
            comparacoes++;
            if (lista.get(dir).getTimestamp().compareTo(lista.get(maior).getTimestamp()) > 0) {
                maior = dir;
            }
        }
        
        // Se o maior não é a raiz
        if (maior != i) {
            Collections.swap(lista, i, maior);
            
            // Recursivamente heapify a subárvore afetada
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

