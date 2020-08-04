
CREATE TABLE `email` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `available` BIT(1) DEFAULT NULL,
    `text` VARCHAR(255) DEFAULT NULL,
    `name` VARCHAR(255) DEFAULT NULL,
    `address` VARCHAR(255) DEFAULT NULL,
    `preview` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);