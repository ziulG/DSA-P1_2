#!/bin/bash
# Script para gerar novos dados de teste

echo "═══════════════════════════════════════════════════════════"
echo "  Gerador de Dados de Teste"
echo "═══════════════════════════════════════════════════════════"
echo

cd geradorBasePratica1

echo "⚙️  Compilando gerador..."
javac Commit.java GeradorArquivosCommitsTeste.java

if [ $? -eq 0 ]; then
    echo "✅ Compilação concluída!"
    echo
    echo "📊 Gerando arquivos de teste..."
    echo
    java GeradorArquivosCommitsTeste
    
    echo
    echo "═══════════════════════════════════════════════════════════"
    echo "✅ Dados gerados com sucesso em: dados_teste/"
    echo "═══════════════════════════════════════════════════════════"
else
    echo "❌ Erro na compilação do gerador!"
    exit 1
fi

cd ..

