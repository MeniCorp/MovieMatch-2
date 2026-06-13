#!/bin/bash
# Generate release notes for GitHub Actions
set -euo pipefail

VERSION_NAME="$1"
VERSION_CODE="$2"
SHA="$3"
EVENT_NAME="$4"
REF_NAME="$5"
COMMITS="$6"
DEBUG_SHA256="$7"
RELEASE_SHA256="$8"
RELEASE_NOTES_INPUT="${9:-}"

NOTES="Release $VERSION_NAME ($VERSION_CODE)

Build: $SHA
Trigger: $EVENT_NAME
Branch: $REF_NAME

### Commit History
$COMMITS

Checksums:
- Debug APK: $DEBUG_SHA256
- Release APK: $RELEASE_SHA256"

if [ -n "$RELEASE_NOTES_INPUT" ]; then
  NOTES="$RELEASE_NOTES_INPUT"
fi

echo "$NOTES"
