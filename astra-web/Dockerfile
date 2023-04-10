FROM node:16-alpine as builder

ARG BASE_API_URL=http://localhost:8080
ARG PROFILE=production

WORKDIR /app
COPY package*.json ./
RUN npm install --silent --force
COPY . .
RUN REACT_APP_BASE_API_URL=$BASE_API_URL REACT_APP_PROFILE=$PROFILE npm run build

FROM nginx:latest
COPY --from=builder /app/build /usr/share/nginx/html
COPY --from=builder /app/default.conf.template /etc/nginx/templates/default.conf.template
CMD /bin/bash -c "envsubst '\$PORT' < /etc/nginx/templates/default.conf.template > /etc/nginx/conf.d/default.conf" && nginx -g 'daemon off;'
