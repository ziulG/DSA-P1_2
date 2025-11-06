package estruturas;

import modelo.Commit;
import java.util.*;

/**
 * Árvore Rubro-Negra para armazenar commits agrupados por timestamp.
 * Garante O(log n) para inserção e busca com balanceamento por cores.
 */
public class ArvoreRubroNegraCommits {
    
    private enum Cor { VERMELHO, PRETO }
    
    private class NoRN {
        Date timestamp;
        List<Commit> commits;
        NoRN esquerda, direita, pai;
        Cor cor;
        
        NoRN(Date timestamp) {
            this.timestamp = timestamp;
            this.commits = new ArrayList<>();
            this.cor = Cor.VERMELHO;  // Novo nó sempre vermelho
        }
    }
    
    private NoRN raiz;
    private NoRN NIL;  // Sentinela para folhas
    
    public ArvoreRubroNegraCommits() {
        NIL = new NoRN(null);
        NIL.cor = Cor.PRETO;
        raiz = NIL;
    }
    
    /**
     * Insere um commit na árvore, agrupado por timestamp
     */
    public void inserir(Date timestamp, Commit commit) {
        NoRN no = new NoRN(timestamp);
        no.commits.add(commit);
        no.esquerda = NIL;
        no.direita = NIL;
        
        NoRN y = null;
        NoRN x = raiz;
        
        // Busca BST padrão
        while (x != NIL) {
            y = x;
            int cmp = timestamp.compareTo(x.timestamp);
            
            if (cmp < 0) {
                x = x.esquerda;
            } else if (cmp > 0) {
                x = x.direita;
            } else {
                // Timestamp já existe
                x.commits.add(commit);
                return;
            }
        }
        
        no.pai = y;
        
        if (y == null) {
            raiz = no;
        } else if (timestamp.compareTo(y.timestamp) < 0) {
            y.esquerda = no;
        } else {
            y.direita = no;
        }
        
        if (no.pai == null) {
            no.cor = Cor.PRETO;
            return;
        }
        
        if (no.pai.pai == null) {
            return;
        }
        
        corrigirInsercao(no);
    }
    
    /**
     * Corrige violações das propriedades Rubro-Negra após inserção
     */
    private void corrigirInsercao(NoRN k) {
        NoRN u;
        
        while (k.pai.cor == Cor.VERMELHO) {
            if (k.pai == k.pai.pai.direita) {
                u = k.pai.pai.esquerda;  // Tio
                
                if (u.cor == Cor.VERMELHO) {
                    // Caso 1: Tio vermelho - recoloração
                    u.cor = Cor.PRETO;
                    k.pai.cor = Cor.PRETO;
                    k.pai.pai.cor = Cor.VERMELHO;
                    k = k.pai.pai;
                } else {
                    if (k == k.pai.esquerda) {
                        // Caso 2: k é filho esquerdo - rotação direita
                        k = k.pai;
                        rotacaoDireita(k);
                    }
                    // Caso 3: k é filho direito - rotação esquerda
                    k.pai.cor = Cor.PRETO;
                    k.pai.pai.cor = Cor.VERMELHO;
                    rotacaoEsquerda(k.pai.pai);
                }
            } else {
                u = k.pai.pai.direita;  // Tio
                
                if (u.cor == Cor.VERMELHO) {
                    u.cor = Cor.PRETO;
                    k.pai.cor = Cor.PRETO;
                    k.pai.pai.cor = Cor.VERMELHO;
                    k = k.pai.pai;
                } else {
                    if (k == k.pai.direita) {
                        k = k.pai;
                        rotacaoEsquerda(k);
                    }
                    k.pai.cor = Cor.PRETO;
                    k.pai.pai.cor = Cor.VERMELHO;
                    rotacaoDireita(k.pai.pai);
                }
            }
            
            if (k == raiz) {
                break;
            }
        }
        
        raiz.cor = Cor.PRETO;
    }
    
    /**
     * Rotação à esquerda
     */
    private void rotacaoEsquerda(NoRN x) {
        NoRN y = x.direita;
        x.direita = y.esquerda;
        
        if (y.esquerda != NIL) {
            y.esquerda.pai = x;
        }
        
        y.pai = x.pai;
        
        if (x.pai == null) {
            raiz = y;
        } else if (x == x.pai.esquerda) {
            x.pai.esquerda = y;
        } else {
            x.pai.direita = y;
        }
        
        y.esquerda = x;
        x.pai = y;
    }
    
    /**
     * Rotação à direita
     */
    private void rotacaoDireita(NoRN x) {
        NoRN y = x.esquerda;
        x.esquerda = y.direita;
        
        if (y.direita != NIL) {
            y.direita.pai = x;
        }
        
        y.pai = x.pai;
        
        if (x.pai == null) {
            raiz = y;
        } else if (x == x.pai.direita) {
            x.pai.direita = y;
        } else {
            x.pai.esquerda = y;
        }
        
        y.direita = x;
        x.pai = y;
    }
    
    /**
     * Busca commits por timestamp
     */
    public List<Commit> buscar(Date timestamp) {
        return buscarRec(raiz, timestamp);
    }
    
    private List<Commit> buscarRec(NoRN no, Date timestamp) {
        if (no == NIL) return new ArrayList<>();
        
        int cmp = timestamp.compareTo(no.timestamp);
        
        if (cmp < 0) return buscarRec(no.esquerda, timestamp);
        if (cmp > 0) return buscarRec(no.direita, timestamp);
        return no.commits;
    }
    
    /**
     * Retorna todos os timestamps em ordem crescente
     */
    public List<Date> obterTimestampsOrdenados() {
        List<Date> timestamps = new ArrayList<>();
        percorrerEmOrdem(raiz, timestamps);
        return timestamps;
    }
    
    private void percorrerEmOrdem(NoRN no, List<Date> timestamps) {
        if (no != NIL) {
            percorrerEmOrdem(no.esquerda, timestamps);
            timestamps.add(no.timestamp);
            percorrerEmOrdem(no.direita, timestamps);
        }
    }
    
    /**
     * Limpa a árvore
     */
    public void limpar() {
        raiz = NIL;
    }
}

