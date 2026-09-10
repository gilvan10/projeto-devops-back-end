#!/bin/bash

set -e

SERVICE_NAME="projeto-devops-back-end"

echo "Iniciando aplicação..."

# Garante as permissoes
chmod +x /opt/projeto-devops-back-end/projeto-devops-back-end.jar

# 2. Recarregar e habilitar o serviço no systemd
systemctl daemon-reload
systemctl enable "$SERVICE_NAME"

#inicia a aplicacao
#systemctl start "$SERVICE_NAME"

#restarta a aplicacao
systemctl restart projeto-devops-back-end.service

echo "Aguardando aplicação inicializar..."

for i in {1..30}; do

    if systemctl is-active --quiet "$SERVICE_NAME"; then
        echo "Aplicação iniciada com sucesso."
        systemctl status "$SERVICE_NAME" --no-pager
        exit 0
    fi

    echo "Aguardando... tentativa $i/30"
    sleep 2

done

echo "ERRO: aplicação não iniciou corretamente."

systemctl status "$SERVICE_NAME" --no-pager || true

exit 1