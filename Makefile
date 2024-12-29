SHELL := /bin/bash

# Variables
ENV_FILE := .env
MODULES := iam/user iam/client authorization
MVNW := ./mvnw

# Default install task
install-local:
	@echo "Installing application locally"; \
	$(MVNW) clean install;

# Run specific module locally
run-module:
	@echo "Running module locally"; \
	if [ -f $(ENV_FILE) ]; then \
	    ENV_VARS=$$(grep -v '^#' $(ENV_FILE) | xargs -I{} echo -D{}); \
	    echo "Running module $$MODULE with environment variables from $(ENV_FILE):"; \
	    $(MVNW) mn:run -pl $$MODULE $$ENV_VARS; \
	else \
	    echo "No .env file found. Please create one."; \
	    $(MVNW) mn:run -pl $$MODULE; \
	fi

# Run all modules locally
run-local: install-local
	@echo "Running all modules locally"; \
	for MODULE in $(MODULES); do \
	    echo "Starting $$MODULE..."; \
	    if [ -f $(ENV_FILE) ]; then \
	        ENV_VARS=$$(grep -v '^#' $(ENV_FILE) | xargs -I{} echo -D{}); \
	        ($(MVNW) mn:run -pl $$MODULE $$ENV_VARS &) \
	    else \
	        echo "No .env file found. Starting $$MODULE without environment variables."; \
	        ($(MVNW) mn:run -pl $$MODULE &) \
	    fi; \
	done; \
	wait;

# Docker tasks
install-docker:
	@echo "Installing in Docker"; \
	$(MVNW) clean install;

run-docker: install-docker
	@echo "Running application in Docker - assuming environment variables are set by Docker"; \
	$(MVNW) mn:run;

# Clean up processes
stop-local:
	@echo "Stopping all locally running modules"; \
	pkill -f 'mn:run' || echo "No modules running."

# Help
help:
	@echo "Usage: make [target]"
	@echo "Targets:"
	@echo "  install-local       Install application locally."
	@echo "  run-local           Run all modules locally with optional .env variables."
	@echo "  run-module MODULE=<module-path>  Run a specific module locally."
	@echo "  stop-local          Stop all locally running modules."
	@echo "  install-docker      Install application in Docker."
	@echo "  run-docker          Run application in Docker."
