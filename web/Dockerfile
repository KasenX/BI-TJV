FROM gradle:7-jdk17-alpine
COPY . /src/eshop_web
WORKDIR /src/eshop_web

RUN gradle --no-daemon build
CMD gradle --no-daemon bootRun