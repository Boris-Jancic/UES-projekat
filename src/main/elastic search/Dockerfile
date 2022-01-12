FROM docker.elastic.co/elasticsearch/elasticsearch:7.4.0
COPY sr-al.zip . 
RUN /usr/share/elasticsearch/bin/elasticsearch-plugin install --batch file:sr-al.zip
