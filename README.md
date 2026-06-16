# Agenda Web

Sistema de Agenda Web para uma clínica médica.

## Aluno

Bruno Hermeto Guimarães

## Tecnologias

| Camada | Tecnologia |
|--------|-----------|
| Backend | Java 11 + Spring Boot 2.7 |
| Frontend | React 18 + React Router |
| Banco de Dados | MySQL 8.0 |
| Build Backend | Maven |
| Build Frontend | Node.js 20 + npm |
| Versionamento | Git + GitHub |
| CI/CD | GitHub Actions |
| Containers | Docker + Docker Compose |
| Produção | AWS (ECS + RDS + ECR + ALB) |

## Estrutura do Projeto

```
agenda-web/
├── backend/           # API REST (Java/Spring Boot)
│   ├── pom.xml
│   ├── Dockerfile
│   └── src/
├── frontend/          # UI (React)
│   ├── package.json
│   ├── Dockerfile
│   └── src/
├── docker-compose.yml
└── .github/workflows/ci-cd.yml
```
