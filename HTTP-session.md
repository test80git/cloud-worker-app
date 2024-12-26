# Вопрос
Чтобы сделать так, чтобы у каждого пользователя была своя отдельная Map, можно использовать механизм сессий или контекстов пользователей. Для этого потребуется изменить способ хранения данных, чтобы они были связаны с конкретным пользователем. Вот несколько вариантов:

# Ответ
## 1. Использовать HTTP-сессии
   В данном случае Map будет сохраняться в рамках пользовательской сессии. Например:

Изменения в HelloCloudService:
Добавьте использование HttpSession для хранения Map:
```java
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.context.annotation.RequestScope;
import javax.servlet.http.HttpSession;

// Удаляем static из map и count, чтобы данные хранились на уровне сессии
@Service
@SessionScope // Указывает, что экземпляр сервиса создается для каждой HTTP-сессии
@Slf4j
public class HelloCloudService {
    private final AtomicInteger count = new AtomicInteger(0);
    private final Map<Integer, Worker> map = new HashMap<>(20);

    public HelloCloudService() {
        map.put(count.incrementAndGet(), new Worker(count.get(), "Tom", "Cruise", LocalDate.of(1962, 7, 3), "USA",
                CREATEDATETIME, CREATEDATETIME));
        map.put(count.incrementAndGet(), new Worker(count.get(), "Katie", "Holmes", LocalDate.of(1978, 12, 18), "USA", CREATEDATETIME,
                CREATEDATETIME));
    }

    // Остальной код остается без изменений, но `map` и `count` будут уникальны для каждого пользователя.
}
```
Теперь map будет уникальной для каждой сессии пользователя.

## 2. Использовать JWT или токены
   Если вы хотите, чтобы данные ассоциировались с пользователем на уровне авторизации, то можно использовать JSON Web Token (JWT) или другой идентификатор, чтобы хранить данные в памяти или в базе данных, привязанные к токену.

#### 1. Создайте сервис для управления данными пользователей:
```java
@Service
public class UserDataService {
    private final Map<String, Map<Integer, Worker>> userData = new HashMap<>();

    public Map<Integer, Worker> getUserData(String userId) {
        return userData.computeIfAbsent(userId, id -> new HashMap<>());
    }
}
```
#### 2. В HelloCloudService передавайте userId при вызовах:

```java
@Service
@Slf4j
public class HelloCloudService {
    private final UserDataService userDataService;

    public HelloCloudService(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    public Worker getWorker(String userId, Integer id) {
        Map<Integer, Worker> map = userDataService.getUserData(userId);
        return map.getOrDefault(id, new Worker());
    }

    // Аналогично в остальных методах добавьте передачу userId.
}
```
Теперь каждый пользователь будет иметь собственную Map, привязанную к их userId.

## 3. Использовать ThreadLocal (не рекомендуется для долгоживущих данных)
   ThreadLocal может быть временным решением для уникальных данных в рамках одного запроса.

```java
private static final ThreadLocal<Map<Integer, Worker>> userData = ThreadLocal.withInitial(HashMap::new);

public Worker getWorker(Integer id) {
Map<Integer, Worker> map = userData.get();
return map.getOrDefault(id, new Worker());
}
```

**Важно: Этот способ подходит только для данных, которые нужны на уровне текущего запроса.**

Рекомендуемый подход зависит от ваших требований:

* *Если данные должны быть временными и уникальными для сессии, используйте HttpSession.*

* *Если требуется авторизация и сохранение между сессиями, используйте JWT или храните данные в базе данных.*