#!/bin/bash
# Script de compilação do Sistema de Ordenação Estável de Commits

echo "═══════════════════════════════════════════════════════════"
echo "  Compilando Sistema de Ordenação Estável de Commits"
echo "═══════════════════════════════════════════════════════════"
echo

# Criar diretório de saída
echo "📁 Criando diretório bin/..."
mkdir -p bin

# Compilar todos os arquivos Java
echo "⚙️  Compilando classes..."
javac -d bin \
    src/modelo/*.java \
    src/estruturas/*.java \
    src/ordenacao/instavel/*.java \
    src/ordenacao/estavel/*.java \
    src/util/*.java \
    src/Main.java

if [ $? -eq 0 ]; then
    echo
    echo "✅ Compilação concluída com sucesso!"
    echo
    echo "Para executar o sistema:"
    echo "  ./executar.sh"
    echo
    echo "Ou manualmente:"
    echo "  java -cp bin Main"
else
    echo
    echo "❌ Erro na compilação!"
    exit 1
fi

