FROM java:8
VOLUME /tmp
RUN mkdir /app
ADD ticketdailyjob-0.0.1-SNAPSHOT.jar /app/app.jar
ADD runboot.sh /app
RUN bash -c 'touch /app/app.jar'
WORKDIR /app
RUN chmod a+x runboot.sh
CMD /app/runboot.sh