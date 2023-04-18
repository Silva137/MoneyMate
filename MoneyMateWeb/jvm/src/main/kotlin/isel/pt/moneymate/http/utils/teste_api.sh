#!/bin/bash

# Test /users endpoint
echo "Testing /users endpoint"
curl -v GET http://localhost:8081/categories
echo ""

# shellcheck disable=SC2162
read
