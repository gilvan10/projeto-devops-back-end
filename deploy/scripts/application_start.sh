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

echo "Aguardando a aplicação Java responder no HTTP (porta 8080)..."

# Aguarda a porta 8080 ou o endpoint /actuator/health responder 200 OK
MAX_ATTEMPTS=30
DELAY=5

for i in $(seq 1 $MAX_ATTEMPTS); do
    if curl -s -o /null -w "%{http_code}" http://localhost:8080/actuator/health | grep -q "200"; then
        echo "Aplicação inicializada e respondendo com HTTP 200!"
        exit 0
    fi

    echo "Aguardando aplicação subida... tentativa $i/$MAX_ATTEMPTS"
    sleep $DELAY
done

echo "ERRO: A aplicação não respondeu na porta HTTP dentro do tempo limite."

systemctl status "$SERVICE_NAME" --no-pager || true
journalctl -u "$SERVICE_NAME" -n 50 --no-pager || true

exit 1