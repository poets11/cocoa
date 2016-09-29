  SELECT a.tablespace_name,
         a.file_count                    AS file_cnt,
         ROUND (a.bt)                    size_mb,
         ROUND (NVL (c.free_bt, 0))      free_mb,
         ROUND (a.bt - NVL (c.free_bt, 0)) use_mb,
         ROUND (100 - ( (1 - (a.bt - NVL (c.free_bt, 0)) / a.bt) * 100), 1)
            use_rt
    FROM (  SELECT tablespace_name,
                   COUNT (1)             file_count,
                     SUM (
                        CASE WHEN maxbytes >= bytes THEN maxbytes ELSE bytes END)
                   / 1024
                   / 1024
                      AS max_bt,
                   SUM (bytes) / 1024 / 1024 bt
              FROM dba_data_files
          GROUP BY TABLESPACE_NAME) a,
         (  SELECT TABLESPACE_NAME, SUM (bytes) / 1024 / 1024 free_bt
              FROM dba_free_space
          GROUP BY TABLESPACE_NAME) c
   WHERE a.tablespace_name = c.tablespace_name(+)
ORDER BY use_rt DESC, size_mb DESC, a.tablespace_name ASC