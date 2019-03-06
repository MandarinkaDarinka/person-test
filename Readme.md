
Приложение предназначено для получения данных о пользователя из текстовых файлов или https://randomuser.me/api/, обработка их и вывод в файлы Excel и PDF.




Сборка проекта

gradle fatJar

После этого будет собран jar со всеми dependency

./build/libs/person-test-1.0-SNAPSHOT-all.jar

Запуск проекта

java -jar person-test-1.0-SNAPSHOT-all.jar

После запуска в папке с jar появятся pdf и xls файлы с результатом
