# homework_maven

Домашнее задание по maven.<br>

## Описание проекта

Java 15<br>
Lombok<br><br>
В родительский pom файл вынес версию java для maven<br><br>
system-info-plugin - maven плагин, запускается на фазе install; собирает некоторые данные о системе,сохраняет их в json по указанному пути
<br>
system-info-rest - рест приложение, по методу get отдает сформированный плагином json. В файле application.properties находится путь до json.<br>
В pom файле проекта в параметрах плагина указан путь до property.
<br><br>

##Сборка проекта

Выполнить mvn clean install для родительского pom файла. 


