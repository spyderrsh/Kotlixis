#!/usr/bin/env bash
set -euo pipefail

# Fetch latest Kotlin VS Code extension (VSIX) from GitHub Releases
# Requires: gh (installed via devcontainer feature)
WORKDIR="${TMPDIR:-/tmp}/kotlin-lsp"
mkdir -p "$WORKDIR"
pushd "$WORKDIR" >/dev/null

# Download only the VSIX asset from Kotlin/kotlin-lsp latest release
gh release download -R Kotlin/kotlin-lsp --pattern "*.vsix" --clobber

# There should be exactly one .vsix; install it into the Codespace VS Code server
VSIX="$(ls *.vsix | head -n1)"
echo "Installing Kotlin LSP VSIX: $VSIX"
code --install-extension "$VSIX" --force

popd >/dev/null
echo "Kotlin LSP installed."
