USE `unreal_estate`;

CREATE TABLE `agents`
(
    `id`         int(11)      NOT NULL AUTO_INCREMENT,
    `firstname`  varchar(255) NOT NULL,
    `lastname`   varchar(255) NOT NULL,
    `nickname`   varchar(255) NOT NULL,
    `email`      varchar(255) NOT NULL,
    `phone`      varchar(255) NOT NULL,
    `password`   varchar(255) NOT NULL,
    `birth_date` timestamp    NOT NULL,
    `created_at` timestamp    NOT NULL,
    `updated_at` timestamp    NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `properties`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `owner_name`  varchar(255) NOT NULL,
    `state`       varchar(255) NOT NULL,
    `city`        varchar(255) NOT NULL,
    `address`     varchar(500) NOT NULL,
    `price`       int(11)      NOT NULL,
    `description` text         NOT NULL,
    `agent_id`    int(11)      NOT NULL REFERENCES `agents` (`id`),
    `created_at`  timestamp    NOT NULL,
    `updated_at`  timestamp    NOT NULL,
    PRIMARY KEY (`id`)
);
