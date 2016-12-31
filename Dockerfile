FROM tomcat:8-jre8
MAINTAINER Tomas Hrdlicka <tomas@hrdlicka.co.uk>

RUN apt-get update && \
    apt-get -y dist-upgrade && \
    apt-get -y install net-tools && \
    apt-get -y install iputils-ping && \
    apt-get -y install telnet && \
    apt-get -y install nano && \
    apt-get -y install htop && \
    apt-get -y install mc && \
    apt-get autoclean && \
    rm -rf webapps/ROOT

ENV TERM xterm

ADD scripts/app-launcher.sh ./
ADD target/aui-demo-webapp.war webapps/ROOT.war

EXPOSE 8080

CMD ["sh", "app-launcher.sh"]