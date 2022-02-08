FROM gradle:7-jdk17-alpine
COPY . /src/eshop
WORKDIR /src/eshop

RUN gradle --no-daemon build
CMD gradle --no-daemon bootRun