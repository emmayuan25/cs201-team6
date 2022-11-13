CREATE DATABASE T2T;
USE T2T;

SET foreign_key_checks = 0;

DROP TABLE IF EXISTS ChatMessage;
DROP TABLE IF EXISTS Post;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Interest;

CREATE TABLE Interest (
	interestID int(11) primary key not null auto_increment,
	viterbi varchar(500) not null DEFAULT '0',
	dornsife varchar(500) not null DEFAULT '0',
	marshall varchar(500) not null DEFAULT '0',
	computerScience varchar(500) not null DEFAULT '0',
	csba varchar(500) not null DEFAULT '0',
	businessAdmin varchar(500) not null DEFAULT '0',
	csgames varchar(500) not null DEFAULT '0',
	cais varchar(500) not null DEFAULT '0',
	athenaHacks varchar(500) not null DEFAULT '0',
	scope varchar(500) not null DEFAULT '0'
);

ALTER TABLE Interest AUTO_INCREMENT=0;
    
CREATE TABLE User (
	userID int(11) primary key not null auto_increment,
	userName varchar(500) not null,
    userImage varchar(500),
    password varchar(500) not null,
    interestID int(11) not null,
    FOREIGN KEY fk2(interestID) REFERENCES Interest(interestID));
    
ALTER TABLE User AUTO_INCREMENT=0;

CREATE TABLE Post (
	postID int(11) primary key not null auto_increment,
	postText varchar(500) not null,
    postImage varchar(500) default null,
    postCreatedAt datetime not null,
    userID int(11) not null,
    interestID int(11)  not null,
    FOREIGN KEY fk1(userID) REFERENCES User(userID),
    FOREIGN KEY fk2(interestID) REFERENCES Interest(interestID));    

ALTER TABLE Post AUTO_INCREMENT=0;

CREATE TABLE ChatMessage (
	messageID int(11) primary key not null auto_increment,
	fromUID int(11) not null,
    toUID int(11) not null,
    messageText varchar(500) not null,
    createdAt datetime not null,
    FOREIGN KEY fk1(fromUID) REFERENCES User(userID),
    FOREIGN KEY fk2(toUID) REFERENCES User(userID));

ALTER TABLE ChatMessage AUTO_INCREMENT=0;

INSERT INTO Interest(viterbi) VALUES (1);
INSERT INTO Interest(cais) VALUES (1);
INSERT INTO Interest(athenaHacks) VALUES (1);
INSERT INTO Interest(athenaHacks, cais) VALUES (1, 1);
INSERT INTO Interest(athenaHacks, viterbi, csgames) VALUES (1, 1, 1);

INSERT INTO User(interestID, userName, password) VALUES (1, 'Sanya', 'password123');
INSERT INTO User(interestID, userName, userImage, password) VALUES (2, 'Adam', 'https://en.wikipedia.org/wiki/File:Torus.svg', 'pass2');
INSERT INTO User(interestID, userName, password) VALUES (3, 'Jacky', 'p0');
INSERT INTO User(interestID, userName, password) VALUES (4, 'Emma', 'pass');
INSERT INTO User(interestID, userName, password) VALUES (5, 'Jacky', 'passw0rd');

INSERT INTO Post(postText, postCreatedAt, userID, interestID) VALUES ('I am posting', '2020-11-07T011:08:22', 1, 1);
INSERT INTO Post(postText, postCreatedAt, userID, interestID) VALUES ('Here', '2021-08-06T09:23:00', 1, 2);
INSERT INTO Post(postText, postImage, postCreatedAt, userID, interestID) VALUES ('Image post', 'https://en.wikipedia.org/wiki/File:Riemann_sqrt.svg', '2022-08-07T09:21:09', 3, 2);

INSERT INTO ChatMessage(fromUID, toUID, messageText, createdAt) VALUES (1, 2, 'messaging u', '2022-09-07T09:23:22');
INSERT INTO ChatMessage(fromUID, toUID, messageText, createdAt) VALUES (1, 4, 'msg', '2022-08-07T09:23:22');
INSERT INTO ChatMessage(fromUID, toUID, messageText, createdAt) VALUES (3, 2, 'hi', '2022-11-09T09:23:20');

SELECT * FROM information_schema.tables WHERE table_schema='T2T';
SELECT * FROM Post;
SELECT * FROM Interest;
SELECT * FROM User;
SELECT * FROM ChatMessage;

USE T2T;
SHOW TABLES IN T2T;
SET SQL_SAFE_UPDATES = 0;

UPDATE Interest, User SET Interest.cais=1 WHERE User.userName = 'Sanya' AND User.interestID = Interest.interestID; 
ALTER TABLE Interest ADD mechanicalEngineering bit not null DEFAULT 0;
EXPLAIN ChatMessage;

SELECT userID FROM User WHERE userName = 'Jacky' AND password = 'p0';
DELETE FROM ChatMessage WHERE fromUID=3 OR toUID = 3;
DELETE FROM Post WHERE userID = 3;
DELETE FROM Interest WHERE interestID = 3;
DELETE FROM User WHERE userID = 3;

