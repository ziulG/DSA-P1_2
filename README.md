# Sistema de Ordenação Estável de Commits

**Estrutura de Dados II - Trabalho 1 e 2 (2025.2)**
**Aluno: Luiz Gustavo Cutrim**

---

## Visão Geral

Este projeto implementa e compara **6 algoritmos de ordenação**:

### Versões Instáveis
- **SelectionSort** - O(n²)
- **QuickSort** - O(n log n) médio
- **HeapSort** - O(n log n) garantido

### Versões Estáveis (com estruturas auxiliares)
- **SelectionSort Estável** (usando Tabela Hash)
- **QuickSort Estável** (usando Árvore AVL)
- **HeapSort Estável** (usando Árvore Rubro-Negra)

---

## Problema Central

### O que é Estabilidade?

Um algoritmo de ordenação é **estável** se preserva a ordem relativa de elementos com chaves iguais.

**Exemplo:**

```
Input:  [CommitA(10:00), CommitB(10:00), CommitC(10:00)]

 Instável: [CommitC(10:00), CommitA(10:00), CommitB(10:00)]
 Estável:  [CommitA(10:00), CommitB(10:00), CommitC(10:00)]
```

### Por que isso importa?

Commits com timestamps idênticos (ex: batch imports, deploys automáticos) devem manter sua ordem original para:
- Rastreabilidade de mudanças
- Reprodutibilidade de builds
- Análise histórica correta

---

## Arquitetura

```
src/
├── modelo/
│   └── Commit.java                    # Modelo de dados
│
├── ordenacao/
│   ├── instavel/                      # Algoritmos originais
│   │   ├── SelectionSortInstavel.java
│   │   ├── QuickSortInstavel.java
│   │   └── HeapSortInstavel.java
│   │
│   └── estavel/                       # Algoritmos estabilizados
│       ├── SelectionSortEstavel.java  # + Tabela Hash
│       ├── QuickSortEstavel.java      # + Árvore AVL
│       └── HeapSortEstavel.java       # + Árvore Rubro-Negra
│
├── estruturas/                        # Estruturas auxiliares
│   ├── TabelaHashCommits.java         # Hash com encadeamento
│   ├── ArvoreAVLCommits.java          # AVL com rotações
│   └── ArvoreRubroNegraCommits.java   # RN com recoloração
│
├── util/                              # Utilitários
│   ├── ValidadorEstabilidade.java     # Verifica estabilidade
│   ├── LeitorJSON.java                # Parser JSON manual
│   ├── AnalisadorDesempenho.java      # Benchmarking
│   └── GeradorRelatorio.java          # Relatório detalhado
│
└── Main.java                          # Orquestrador
```

---

## Implementações

### Estratégia de Estabilização (3 Fases)

Todos os algoritmos estáveis seguem o mesmo padrão:

#### **Fase 1: Agrupamento**
```java
// Agrupar commits por timestamp usando estrutura auxiliar
estrutura.inserir(timestamp, commit);  // Preserva ordem de inserção
```

#### **Fase 2: Ordenação**
```java
// Ordenar APENAS os timestamps únicos
List<Date> timestampsUnicos = estrutura.obterTimestamps();
algoritmo.ordenar(timestampsUnicos); 
```

#### **Fase 3: Reconstrução**
```java
// Concatenar grupos mantendo ordem interna
for (Date timestamp : timestampsOrdenados) {
    resultado.addAll(estrutura.buscar(timestamp));
}
```

### Estruturas Auxiliares

#### 1. Tabela Hash
- **Função hash:** `Math.abs(timestamp.hashCode()) % capacidade`
- **Colisões:** Encadeamento (linked list)
- **Complexidade:** O(1) inserção/busca médio

#### 2. Árvore AVL
- **Balanceamento:** Rotações LL, RR, LR, RL
- **Fator de balanceamento:** altura(esq) - altura(dir)
- **Complexidade:** O(log n) inserção/busca garantido

#### 3. Árvore Rubro-Negra
- **Propriedades:** 5 regras de cores
- **Operações:** Recoloração + rotações
- **Complexidade:** O(log n) inserção/busca garantido

---

## Resultados

### Benchmarks Reais (100.000 commits)

| Algoritmo | Tempo (ms) | Comparações | Estável? |
|-----------|------------|-------------|----------|
| **SelectionSort Instável** | 61.322,796 | 704.982.704 | (94.792 violações) |
| **SelectionSort Estável** | 52,444 | 488.566 | (0 violações) |
| **QuickSort Instável** | 58,531 | 1.897.845 | (99.003 violações) |
| **QuickSort Estável** | 23,565 | 8.910 | (0 violações) |
| **HeapSort Instável** | 137,631 | 3.018.856 | (99.033 violações) |
| **HeapSort Estável** | 40,288 | 17.368 | (0 violações) |

### Overhead de Estabilização

```
SelectionSort: -99,9% (MAIS RÁPIDO!)
QuickSort:     -59,7% (MAIS RÁPIDO!)
HeapSort:      -70,7% (MAIS RÁPIDO!)
```

**Por que overhead negativo?**

As versões estáveis ordenam apenas timestamps únicos (~1.000) ao invés de todos os commits (100.000):
- 100.000 commits → ~1.000 timestamps únicos
- Redução de **99%** no tamanho da entrada para ordenação
- Estruturas auxiliares agrupam eficientemente

---

## Conclusões

### Descobertas Principais

1. **Instabilidade Confirmada**
   - Todos os algoritmos originais violam estabilidade
   - SelectionSort: 94.792 violações em 100k commits
   - QuickSort/HeapSort: ~99.000 violações

2. **Estabilização Perfeita**
   - Todas as versões estáveis: **0 violações**
   - Estratégia de 3 fases funciona consistentemente

3. **Performance Surpreendente**
   - Versões estáveis são **mais rápidas** que instáveis
   - SelectionSort: 61s → 52ms (1.169x mais rápido)
   - QuickSort: 58ms → 23ms (2,5x mais rápido)

4. **Estruturas Auxiliares**
   - AVL: Melhor para QuickSort (menos comparações)
   - Hash: Excelente para SelectionSort (redução massiva)
   - Rubro-Negra: Balanceada para HeapSort

### Recomendações Práticas

- **Para dados pequenos (<1k):** HeapSort Estável
- **Para dados médios (1k-100k):** QuickSort Estável
- **Para dados grandes (>100k):** QuickSort Estável (AVL)

---

