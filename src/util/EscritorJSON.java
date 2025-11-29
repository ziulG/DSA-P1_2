package util;

import modelo.CommitModel;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class EscritorJSON {
    
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    // Escreve uma lista de commits em um arquivo JSON.
    public static void escreverCommits(List<CommitModel> commits, String caminhoArquivo) 
            throws IOException {
        
        // cria diretório se não existir
        File arquivo = new File(caminhoArquivo);
        File diretorio = arquivo.getParentFile();
        if (diretorio != null && !diretorio.exists()) {
            diretorio.mkdirs();
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            writer.write("[\n");
            
            for (int i = 0; i < commits.size(); i++) {
                CommitModel commit = commits.get(i);
                
                writer.write("  {\n");
                writer.write(String.format("    \"hash\": \"%s\",\n", escaparString(commit.getHash())));
                writer.write(String.format("    \"autor\": \"%s\",\n", escaparString(commit.getAutor())));
                writer.write(String.format("    \"mensagem\": \"%s\",\n", escaparString(commit.getMensagem())));
                writer.write(String.format("    \"timestamp\": \"%s\",\n", sdf.format(commit.getTimestamp())));
                writer.write(String.format("    \"ordem_original\": %d,\n", commit.getOrdemOriginal()));
                writer.write(String.format("    \"branch\": \"%s\",\n", escaparString(commit.getBranch())));
                
                writer.write("    \"arquivos_alterados\": [");
                List<String> arquivos = commit.getArquivosAlterados();
                if (arquivos != null && !arquivos.isEmpty()) {
                    writer.write("\n");
                    for (int j = 0; j < arquivos.size(); j++) {
                        writer.write(String.format("      \"%s\"", escaparString(arquivos.get(j))));
                        if (j < arquivos.size() - 1) {
                            writer.write(",");
                        }
                        writer.write("\n");
                    }
                    writer.write("    ");
                }
                writer.write("]\n");
                
                writer.write("  }");
                
                if (i < commits.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }
            
            writer.write("]\n");
        }
    }
    
    // Escapa caracteres especiais para JSON.
    private static String escaparString(String str) {
        if (str == null) return "";
        
        return str
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");
    }
    
    // Gera nome do arquivo de saída baseado no arquivo original e algoritmo usado.
    public static String gerarNomeArquivoSaida(String arquivoOriginal, String nomeAlgoritmo) {
        String nomeBase = new File(arquivoOriginal).getName();
        nomeBase = nomeBase.replace(".json", "");
        
        // determina se é estável ou instável
        String subpasta = nomeAlgoritmo.toLowerCase().contains("instável") ? "instaveis" : "estaveis";
        
        // extrai apenas o nome do algoritmo (ex: SelectionSort, QuickSort, HeapSort)
        String nomeAlgo = nomeAlgoritmo.split(" ")[0].toLowerCase();
        
        // extrai a estrutura auxiliar se existir (Hash, AVL, Rubro-Negra)
        String estrutura = "";
        if (nomeAlgoritmo.contains("(") && nomeAlgoritmo.contains(")")) {
            estrutura = "_" + nomeAlgoritmo
                .substring(nomeAlgoritmo.indexOf("(") + 1, nomeAlgoritmo.indexOf(")"))
                .toLowerCase()
                .replace("-", "");
        }
        
        return String.format("dados_teste/ordenados/%s/%s_%s%s.json", 
                            subpasta, nomeBase, nomeAlgo, estrutura);
    }
}

