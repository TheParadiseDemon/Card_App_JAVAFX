SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `CourseWork` DEFAULT CHARACTER SET utf8 ;
USE `CourseWork` ;

create table if not exists module1(
	id int unique not null auto_increment,
    Слово varchar(30) not null,
    Перевод varchar(30) not null
);

create table if not exists module2(
	id int unique not null auto_increment,
    Слово varchar(30) not null,
    Перевод varchar(30) not null
);

create table if not exists module3(
	id int unique not null auto_increment,
    Слово varchar(30) not null,
    Перевод varchar(30) not null
);

create table if not exists passwords(
	пароль int not null,
    логин varchar(30) not null,
    имя varchar(30) not null,
    фамилия varchar(30) not null
);

-- insert into module1(Слово, Перевод) values
-- 		("Машина", "Car"),
--         ("Человек","Person"),
--         ("Мальчик","Boy"),
--         ("Девочка","Girl"),
--         ("Студент","Student"),
--         ("Дом","House");
--         
-- insert into module2(Слово, Перевод) values
-- 		("Снег", "Snow"),
--         ("Снеговик","Snowman"),
--         ("Палка","Stick"),
--         ("Морковь","Carrot"),
--         ("Камень","Stone"),
--         ("Дерево","Tree");
--         
-- insert into module3(Слово, Перевод) values
-- 		("Горячий", "Hot"),
--         ("Холодный","Cold"),
--         ("Прохладный","Cool"),
--         ("Дождь","Rain"),
--         ("Солнечно","Sunny"),
--         ("Пасмурно","Cloudy");

-- insert into passwords(пароль, логин, имя, фамилия) values
-- 	(12345678, "admin", "Денис", "Проценко");

-- delete from passwords;
select * from passwords;
-- select * from module1;
-- select * from module2;
-- select * from module3;