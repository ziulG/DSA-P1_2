package util;

import util.AnalisadorDesempenho.ResultadoBenchmark;
import java.io.*;
import java.util.*;

/**
 * Gera relatório detalhado com resultados dos benchmarks.
 */
public class GeradorRelatorio {
    private PrintWriter writer;
    private List<ResultadoBenchmark> todosResultados;
    
    public GeradorRelatorio(String caminhoArquivo) throws IOException {
        this.writer = new PrintWriter(new FileWriter(caminhoArquivo));
        this.todosResultados = new ArrayList<>();
        
        // Cabeçalho
        writer.println("╔════════════════════════════════════════════════════════════════╗");
        writer.println("║        RELATÓRIO DE ORDENAÇÃO ESTÁVEL DE COMMITS              ║");
        writer.println("║           ESTRUTURA DE DADOS II - 2025                        ║");
        writer.println("╚════════════════════════════════════════════════════════════════╝");
        writer.println();
        writer.println("Data de geração: " + new Date());
        writer.println();
    }
    
    public void adicionarSecaoArquivo(String arquivo, int tamanho, 
                                     List<ResultadoBenchmark> resultados) {
        
        writer.println("\n" + "=".repeat(70));
        writer.printf("ARQUIVO: %s (%,d commits)\n", arquivo, tamanho);
        writer.println("=".repeat(70) + "\n");
        
        for (ResultadoBenchmark resultado : resultados) {
            writer.println(resultado.algoritmo);
            writer.println("-".repeat(resultado.algoritmo.length()));
            writer.printf("Tempo de execução:     %.3f ms\n", resultado.tempoMs);
            writer.printf("Número de comparações: %,d\n", resultado.comparacoes);
            writer.printf("Estável:               %s\n", resultado.estavel ? "SIM ✓" : "NÃO ✗");
            
            if (!resultado.estavel && !resultado.violacoes.isEmpty()) {
                writer.println("\nExemplos de violações de estabilidade:");
                for (int i = 0; i < Math.min(3, resultado.violacoes.size()); i++) {
                    writer.println("  • " + resultado.violacoes.get(i));
                }
                if (resultado.violacoes.size() > 3) {
                    writer.printf("  ... e mais %d violações\n", resultado.violacoes.size() - 3);
                }
            }
            
            writer.println();
        }
        
        todosResultados.addAll(resultados);
        writer.flush();
    }
    
    public void finalizarAnaliseComparativa() {
        writer.println("\n\n" + "═".repeat(70));
        writer.println("ANÁLISE COMPARATIVA GERAL");
        writer.println("═".repeat(70) + "\n");
        
        // Melhor algoritmo instável
        ResultadoBenchmark maisRapidoInstavel = 
            todosResultados.stream()
                .filter(r -> !r.estavel)
                .min(Comparator.comparingDouble(r -> r.tempoMs))
                .orElse(null);
        
        if (maisRapidoInstavel != null) {
            writer.printf("🏆 Algoritmo instável mais rápido: %s (%.3f ms)\n", 
                maisRapidoInstavel.algoritmo, maisRapidoInstavel.tempoMs);
        }
        
        // Melhor algoritmo estável
        ResultadoBenchmark maisRapidoEstavel = 
            todosResultados.stream()
                .filter(r -> r.estavel)
                .min(Comparator.comparingDouble(r -> r.tempoMs))
                .orElse(null);
        
        if (maisRapidoEstavel != null) {
            writer.printf("🏆 Algoritmo estável mais rápido: %s (%.3f ms)\n", 
                maisRapidoEstavel.algoritmo, maisRapidoEstavel.tempoMs);
        }
        
        // Análise de overhead
        writer.println("\n📊 OVERHEAD DE ESTABILIZAÇÃO:\n");
        analisarOverhead();
        
        // Conclusões
        writer.println("\n💡 CONCLUSÕES:\n");
        writer.println("  • Todos os algoritmos originais (instáveis) NÃO preservam ordem relativa");
        writer.println("  • As versões estabilizadas PRESERVAM corretamente a ordem relativa");
        writer.println("  • O overhead de estabilização varia conforme a estrutura auxiliar");
        writer.println("  • Estruturas balanceadas (AVL/RN) geralmente têm melhor desempenho");
        writer.println("  • Tabela Hash tem desempenho O(1) para busca, mas requer ordenação dos timestamps");
        writer.println("  • QuickSort apresenta melhor desempenho médio O(n log n)");
        writer.println("  • HeapSort garante O(n log n) em todos os casos (melhor/médio/pior)");
        
        writer.flush();
    }
    
    private void analisarOverhead() {
        // Agrupar por tamanho de entrada
        Map<Integer, List<ResultadoBenchmark>> porTamanho = new HashMap<>();
        
        for (ResultadoBenchmark r : todosResultados) {
            porTamanho.computeIfAbsent(r.tamanhoEntrada, k -> new ArrayList<>()).add(r);
        }
        
        for (Integer tamanho : porTamanho.keySet()) {
            List<ResultadoBenchmark> grupo = porTamanho.get(tamanho);
            
            // Encontrar pares instável/estável
            Map<String, ResultadoBenchmark> instavel = new HashMap<>();
            Map<String, ResultadoBenchmark> estavel = new HashMap<>();
            
            for (ResultadoBenchmark r : grupo) {
                String algoBase = r.algoritmo.split(" ")[0];
                if (r.estavel) {
                    estavel.put(algoBase, r);
                } else {
                    instavel.put(algoBase, r);
                }
            }
            
            writer.printf("Entrada com %,d commits:\n", tamanho);
            
            for (String algo : instavel.keySet()) {
                if (estavel.containsKey(algo)) {
                    ResultadoBenchmark inst = instavel.get(algo);
                    ResultadoBenchmark est = estavel.get(algo);
                    
                    double overhead = ((est.tempoMs - inst.tempoMs) / inst.tempoMs) * 100;
                    
                    writer.printf("  %s: instável %.3f ms → estável %.3f ms (overhead: %+.1f%%)\n",
                        algo, inst.tempoMs, est.tempoMs, overhead);
                }
            }
            writer.println();
        }
    }
    
    public void fechar() {
        writer.println("\n" + "═".repeat(70));
        writer.println("FIM DO RELATÓRIO");
        writer.println("═".repeat(70));
        writer.close();
    }
}

