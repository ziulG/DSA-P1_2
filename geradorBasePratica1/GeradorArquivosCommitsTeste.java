import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class GeradorArquivosCommitsTeste {
    
    public static void main(String[] args) {
        System.out.println("=== GERADOR DE ARQUIVOS DE TESTE PARA COMMITS ===\n");
        
        // Criar diretório para os arquivos
        File diretorio = new File("dados_teste");
        if (!diretorio.exists()) {
            diretorio.mkdir();
        }
        
        // Gerar bases de diferentes tamanhos
        System.out.println("Gerando arquivos de teste...");
        
        List<Commit> commits1000 = gerarCommits(1000, 50);
        salvarCommitsJSON(commits1000, "dados_teste/commits_1000.json");
        
        List<Commit> commits10000 = gerarCommits(10000, 200);
        salvarCommitsJSON(commits10000, "dados_teste/commits_10000.json");
        
        List<Commit> commits100000 = gerarCommits(100000, 1000);
        salvarCommitsJSON(commits100000, "dados_teste/commits_100000.json");
        
        // Gerar cenário crítico
        List<Commit> commitsCriticos = gerarCenarioCritico(500);
        salvarCommitsJSON(commitsCriticos, "dados_teste/commits_criticos.json");
        
        // Gerar arquivo de estatísticas
        gerarArquivoEstatisticas();
        
        System.out.println("\nArquivos gerados com sucesso na pasta 'dados_teste/'");
        listarArquivosGerados();
    }
    
    public static List<Commit> gerarCommits(int quantidade, int timestampsUnicos) {
        List<Commit> commits = new ArrayList<>();
        Random random = new Random(42);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Gerar timestamps base
        Date[] timestampsBase = new Date[timestampsUnicos];
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.JANUARY, 1, 0, 0, 0);
        
        for (int i = 0; i < timestampsUnicos; i++) {
            int diasOffset = random.nextInt(30);
            int horasOffset = random.nextInt(24);
            int minutosOffset = random.nextInt(60);
            
            Calendar timestampCal = (Calendar) cal.clone();
            timestampCal.add(Calendar.DAY_OF_YEAR, diasOffset);
            timestampCal.add(Calendar.HOUR_OF_DAY, horasOffset);
            timestampCal.add(Calendar.MINUTE, minutosOffset);
            
            timestampsBase[i] = timestampCal.getTime();
        }
        
        // Arrays para dados aleatórios
        String[] autores = {"alice", "paulo", "carol", "marcos", "eva", "joao", "luis", "maria", "lucas", "pedro"};
        String[] branches = {"main", "develop", "feature/auth", "feature/ui", "hotfix/bug", "release/v1.0"};
        String[] tiposMensagem = {
            "fix: corrige bug em ", "feat: adiciona funcionalidade ", "refactor: melhora código de ",
            "docs: atualiza documentação de ", "test: adiciona testes para ", "style: formata código de "
        };
        String[] modulos = {"autenticação", "frontEnd", "banco de dados", "backEnd", "API", "serviço de email", "cache"};
        
        // Gerar commits
        for (int i = 0; i < quantidade; i++) {
            Date timestamp = timestampsBase[random.nextInt(timestampsUnicos)];
            String hash = gerarHash(random);
            String autor = autores[random.nextInt(autores.length)];
            String mensagem = gerarMensagemCommit(random, tiposMensagem, modulos);
            List<String> arquivosAlterados = gerarArquivosAlterados(random);
            String branch = branches[random.nextInt(branches.length)];
            
            Commit commit = new Commit(hash, autor, mensagem, timestamp, i, arquivosAlterados, branch);
            commits.add(commit);
        }
        
        Collections.shuffle(commits, new Random(123));
        return commits;
    }
    
    public static List<Commit> gerarCenarioCritico(int quantidade) {
        List<Commit> commits = new ArrayList<>();
        Random random = new Random(99);
        
        Date mesmoTimestamp;
        try {
            mesmoTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2024-01-15 10:00:00");
        } catch (Exception e) {
            mesmoTimestamp = new Date();
        }
        
        String[] autores = {"bot", "sistema", "ci-cd"};
        String[] modulos = {"build", "deploy", "config", "database"};
        
        for (int i = 0; i < quantidade; i++) {
            String hash = "crit" + i + "-" + gerarHash(random);
            String autor = autores[random.nextInt(autores.length)];
            String mensagem = "auto: atualização automática em " + modulos[random.nextInt(modulos.length)];
            
            List<String> arquivos = Arrays.asList(
                "pom.xml", "package.json", "Dockerfile", "config.properties"
            );
            
            Commit commit = new Commit(hash, autor, mensagem, mesmoTimestamp, i, arquivos, "main");
            commits.add(commit);
        }
        
        return commits;
    }
    
    private static String gerarHash(Random random) {
        StringBuilder hash = new StringBuilder();
        String chars = "0123456789abcdef";
        for (int i = 0; i < 7; i++) {
            hash.append(chars.charAt(random.nextInt(chars.length())));
        }
        return hash.toString();
    }
    
    private static String gerarMensagemCommit(Random random, String[] tipos, String[] modulos) {
        String tipo = tipos[random.nextInt(tipos.length)];
        String modulo = modulos[random.nextInt(modulos.length)];
        
        if (random.nextDouble() < 0.3) {
            String[] detalhes = {
                "melhorando performance", "adicionando validações", "corrigindo race condition",
                "adicionando logs", "otimizando consultas", "melhorando tratamento de erro"
            };
            return tipo + modulo + " - " + detalhes[random.nextInt(detalhes.length)];
        }
        
        return tipo + modulo;
    }
    
    private static List<String> gerarArquivosAlterados(Random random) {
        List<String> arquivos = new ArrayList<>();
        String[] diretorios = {"src/", "test/", "docs/", "config/", "scripts/"};
        String[] extensoes = {".java", ".xml", ".md", ".yml", ".js", ".html", ".css"};
        
        int numArquivos = 1 + random.nextInt(5);
        
        for (int i = 0; i < numArquivos; i++) {
            String diretorio = diretorios[random.nextInt(diretorios.length)];
            String nomeArquivo = gerarNomeArquivo(random);
            String extensao = extensoes[random.nextInt(extensoes.length)];
            arquivos.add(diretorio + nomeArquivo + extensao);
        }
        
        return arquivos;
    }
    
    private static String gerarNomeArquivo(Random random) {
        String[] prefixos = {"User", "Product", "Order", "Auth", "Database", "Service", "Util", "Config"};
        String[] sufixos = {"Manager", "Service", "Helper", "Factory", "Repository", "Controller", "Model"};
        
        if (random.nextDouble() < 0.7) {
            return prefixos[random.nextInt(prefixos.length)] + sufixos[random.nextInt(sufixos.length)];
        } else {
            return prefixos[random.nextInt(prefixos.length)];
        }
    }
    

    
    public static void salvarCommitsJSON(List<Commit> commits, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("[");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            for (int i = 0; i < commits.size(); i++) {
                Commit commit = commits.get(i);
                writer.println("  {");
                writer.printf("    \"hash\": \"%s\",%n", commit.getHash());
                writer.printf("    \"autor\": \"%s\",%n", commit.getAutor());
                writer.printf("    \"mensagem\": \"%s\",%n", 
                    commit.getMensagem().replace("\"", "\\\""));
                writer.printf("    \"timestamp\": \"%s\",%n", sdf.format(commit.getTimestamp()));
                writer.printf("    \"ordem_original\": %d,%n", commit.getOrdemOriginal());
                writer.printf("    \"branch\": \"%s\",%n", commit.getBranch());
                writer.printf("    \"arquivos_alterados\": [%n");
                
                List<String> arquivos = commit.getArquivosAlterados();
                for (int j = 0; j < arquivos.size(); j++) {
                    writer.printf("      \"%s\"%s%n", 
                        arquivos.get(j), 
                        (j < arquivos.size() - 1) ? "," : "");
                }
                writer.printf("    ]%n");
                writer.printf("  }%s%n", (i < commits.size() - 1) ? "," : "");
            }
            
            writer.println("]");
            System.out.println("✓ JSON gerado: " + filename);
        } catch (IOException e) {
            System.err.println("Erro ao salvar JSON: " + e.getMessage());
        }
    }
    
    public static void gerarArquivoEstatisticas() {
        List<Commit> commits1000 = gerarCommits(1000, 50);
        List<Commit> commits10000 = gerarCommits(10000, 200);
        List<Commit> commits100000 = gerarCommits(100000, 1000);
        List<Commit> commitsCriticos = gerarCenarioCritico(500);
        
        try (PrintWriter writer = new PrintWriter(new FileWriter("dados_teste/estatisticas.txt"))) {
            writer.println("=== ESTATÍSTICAS DOS ARQUIVOS DE TESTE ===");
            writer.println();
            
            writer.println("1. COMMITS_1000.JSON");
            writer.println(gerarEstatisticasTexto("1000 commits", commits1000));
            writer.println();
            
            writer.println("2. COMMITS_10000.JSON");
            writer.println(gerarEstatisticasTexto("10000 commits", commits10000));
            writer.println();
            
            writer.println("3. COMMITS_100000.JSON");
            writer.println(gerarEstatisticasTexto("100000 commits", commits100000));
            writer.println();
            
            writer.println("4. COMMITS_CRITICOS.JSON");
            writer.println(gerarEstatisticasTexto("500 commits críticos", commitsCriticos));
            writer.println();
            
            writer.println("=== INSTRUÇÕES DE USO ===");
            writer.println("CSV: Use com Excel, Python pandas, ou qualquer leitor de CSV");
            writer.println("JSON: Use para aplicações web ou parsing fácil");
            writer.println("Binário: Use com ObjectInputStream em Java para carregamento rápido");
            writer.println();
            writer.println("Gerado em: " + new Date());
            
            System.out.println("Estatísticas geradas: dados_teste/estatisticas.txt");
        } catch (IOException e) {
            System.err.println("Erro ao salvar estatísticas: " + e.getMessage());
        }
    }
    
    private static String gerarEstatisticasTexto(String titulo, List<Commit> commits) {
        StringBuilder stats = new StringBuilder();
        
        Set<Date> timestampsUnicos = new HashSet<>();
        Map<Date, Integer> commitsPorTimestamp = new HashMap<>();
        Set<String> autoresUnicos = new HashSet<>();
        Set<String> branchesUnicos = new HashSet<>();
        
        for (Commit commit : commits) {
            timestampsUnicos.add(commit.getTimestamp());
            commitsPorTimestamp.merge(commit.getTimestamp(), 1, Integer::sum);
            autoresUnicos.add(commit.getAutor());
            branchesUnicos.add(commit.getBranch());
        }
        
        int maxCommitsMesmoTimestamp = Collections.max(commitsPorTimestamp.values());
        double mediaCommitsPorTimestamp = (double) commits.size() / timestampsUnicos.size();
        
        stats.append(String.format("Total de commits: %,d%n", commits.size()));
        stats.append(String.format("Timestamps únicos: %,d%n", timestampsUnicos.size()));
        stats.append(String.format("Máximo commits/timestamp: %,d%n", maxCommitsMesmoTimestamp));
        stats.append(String.format("Média commits/timestamp: %.2f%n", mediaCommitsPorTimestamp));
        stats.append(String.format("Autores únicos: %,d%n", autoresUnicos.size()));
        stats.append(String.format("Branches únicos: %,d%n", branchesUnicos.size()));
        
        // Top 3 timestamps com mais commits
        stats.append("Top 3 timestamps com mais commits:\n");
        commitsPorTimestamp.entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .limit(3)
            .forEach(entry -> {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                stats.append(String.format("  %s: %,d commits%n", 
                    sdf.format(entry.getKey()), entry.getValue()));
            });
        
        return stats.toString();
    }
    
    private static void listarArquivosGerados() {
        File diretorio = new File("dados_teste");
        File[] arquivos = diretorio.listFiles();
        
        if (arquivos != null) {
            System.out.println("\nArquivos gerados:");
            Arrays.stream(arquivos)
                  .sorted(Comparator.comparing(File::getName))
                  .forEach(file -> {
                      double sizeKB = file.length() / 1024.0;
                      System.out.printf("  %-25s %,8.1f KB%n", file.getName(), sizeKB);
                  });
        }
    }
    
}