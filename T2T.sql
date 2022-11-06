CREATE DATABASE T2T;
USE T2T;

DROP TABLE IF EXISTS ChatMessage;
DROP TABLE IF EXISTS Post;
DROP TABLE IF EXISTS UserLogin;
DROP TABLE IF EXISTS Interest;


CREATE TABLE Interest (
	interestID int(11) primary key not null auto_increment,
	viterbi bit not null DEFAULT 0,
	dornsife bit not null DEFAULT 0,
	marshall bit not null DEFAULT 0,
	computerScience bit not null DEFAULT 0,
	csba bit not null DEFAULT 0,
	businessAdmin bit not null DEFAULT 0,
	csgames bit not null DEFAULT 0,
	cais bit not null DEFAULT 0,
	athenaHacks bit not null DEFAULT 0,
	scope bit not null DEFAULT 0
);


ALTER TABLE Interest AUTO_INCREMENT=0;
    
CREATE TABLE UserLogin (
	userID int(11) primary key not null auto_increment,
	userName varchar(500) not null,
    userImage varchar(500),
    password varchar(500) not null,
    interestID int(11) not null,
    FOREIGN KEY fk2(interestID) REFERENCES Interest(interestID));
    
ALTER TABLE UserLogin AUTO_INCREMENT=0;

CREATE TABLE Post (
	postID int(11) primary key not null auto_increment,
	postText varchar(500) not null,
    postImage varchar(500) default null,
    userID int(11) not null,
    interestID int(11)  not null,
    FOREIGN KEY fk1(userID) REFERENCES UserLogin(userID),
    FOREIGN KEY fk2(interestID) REFERENCES Interest(interestID));    

ALTER TABLE Post AUTO_INCREMENT=0;

CREATE TABLE ChatMessage (
	messageID int(11) primary key not null auto_increment,
	fromUID int(11) not null,
    toUID int(11) not null,
    messageText varchar(500) not null,
    createdAt datetime not null,
    FOREIGN KEY fk1(fromUID) REFERENCES UserLogin(userID),
    FOREIGN KEY fk2(toUID) REFERENCES UserLogin(userID));

ALTER TABLE ChatMessage AUTO_INCREMENT=0;

INSERT INTO Interest(viterbi) VALUES (1);
INSERT INTO Interest(cais) VALUES (1);
INSERT INTO Interest(athenaHacks) VALUES (1);
INSERT INTO Interest(athenaHacks, cais) VALUES (1, 1);
INSERT INTO Interest(athenaHacks, viterbi, csgames) VALUES (1, 1, 1);

INSERT INTO UserLogin(interestID, userName, password) VALUES (1, 'Sanya', 'password123');
INSERT INTO UserLogin(interestID, userName, userImage, password) VALUES (2, 'Adam', 'https://en.wikipedia.org/wiki/File:Torus.svg', 'pass2');
INSERT INTO UserLogin(interestID, userName, password) VALUES (3, 'Jacky', 'p0');
INSERT INTO UserLogin(interestID, userName, password) VALUES (4, 'Emma', 'pass');
INSERT INTO UserLogin(interestID, userName, password) VALUES (5, 'Jacky', 'passw0rd');

INSERT INTO Post(postText, userID, interestID) VALUES ('I am posting', 1, 1);
INSERT INTO Post(postText, userID, interestID) VALUES ('Here', 1, 2);
INSERT INTO Post(postText, postImage, userID, interestID) VALUES ('Image post', 'https://en.wikipedia.org/wiki/File:Riemann_sqrt.svg', 3, 2);

INSERT INTO ChatMessage(fromUID, toUID, messageText, createdAt) VALUES (1, 2, 'messaging u', '2022-09-07T09:23:22');
INSERT INTO ChatMessage(fromUID, toUID, messageText, createdAt) VALUES (1, 4, 'msg', '2022-08-07T09:23:22');
INSERT INTO ChatMessage(fromUID, toUID, messageText, createdAt) VALUES (3, 2, 'hi', '2022-11-09T09:23:20');

SELECT * FROM information_schema.tables WHERE table_schema='T2T';
SELECT * FROM Post;
SELECT * FROM Interest;
SELECT * FROM UserLogin;
SELECT * FROM ChatMessage;

USE T2T;
SHOW TABLES IN T2T;

UPDATE Interest, UserLogin SET Interest.cais=1 WHERE UserLogin.userName = 'Sanya' AND UserLogin.interestID = Interest.interestID; 
ALTER TABLE Interest ADD mechanicalEngineering bit not null DEFAULT 0;
DELETE FROM UserLogin WHERE userID = 1;

