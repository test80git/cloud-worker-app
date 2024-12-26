## Вопрос. Случилась ошибка.

<img src="https://img.shields.io/badge/ Error: Could not find or load main class com.example.cloudtest.CloudtestApplication-red "/>
<img src="https://img.shields.io/badge/Caused by: java.lang.ClassNotFoundException: com.example.cloudtest.CloudtestApplication-red "/>

я перенес всю папку src из корня проекта в папку cloud-worker-app. Как сделать чтобы запускалось отсюда?
## Ответ
Если вы перенесли папку src в новую директорию, такую как cloud-worker-app, нужно обновить настройки проекта и пути, чтобы Spring Boot приложение могло найти главный класс (CloudtestApplication) и другие ресурсы. Вот пошаговое решение:

### 1. Обновите структуру проекта, убедитесь, что структура проекта выглядит так:

```css
   
   cloud-worker-app/
   ├── src/
   │   ├── main/
   │   │   ├── java/
   │   │   │   └── com/
   │   │   │       └── example/
   │   │   │           └── cloudtest/
   │   │   │               └── CloudtestApplication.java
   │   │   ├── resources/
   │   │       └── application.properties
   ├── pom.xml
```
   Если вы используете Gradle, замените pom.xml на build.gradle.

### 2. Обновите build.gradle или pom.xml

* Для Maven (pom.xml):
  Обновите <build> секцию, чтобы указать новый путь к ресурсам и главным классам:

```xml
<build>
    <sourceDirectory>${project.basedir}/cloud-worker-app/src/main/java</sourceDirectory>
    <resources>
        <resource>
            <directory>${project.basedir}/cloud-worker-app/src/main/resources</directory>
        </resource>
    </resources>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <mainClass>com.example.cloudtest.CloudtestApplication</mainClass>
            </configuration>
        </plugin>
    </plugins>
</build>
```
* Для Gradle (build.gradle):
  Добавьте правильные пути для компиляции:

```gradle

sourceSets {
    main {
        java {
            srcDirs = ['cloud-worker-app/src/main/java']
        }
        resources {
            srcDirs = ['cloud-worker-app/src/main/resources']
        }
    }
}
bootRun {
    mainClass = "com.example.cloudtest.CloudtestApplication"
}
```
 Обновите build.gradle или pom.xml

### 3. Убедитесь, что главный класс находится в правильном месте
 *  Файл CloudtestApplication.java должен быть внутри:

```css
cloud-worker-app/src/main/java/com/example/cloudtest/CloudtestApplication.java
```
* Главный класс должен быть объявлен с аннотацией @SpringBootApplication:

```java
package com.example.cloudtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudtestApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudtestApplication.class, args);
    }
}
```
### 4. Обновите настройки IDE
   Если вы используете IntelliJ IDEA или Eclipse:

#### 4.1. Перезагрузите Maven/Gradle проект:

 * Для Maven: В IntelliJ IDEA в правом меню Maven нажмите "Reload All Maven Projects".
 * Для Gradle: В меню Gradle нажмите "Reload Gradle Project".
#### 4.2. Убедитесь, что указаны правильные пути к исходным кодам:

* В IntelliJ IDEA: File -> Project Structure -> Modules -> Sources и укажите cloud-worker-app/src/main/java как Sources.
* В Eclipse: Правой кнопкой на проекте → Properties -> Java Build Path -> Source → Убедитесь, что добавлены 
  правильные пути.
#### 4.3. Обновите конфигурацию запуска:

* В IntelliJ IDEA: Убедитесь, что в Run/Debug Configurations в поле Main class указано com.example.cloudtest.
CloudtestApplication.
* В Eclipse: Настройте запуск так, чтобы главный класс был CloudtestApplication.

### 5. Запустите приложение
   Теперь вы можете запустить приложение с помощью:
- Maven:
```bash
mvn spring-boot:run
```
- Gradle:
```bash
./gradlew bootRun
```
Или через вашу IDE, выбрав соответствующую конфигурацию запуска.

