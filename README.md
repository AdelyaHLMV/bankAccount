# Bank account

Web сервис (backend) для работы с банковским аккаунтом (SpringBoot)
позволяющий проводить такие операции как: перевод денег с одного счёта на другой,
пополнение счёта, снятие денег со счёта.

* [Swagger Api](http://localhost:10030/bank/swagger-ui.html)
* [H2 database](http://localhost:10030/bank/h2-console) данные для подключения: url: jdbc:h2:mem:testdb, user: test, password:password

## Bank account Web Service 
###Jar
В директории commons/exec хранится jar file
#### Prerequisites
JRE 1.8.0_211
#### Run
Перейти в директорию commons/exec.
java -jar bankAccount-0.0.1-SNAPSHOT.jar

###Python Test
В директории commons/python находится пример нагрузочного теста на языке python и файл test.bat 
#### Prerequisites
Python 3.8.5
#### Run
Перейти в директорию commons/python.
запустить test.bat