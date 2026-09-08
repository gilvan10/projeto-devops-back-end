#!/bin/bash

SERVICE_NAME="projeto-devops-back-end"

echo "Verificando aplicação..."

# Desativamos o set -e nessa checagem para evitar que o exit code do systemctl mate o script
if systemctl is-active --quiet "$SERVICE_NAME"; then
    echo "Parando $SERVICE_NAME..."
    systemctl stop "$SERVICE_NAME"
else
    echo "$SERVICE_NAME não está em execução ou ainda não foi instalado. Prosseguindo com o deploy..."
fi

# Força o retorno com sucesso (exit status 0) para o CodeDeploy prosseguir
exit 0
