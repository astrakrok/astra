FROM node:16-alpine as builder

ARG BASE_API_URL=http://localhost:8080/api/v1

WORKDIR /app
COPY package*.json ./
RUN npm install --silent
COPY . .
RUN REACT_APP_BASE_API_URL=$BASE_API_URL npm run build

FROM nginx:latest
COPY --from=builder /app/build /usr/share/nginx/html
COPY --from=builder /app/default.conf.template /etc/nginx/templates/default.conf.template
CMD /bin/bash -c "envsubst '\$PORT' < /etc/nginx/templates/default.conf.template > /etc/nginx/conf.d/default.conf" && nginx -g 'daemon off;'
