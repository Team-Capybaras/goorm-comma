#!/usr/bin/env bash

# 에러 발생 시 즉시 종료
set -e

ENV_FILE=".env.dev"

if [[ $# -lt 2 ]]; then
  echo "Usage: ./dev.sh {front|back} {build|up|detach|down}"
  exit 1
fi

case "$1" in
  front)
    echo "development profile front starting..."
    COMPOSE_FILE="docker-compose.frontend-dev.yml"
    ;;
  back)
    echo "development profile backend starting..."
    COMPOSE_FILE="docker-compose.backend-dev.yml"
    ;;
  *)
    echo "Invalid profile: $1"
    echo "Usage: ./dev.sh {front|back} {build|up|detach|down}"
    exit 1
    ;;
esac

case "$2" in
  up)
    echo "Starting DEV containers..."
    docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" up
    ;;

  build)
    echo "Building DEV containers..."
    docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" up --build
    ;;

  detach)
    echo "Starting DEV containers in detached mode..."
    docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" up -d
    ;;

  down)
    echo "Stopping DEV containers..."
    docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" stop || true

    echo "Removing DEV containers..."
    docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" down || true
    ;;

  *)
    echo "Invalid command: $2"
    echo "Usage: ./dev.sh {front|back} {build|up|detach|down}"
    exit 1
    ;;
esac
