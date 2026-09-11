#!/bin/bash
set -e

SERVICE_NAME="projeto-devops-back-end"

echo "========================================="
echo "APPLICATION STOP"
echo "========================================="

if systemctl is-active --quiet "$SERVICE_NAME"; then
    echo "Parando serviço $SERVICE_NAME..."
    systemctl stop "$SERVICE_NAME"
else
    echo "Serviço $SERVICE_NAME não está em execução."
fi