CREATE TABLE user (
    id INTEGER PRIMARY KEY,
    name VARCHAR(20),
    email VARCHAR(50),
    ip VARCHAR(64),
    cts DATETIME NOT NULL DEFAULT (DATETIME('NOW', 'LOCALTIME')),
    uts DATETIME NOT NULL DEFAULT (DATETIME('NOW', 'LOCALTIME'))
);

CREATE TABLE article (
    id INTEGER PRIMARY KEY,
    title VARCHAR(300),
    author INTEGER,
    file CHAR(32) NULL,
    preview TEXT,
    hit_count INTEGER DEFAULT 0,
    read_count INTEGER DEFAULT 0,
    useful_count INTEGER DEFAULT 0,
    rating_count INTEGER DEFAULT 0,
    rating FLOAT DEFAULT 0,
    type INTEGER DEFAULT 1,
    tag TEXT,
    comment_count INTEGER DEFAULT 0,
    cts DATETIME NOT NULL DEFAULT (DATETIME('NOW', 'LOCALTIME')),
    uts DATETIME NOT NULL DEFAULT (DATETIME('NOW', 'LOCALTIME'))
);

CREATE TABLE comment (
    id INTEGER PRIMARY KEY,
    article INTEGER,
    user INTEGER,
    content TEXT,
    reply INTEGER,
    cts DATETIME NOT NULL DEFAULT (DATETIME('NOW', 'LOCALTIME')),
    uts DATETIME NOT NULL DEFAULT (DATETIME('NOW', 'LOCALTIME'))
);

CREATE TABLE board (
    id INTEGER PRIMARY KEY,
    user INTEGER,
    content TEXT,
    reply INTEGER,
    ip varchar,
    cts DATETIME NOT NULL DEFAULT (DATETIME('NOW', 'LOCALTIME')),
    uts DATETIME NOT NULL DEFAULT (DATETIME('NOW', 'LOCALTIME'))
);

CREATE TABLE tag (
    id INTEGER PRIMARY KEY,
    name VARCHAR(10) NOT NULL
);

-- Use trigger for count accumulation.
CREATE TRIGGER inc_article_comment_count AFTER INSERT ON comment
BEGIN
    UPDATE article SET comment_count = comment_count + 1 WHERE id = NEW.article;
END;

INSERT INTO user (name, email) VALUES ('Nen Den', 'zhegema@126.com');
INSERT INTO tag (id, name) VALUES
    (1, 'Unix'), (2, 'Linux'), (3, 'C'), (4, 'Java'), (5, 'Lua'), (6, 'JS'), (7, 'Spring'),
    (8, 'HTML'), (9, 'CSS'), (10, 'SQL'), (11, 'MySQL'), (12, 'Nginx'), (13, 'SVN'), (14, 'Log4j'),
    (15, 'SSH'), (16, 'WebSocket'), (17, 'R'), (18, 'tomcat');
