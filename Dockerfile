# start from base
FROM clojure:latest
MAINTAINER Sergey Sobko <S.Sobko@profitware.ru>

# copy our application code
ADD . /opt/tpg-web
WORKDIR /opt/tpg-web

RUN lein deps

# expose port
EXPOSE 5000

# start app
CMD [ "lein", "run" ]
