CREATE TABLE tbl_payment(
id MEDIUMINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
code VARCHAR(36)NOT NULL,
date_paid Date NOT NULL,
type VARCHAR(50),
status VARCHAR(50),
id_player MEDIUMINT NOT NULL);

alter table tbl_payment add constraint uk_tbl_payment_code unique (code);