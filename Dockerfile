# Utilizza un'immagine ufficiale OpenJDK come immagine di base.
# Assicurati di utilizzare una versione di Java compatibile con il tuo progetto (Java 21).
FROM openjdk:21-jdk-slim

# Imposta la directory di lavoro nel container
WORKDIR /app

# Copia il file JAR dell'applicazione (generato da Gradle) nel container.
# Questo comando presume che ci sia un solo file .jar nella directory build/libs.
# Se ce ne sono diversi, dovrai specificare il nome esatto
# o assicurarti che il task bootJar produca un JAR con un nome prevedibile.
COPY build/libs/*.jar app.jar

# Esponi la porta su cui la tua applicazione Spring Boot è in ascolto (di default 8080)
EXPOSE 8080

# Comando per eseguire l'applicazione quando il container si avvia
ENTRYPOINT ["java", "-jar", "/app/app.jar"]