select a.tablespace_name, 
       a.file_count as file_cnt, 
       round(a.bt) size_mb, 
       round(nvl(c.free_bt,0)) free_mb,
       round(a.bt - nvl(c.free_bt,0)) use_mb,
       round(100-((1-(a.bt-nvl(c.free_bt,0))/a.bt)*100),1) use_rt
from (select tablespace_name, count(1) file_count,
             sum( case when maxbytes >= bytes then maxbytes
                  else bytes
                  end )/1024/1024 as max_bt, 
             sum(bytes)/1024/1024 bt
from dba_data_files
group by TABLESPACE_NAME) a,
(select TABLESPACE_NAME, sum(bytes)/1024/1024 free_bt from dba_free_space
group by TABLESPACE_NAME) c
where a.tablespace_name = c.tablespace_name (+)
order by use_rt desc, size_mb desc, a.tablespace_name asc