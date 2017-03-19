drop table if exists people;

CREATE TABLE people (
`person_id`  int NOT NULL AUTO_INCREMENT ,
`first_name`  varchar(20) NULL ,
`last_name`  varchar(20) NULL ,
PRIMARY KEY (`person_id`)
)
;
