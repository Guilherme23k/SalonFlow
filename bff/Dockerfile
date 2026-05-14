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

# 1. Cria o grupo e usuário primeiro
RUN addgroup -S nest && adduser -S nest -G nest

# 2. Define o WORKDIR e JÁ garante que o dono dele é o nest
WORKDIR /app
RUN chown nest:nest /app

# 3. Muda para o usuário nest
USER nest

# 4. Copia os arquivos (o --chown é essencial aqui também)
COPY --chown=nest:nest --from=builder /app/package*.json ./

# 5. Instala as dependências (agora ele tem permissão total em /app)
RUN npm ci --omit=dev

# 6. Copia o resto da build
COPY --chown=nest:nest --from=builder /app/dist ./dist

EXPOSE 3000

CMD ["node", "dist/main"]