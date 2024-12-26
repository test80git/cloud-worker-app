# cloud-worker-app
Проект для размещения в cloud.ru. Можно обращаться к endpoint. CRUD Api.
# Пример простой HTML-страницы, 
которая позволяет отправлять POST-запрос к вашему эндпоинту /new для создания нового работника. Страница использует форму с методом POST и отправляет данные в формате JSON через JavaScript. 
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Worker</title>
</head>
<body>
    <h1>Create Worker</h1>
    <form id="workerForm">
        <label for="name">First Name:</label>
        <input type="text" id="name" name="name" required>
        <br><br>
        <label for="surName">Last Name:</label>
        <input type="text" id="surName" name="surName" required>
        <br><br>
        <label for="dateOfBirth">Date of Birth:</label>
        <input type="date" id="dateOfBirth" name="dateOfBirth" required>
        <br><br>
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" required>
        <br><br>
        <button type="button" onclick="submitForm()">Submit</button>
    </form>

    <p id="responseMessage"></p>

    <script>
        async function submitForm() {
            const form = document.getElementById('workerForm');
            const formData = new FormData(form);

            // Create JSON object from form data
            const workerRequest = {
                name: formData.get('name'),
                surName: formData.get('surName'),
                dateOfBirth: formData.get('dateOfBirth'),
                address: formData.get('address')
            };

            try {
                const response = await fetch('/new', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(workerRequest)
                });

                if (response.ok) {
                    const worker = await response.json();
                    document.getElementById('responseMessage').innerText = 
                        `Worker created successfully! ID: ${worker.id}, Name: ${worker.name} ${worker.surName}`;
                } else {
                    document.getElementById('responseMessage').innerText = 
                        `Error: ${response.status} ${response.statusText}`;
                }
            } catch (error) {
                document.getElementById('responseMessage').innerText = `Error: ${error.message}`;
            }
        }
    </script>
</body>
</html>
```
## Как это работает:
#### 1. Форма ввода данных:
* Поля формы (name, surName, dateOfBirth, address) позволяют вводить данные нового работника.
#### 2. Кнопка отправки:
* При нажатии кнопки "Submit", вызывается функция submitForm(), которая собирает данные формы.
#### 3. Отправка данных через fetch:
* Метод fetch отправляет POST-запрос к эндпоинту /new с телом в формате JSON.
#### 4. Обработка ответа:
* Если запрос успешен, выводится сообщение об успешном создании работника.
* Если произошла ошибка, выводится сообщение с кодом ошибки.
## Что нужно сделать перед использованием:
#### 1. Подключите эндпоинт /new:
* Убедитесь, что ваш сервер обрабатывает POST-запросы на /new.
#### 2. Разрешите CORS (если вы тестируете из другого домена):
* В вашем Spring Boot приложении добавьте настройки CORS, чтобы разрешить запросы из браузера:
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
@Override
public void addCorsMappings(CorsRegistry registry) {
registry.addMapping("/new").allowedOrigins("http://localhost:8080"); // или другой URL
}
}
```
#### 3. Запустите приложение и откройте страницу в браузере:
Сохраните этот HTML в файл (например, create-worker.html) и откройте его в браузере.