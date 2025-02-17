# Usa una imatge base de Java 23 (si està disponible, si no, usa Java 21 o 17)
FROM openjdk:23-jdk

# Defineix el directori de treball dins del contenidor
WORKDIR /app

# Copia el fitxer JAR generat pel teu projecte a dins del contenidor
COPY target/S05T01N01-0.0.1-SNAPSHOT.jar app.jar

# Defineix el punt d'entrada per executar l'aplicació
ENTRYPOINT ["java", "-jar", "app.jar"]