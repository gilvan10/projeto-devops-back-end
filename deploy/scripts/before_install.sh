#!/bin/bash

set -e

APP_DIR="/opt/projeto-devops-back-end"

echo "========================================="
echo "BEFORE INSTALL"
echo "========================================="

echo "Preparando diretório da aplicação..."

mkdir -p "$APP_DIR"

# Remove o JAR antigo para evitar corrupção da JVM durante a sobrescrita
echo "Removendo artefatos antigos..."
rm -f "$APP_DIR"/*.jar

# Garante que o ec2-user seja o dono da pasta
chown -R ec2-user:ec2-user "$APP_DIR"
chmod -R 755 "$APP_DIR"

echo "Diretório da aplicação:"
ls -ld "$APP_DIR"

echo "BeforeInstall finalizado com sucesso."
