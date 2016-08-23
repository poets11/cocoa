SELECT TA.*,
       TA.size_mb - TA.free_mb                         AS use_mb,
       ( (TA.size_mb - TA.free_mb) / TA.size_mb * 100) AS use_rt
  FROM (  SELECT a.tablespace_name,
                 COUNT (a.file_name)                AS file_cnt,
                 SUM (a.bytes / 1024 / 1024)        AS size_mb,
                 NVL (SUM (b.bytes / 1024 / 1024), 0) AS free_mb
            FROM dba_data_files a,
                 (SELECT tablespace_name, bytes FROM dba_free_space) b
           WHERE a.tablespace_name = b.tablespace_name
        GROUP BY a.tablespace_name) TA
UNION ALL
SELECT TA.*,
       TA.temp_mb - TA.temp_free_mb,
       (TA.temp_mb - TA.temp_free_mb) / TA.temp_mb * 100 AS temp_se
  FROM (  SELECT a.tablespace_name,
                 COUNT (a.file_name)          AS file_cnt,
                 SUM (a.bytes) / 1024 / 1024  AS temp_mb,
                 SUM (free_space) / 1024 / 1024 AS temp_free_mb
            FROM dba_temp_files a, (SELECT * FROM dba_temp_free_space) b
           WHERE a.tablespace_name = b.tablespace_name
        GROUP BY a.tablespace_name) TA