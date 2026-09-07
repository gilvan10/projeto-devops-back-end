#!/bin/bash

set -e

SERVICE_NAME="projeto-devops-back-end"

echo "Verificando aplicação..."

if systemctl is-active --quiet "$SERVICE_NAME"; then
    echo "Parando $SERVICE_NAME..."
    systemctl stop "$SERVICE_NAME"
else
    echo "$SERVICE_NAME não está em execução."
fi
