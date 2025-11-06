# 📑 Índice Completo do Projeto

## 📂 Estrutura de Diretórios

```
DSA-P1_2/Dj7IF/
│
├── 📄 Documentação
│   ├── README.md                     # Documentação principal do projeto
│   ├── INSTRUCOES.md                 # Guia detalhado de uso
│   ├── RESUMO_EXECUTIVO.md           # Resumo executivo do projeto
│   ├── INDEX.md                      # Este arquivo - índice completo
│   └── pratica1._22025.2.pdf         # Especificação original
│
├── 🛠️ Scripts de Automação
│   ├── compilar.sh                   # Script de compilação
│   ├── executar.sh                   # Script de execução completa
│   └── gerar_dados.sh                # Script gerador de dados de teste
│
├── 📦 Código Fonte (src/)
│   │
│   ├── 📝 Main.java                  # Classe principal - orquestrador
│   │
│   ├── 🎯 modelo/
│   │   └── Commit.java               # Modelo de dados (com compareTo estável)
│   │
│   ├── 🗂️ estruturas/
│   │   ├── TabelaHashCommits.java    # Tabela Hash com encadeamento
│   │   ├── ArvoreAVLCommits.java     # Árvore AVL com rotações
│   │   └── ArvoreRubroNegraCommits.java  # Árvore RN com recoloração
│   │
│   ├── 🔄 ordenacao/
│   │   │
│   │   ├── instavel/
│   │   │   ├── SelectionSortInstavel.java   # O(n²) instável
│   │   │   ├── QuickSortInstavel.java       # O(n log n) instável
│   │   │   └── HeapSortInstavel.java        # O(n log n) instável
│   │   │
│   │   └── estavel/
│   │       ├── SelectionSortEstavel.java    # + Tabela Hash
│   │       ├── QuickSortEstavel.java        # + Árvore AVL
│   │       └── HeapSortEstavel.java         # + Árvore Rubro-Negra
│   │
│   └── 🔧 util/
│       ├── ValidadorEstabilidade.java       # Verifica estabilidade
│       ├── AnalisadorDesempenho.java        # Benchmarking
│       ├── GeradorRelatorio.java            # Geração de relatórios
│       └── LeitorJSON.java                  # Parser JSON manual
│
├── 🧪 Gerador de Dados (geradorBasePratica1/)
│   ├── Commit.java                   # Modelo para gerador
│   └── GeradorArquivosCommitsTeste.java  # Gerador de JSONs
│
├── 📊 Dados de Teste (dados_teste/)
│   ├── commits_1000.json             # 1.000 commits (330 KB)
│   ├── commits_10000.json            # 10.000 commits (3,2 MB)
│   ├── commits_100000.json           # 100.000 commits (32 MB)
│   ├── commits_criticos.json         # 500 commits mesmo timestamp
│   └── estatisticas.txt              # Estatísticas dos datasets
│
├── 💾 Saída Compilada (bin/)
│   ├── Main.class
│   ├── modelo/
│   ├── estruturas/
│   ├── ordenacao/
│   └── util/
│
└── 📈 Resultados
    └── relatorio.txt                 # Relatório completo gerado
```

---

## 📊 Estatísticas do Projeto

### Código Fonte

| Componente | Arquivos | Linhas | Descrição |
|------------|----------|--------|-----------|
| **Modelo** | 1 | ~90 | Classe Commit |
| **Estruturas** | 3 | ~700 | Hash, AVL, Rubro-Negra |
| **Ordenação Instável** | 3 | ~300 | SelectionSort, QuickSort, HeapSort |
| **Ordenação Estável** | 3 | ~450 | Versões estabilizadas |
| **Utilitários** | 4 | ~750 | Validação, análise, relatório |
| **Main** | 1 | ~200 | Orquestrador |
| **TOTAL** | **15** | **~2.500** | Código completo |

### Documentação

| Arquivo | Páginas | Descrição |
|---------|---------|-----------|
| README.md | ~10 | Visão geral e resultados |
| INSTRUCOES.md | ~15 | Guia completo de uso |
| RESUMO_EXECUTIVO.md | ~12 | Análise executiva |
| INDEX.md | ~5 | Este arquivo |
| **TOTAL** | **~42** | Documentação completa |

### Dados de Teste

| Arquivo | Commits | Tamanho | Timestamps Únicos |
|---------|---------|---------|-------------------|
| commits_1000.json | 1.000 | 330 KB | ~50 |
| commits_10000.json | 10.000 | 3,2 MB | ~200 |
| commits_100000.json | 100.000 | 32 MB | ~1.000 |
| commits_criticos.json | 500 | 162 KB | 1 |
| **TOTAL** | **111.500** | **~36 MB** | **~1.251** |

---

## 🎯 Mapa de Funcionalidades

### Algoritmos Implementados (6)

#### Instáveis
1. **SelectionSort** → `src/ordenacao/instavel/SelectionSortInstavel.java`
   - Complexidade: O(n²)
   - Comparações: n(n-1)/2
   - Estável: ❌

2. **QuickSort** → `src/ordenacao/instavel/QuickSortInstavel.java`
   - Complexidade: O(n log n) médio
   - Particionamento: Pivô central
   - Estável: ❌

3. **HeapSort** → `src/ordenacao/instavel/HeapSortInstavel.java`
   - Complexidade: O(n log n) garantido
   - Estrutura: Max-heap
   - Estável: ❌

#### Estáveis
4. **SelectionSort + Hash** → `src/ordenacao/estavel/SelectionSortEstavel.java`
   - Estrutura: Tabela Hash (encadeamento)
   - Estável: ✅

5. **QuickSort + AVL** → `src/ordenacao/estavel/QuickSortEstavel.java`
   - Estrutura: Árvore AVL (rotações)
   - Estável: ✅

6. **HeapSort + RN** → `src/ordenacao/estavel/HeapSortEstavel.java`
   - Estrutura: Árvore Rubro-Negra (recoloração)
   - Estável: ✅

### Estruturas Auxiliares (3)

1. **Tabela Hash** → `src/estruturas/TabelaHashCommits.java`
   - Função hash: `Math.abs(timestamp.hashCode()) % capacidade`
   - Colisões: Encadeamento
   - Operações: O(1) médio

2. **Árvore AVL** → `src/estruturas/ArvoreAVLCommits.java`
   - Balanceamento: Altura
   - Rotações: LL, RR, LR, RL
   - Operações: O(log n)

3. **Árvore Rubro-Negra** → `src/estruturas/ArvoreRubroNegraCommits.java`
   - Balanceamento: Cores
   - Propriedades: 5 regras
   - Operações: O(log n)

### Utilitários (4)

1. **Validador** → `src/util/ValidadorEstabilidade.java`
   - Agrupa por timestamp
   - Compara ordem relativa
   - Reporta violações

2. **Analisador** → `src/util/AnalisadorDesempenho.java`
   - Mede tempo (nanoTime)
   - Conta comparações
   - Executa benchmarks

3. **Relatório** → `src/util/GeradorRelatorio.java`
   - Formata resultados
   - Análise comparativa
   - Overhead de estabilização

4. **Leitor JSON** → `src/util/LeitorJSON.java`
   - Parser manual (sem deps)
   - Lê arquivos de teste
   - Converte para objetos Commit

---

## 🚀 Fluxo de Execução

```
1. INÍCIO
   └─> Main.java

2. CARREGAR DADOS
   └─> LeitorJSON.lerCommits()
       └─> Arquivos JSON → List<Commit>

3. PARA CADA ALGORITMO
   ├─> Instável
   │   ├─> Ordenar commits
   │   ├─> Medir tempo
   │   └─> Contar comparações
   │
   └─> Estável
       ├─> FASE 1: Agrupar (estrutura auxiliar)
       ├─> FASE 2: Ordenar timestamps únicos
       └─> FASE 3: Reconstruir lista

4. VALIDAR ESTABILIDADE
   └─> ValidadorEstabilidade.verificar()
       ├─> Agrupar por timestamp
       ├─> Comparar ordem relativa
       └─> Reportar violações

5. GERAR RELATÓRIO
   └─> GeradorRelatorio
       ├─> Resultados individuais
       ├─> Análise comparativa
       └─> Overhead de estabilização

6. FIM
   └─> relatorio.txt gerado
```

---

## 📋 Dependências

### Internas (Java Standard Library)
- `java.util.*` - Collections, List, ArrayList
- `java.io.*` - File I/O
- `java.text.SimpleDateFormat` - Formatação de datas
- `java.util.Date` - Timestamps

### Externas
- **NENHUMA!** 🎉
- Parser JSON implementado manualmente
- Todas estruturas implementadas do zero

---

## 🔧 Comandos Úteis

### Compilação
```bash
./compilar.sh                          # Com script
javac -d bin src/**/*.java             # Manual
```

### Execução
```bash
./executar.sh                          # Com script
java -cp bin Main                      # Manual
```

### Geração de Dados
```bash
./gerar_dados.sh                       # Com script
cd geradorBasePratica1 && java GeradorArquivosCommitsTeste  # Manual
```

### Visualização de Resultados
```bash
cat relatorio.txt                      # Terminal
less relatorio.txt                     # Paginado
open relatorio.txt                     # Editor padrão (macOS)
```

### Limpeza
```bash
rm -rf bin/                            # Remover compilados
rm relatorio.txt                       # Remover relatório antigo
rm -rf dados_teste/                    # Remover dados de teste
```

---

## 📖 Guia de Leitura

### Para Entender o Projeto
1. Leia `README.md` - Visão geral
2. Consulte `RESUMO_EXECUTIVO.md` - Resultados
3. Veja `INSTRUCOES.md` - Como usar

### Para Implementar/Modificar
1. Estude `src/modelo/Commit.java` - Modelo base
2. Analise `src/estruturas/` - Estruturas auxiliares
3. Veja `src/ordenacao/instavel/` - Algoritmos base
4. Entenda `src/ordenacao/estavel/` - Estabilização

### Para Validar/Testar
1. Execute `./executar.sh`
2. Analise `relatorio.txt`
3. Compare resultados esperados

---

## 🎓 Conceitos Aplicados

### Estruturas de Dados
- ✅ Tabelas Hash
- ✅ Árvores Balanceadas (AVL)
- ✅ Árvores Rubro-Negras
- ✅ Listas Encadeadas

### Algoritmos
- ✅ SelectionSort
- ✅ QuickSort
- ✅ HeapSort
- ✅ Análise de Estabilidade

### Técnicas
- ✅ Agrupamento por chave
- ✅ Redução de complexidade
- ✅ Benchmarking
- ✅ Validação automática

### Engenharia
- ✅ Organização em pacotes
- ✅ Separação de responsabilidades
- ✅ Interface unificada
- ✅ Documentação completa

---

## 🏆 Checklist de Qualidade

### Código
- [x] Compilação sem erros
- [x] Compilação sem warnings
- [x] Código organizado
- [x] Nomes descritivos
- [x] Comentários adequados

### Funcionalidade
- [x] Todos algoritmos funcionam
- [x] Todas estruturas implementadas
- [x] Validação correta
- [x] Benchmarking preciso
- [x] Relatórios completos

### Testes
- [x] Teste com 1k commits
- [x] Teste com 10k commits
- [x] Teste com 100k commits
- [x] Teste crítico (mesmo timestamp)
- [x] Validação de estabilidade

### Documentação
- [x] README completo
- [x] Instruções claras
- [x] Resumo executivo
- [x] Índice de arquivos
- [x] Scripts comentados

---

## 📞 Navegação Rápida

### Documentação
- [README.md](README.md) - Início aqui
- [INSTRUCOES.md](INSTRUCOES.md) - Como usar
- [RESUMO_EXECUTIVO.md](RESUMO_EXECUTIVO.md) - Análise completa
- [INDEX.md](INDEX.md) - Este arquivo

### Código Principal
- [Main.java](src/Main.java) - Ponto de entrada
- [Commit.java](src/modelo/Commit.java) - Modelo base

### Algoritmos
- [SelectionSort](src/ordenacao/instavel/SelectionSortInstavel.java)
- [QuickSort](src/ordenacao/instavel/QuickSortInstavel.java)
- [HeapSort](src/ordenacao/instavel/HeapSortInstavel.java)

### Estruturas
- [Hash](src/estruturas/TabelaHashCommits.java)
- [AVL](src/estruturas/ArvoreAVLCommits.java)
- [Rubro-Negra](src/estruturas/ArvoreRubroNegraCommits.java)

---

## 📊 Resultados Rápidos

### Performance (100k commits)

| Algoritmo | Instável | Estável | Melhoria |
|-----------|----------|---------|----------|
| SelectionSort | 61.322 ms | 52 ms | **1.169x** |
| QuickSort | 58 ms | 23 ms | **2,5x** |
| HeapSort | 137 ms | 40 ms | **3,4x** |

### Estabilidade

| Algoritmo | Instável | Estável |
|-----------|----------|---------|
| SelectionSort | ❌ 94.792 violações | ✅ 0 violações |
| QuickSort | ❌ 99.003 violações | ✅ 0 violações |
| HeapSort | ❌ 99.033 violações | ✅ 0 violações |

---

## ✅ Status Final

- **Implementação:** ✅ 100% completa
- **Testes:** ✅ Todos passando
- **Documentação:** ✅ Completa
- **Qualidade:** ✅ Alta
- **Pronto para entrega:** ✅ SIM

---

**Última atualização:** Novembro 2025  
**Versão:** 1.0.0  
**Status:** ✅ **COMPLETO E FUNCIONAL**

