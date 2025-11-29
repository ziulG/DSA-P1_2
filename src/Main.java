import modelo.CommitModel;
import ordenacao.instavel.*;
import ordenacao.estavel.*;
import util.*;
import util.AnalisadorDesempenho.*;

import java.io.File;
import java.util.*;

/**
 * Executa todos os algoritmos (instáveis e estáveis) e gera relatório comparativo.
 */
public class Main {
    
    public static void main(String[] args) {
        try {
            System.out.println("╔═══════════════════════════════════════════════════════════╗");
            System.out.println("║   SISTEMA DE ORDENAÇÃO ESTÁVEL DE COMMITS - ED II         ║");
            System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
            
            String[] arquivos = {
                "dados_teste/commits_1000.json",
                "dados_teste/commits_10000.json",
                "dados_teste/commits_100000.json"
            };
            
            // cria diretórios para arquivos ordenados
            File dirEstaveis = new File("dados_teste/ordenados/estaveis");
            File dirInstaveis = new File("dados_teste/ordenados/instaveis");
            if (!dirEstaveis.exists()) dirEstaveis.mkdirs();
            if (!dirInstaveis.exists()) dirInstaveis.mkdirs();
            
            GeradorRelatorio relatorio = new GeradorRelatorio("relatorio.txt");
            
            // processa cada arquivo
            for (String arquivo : arquivos) {
                if (new java.io.File(arquivo).exists()) {
                    processarArquivo(arquivo, relatorio);
                } else {
                    System.out.println("Arquivo não encontrado: " + arquivo);
                }
            }
            
            // finaliza relatorio
            relatorio.finalizarAnaliseComparativa();
            relatorio.fechar();
            
            System.out.println("\nRelatório completo gerado em: relatorio.txt");
            
        } catch (Exception e) {
            System.err.println("Erro na execução: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void processarArquivo(String caminho, GeradorRelatorio relatorio) 
            throws Exception {
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("PROCESSANDO: " + caminho);
        System.out.println("=".repeat(60));
        
        // carrega commits
        List<CommitModel> commits = LeitorJSON.lerCommits(caminho);
        System.out.printf("Carregados %,d commits\n\n", commits.size());
        
        // inicializa algoritmos com adaptadores
        Map<String, OrdenadorCommits> algoritmos = new LinkedHashMap<>();
        
        // versões instaveis
        SelectionSortInstavel selectionInst = new SelectionSortInstavel();
        QuickSortInstavel quickInst = new QuickSortInstavel();
        HeapSortInstavel heapInst = new HeapSortInstavel();
        
        algoritmos.put("SelectionSort Original (Instável)", new OrdenadorAdapter() {
            public List<CommitModel> ordenar(List<CommitModel> c) { return selectionInst.ordenar(c); }
            public int getComparacoes() { return selectionInst.getComparacoes(); }
            public void resetarComparacoes() { selectionInst.resetarComparacoes(); }
        });
        
        algoritmos.put("QuickSort Original (Instável)", new OrdenadorAdapter() {
            public List<CommitModel> ordenar(List<CommitModel> c) { return quickInst.ordenar(c); }
            public int getComparacoes() { return quickInst.getComparacoes(); }
            public void resetarComparacoes() { quickInst.resetarComparacoes(); }
        });
        
        algoritmos.put("HeapSort Original (Instável)", new OrdenadorAdapter() {
            public List<CommitModel> ordenar(List<CommitModel> c) { return heapInst.ordenar(c); }
            public int getComparacoes() { return heapInst.getComparacoes(); }
            public void resetarComparacoes() { heapInst.resetarComparacoes(); }
        });
        
        // versões estaveis
        SelectionSortEstavel selectionEst = new SelectionSortEstavel(10000);
        QuickSortEstavel quickEst = new QuickSortEstavel();
        HeapSortEstavel heapEst = new HeapSortEstavel();
        
        algoritmos.put("SelectionSort Estável (Hash)", new OrdenadorAdapter() {
            public List<CommitModel> ordenar(List<CommitModel> c) { return selectionEst.ordenar(c); }
            public int getComparacoes() { return selectionEst.getComparacoes(); }
            public void resetarComparacoes() { selectionEst.resetarComparacoes(); }
        });
        
        algoritmos.put("QuickSort Estável (AVL)", new OrdenadorAdapter() {
            public List<CommitModel> ordenar(List<CommitModel> c) { return quickEst.ordenar(c); }
            public int getComparacoes() { return quickEst.getComparacoes(); }
            public void resetarComparacoes() { quickEst.resetarComparacoes(); }
        });
        
        algoritmos.put("HeapSort Estável (Rubro-Negra)", new OrdenadorAdapter() {
            public List<CommitModel> ordenar(List<CommitModel> c) { return heapEst.ordenar(c); }
            public int getComparacoes() { return heapEst.getComparacoes(); }
            public void resetarComparacoes() { heapEst.resetarComparacoes(); }
        });
        
        // executa benchmarks
        List<ResultadoBenchmark> resultados = new ArrayList<>();
        int arquivosExportados = 0;
        
        for (Map.Entry<String, OrdenadorCommits> entry : algoritmos.entrySet()) {
            String nome = entry.getKey();
            OrdenadorCommits ordenador = entry.getValue();
            
            System.out.printf("Executando: %s...\n", nome);
            
            // copia para não alterar a lista original
            List<CommitModel> copiaCommits = new ArrayList<>(commits);
            
            // executa ordenação e benchmark
            ResultadoBenchmark resultado = 
                AnalisadorDesempenho.executar(nome, copiaCommits, ordenador);
            
            resultados.add(resultado);
            
            System.out.println(resultado);
            
            // exporta commits ordenados para JSON
            String arquivoSaida = EscritorJSON.gerarNomeArquivoSaida(caminho, nome);
            List<CommitModel> commitsOrdenados = resultado.listaOrdenada();
            
            if (commitsOrdenados != null && !commitsOrdenados.isEmpty()) {
                EscritorJSON.escreverCommits(commitsOrdenados, arquivoSaida);
                System.out.printf("  → Exportado: %s\n", arquivoSaida);
                arquivosExportados++;
            }
            
            System.out.println();
        }
        
        System.out.printf("%d arquivos JSON ordenados exportados para dados_teste/ordenados/\n", 
                          arquivosExportados);
        
        // adiciona ao relatorio
        relatorio.adicionarSecaoArquivo(caminho, commits.size(), resultados);
        
        // analise de overhead
        analisarOverhead(resultados);
    }
    
    private static void analisarOverhead(List<ResultadoBenchmark> resultados) {
        System.out.println("\nANÁLISE DE OVERHEAD DA ESTABILIZAÇÃO:");
        System.out.println("-".repeat(60));
        
        // compara versões estaveis vs instaveis para cada algoritmo
        for (int i = 0; i < 3; i++) {
            ResultadoBenchmark instavel = resultados.get(i);
            ResultadoBenchmark estavel = resultados.get(i + 3);
            
            double overhead = ((estavel.tempoMs() - instavel.tempoMs()) / instavel.tempoMs()) * 100;
            
            String nomeAlgo = instavel.algoritmo().split(" ")[0];
            System.out.printf("%s:\n", nomeAlgo);
            System.out.printf("  Instável:  %.3f ms\n", instavel.tempoMs());
            System.out.printf("  Estável:   %.3f ms\n", estavel.tempoMs());
            System.out.printf("  Overhead:  %+.1f%%\n\n", overhead);
        }
    }
    
    /**
     * Classe adaptadora para unificar interface dos ordenadores
     */
    private static abstract class OrdenadorAdapter implements OrdenadorCommits {
        public abstract List<CommitModel> ordenar(List<CommitModel> commits);
        public abstract int getComparacoes();
        public abstract void resetarComparacoes();
    }
}

