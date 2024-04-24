CREATE TABLE tbl_player(
id MEDIUMINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
code VARCHAR(36)NOT NULL,
name VARCHAR(100) NOT NULL,
date_birthday DATE NOT NULL,
rg VARCHAR(11) NOT NULL);

alter table tbl_player add constraint uk_tbl_player_code unique (code);


