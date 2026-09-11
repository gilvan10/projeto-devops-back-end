#!/bin/bash

set -e

SERVICE_NAME="projeto-devops-back-end"
JAR_NAME="projeto-devops-back-end.jar"

echo "Validando aplicação..."

for i in {1..12}; do

    echo "Validação tentativa $i/12..."

    if systemctl is-active --quiet "$SERVICE_NAME"; then
        echo "Serviço $SERVICE_NAME está ativo."

        if pgrep -f "$JAR_NAME" > /dev/null; then
            echo "Processo $JAR_NAME está em execução."
            echo "Validação concluída com sucesso."

            systemctl status "$SERVICE_NAME" --no-pager

            exit 0
        fi

        echo "Serviço está ativo, mas o processo $JAR_NAME não foi encontrado."
    else
        echo "Serviço ainda não está ativo."
    fi

    sleep 5
done

echo "ERRO: aplicação não iniciou corretamente."

echo "===== STATUS DO SYSTEMD ====="
systemctl status "$SERVICE_NAME" --no-pager || true

echo "===== PROCESSO JAVA ====="
pgrep -af "$JAR_NAME" || true

echo "===== ÚLTIMOS LOGS ====="
journalctl -u "$SERVICE_NAME" -n 50 --no-pager || true

exit 1
