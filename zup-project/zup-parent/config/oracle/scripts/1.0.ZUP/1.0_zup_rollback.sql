--DROP USERS

--SELECT 'ALTER SYSTEM KILL SESSION ''' || SID || ',' || SERIAL# || ''' IMMEDIATE;' FROM V$SESSION WHERE USERNAME LIKE '%ZUP';

DROP USER BDZUP CASCADE;
DROP USER BD_ZUP CASCADE;


--DROP ROLE

DROP ROLE RO_ZUP;

--DROP TABLESPACES

DROP TABLESPACE ZUP_IDX INCLUDING CONTENTS AND DATAFILES CASCADE CONSTRAINTS;
DROP TABLESPACE ZUP_DT INCLUDING CONTENTS AND DATAFILES CASCADE CONSTRAINTS;