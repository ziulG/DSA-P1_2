package estruturas;

import modelo.CommitModel;
import java.util.*;

/**
 * Árvore AVL (auto-balanceada) para armazenar commits agrupados por timestamp.
 * Garante O(log n) para inserção e busca.
 */
public class ArvoreAVLCommits {
    
    private class NoAVL {
        Date timestamp;
        List<CommitModel> commits;
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
    
    // insere commit, agrupado por timestamp
    public void inserir(Date timestamp, CommitModel commit) {
        raiz = inserirRec(raiz, timestamp, commit);
    }
    
    private NoAVL inserirRec(NoAVL no, Date timestamp, CommitModel commit) {
        // inserção padrão BST
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
            no.commits.add(commit);
            return no;
        }
        
        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));
        
        int balance = getBalance(no);
        
        // LL
        if (balance > 1 && timestamp.compareTo(no.esquerda.timestamp) < 0) {
            return rotacaoDireita(no);
        }
        
        // RR
        if (balance < -1 && timestamp.compareTo(no.direita.timestamp) > 0) {
            return rotacaoEsquerda(no);
        }
        
        // LR
        if (balance > 1 && timestamp.compareTo(no.esquerda.timestamp) > 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }
        
        // RL
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
    
   // RSR
    private NoAVL rotacaoDireita(NoAVL y) {
        NoAVL x = y.esquerda;
        NoAVL T2 = x.direita;
        
        x.direita = y;
        y.esquerda = T2;
        
        y.altura = 1 + Math.max(altura(y.esquerda), altura(y.direita));
        x.altura = 1 + Math.max(altura(x.esquerda), altura(x.direita));
        
        return x;
    }
    
   // LSR
    private NoAVL rotacaoEsquerda(NoAVL x) {
        NoAVL y = x.direita;
        NoAVL T2 = y.esquerda;
        
        y.esquerda = x;
        x.direita = T2;
        
        x.altura = 1 + Math.max(altura(x.esquerda), altura(x.direita));
        y.altura = 1 + Math.max(altura(y.esquerda), altura(y.direita));
        
        return y;
    }
    
   // busca commits por timestamp
    public List<CommitModel> buscar(Date timestamp) {
        return buscarRec(raiz, timestamp);
    }
    
    private List<CommitModel> buscarRec(NoAVL no, Date timestamp) {
        if (no == null) return new ArrayList<>();
        
        int cmp = timestamp.compareTo(no.timestamp);
        
        if (cmp < 0) return buscarRec(no.esquerda, timestamp);
        if (cmp > 0) return buscarRec(no.direita, timestamp);
        return no.commits;
    }
    
   // percurso in-order
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
    
    public void limpar() {
        raiz = null;
    }
}

