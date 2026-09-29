#!/bin/sh
set -eu
VER=3.9.9
MAVEN_HOME="${MAVEN_USER_HOME:-$HOME/.m2}/wrapper/apache-maven-$VER"
URL="https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/$VER/apache-maven-$VER-bin.tar.gz"
if [ ! -x "$MAVEN_HOME/bin/mvn" ]; then
  echo "Downloading Apache Maven $VER ..."
  TMP=$(mktemp -d)
  trap 'rm -rf "$TMP"' EXIT
  curl -fL "$URL" -o "$TMP/maven.tgz"
  tar -xzf "$TMP/maven.tgz" -C "$TMP"
  mkdir -p "$(dirname "$MAVEN_HOME")"
  rm -rf "$MAVEN_HOME"
  mv "$TMP/apache-maven-$VER" "$MAVEN_HOME"
fi
exec "$MAVEN_HOME/bin/mvn" "$@"
