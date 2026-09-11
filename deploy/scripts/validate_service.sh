#!/bin/bash
set -e

SERVICE_NAME="projeto-devops-back-end"
JAR_NAME="projeto-devops-back-end.jar"

echo "========================================="
echo "VALIDATE SERVICE"
echo "========================================="

for i in {1..12}; do
    echo "Validação tentativa $i/12..."

    if systemctl is-active --quiet "$SERVICE_NAME"; then
        if pgrep -f "$JAR_NAME" > /dev/null; then
            echo "Sucesso: Serviço $SERVICE_NAME está ativo e o processo $JAR_NAME está em execução."
            systemctl status "$SERVICE_NAME" --no-pager
            exit 0
        else
            echo "Aviso: O serviço systemd está ativo, mas o processo Java ainda não foi localizado."
        fi
    else
        echo "Serviço $SERVICE_NAME ainda não está ativo."
    fi

    sleep 5
done

echo "ERRO: A validação falhou após 60 segundos."
echo "===== STATUS DO SYSTEMD ====="
systemctl status "$SERVICE_NAME" --no-pager || true

echo "===== PROCESSOS JAVA EM EXECUÇÃO ====="
pgrep -af "$JAR_NAME" || true

echo "===== ÚLTIMOS LOGS DO SYSTEMD ====="
journalctl -u "$SERVICE_NAME" -n 50 --no-pager || true

exit 1
