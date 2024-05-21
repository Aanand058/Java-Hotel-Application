use apd;

CREATE TABLE IF NOT EXISTS users
  (
     username VARCHAR(50) NOT NULL,
     password VARCHAR(50) NOT NULL,
     isadmin  BOOLEAN NOT NULL
  );


  
-- Insert data into the users table
INSERT INTO users (username, password, isadmin) VALUES ('admin', 'admin', TRUE);

INSERT INTO users
            (username,
             password,
             isadmin)
VALUES      ('Aman',
             'Aman123',
             false);

INSERT INTO users
            (username,
             password,
             isadmin)
VALUES      ('Aanand',
             'a1m2i3t4',
             true);

INSERT INTO users
            (username,
             password,
             isadmin)
VALUES      ('Sam',
             'Sam@098',
             true);

INSERT INTO users
            (username,
             password,
             isadmin)
VALUES      ('Vlad',
             'Vlad123',
             false);

INSERT INTO users
            (username,
             password,
             isadmin)
VALUES      ('Ali',
             'Ali123',
             true); 