#!/usr/bin/env bash
set -euo pipefail

# Install Kotlin LSP VSIX into VS Code Server
# This runs during postStartCommand when VS Code server is ready
echo "Installing Kotlin LSP extension..."
WORKDIR="/workspaces/.kotlin-lsp"

if [ ! -d "$WORKDIR" ]; then
    echo "VSIX directory not found. Extension may not have been downloaded."
    exit 1
fi

pushd "$WORKDIR" >/dev/null

VSIX="$(ls *.vsix 2>/dev/null | head -n1)"
if [ -z "$VSIX" ]; then
    echo "No VSIX file found in $WORKDIR"
    exit 1
fi

echo "Installing Kotlin LSP VSIX: $VSIX"

# Try different installation methods for Codespaces
if command -v code-server >/dev/null 2>&1; then
    code-server --install-extension "$VSIX" --force
elif command -v code >/dev/null 2>&1; then
    code --install-extension "$VSIX" --force
else
    # Manual installation to VS Code Server extensions directory
    EXT_DIR="$HOME/.vscode-remote/extensions"
    mkdir -p "$EXT_DIR"
    
    # Extract VSIX to extensions directory
    TEMP_EXTRACT="/tmp/kotlin-lsp-extract"
    mkdir -p "$TEMP_EXTRACT"
    unzip -q "$VSIX" -d "$TEMP_EXTRACT"
    
    # Find the extension ID from package.json
    EXT_ID=$(jq -r '"\(.publisher).\(.name)-\(.version)"' "$TEMP_EXTRACT/extension/package.json")
    EXT_TARGET="$EXT_DIR/$EXT_ID"
    
    echo "Installing to: $EXT_TARGET"
    rm -rf "$EXT_TARGET"
    mv "$TEMP_EXTRACT/extension" "$EXT_TARGET"
    rm -rf "$TEMP_EXTRACT"
    
    echo "Kotlin LSP manually installed to extensions directory"
fi

popd >/dev/null
echo "Kotlin LSP installation complete!"
