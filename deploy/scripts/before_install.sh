#!/bin/bash
set -e

APP_DIR="/opt/projeto-devops-back-end"
JAR_FILE="$APP_DIR/projeto-devops-back-end.jar"

echo "Preparando diretório da aplicação: $APP_DIR"

# 1. Garante que o diretório exista
mkdir -p "$APP_DIR"

# 2. Em vez de apagar o JAR manualmente, apenas garante as permissões corretas do diretório
# O próprio CodeDeploy trata a substituição do arquivo (devido ao file_exists_behavior: OVERWRITE no appspec.yml)
if [ -f "$JAR_FILE" ]; then
    echo "JAR anterior encontrado:"
    ls -lh "$JAR_FILE"
fi

# 3. Ajusta a propriedade e permissão da pasta para o ec2-user
chown -R ec2-user:ec2-user "$APP_DIR"
chmod -R 755 "$APP_DIR"

echo "Diretório $APP_DIR preparado com sucesso."