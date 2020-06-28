
INSERT INTO `user` 
VALUES ('1', '2020-06-27T12:50:51', 'nether.songs.radio@gmail.com', '2020-06-27T13:19:28', 'ref.Admin', 'password2');


INSERT INTO `meditation`
VALUES ('1', b'1', '25min', 'Basic meditation', '../media/basic-med.txt', 10, 'https://images.unsplash.com/photo-1507501336603-6e31db2be093?auto=format&fit=crop&w=800&q=80');

INSERT INTO `submeditation`
VALUES
('1', '../media/basic-submed01.txt', '../media/basic-submed01.txt', 'Submeditation 1', '1');

INSERT INTO `user_meditation`
VALUES
('1', '1', 'Some text');