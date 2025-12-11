FROM amazoncorretto:21-alpine

ENV WIREMOCK_VERSION 3.9.1

RUN mkdir -p /var/wiremock/mappings

WORKDIR /var/wiremock

RUN wget -O /var/wiremock/wiremock-standalone.jar https://repo1.maven.org/maven2/org/wiremock/wiremock-standalone/$WIREMOCK_VERSION/wiremock-standalone-$WIREMOCK_VERSION.jar

COPY ./stubs.json /var/wiremock/mappings/stubs.json
COPY ./jwks.json /var/wiremock/jwks.json

EXPOSE 8080

CMD java -jar /var/wiremock/wiremock-standalone.jar --global-response-templating --root-dir /var/wiremock