#!/bin/bash
set -e

SERVICE_NAME="projeto-devops-back-end"
APP_PORT=8080 # Altere para a porta que sua aplicação Spring Boot utiliza

echo "Iniciando aplicação..."

systemctl daemon-reload
systemctl enable "$SERVICE_NAME"
systemctl restart "$SERVICE_NAME"

echo "Aguardando aplicação subir e responder na porta $APP_PORT..."

# Aguarda a JVM e a aplicação Spring Boot responderem de fato
for i in {1..30}; do
    # Opção A: Checa se a porta HTTP está aberta e aceitando conexões
    if nc -z localhost "$APP_PORT" 2>/dev/null || curl -s http://localhost:"$APP_PORT"/ > /dev/null; then
        echo "Aplicação Spring Boot iniciada e respondendo na porta $APP_PORT com sucesso!"
        systemctl status "$SERVICE_NAME" --no-pager
        exit 0
    fi

    # Se o serviço do Systemd tiver caído durante a tentativa, interrompe imediatamente
    if ! systemctl is-active --quiet "$SERVICE_NAME"; then
        echo "ERRO: O serviço $SERVICE_NAME caiu durante a inicialização!"
        break
    fi

    echo "Aguardando inicialização do Spring Boot... ($i/30)"
    sleep 3
done

echo "ERRO: A aplicação não respondeu a tempo ou falhou ao iniciar."
echo "=== Últimos logs do Spring Boot (Journalctl) ==="
journalctl -u "$SERVICE_NAME" -n 50 --no-pager

exit 1