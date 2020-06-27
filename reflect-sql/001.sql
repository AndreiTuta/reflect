CREATE SCHEMA reflect;

USE reflect;

CREATE TABLE `user` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `created` DATETIME DEFAULT NULL,
    `email` VARCHAR(255) DEFAULT NULL,
    `modified` DATETIME DEFAULT NULL,
    `name` VARCHAR(255) DEFAULT NULL,
    `password` VARCHAR(255) DEFAULT NULL
);

INSERT INTO `user` 
VALUES ('1', '2020-06-27T12:50:51', 'nether.songs.radio@gmail.com', '2020-06-27T13:19:28', 'ref.Admin', 'password2');

CREATE TABLE `meditation` (
    `id` INT NOT NULL,
    `available` BIT(1) DEFAULT NULL,
    `duration` VARCHAR(255) DEFAULT NULL,
    `name` VARCHAR(255) DEFAULT NULL,
    `address` VARCHAR(255) DEFAULT NULL,
    `num_med` INT DEFAULT NULL,
    `preview` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

INSERT INTO `meditation`
VALUES ('1', b'1', '25min', 'Basic meditation', '../media/basic-med.txt', 10, 'https://images.unsplash.com/photo-1507501336603-6e31db2be093?auto=format&fit=crop&w=800&q=80');

CREATE TABLE `submeditation` (
    `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `meditation_audio_address` VARCHAR(255) DEFAULT NULL,
    `meditation_player_address` VARCHAR(255) DEFAULT NULL,
    `name` VARCHAR(255) DEFAULT NULL,
    `parent_meditation_id` INT DEFAULT NULL,
    CONSTRAINT fk_submed_meditation_id FOREIGN KEY (parent_meditation_id)
        REFERENCES meditation (id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO `submeditation`
VALUES
('1', '../media/basic-submed01.txt', '../media/basic-submed01.txt', 'Submeditation 1', '1');

CREATE TABLE `user_meditation` (
    `meditation_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    `user_meditation_text` VARCHAR(45) DEFAULT NULL,
    PRIMARY KEY (`meditation_id` , `user_id`),
    KEY `fk_usermed_user_id _idx` (`user_id`),
    CONSTRAINT `fk_usermed_meditation_id ` FOREIGN KEY (`meditation_id`)
        REFERENCES `submeditation` (`id`),
    CONSTRAINT `fk_usermed_user_id ` FOREIGN KEY (`user_id`)
        REFERENCES `user` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO `user_meditation`
VALUES
('1', '1', 'Some text');