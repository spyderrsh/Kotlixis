#!/usr/bin/env bash
set -euo pipefail
# Requirements: curl, grep, sed (present in Codespaces images); VS Code CLI `code` is available in Codespaces
# Kotlin LSP requires Java 17+ (we'll rely on the devcontainer image for that).

RELEASES_URL="https://github.com/Kotlin/kotlin-lsp/releases/latest"

echo "Resolving latest Kotlin LSP VSIX URL from ${RELEASES_URL} ..."
# Parse the latest release HTML for the JetBrains CDN VSIX link
VSIX_URL="$(curl -fsSL "$RELEASES_URL" \
  | grep -Eo 'https://download-cdn\.jetbrains\.com/[^"]+\.vsix' \
  | head -n1)"

if [[ -z "${VSIX_URL}" ]]; then
  echo "Could not find a VSIX link on the Releases page."
  echo "As a fallback, consider installing the (deprecated) fwcd.kotlin extension from Marketplace."
  exit 1
fi

echo "Downloading VSIX: ${VSIX_URL}"

WORKDIR="/workspaces/.kotlin-lsp"
mkdir -p "$WORKDIR"
pushd "$WORKDIR" >/dev/null

VSIX_PATH="${WORKDIR}/kotlin-lsp.vsix"
curl -fL "${VSIX_URL}" -o "${VSIX_PATH}"

popd >/dev/null
echo "Kotlin LSP VSIX downloaded to $VSIX_PATH"
