package util;

import modelo.CommitModel;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * implementação manual para parsing de JSON simples.
 */
public class LeitorJSON {
    
    public static List<CommitModel> lerCommits(String caminhoArquivo) throws IOException {
        List<CommitModel> commits = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            StringBuilder json = new StringBuilder();
            String linha;
            
            while ((linha = reader.readLine()) != null) {
                json.append(linha.trim());
            }
            
            // parse manual do JSON
            commits = parseCommitsJSON(json.toString());
        }
        
        return commits;
    }
    
    private static List<CommitModel> parseCommitsJSON(String json) {
        List<CommitModel> commits = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // remove colchetes externos
        json = json.substring(json.indexOf('[') + 1, json.lastIndexOf(']'));
        
        // divide por objetos
        List<String> objetos = extrairObjetos(json);
        
        for (String obj : objetos) {
            try {
                String hash = extrairValor(obj, "hash");
                String autor = extrairValor(obj, "autor");
                String mensagem = extrairValor(obj, "mensagem");
                String timestampStr = extrairValor(obj, "timestamp");
                String ordemStr = extrairValor(obj, "ordem_original");
                String branch = extrairValor(obj, "branch");
                
                Date timestamp = sdf.parse(timestampStr);
                int ordemOriginal = Integer.parseInt(ordemStr);
                
                List<String> arquivos = extrairArray(obj, "arquivos_alterados");
                
                CommitModel commit = new CommitModel(hash, autor, mensagem, timestamp, 
                    ordemOriginal, arquivos, branch);
                commits.add(commit);
                
            } catch (Exception e) {
                System.err.println("Erro ao parsear commit: " + e.getMessage());
            }
        }
        
        return commits;
    }
    
    // extrai objetos individuais do JSON
    private static List<String> extrairObjetos(String json) {
        List<String> objetos = new ArrayList<>();
        int nivel = 0;
        int inicio = -1;
        
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            
            if (c == '{') {
                if (nivel == 0) inicio = i;
                nivel++;
            } else if (c == '}') {
                nivel--;
                if (nivel == 0 && inicio != -1) {
                    objetos.add(json.substring(inicio, i + 1));
                    inicio = -1;
                }
            }
        }
        
        return objetos;
    }
    
    // extrai valor de uma chave no objeto JSON
    private static String extrairValor(String obj, String chave) {
        String busca = "\"" + chave + "\":";
        int inicio = obj.indexOf(busca);
        
        if (inicio == -1) return "";
        
        inicio += busca.length();
        
        // pula espaços
        while (inicio < obj.length() && Character.isWhitespace(obj.charAt(inicio))) {
            inicio++;
        }
        
        // se for string (começa com ")
        if (obj.charAt(inicio) == '"') {
            inicio++;
            int fim = obj.indexOf('"', inicio);
            return obj.substring(inicio, fim);
        } else {
            // se for numero
            int fim = inicio;
            while (fim < obj.length() && (Character.isDigit(obj.charAt(fim)) || obj.charAt(fim) == '-')) {
                fim++;
            }
            return obj.substring(inicio, fim);
        }
    }
    
    // extrai array de strings do JSON
    private static List<String> extrairArray(String obj, String chave) {
        List<String> resultado = new ArrayList<>();
        String busca = "\"" + chave + "\":";
        int inicio = obj.indexOf(busca);
        
        if (inicio == -1) return resultado;
        
        inicio = obj.indexOf('[', inicio);
        int fim = obj.indexOf(']', inicio);
        
        if (inicio == -1 || fim == -1) return resultado;
        
        String arrayStr = obj.substring(inicio + 1, fim);
        
        // extrai elementos do array
        int pos = 0;
        while (pos < arrayStr.length()) {
            int inicioStr = arrayStr.indexOf('"', pos);
            if (inicioStr == -1) break;
            
            int fimStr = arrayStr.indexOf('"', inicioStr + 1);
            if (fimStr == -1) break;
            
            resultado.add(arrayStr.substring(inicioStr + 1, fimStr));
            pos = fimStr + 1;
        }
        
        return resultado;
    }
}

