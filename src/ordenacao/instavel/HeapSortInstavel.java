package ordenacao.instavel;

import modelo.CommitModel;
import java.util.*;

/**
 * Implementação do algoritmo HeapSort INSTÁVEL para ordenação de commits.
 */
public class HeapSortInstavel {
    private int comparacoes = 0;
    
    public List<CommitModel> ordenar(List<CommitModel> commits) {
        List<CommitModel> lista = new ArrayList<>(commits);
        int n = lista.size();
        comparacoes = 0;
        
        // constroi heap máximo
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(lista, n, i);
        }
        
        // extrai elementos do heap um por um
        for (int i = n - 1; i > 0; i--) {
            Collections.swap(lista, 0, i);
            
            heapify(lista, i, 0);
        }
        
        return lista;
    }
    
    private void heapify(List<CommitModel> lista, int n, int i) {
        int maior = i;
        int esq = 2 * i + 1;
        int dir = 2 * i + 2;
        
        // se filho esquerdo é > que a raiz
        if (esq < n) {
            comparacoes++;
            if (lista.get(esq).getTimestamp().compareTo(lista.get(maior).getTimestamp()) > 0) {
                maior = esq;
            }
        }
        
        // se filho direito é > que o maior até agora
        if (dir < n) {
            comparacoes++;
            if (lista.get(dir).getTimestamp().compareTo(lista.get(maior).getTimestamp()) > 0) {
                maior = dir;
            }
        }
        
        // se o maior não é a raiz
        if (maior != i) {
            Collections.swap(lista, i, maior);
            
            // recursivamente heapify a subárvore afetada
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

