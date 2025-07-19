ToDoListProject-Java

  Простой, но функциональный ToDo-лист, созданный на Java, UI реализован с помощью JavaFX, данные хранятся в PostgreSQL. Поддерживает авторизацию пользователей, управление задачами и хранение данных в базе данных.

---

## Основные возможности

- Регистрация и авторизация пользователей
- Создание, редактирование и удаление задач
- Задачи привязаны к каждому конкретному пользователю
- Чистая архитектура и понятная структура кода
- Хранение данных в PostgreSQL
- Удобный и лаконичный графический интерфейс на JavaFX

---

## Стек технологий

- Java 17+
- JavaFX 17+
- PostgreSQL
- FXML
- postgreSQL
- Maven

---

## Структура проекта

📁 src/  
┣ 📁 main  
┃ ┣ 📁 java  
┃ ┃ ┣ 📁 com.todolist  
┃ ┃ ┃ ┣ 📁 Controllers  
┃ ┃ ┃ ┣ 📁 DB   
┃ ┃ ┃ ┣ 📁 Model  
┣ ┃ ┃📄 MainToDoList.java  
┣ ┣ 📁resources  
┣ ┃ ┣ 📁 com.todolist  
┃ ┃ ┃ ┣ 📁 FilesFXML  
┃ ┃ ┃ ┣ 📁 Image  
┃ ┃ ┃ ┣ 📁 Style  
  
---

## Структура базы данных (PostgreSQL)

```

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100)
);

CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    userId INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    status BOOLEAN DEFAULT false,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

---

## Установка и запуск

1. Установите Java 17+
2. Установите postgreSQL и создайте базу данных
3. Клонируйте проект:
```
  git clone https://github.com/IVGergert/ToDoListProject-Java.git
  cd yourPackage
```
4. Обновите DBConfig.java с вашими данными подключения к PostgresSQL
5. Запустите MainToDoList.java в вашей IDE (IntelliJ IDEA, Eclipse и т.д.)

## Скриншоты

## Примеры интерфейса

<img width="898" height="598" alt="image" src="https://github.com/user-attachments/assets/278277c0-7f6d-4f4a-8be3-82309a87dafe" />
<img width="598" height="553" alt="image" src="https://github.com/user-attachments/assets/091d6e48-77dd-4739-862e-b63f8149043f" />
<img width="596" height="545" alt="image" src="https://github.com/user-attachments/assets/3803cd11-5ff8-40c2-8db2-66d3dd2f4eb9" />
<img width="596" height="548" alt="image" src="https://github.com/user-attachments/assets/7beef0ea-a829-45b7-8eb0-a13f9872e356" />
<img width="596" height="546" alt="image" src="https://github.com/user-attachments/assets/43a2d63d-8904-4ba2-891e-62a33a96adcf" />
<img width="596" height="549" alt="image" src="https://github.com/user-attachments/assets/cef58d19-36ba-48de-938b-d71db7428545" />
