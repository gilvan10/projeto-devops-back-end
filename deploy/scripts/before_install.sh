#!/bin/bash
set -e

APP_DIR="/opt/projeto-devops-back-end"

echo "========================================="
echo "BEFORE INSTALL"
echo "========================================="

echo "Preparando diretório da aplicação em $APP_DIR..."
mkdir -p "$APP_DIR"

# Limpa artefatos antigos da execução anterior
rm -f "$APP_DIR"/*.jar

# Ajusta permissões do diretório base
chown -R ec2-user:ec2-user "$APP_DIR"
chmod 755 "$APP_DIR"

echo "Diretório preparado com sucesso:"
ls -ld "$APP_DIR"
