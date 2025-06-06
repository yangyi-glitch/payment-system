CREATE TABLE `article`
(
    `id`        int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `book_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '书名',
    `price`     varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '价格',
    `author`    varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '作者',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `file`
(
    `id`        int(11) NOT NULL AUTO_INCREMENT,
    `file_name` varchar(255) COLLATE utf8_bin  DEFAULT NULL,
    `file_url`  varchar(255) COLLATE utf8_bin  DEFAULT NULL,
    `file_type` varchar(255) COLLATE utf8_bin  DEFAULT NULL,
    `word_url`  varchar(1000) COLLATE utf8_bin DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=971 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `user`
(
    `user_id`       int(11) NOT NULL AUTO_INCREMENT,
    `account`       varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `username`      varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `password`      varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `email`         varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `created`       varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `last_modified` varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `role`          int(255) DEFAULT NULL,
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
