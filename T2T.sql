CREATE DATABASE T2T;
USE T2T;

DROP TABLE IF EXISTS ChatMessage;
DROP TABLE IF EXISTS Chat;
DROP TABLE IF EXISTS Post;
DROP TABLE IF EXISTS UserLogin;
DROP TABLE IF EXISTS Interest;

CREATE TABLE Interest (
	interestID int(11) primary key not null auto_increment,
	interestList varchar(500) not null);

ALTER TABLE Interest AUTO_INCREMENT=111;
    
CREATE TABLE UserLogin (
	userID int(11) primary key not null auto_increment,
	userName varchar(500) not null,
    password varchar(500) not null,
    interestID int(11) not null,
    FOREIGN KEY fk2(interestID) REFERENCES Interest(interestID));
    
ALTER TABLE UserLogin AUTO_INCREMENT=0;

CREATE TABLE Post (
	postID int(11) primary key not null auto_increment,
	postText varchar(500) not null,
    postImage varchar(500),
    userID int(11) not null,
    interestID int(11)  not null,
    FOREIGN KEY fk1(userID) REFERENCES UserLogin(userID),
    FOREIGN KEY fk2(interestID) REFERENCES Interest(interestID));    

ALTER TABLE Post AUTO_INCREMENT=0;

CREATE TABLE Chat (
	chatID int(11) primary key not null auto_increment,
	userID int(11) not null,
    accessTime datetime not null,
    FOREIGN KEY fk1(userID) REFERENCES UserLogin(userID));    

ALTER TABLE Chat AUTO_INCREMENT=0;

CREATE TABLE ChatMessage (
	messageID int(11) primary key not null auto_increment,
	userID int(11) not null,
    chatID int(11) not null,
    messageText varchar(500) not null,
    createdAt datetime not null,
    FOREIGN KEY fk1(userID) REFERENCES UserLogin(userID),
    FOREIGN KEY fk2(chatID) REFERENCES Chat(chatID));

ALTER TABLE ChatMessage AUTO_INCREMENT=0;

INSERT INTO Interest(interestList) VALUES ('Computer Science');
INSERT INTO UserLogin(interestID, userName, password) VALUES (111, 'Sanya', 'password123');
INSERT INTO Post(postText, userID, interestID) VALUES ('I am posting', 1, 111);
INSERT INTO Chat(userID, accessTime) VALUES (1, '2022-08-08');
INSERT INTO ChatMessage(chatID, userID, messageText, createdAt) VALUES (1, 1, 'message', '2022-09-07');

SELECT * FROM information_schema.tables WHERE table_schema='T2T';
SELECT * FROM Post;
SELECT * FROM Interest;
SELECT * FROM UserLogin;
SELECT * FROM Chat;
SELECT * FROM ChatMessage;

USE T2T;
SHOW TABLES;



