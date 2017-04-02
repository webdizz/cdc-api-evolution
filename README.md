# cdc-api-evolution
Demo source code to support talk regarding CDC and API evolution

# Generate consumption contract for Java consumer
``` ./gradlew clean :consumer:test```

# Generate consumption contract for JS consumer
``` cd js-consumer; npm t```

# Execute verification with Pact Gradle plugin
``` ./gradlew :provider:pactVerify```

