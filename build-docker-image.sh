#!/bin/bash
set -e  # interrompe lo script in caso di errore

# Colori
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

# Path Maven interno di IntelliJ
INTELLIJ_MAVEN="/Users/thomas/Applications/IntelliJ IDEA Ultimate.app/Contents/plugins/maven/lib/maven3/bin/mvn"
EVENT_LISTENER="/Users/thomas/Applications/IntelliJ IDEA Ultimate.app/Contents/plugins/maven/lib/maven-event-listener.jar"
REPO_PATH="/Users/thomas/.m2/repository"

# Verifica che esista Maven di IntelliJ
if [ ! -f "$INTELLIJ_MAVEN" ]; then
  echo -e "${RED}❌ Maven di IntelliJ non trovato a: $INTELLIJ_MAVEN${NC}"
  exit 1
fi

# Trova tutti i moduli con pom.xml, escludendo 'clients'
modules=$(find . -mindepth 2 -name "pom.xml" -not -path "*/clients/*" -exec dirname {} \; | sort)

if [ -z "$modules" ]; then
  echo -e "${RED}❌ Nessun modulo Maven trovato.${NC}"
  exit 1
fi

echo -e "${GREEN}🧱 Avvio build immagini Docker Spring Boot (usando Maven di IntelliJ)...${NC}\n"

# Pulizia progetto root
echo -e "${GREEN}🧹 Pulizia progetto root...${NC}"
"$INTELLIJ_MAVEN" \
  -Didea.version=2025.2.4 \
  -Dmaven.ext.class.path="$EVENT_LISTENER" \
  -Djansi.passthrough=true -Dstyle.color=always \
  -Dmaven.repo.local="$REPO_PATH" \
  clean -f pom.xml

echo ""

# Build immagini
for module in $modules; do
  module_name=$(basename "$module")
  echo -e "${GREEN}➡️  Costruzione immagine per modulo:${NC} $module_name"

  (
    cd "$module"
    "$INTELLIJ_MAVEN" \
      -Didea.version=2025.2.4 \
      -Dmaven.ext.class.path="$EVENT_LISTENER" \
      -Djansi.passthrough=true -Dstyle.color=always \
      -Dmaven.repo.local="$REPO_PATH" \
      -T 1C clean spring-boot:build-image -DskipTests -f pom.xml
  )

  echo ""
done

echo -e "${GREEN}✅ Tutte le immagini (eccetto 'clients') sono state costruite con successo.${NC}"
