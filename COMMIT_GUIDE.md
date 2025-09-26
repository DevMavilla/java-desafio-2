# 📌 Guia de Commits - Projeto Java Desafio 2

Este guia segue o padrão **Conventional Commits**, para manter o histórico limpo, organizado e fácil de entender.

---

## 🔑 Padrões de commits

- **feat:** quando adicionar **funcionalidades novas**  
  👉 Exemplo: `git commit -m "feat: adiciona opção de marcar tarefa como concluída"`

- **fix:** quando corrigir **erros/bugs**  
  👉 Exemplo: `git commit -m "fix: corrige erro de InputMismatch no addTask"`

- **refactor:** quando **refatorar código** sem mudar funcionalidade  
  👉 Exemplo: `git commit -m "refactor: organiza método menu para melhorar leitura"`

- **docs:** para alterações na **documentação**  
  👉 Exemplo: `git commit -m "docs: adiciona explicação sobre uso do método summaryByDiscipline"`

- **style:** ajustes de **formatação/código**, sem impacto lógico  
  👉 Exemplo: `git commit -m "style: corrige indentação no CrudTarefas"`

- **chore:** tarefas de **manutenção**, sem mexer no código da lógica principal  
  👉 Exemplo: `git commit -m "chore: atualiza .gitignore"`

- **test:** quando criar ou ajustar **testes automatizados**  
  👉 Exemplo: `git commit -m "test: adiciona teste unitário para método addTask"`

---

## 🚀 Fluxo de commit e push

1. Ver o que mudou  
   ```bash
   git status
   ```

2. Adicionar alterações  
   ```bash
   git add .
   ```

3. Criar commit  
   ```bash
   git commit -m "feat: [sua descrição aqui]"
   ```

4. Subir pro GitHub  
   ```bash
   git push origin main
   ```

---

## 🎯 Dicas extras

- Use frases curtas e objetivas no commit.  
- Sempre escreva no **imperativo** (ex: "adiciona", "corrige").  
- Cada commit deve representar uma **única mudança lógica**.  
