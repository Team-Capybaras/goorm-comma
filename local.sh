#!/usr/bin/env bash

set -euo pipefail

COMPOSE_FILE="docker-compose.local.yml"
ENV_FILE=".env.prod"

timestamp() { 
  if [[ "$OSTYPE" == "darwin"* ]]; then
    date -u +"%Y-%m-%dT%H:%M:%SZ"
  else
    date --iso-8601=seconds
  fi
}

on_exit() {
  rc=$?
  if [ $rc -eq 0 ]; then
    echo ">>> local.sh: FINISHED SUCCESS rc=0 $(timestamp)"
  else
    echo ">>> local.sh: FINISHED FAILED rc=$rc $(timestamp)" >&2
  fi
}
trap on_exit EXIT

echo ">>> local.sh: START $(timestamp) args=$*"

BUILD_OPTS="${2:-}"

case "${1:-}" in
  down)
    echo ">>> local.sh: stopping containers $(timestamp)"
    docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" stop || true
    docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" down
    echo ">>> local.sh: stop complete $(timestamp)"
    ;;

  build)
    echo ">>> local.sh: Building images (no attach) $(timestamp)"
    docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" build $BUILD_OPTS
    echo ">>> local.sh: Build complete $(timestamp)"
    ;;

  up)
    echo ">>> local.sh: Starting containers $(timestamp)"
    docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" up
    echo ">>> local.sh: Containers started $(timestamp)"
    ;;

  detach)
    echo ">>> local.sh: Starting containers in detached mode $(timestamp)"
    docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" up -d
    echo ">>> local.sh: Containers started in background $(timestamp)"
    ;;

  restart)
    echo ">>> local.sh: Restarting containers (down + up -d) $(timestamp)"
    docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" down
    docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" up -d
    echo ">>> local.sh: Restart finished $(timestamp)"
    ;;

  deploy)
    echo ">>> local.sh: FULL DEPLOY START $(timestamp)"
    docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" build $BUILD_OPTS
    docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" down
    docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" up -d
    echo ">>> local.sh: FULL DEPLOY SUCCESS $(timestamp)"
    ;;

  pull)
    echo ">>> local.sh: Stopping and removing existing containers $(timestamp)"
    docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" down
    echo ">>> local.sh: Pulling latest images $(timestamp)"
    docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" pull
    echo ">>> local.sh: Applying containers $(timestamp)"
    docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" up -d
    echo ">>> local.sh: Pull and deploy complete $(timestamp)"
    ;;

  logs)
    SERVICE="${2:-}"
    if [ -z "$SERVICE" ]; then
      echo ">>> local.sh: Showing logs for all services $(timestamp)"
      docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" logs -f
    else
      echo ">>> local.sh: Showing logs for service: $SERVICE $(timestamp)"
      docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" logs -f "$SERVICE"
    fi
    ;;

  *)
    echo "Usage: ./local.sh {build|up|detach|restart|deploy|down|pull|logs} [--no-cache|service_name]"
    echo ""
    echo "Commands:"
    echo "  build     - Build images (optionally with --no-cache)"
    echo "  up        - Start containers in foreground"
    echo "  detach    - Start containers in background (detached mode)"
    echo "  restart   - Restart containers (down + up -d)"
    echo "  deploy    - Full deploy (build + down + up -d)"
    echo "  down      - Stop and remove containers"
    echo "  pull      - Pull latest images and deploy"
    echo "  logs      - Show logs (all services or specific service)"
    echo ""
    echo "Examples:"
    echo "  ./local.sh build"
    echo "  ./local.sh build --no-cache"
    echo "  ./local.sh up"
    echo "  ./local.sh detach"
    echo "  ./local.sh logs backend"
    echo "  ./local.sh logs"
    exit 1
    ;;
esac
