#!/bin/bash
# Script de execução do Sistema de Ordenação Estável de Commits

echo "═══════════════════════════════════════════════════════════"
echo "  Executando Sistema de Ordenação Estável de Commits"
echo "═══════════════════════════════════════════════════════════"
echo

# Verificar se está compilado
if [ ! -d "bin" ] || [ ! -f "bin/Main.class" ]; then
    echo "Sistema não compilado. Compilando agora..."
    echo
    ./compilar.sh
    echo
fi

# Verificar se existem dados de teste
if [ ! -d "dados_teste" ]; then
    echo "Dados de teste não encontrados!"
    echo "Gerando dados de teste..."
    echo
    cd geradorBasePratica1
    javac Commit.java GeradorArquivosCommitsTeste.java
    java GeradorArquivosCommitsTeste
    cd ..
    echo
fi

# Executar o sistema
echo "Executando análise comparativa..."
echo
java -cp bin Main

echo
echo "═══════════════════════════════════════════════════════════"
echo "Execução concluída!"
echo
echo "Relatório detalhado gerado em: relatorio.txt"
echo "═══════════════════════════════════════════════════════════"

