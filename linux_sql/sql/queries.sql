--Grouping hosts by hardware info
SELECT cpu_number,host_id,total_mem, row_number()
    OVER(
PARTITION BY cpu_number ORDER BY total_mem DESC
)
FROM host_info INNER JOIN host_usage ON host_info.id = host_usage.host_id;

-- Average memory usage
CREATE FUNCTION round5(ts timestamp) RETURNS timestamp AS
    $$
BEGIN
RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
END;
$$
LANGUAGE PLPGSQL;

SELECT host_id,
       hostname,
       round5(host_usage.timestamp) as rounded_timestamp,
       AVG(host_info.total_mem - host_usage.memory_free)/AVG(host_info.total_mem)*100 as avg_used_mem_percentage
FROM host_info INNER JOIN host_usage ON host_info.id = host_usage.host_id
GROUP BY rounded_timestamp, hostname, host_id
ORDER BY avg_used_mem_percentage;

-- Detect host failure
SELECT host_id,
       round5(timestamp) as rounded_timestamp,
       COUNT(host_id) as num_data_points
FROM host_usage
GROUP BY host_id, rounded_timestamp
HAVING COUNT(host_id)<3
ORDER BY rounded_timestamp;