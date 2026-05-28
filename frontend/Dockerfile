# ───────────────────────────────────────────
# Stage 1 — Build
# ───────────────────────────────────────────
FROM node:22-alpine AS builder

WORKDIR /app

ARG VITE_API_URL
ENV VITE_API_URL=$VITE_API_URL

COPY package*.json ./
RUN npm ci

COPY . .
RUN npm run build

# ───────────────────────────────────────────
# Stage 2 — Runtime (Nginx)
# ───────────────────────────────────────────
FROM nginx:alpine

COPY --from=builder /app/dist /usr/share/nginx/html

# Configuração do Nginx para React Router funcionar
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80