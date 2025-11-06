package estruturas;

import modelo.Commit;
import java.util.*;

/**
 * Árvore AVL (auto-balanceada) para armazenar commits agrupados por timestamp.
 * Garante O(log n) para inserção e busca.
 */
public class ArvoreAVLCommits {
    
    private class NoAVL {
        Date timestamp;
        List<Commit> commits;
        NoAVL esquerda, direita;
        int altura;
        
        NoAVL(Date timestamp) {
            this.timestamp = timestamp;
            this.commits = new ArrayList<>();
            this.altura = 1;
        }
    }
    
    private NoAVL raiz;
    
    public ArvoreAVLCommits() {
        this.raiz = null;
    }
    
    /**
     * Insere um commit na árvore, agrupado por timestamp
     */
    public void inserir(Date timestamp, Commit commit) {
        raiz = inserirRec(raiz, timestamp, commit);
    }
    
    private NoAVL inserirRec(NoAVL no, Date timestamp, Commit commit) {
        // Inserção padrão BST
        if (no == null) {
            NoAVL novo = new NoAVL(timestamp);
            novo.commits.add(commit);
            return novo;
        }
        
        int cmp = timestamp.compareTo(no.timestamp);
        
        if (cmp < 0) {
            no.esquerda = inserirRec(no.esquerda, timestamp, commit);
        } else if (cmp > 0) {
            no.direita = inserirRec(no.direita, timestamp, commit);
        } else {
            // Timestamp já existe, adicionar commit à lista
            no.commits.add(commit);
            return no;
        }
        
        // Atualizar altura
        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));
        
        // Calcular fator de balanceamento
        int balance = getBalance(no);
        
        // Caso Left-Left (LL)
        if (balance > 1 && timestamp.compareTo(no.esquerda.timestamp) < 0) {
            return rotacaoDireita(no);
        }
        
        // Caso Right-Right (RR)
        if (balance < -1 && timestamp.compareTo(no.direita.timestamp) > 0) {
            return rotacaoEsquerda(no);
        }
        
        // Caso Left-Right (LR)
        if (balance > 1 && timestamp.compareTo(no.esquerda.timestamp) > 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }
        
        // Caso Right-Left (RL)
        if (balance < -1 && timestamp.compareTo(no.direita.timestamp) < 0) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }
        
        return no;
    }
    
    private int altura(NoAVL no) {
        return no == null ? 0 : no.altura;
    }
    
    private int getBalance(NoAVL no) {
        return no == null ? 0 : altura(no.esquerda) - altura(no.direita);
    }
    
    /**
     * Rotação simples à direita
     */
    private NoAVL rotacaoDireita(NoAVL y) {
        NoAVL x = y.esquerda;
        NoAVL T2 = x.direita;
        
        // Realizar rotação
        x.direita = y;
        y.esquerda = T2;
        
        // Atualizar alturas
        y.altura = 1 + Math.max(altura(y.esquerda), altura(y.direita));
        x.altura = 1 + Math.max(altura(x.esquerda), altura(x.direita));
        
        return x;
    }
    
    /**
     * Rotação simples à esquerda
     */
    private NoAVL rotacaoEsquerda(NoAVL x) {
        NoAVL y = x.direita;
        NoAVL T2 = y.esquerda;
        
        // Realizar rotação
        y.esquerda = x;
        x.direita = T2;
        
        // Atualizar alturas
        x.altura = 1 + Math.max(altura(x.esquerda), altura(x.direita));
        y.altura = 1 + Math.max(altura(y.esquerda), altura(y.direita));
        
        return y;
    }
    
    /**
     * Busca commits por timestamp
     */
    public List<Commit> buscar(Date timestamp) {
        return buscarRec(raiz, timestamp);
    }
    
    private List<Commit> buscarRec(NoAVL no, Date timestamp) {
        if (no == null) return new ArrayList<>();
        
        int cmp = timestamp.compareTo(no.timestamp);
        
        if (cmp < 0) return buscarRec(no.esquerda, timestamp);
        if (cmp > 0) return buscarRec(no.direita, timestamp);
        return no.commits;
    }
    
    /**
     * Retorna todos os timestamps em ordem crescente (percurso in-order)
     */
    public List<Date> obterTimestampsOrdenados() {
        List<Date> timestamps = new ArrayList<>();
        percorrerEmOrdem(raiz, timestamps);
        return timestamps;
    }
    
    private void percorrerEmOrdem(NoAVL no, List<Date> timestamps) {
        if (no != null) {
            percorrerEmOrdem(no.esquerda, timestamps);
            timestamps.add(no.timestamp);
            percorrerEmOrdem(no.direita, timestamps);
        }
    }
    
    /**
     * Limpa a árvore
     */
    public void limpar() {
        raiz = null;
    }
}

