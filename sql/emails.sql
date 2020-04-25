CREATE TABLE med_db1.rft_emails (
	id INT auto_increment NOT NULL,
	email_body TEXT NOT NULL,
	`type` varchar(45) NULL,
	template BOOL DEFAULT false NULL
)
ENGINE=InnoDB
DEFAULT CHARSET=latin1
COLLATE=latin1_swedish_ci;

