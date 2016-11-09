SELECT (SELECT limit_value FROM v$resource_limit WHERE resource_name = 'sessions') AS limit,
(SELECT COUNT (*) FROM v$session) AS total,
(SELECT COUNT (*) FROM v$session WHERE status = 'ACTIVE' and type <> 'BACKGROUND') AS active,
(SELECT COUNT (*) FROM gv$lock WHERE request > 0) AS locked
FROM DUAL