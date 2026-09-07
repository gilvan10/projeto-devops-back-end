#!/bin/bash

set -e

APP_DIR="/opt/projeto-devops-back-end"
JAR_FILE="$APP_DIR/projeto-devops-back-end.jar"

echo "Preparando diretório da aplicação..."

mkdir -p "$APP_DIR"

echo "Verificando arquivo antigo..."

if [ -f "$JAR_FILE" ]; then
    echo "JAR encontrado:"
    ls -lh "$JAR_FILE"

    echo "Removendo JAR antigo..."
    rm -f "$JAR_FILE"

    echo "JAR antigo removido."
else
    echo "Nenhum JAR antigo encontrado."
fi

chown -R ec2-user:ec2-user "$APP_DIR"

echo "Diretório preparado: $APP_DIR"
