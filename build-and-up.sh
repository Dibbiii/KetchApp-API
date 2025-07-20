#!/bin/bash
set -e

echo "Building Docker image from Dockerfile..."
docker build -t ketchapp-api .

echo "Starting Docker Compose..."
docker-compose up --build

