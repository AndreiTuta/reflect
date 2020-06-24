DROP TABLE IF EXISTS rft_user, rft_meditation, rft_submeditation, rft_user_meditation;

CREATE TABLE `rft_user` (
    `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `email` VARCHAR(255) DEFAULT NULL,
    `name` VARCHAR(255) DEFAULT NULL,
    `password` VARCHAR(255) DEFAULT NULL,
    `username` VARCHAR(255) DEFAULT NULL
);

INSERT INTO `rft_user`
VALUES
('1', 'nether.songs.radio@gmail.com', 'andrei admin', 'password', 'ref_admin');

CREATE TABLE `rft_meditation` (
    `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `available` BIT(1) DEFAULT NULL,
    `duration` VARCHAR(255) DEFAULT NULL,
    `name` VARCHAR(255) DEFAULT NULL,
    `address` VARCHAR(255) DEFAULT NULL,
    `num_med` INT DEFAULT NULL,
    `preview` VARCHAR(255) DEFAULT NULL
);

INSERT INTO `rft_meditation`
VALUES
('1', b'1', '25min', 'Basic meditation', '../media/basic-med.txt', '0', 'https://images.unsplash.com/photo-1507501336603-6e31db2be093?auto=format&fit=crop&w=800&q=80'
);


CREATE TABLE `rft_submeditation` (
  `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `meditation_audio_address` varchar(255) DEFAULT NULL,
  `meditation_player_address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_meditation_id` int DEFAULT NULL,
  CONSTRAINT fk_submed_meditation_id FOREIGN KEY (parent_meditation_id) REFERENCES rft_meditation (id)  
  ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO `rft_submeditation`
VALUES
('1', '../media/basic-submed01.txt', '../media/basic-submed01.txt', 'Submeditation 1', '1');


CREATE TABLE `rft_user_meditation` (
  `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `meditation_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  CONSTRAINT fk_usermed_meditation_id FOREIGN KEY (meditation_id) REFERENCES rft_meditation (id)  
  ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_usermed_user_id FOREIGN KEY (user_id) REFERENCES rft_user (id)  
  ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO `rft_user_meditation`
VALUES
('4', '1', '1');
