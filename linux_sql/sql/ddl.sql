\c host_agent;

CREATE TABLE IF NOT EXISTS PUBLIC.host_info
  (
     id               SERIAL NOT NULL PRIMARY KEY,
     hostname         VARCHAR NOT NULL UNIQUE,
     cpu_number       INT NOT NULL,
     cpu_architecture VARCHAR NOT NULL,
     cpu_model        VARCHAR NOT NULL,
     cpu_mhz          FLOAT(3),
     L2_cache         INT NOT NULL,
     total_mem        INT NOT NULL,
     timestamp        TIMESTAMP NOT NULL
  );


CREATE TABLE IF NOT EXISTS PUBLIC.host_usage
(
    "timestamp"    TIMESTAMP NOT NULL,
    host_id        SERIAL REFERENCES host_info(id) NOT NULL,
    memory_free    INT NOT NULL,
    cpu_idle       INT NOT NULL,
    cpu_kernel     INT NOT NULL,
    disk_io        INT NOT NULL,
    disk_available INT NOT NULL
);
