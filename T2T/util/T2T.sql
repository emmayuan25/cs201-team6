DROP DATABASE IF EXISTS T2T;
CREATE DATABASE T2T;
USE T2T;

DROP TABLE IF EXISTS ChatMessage;
DROP TABLE IF EXISTS Post;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Interest;
DROP TABLE IF EXISTS PostInterest;


CREATE TABLE Interest (
	interestID int primary key not null auto_increment,
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


CREATE TABLE User (
	userID int primary key not null auto_increment,
	userName varchar(500) not null,
    userImage varchar(500),
    password varchar(500) not null,
    interestID int not null,
    FOREIGN KEY fk2(interestID) REFERENCES Interest(interestID)
);


CREATE TABLE ChatMessage (
	messageID int primary key not null auto_increment,
	fromUID int not null,
    toUID int not null,
    messageText varchar(500) not null,
    createdAt datetime not null,
    FOREIGN KEY fk1(fromUID) REFERENCES User(userID),
    FOREIGN KEY fk2(toUID) REFERENCES User(userID)
);


CREATE TABLE PostInterest (
	interestID int primary key not null auto_increment,
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


CREATE TABLE Post (
	postID int primary key not null auto_increment,
	postText varchar(500) not null,
    postImage varchar(500) default null,
    postEdited bit not null DEFAULT 0,
    userID int not null,
    interestID int  not null,
    FOREIGN KEY fk1(userID) REFERENCES User(userID),
    FOREIGN KEY fk2(interestID) REFERENCES PostInterest(interestID)
);   

INSERT INTO Interest(computerScience) VALUES (1);
INSERT INTO User(userName, userImage, password, interestID) VALUES ("Jacky", "https://images.unsplash.com/photo-1611915387288-fd8d2f5f928b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxleHBsb3JlLWZlZWR8MXx8fGVufDB8fHx8&w=1000&q=80", "1", 1);
