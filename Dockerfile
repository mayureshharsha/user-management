FROM maven:latest
COPY . /server
WORKDIR /server
RUN mvn clean install -DskipTests=true
RUN ls -la

#FROM alpine:latest  
#RUN apk --no-cache add ca-certificates
#WORKDIR /root/
#COPY --from=0 /go/src/github.com/alexellis/href-counter/app .
#CMD ["./app"]  
