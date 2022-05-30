FROM node:16-alpine as builder
WORKDIR /app
COPY package*.json ./
RUN npm install --silent
COPY . .
RUN npm run build

FROM nginx:latest
COPY --from=builder /app/build /usr/share/nginx/html
COPY --from=builder /app/default.conf.template /etc/nginx/templates/default.conf.template
CMD /bin/bash -c "envsubst '\$PORT' < /etc/nginx/templates/default.conf.template > /etc/nginx/conf.d/default.conf" && nginx -g 'daemon off;'
