#!/usr/bin/env bash
set -euo pipefail

# Download Kotlin VS Code extension (VSIX) from GitHub Releases
# This runs during onCreateCommand
echo "Downloading Kotlin LSP VSIX..."
WORKDIR="/workspaces/.kotlin-lsp"
mkdir -p "$WORKDIR"
pushd "$WORKDIR" >/dev/null

# Download only the VSIX asset from Kotlin/kotlin-lsp latest release
gh release download -R Kotlin/kotlin-lsp --pattern "*.vsix" --clobber

popd >/dev/null
echo "Kotlin LSP VSIX downloaded to $WORKDIR"
