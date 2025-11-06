package util;

import modelo.Commit;
import java.util.*;

/**
 * Analisa o desempenho de algoritmos de ordenação.
 * Mede tempo de execução, comparações e valida estabilidade.
 */
public class AnalisadorDesempenho {
    
    /**
     * Interface para algoritmos de ordenação
     */
    public interface OrdenadorCommits {
        List<Commit> ordenar(List<Commit> commits);
        int getComparacoes();
        void resetarComparacoes();
    }
    
    /**
     * Executa benchmark de um algoritmo
     */
    public static ResultadoBenchmark executar(
            String nomeAlgoritmo, 
            List<Commit> commits, 
            OrdenadorCommits ordenador) {
        
        List<Commit> copia = new ArrayList<>(commits);
        
        // Resetar contador de comparações
        ordenador.resetarComparacoes();
        
        // Medir tempo
        long inicio = System.nanoTime();
        List<Commit> resultado = ordenador.ordenar(copia);
        long fim = System.nanoTime();
        
        double tempoMs = (fim - inicio) / 1_000_000.0;
        int comparacoes = ordenador.getComparacoes();
        
        // Validar estabilidade
        ValidadorEstabilidade.ResultadoValidacao validacao = 
            ValidadorEstabilidade.verificar(commits, resultado);
        
        return new ResultadoBenchmark(
            nomeAlgoritmo,
            commits.size(),
            tempoMs,
            comparacoes,
            validacao.estavel,
            validacao.mensagem,
            validacao.violacoes
        );
    }
    
    /**
     * Classe para armazenar resultado de benchmark
     */
    public static class ResultadoBenchmark {
        public final String algoritmo;
        public final int tamanhoEntrada;
        public final double tempoMs;
        public final int comparacoes;
        public final boolean estavel;
        public final String mensagemEstabilidade;
        public final List<String> violacoes;
        
        public ResultadoBenchmark(String algoritmo, int tamanhoEntrada, double tempoMs,
                                 int comparacoes, boolean estavel, String mensagemEstabilidade,
                                 List<String> violacoes) {
            this.algoritmo = algoritmo;
            this.tamanhoEntrada = tamanhoEntrada;
            this.tempoMs = tempoMs;
            this.comparacoes = comparacoes;
            this.estavel = estavel;
            this.mensagemEstabilidade = mensagemEstabilidade;
            this.violacoes = violacoes;
        }
        
        @Override
        public String toString() {
            return String.format(
                "%s (%,d commits):\n" +
                "  Tempo: %.3f ms\n" +
                "  Comparações: %,d\n" +
                "  Estável: %s\n" +
                "  %s",
                algoritmo, tamanhoEntrada, tempoMs, comparacoes,
                estavel ? "SIM ✓" : "NÃO ✗",
                mensagemEstabilidade
            );
        }
    }
}

