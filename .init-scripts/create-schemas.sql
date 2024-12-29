CREATE DATABASE IF NOT EXISTS client_db;
CREATE DATABASE IF NOT EXISTS user_db;

CREATE USER IF NOT EXISTS '${MYSQLDB_CLIENT_BC_USER}'@'%' IDENTIFIED BY '${MYSQLDB_CLIENT_BC_PASSWORD}';
GRANT ALL PRIVILEGES ON client_db.* TO '${MYSQLDB_CLIENT_BC_USER}'@'%';

CREATE USER IF NOT EXISTS '${MYSQLDB_USER_BC_USER}'@'%' IDENTIFIED BY '${MYSQLDB_USER_BC_PASSWORD}';
GRANT ALL PRIVILEGES ON user_db.* TO '${MYSQLDB_USER_BC_USER}'@'%';

FLUSH PRIVILEGES;
