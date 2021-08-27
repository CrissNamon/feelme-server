CREATE TABLE IF NOT EXISTS app_user(
    id SERIAL PRIMARY KEY,
    login VARCHAR(32) NOT NULL,
    device_uid VARCHAR(255) NOT NULL,
    firebase_token VARCHAR(512) NOT NULL,
    token VARCHAR(32) DEFAULT '',
    code VARCHAR(16) NOT NULL
);

CREATE TABLE IF NOT EXISTS accepted_user(
    id SERIAL PRIMARY KEY,
    original_user_id SERIAL NOT NULL,
    accepted_user_id SERIAL NOT NULL
);

ALTER TABLE accepted_user ADD CONSTRAINT fk_original_user_id FOREIGN KEY (original_user_id) REFERENCES app_user(id) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE accepted_user ADD CONSTRAINT fk_accepted_user_id FOREIGN KEY (accepted_user_id) REFERENCES app_user(id) ON UPDATE CASCADE ON DELETE CASCADE;
