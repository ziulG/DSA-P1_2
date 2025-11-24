package util;

import modelo.CommitModel;
import java.util.*;


public class ValidadorEstabilidade {
    
    // verifica se a ordenação foi estável comparando com a lista original
    public static ResultadoValidacao verificar(List<CommitModel> original, List<CommitModel> ordenado) {
        // 1 -> verificar se ambas tem mesmo tamanho
        if (original.size() != ordenado.size()) {
            return new ResultadoValidacao(false, "Tamanhos diferentes! Original: " + 
                original.size() + ", Ordenado: " + ordenado.size());
        }
        
        // 2 -> agrupar por timestamp na lista original
        Map<Date, List<CommitModel>> gruposOriginal = agruparPorTimestamp(original);
        Map<Date, List<CommitModel>> gruposOrdenado = agruparPorTimestamp(ordenado);
        
        // 3 -> verificar cada grupo
        List<String> violacoes = new ArrayList<>();
        
        for (Date timestamp : gruposOriginal.keySet()) {
            List<CommitModel> orig = gruposOriginal.get(timestamp);
            List<CommitModel> ord = gruposOrdenado.get(timestamp);
            
            if (ord == null) {
                violacoes.add("Timestamp " + timestamp + " não encontrado na lista ordenada");
                continue;
            }
            
            if (orig.size() != ord.size()) {
                violacoes.add(String.format(
                    "Tamanho diferente para timestamp %s: esperado %d, encontrado %d",
                    timestamp, orig.size(), ord.size()
                ));
                continue;
            }
            
            // verifica se ordem é idêntica
            for (int i = 0; i < orig.size(); i++) {
                if (!orig.get(i).getHash().equals(ord.get(i).getHash())) {
                    violacoes.add(String.format(
                        "Violação em %s: posição %d - esperado hash %s (ordem original %d), encontrado %s (ordem original %d)",
                        timestamp, i, 
                        orig.get(i).getHash(), orig.get(i).getOrdemOriginal(),
                        ord.get(i).getHash(), ord.get(i).getOrdemOriginal()
                    ));
                }
            }
        }
        
        boolean estavel = violacoes.isEmpty();
        String mensagem = estavel ? "✓ Ordenação ESTÁVEL - ordem relativa preservada" : 
            "✗ Ordenação INSTÁVEL - " + violacoes.size() + " violação(ões) encontrada(s)";
        
        return new ResultadoValidacao(estavel, mensagem, violacoes);
    }
    
    // agrupa commits por timestamp mantendo ordem de inserção
    private static Map<Date, List<CommitModel>> agruparPorTimestamp(List<CommitModel> commits) {
        Map<Date, List<CommitModel>> grupos = new LinkedHashMap<>();
        
        for (CommitModel c : commits) {
            grupos.computeIfAbsent(c.getTimestamp(), k -> new ArrayList<>()).add(c);
        }
        
        return grupos;
    }
    
    // classe para armazenar resultado da validação
    public static class ResultadoValidacao {
        public final boolean estavel;
        public final String mensagem;
        public final List<String> violacoes;
        
        public ResultadoValidacao(boolean estavel, String mensagem) {
            this(estavel, mensagem, new ArrayList<>());
        }
        
        public ResultadoValidacao(boolean estavel, String mensagem, List<String> violacoes) {
            this.estavel = estavel;
            this.mensagem = mensagem;
            this.violacoes = violacoes;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(mensagem);
            if (!violacoes.isEmpty() && violacoes.size() <= 5) {
                sb.append("\nExemplos de violações:");
                for (String v : violacoes) {
                    sb.append("\n  • ").append(v);
                }
            } else if (violacoes.size() > 5) {
                sb.append("\nPrimeiras 5 violações:");
                for (int i = 0; i < 5; i++) {
                    sb.append("\n  • ").append(violacoes.get(i));
                }
                sb.append("\n  ... e mais ").append(violacoes.size() - 5).append(" violações");
            }
            return sb.toString();
        }
    }
}

