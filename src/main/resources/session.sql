SELECT (SELECT COUNT (*) FROM v$session) AS total,
       (SELECT COUNT (*)
          FROM v$session
         WHERE status = 'ACTIVE')
          AS active,
       (SELECT COUNT (*)
          FROM gv$lock
         WHERE request > 0)
          AS locked
  FROM DUAL