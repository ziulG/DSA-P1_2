package util;

import modelo.CommitModel;
import java.util.*;

/**
 * Mede tempo de execução, comparações e valida estabilidade.
 */
public class AnalisadorDesempenho {
    
    public interface OrdenadorCommits {
        List<CommitModel> ordenar(List<CommitModel> commits);
        int getComparacoes();
        void resetarComparacoes();
    }
    
    // executa benchmark
    public static ResultadoBenchmark executar(
            String nomeAlgoritmo, 
            List<CommitModel> commits, 
            OrdenadorCommits ordenador) {
        
        List<CommitModel> copia = new ArrayList<>(commits);
        
        // reseta contador de comparações
        ordenador.resetarComparacoes();
        
        long inicio = System.nanoTime();
        List<CommitModel> resultado = ordenador.ordenar(copia);
        long fim = System.nanoTime();
        
        double tempoMs = (fim - inicio) / 1_000_000.0;
        int comparacoes = ordenador.getComparacoes();
        
        // valida estabilidade
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

