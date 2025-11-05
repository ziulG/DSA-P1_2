import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class Commit implements Comparable<Commit>, Serializable {
    private static final long serialVersionUID = 1L;
    
    private String hash;
    private String autor;
    private String mensagem;
    private Date timestamp;
    private int ordemOriginal;
    private List<String> arquivosAlterados;
    private String branch;
    
    public Commit(String hash, String autor, String mensagem, Date timestamp, int ordemOriginal) {
        this(hash, autor, mensagem, timestamp, ordemOriginal, new ArrayList<>(), "main");
    }
    
    public Commit(String hash, String autor, String mensagem, Date timestamp, 
                  int ordemOriginal, List<String> arquivosAlterados, String branch) {
        this.hash = hash;
        this.autor = autor;
        this.mensagem = mensagem;
        this.timestamp = timestamp;
        this.ordemOriginal = ordemOriginal;
        this.arquivosAlterados = arquivosAlterados;
        this.branch = branch;
    }
    
    @Override
    public int compareTo(Commit outro) {
        int comparacaoTimestamp = this.timestamp.compareTo(outro.timestamp);
        if (comparacaoTimestamp == 0) {
            return Integer.compare(this.ordemOriginal, outro.ordemOriginal);
        }
        return comparacaoTimestamp;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commit commit = (Commit) o;
        return Objects.equals(hash, commit.hash);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(hash);
    }
    
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return String.format("Commit{hash='%s', autor='%s', timestamp=%s, ordem=%d}", 
                           hash, autor, sdf.format(timestamp), ordemOriginal);
    }
    
    // Getters
    public String getHash() { return hash; }
    public String getAutor() { return autor; }
    public String getMensagem() { return mensagem; }
    public Date getTimestamp() { return timestamp; }
    public int getOrdemOriginal() { return ordemOriginal; }
    public List<String> getArquivosAlterados() { return arquivosAlterados; }
    public String getBranch() { return branch; }
}