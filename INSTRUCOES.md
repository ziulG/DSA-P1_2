# 📖 Instruções de Uso - Sistema de Ordenação Estável

## 🚀 Início Rápido

### Opção 1: Usar Scripts (Recomendado)

```bash
# Compilar e executar tudo de uma vez
./executar.sh
```

### Opção 2: Comandos Manuais

```bash
# 1. Compilar
./compilar.sh

# 2. Executar
java -cp bin Main

# 3. Ver relatório
cat relatorio.txt
```

---

## 📂 Estrutura de Arquivos

```
DSA-P1_2/Dj7IF/
│
├── src/                          # Código fonte
│   ├── modelo/                   # Classes de modelo
│   ├── estruturas/               # Estruturas auxiliares
│   ├── ordenacao/                # Algoritmos de ordenação
│   │   ├── instavel/            # Versões originais
│   │   └── estavel/             # Versões estabilizadas
│   ├── util/                     # Utilitários
│   └── Main.java                 # Classe principal
│
├── dados_teste/                  # Arquivos JSON de teste
│   ├── commits_1000.json
│   ├── commits_10000.json
│   ├── commits_100000.json
│   └── commits_criticos.json
│
├── bin/                          # Classes compiladas
├── relatorio.txt                 # Relatório de saída
│
├── compilar.sh                   # Script de compilação
├── executar.sh                   # Script de execução
├── gerar_dados.sh                # Script gerador de dados
│
├── README.md                     # Documentação principal
└── INSTRUCOES.md                 # Este arquivo
```

---

## 🛠️ Requisitos

- **Java JDK 8+** instalado
- **Terminal/Console** (bash, zsh, etc.)
- **~50 MB** de espaço em disco (para dados de teste)

### Verificar Java

```bash
java -version
javac -version
```

Se não tiver Java instalado:
- **macOS:** `brew install openjdk`
- **Linux:** `sudo apt install default-jdk`
- **Windows:** Baixar de [java.com](https://java.com)

---

## 📊 Gerando Dados de Teste

### Automaticamente

```bash
./gerar_dados.sh
```

### Manualmente

```bash
cd geradorBasePratica1
javac Commit.java GeradorArquivosCommitsTeste.java
java GeradorArquivosCommitsTeste
cd ..
```

### Arquivos Gerados

| Arquivo | Tamanho | Commits | Timestamps Únicos |
|---------|---------|---------|-------------------|
| `commits_1000.json` | 330 KB | 1.000 | ~50 |
| `commits_10000.json` | 3,2 MB | 10.000 | ~200 |
| `commits_100000.json` | 32 MB | 100.000 | ~1.000 |
| `commits_criticos.json` | 162 KB | 500 | 1 (teste extremo) |

---

## ⚙️ Compilação

### Com Script

```bash
./compilar.sh
```

### Manualmente

```bash
mkdir -p bin

javac -d bin \
    src/modelo/*.java \
    src/estruturas/*.java \
    src/ordenacao/instavel/*.java \
    src/ordenacao/estavel/*.java \
    src/util/*.java \
    src/Main.java
```

### Verificar Compilação

```bash
ls bin/
# Deve mostrar: Main.class, modelo/, estruturas/, ordenacao/, util/
```

---

## 🚀 Execução

### Execução Completa

```bash
java -cp bin Main
```

Isso irá:
1. ✅ Carregar os 3 arquivos de teste
2. ✅ Executar os 6 algoritmos em cada arquivo
3. ✅ Validar estabilidade de cada execução
4. ✅ Medir tempo e comparações
5. ✅ Gerar relatório completo

### Tempo Estimado

- **commits_1000.json:** ~1 segundo
- **commits_10000.json:** ~3 segundos
- **commits_100000.json:** ~60 segundos

**Total:** ~1-2 minutos

---

## 📈 Interpretando Resultados

### Console

```
╔═══════════════════════════════════════════════════════════╗
║   SISTEMA DE ORDENAÇÃO ESTÁVEL DE COMMITS - ED II        ║
╚═══════════════════════════════════════════════════════════╝

============================================================
📂 PROCESSANDO: dados_teste/commits_1000.json
============================================================
✓ Carregados 1.000 commits

⏳ Executando: SelectionSort Original (Instável)...
SelectionSort Original (Instável) (1.000 commits):
  Tempo: 8,691 ms
  Comparações: 499.500
  Estável: NÃO ✗
  ✗ Ordenação INSTÁVEL - 847 violação(ões) encontrada(s)
```

### Relatório (relatorio.txt)

```bash
cat relatorio.txt
# ou
less relatorio.txt
# ou abrir em editor de texto
```

**Estrutura do Relatório:**
1. **Seções por arquivo** - Resultados detalhados
2. **Análise comparativa** - Melhores algoritmos
3. **Overhead de estabilização** - Comparações diretas
4. **Conclusões** - Insights principais

---

## 🔍 Entendendo os Dados

### Métricas Principais

#### 1. Tempo de Execução
```
Tempo: 8,691 ms
```
Tempo total em milissegundos para ordenar todos os commits.

#### 2. Número de Comparações
```
Comparações: 499.500
```
Quantidade de comparações entre elementos (complexidade).

#### 3. Estabilidade
```
Estável: NÃO ✗
✗ Ordenação INSTÁVEL - 847 violação(ões) encontrada(s)
```
- **SIM ✓:** Preserva ordem relativa
- **NÃO ✗:** Altera ordem relativa (com exemplos)

#### 4. Overhead
```
Overhead: -94,0%
```
- **Negativo:** Versão estável é MAIS RÁPIDA
- **Positivo:** Versão estável é mais lenta

---

## 🎯 Casos de Uso

### Teste Básico (Rápido)

Editar `Main.java` para usar apenas arquivo pequeno:

```java
String[] arquivos = {
    "dados_teste/commits_1000.json"
};
```

### Teste Crítico (Extremo)

Para testar com todos commits no mesmo timestamp:

```java
String[] arquivos = {
    "dados_teste/commits_criticos.json"
};
```

### Teste Completo (Padrão)

Todos os 3 arquivos (já configurado).

---

## 🐛 Troubleshooting

### Erro: "Arquivo não encontrado"

**Causa:** Dados de teste não gerados

**Solução:**
```bash
./gerar_dados.sh
```

### Erro: "Main.class não encontrado"

**Causa:** Projeto não compilado

**Solução:**
```bash
./compilar.sh
```

### Erro: "OutOfMemoryError"

**Causa:** JVM sem memória para 100k commits

**Solução:**
```bash
java -Xmx2g -cp bin Main
```

### Erro: "Permission denied"

**Causa:** Scripts sem permissão de execução

**Solução:**
```bash
chmod +x *.sh
```

---

## 📝 Modificando o Código

### Adicionar Novo Algoritmo

1. Criar classe em `src/ordenacao/instavel/` ou `src/ordenacao/estavel/`
2. Implementar métodos: `ordenar()`, `getComparacoes()`, `resetarComparacoes()`
3. Adicionar no `Main.java`:

```java
MeuAlgoritmo meuAlgo = new MeuAlgoritmo();
algoritmos.put("Meu Algoritmo", new OrdenadorAdapter() {
    public List<Commit> ordenar(List<Commit> c) { return meuAlgo.ordenar(c); }
    public int getComparacoes() { return meuAlgo.getComparacoes(); }
    public void resetarComparacoes() { meuAlgo.resetarComparacoes(); }
});
```

### Adicionar Nova Estrutura Auxiliar

1. Criar classe em `src/estruturas/`
2. Implementar métodos: `inserir()`, `buscar()`, `obterTimestamps()`
3. Usar em algoritmo estável

---

## 📚 Referências Rápidas

### Complexidades

| Algoritmo | Melhor | Médio | Pior |
|-----------|--------|-------|------|
| SelectionSort | O(n²) | O(n²) | O(n²) |
| QuickSort | O(n log n) | O(n log n) | O(n²) |
| HeapSort | O(n log n) | O(n log n) | O(n log n) |

### Estruturas

| Estrutura | Inserção | Busca | Ordenação |
|-----------|----------|-------|-----------|
| Hash | O(1) médio | O(1) médio | - |
| AVL | O(log n) | O(log n) | O(n) in-order |
| Rubro-Negra | O(log n) | O(log n) | O(n) in-order |

---

## 🎓 Conceitos Importantes

### Estabilidade

Um algoritmo é **estável** se:
```
∀ i,j: se key[i] = key[j] e i < j antes
       então i < j depois
```

### Por que versões estáveis são mais rápidas?

1. **Agrupamento eficiente:** Hash/AVL/RN agrupam em O(n)
2. **Ordenação reduzida:** Ordena só timestamps únicos (~1% do total)
3. **Sem trocas desnecessárias:** Não altera ordem dentro dos grupos

**Exemplo:**
- 100.000 commits → ~1.000 timestamps únicos
- SelectionSort em 100k: O(n²) = 10.000.000.000 ops
- SelectionSort em 1k: O(n²) = 1.000.000 ops
- **Redução de 10.000x!**

---

## ✅ Checklist de Testes

Antes de entregar, verificar:

- [ ] Todos os algoritmos compilam sem erros
- [ ] Dados de teste gerados
- [ ] Execução completa sem crashes
- [ ] Relatório gerado corretamente
- [ ] Algoritmos instáveis mostram violações
- [ ] Algoritmos estáveis NÃO mostram violações
- [ ] Tempos são razoáveis (< 2 min total)

---

## 📞 Suporte

### Perguntas Frequentes

**Q: Posso usar outros arquivos JSON?**  
A: Sim, desde que sigam o formato esperado (ver `commits_1000.json`)

**Q: Como alterar capacidade da Hash?**  
A: Em `Main.java`, alterar: `new SelectionSortEstavel(10000)`

**Q: Posso adicionar mais testes?**  
A: Sim, adicionar em `Main.java` no array `arquivos`

---

## 🏆 Critérios de Avaliação

Conforme especificação do projeto:

- ✅ Algoritmos originais corretos (20%)
- ✅ Versões estabilizadas funcionais (30%)
- ✅ Estruturas auxiliares implementadas (20%)
- ✅ Análise de desempenho (15%)
- ✅ Qualidade do código (10%)
- ✅ Validação de estabilidade (5%)

---

**Última atualização:** Novembro 2025  
**Versão:** 1.0.0

