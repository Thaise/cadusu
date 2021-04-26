FROM node:10.24-stretch AS builder

WORKDIR /opt/web
COPY package.json package-lock.json ./
RUN npm install

ENV PATH="./node_modules/.bin:$PATH"

COPY . ./
RUN ng build --prod

FROM nginx:alpine
COPY --from=builder /opt/web/dist/cadusufront /usr/share/nginx/html
COPY nginx.config /etc/nginx/nginx.conf