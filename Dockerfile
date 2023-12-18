FROM ghcr.io/graalvm/graalvm-ce:22.3.2 as build

RUN microdnf install yum -y \
  && yum --version \
  && yum install wget xz -y \
  && wget https://github.com/upx/upx/releases/download/v4.0.2/upx-4.0.2-amd64_linux.tar.xz -P /tmp \
  && wget https://downloads.apache.org/maven/maven-3/3.9.4/binaries/apache-maven-3.9.4-bin.tar.gz -P /tmp \
  && tar xf /tmp/apache-maven-3.9.4-bin.tar.gz -C /opt \
  && tar xf /tmp/upx-4.0.2-amd64_linux.tar.xz -C /opt \
  && ln -s /opt/upx-4.0.2-amd64_linux /opt/upx \
  && ln -s /opt/apache-maven-3.9.4 /opt/maven \
  && ln -s /opt/graalvm-ce-java17-22.3.2 /opt/graalvm \
  && gu install native-image




ENV JAVA_HOME=/opt/graalvm
ENV M2_HOME=/opt/maven
ENV MAVEN_HOME=/opt/maven
ENV UPX_HOME=/opt/upx
ENV PATH=${M2_HOME}/bin:${PATH}
ENV PATH=${JAVA_HOME}/bin:${PATH}
ENV PATH=${JAVA_HOME}/bin:${PATH}:${UPX_HOME}



COPY ./pom.xml ./pom.xml
COPY src ./src/


RUN mvn -Pnative native:compile \
   && cp ./target/graalvm-native-demo /app/native-app \
   && upx /app/native-app

#
#
FROM oraclelinux:9-slim as runtime

# Add Spring Boot Native app spring-boot-graal to Container
COPY --from=build /app/native-app native-app

# Fire up our Spring Boot Native app by default
CMD [ "./native-app" ]
