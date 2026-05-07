# ───────────────────────────────────────────
# Stage 1 — Build
# ───────────────────────────────────────────
FROM node:22-alpine AS builder

WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .
RUN npm run build

# ───────────────────────────────────────────
# Stage 2 — Runtime
# ───────────────────────────────────────────
FROM node:22-alpine

WORKDIR /app

RUN addgroup -S nest && adduser -S nest -G nest
USER nest

COPY --from=builder /app/package*.json ./
RUN npm ci --omit=dev

COPY --from=builder /app/dist ./dist

EXPOSE 3000

CMD ["node", "dist/main"]