#!/bin/bash
set -e

SERVICE_NAME="projeto-devops-back-end"
APP_DIR="/opt/projeto-devops-back-end"

echo "========================================="
echo "APPLICATION START"
echo "========================================="

# Garante o proprietário correto no arquivo recém copiado pelo CodeDeploy
chown -R ec2-user:ec2-user "$APP_DIR"

echo "Recarregando configurações do systemd..."
systemctl daemon-reload
systemctl enable "$SERVICE_NAME"

echo "Iniciando $SERVICE_NAME..."
systemctl start "$SERVICE_NAME"

echo "Aguardando inicialização do serviço..."
for i in {1..30}; do
    if systemctl is-active --quiet "$SERVICE_NAME"; then
        echo "Serviço $SERVICE_NAME ativo e rodando."
        systemctl status "$SERVICE_NAME" --no-pager
        exit 0
    fi

    echo "Aguardando subida do serviço... tentativa $i/30"
    sleep 2
done

echo "ERRO: O serviço $SERVICE_NAME falhou ao iniciar no tempo limite."
systemctl status "$SERVICE_NAME" --no-pager || true
journalctl -u "$SERVICE_NAME" -n 50 --no-pager || true

exit 1