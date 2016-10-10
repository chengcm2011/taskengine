
create table PUB_OID
(
  oid     VARCHAR2(20) not null,
  ts      VARCHAR2(19) not null,
  pk_corp VARCHAR2(4) not null,
  dr      NUMBER(11) not null
);
alter table PUB_OID add constraint PK_PUB_OID primary key (PK_CORP);
CREATE TABLE task_taskplugin
   ( 
  pk_taskplugin CHAR(20), 
  pluginname VARCHAR2(20), 
  pluginclass VARCHAR2(200), 
  plugindescription VARCHAR2(200), 
  bmodule VARCHAR2(20), 
  TS CHAR(19) DEFAULT to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') ,
  DR NUMBER(10,0) DEFAULT 0
   ) ;
   ALTER TABLE task_taskplugin ADD CONSTRAINT pk_task_taskplugin PRIMARY key(pk_taskplugin);
   
   CREATE TABLE task_taskdeploy 
   ( 
  pk_taskplugin CHAR(20),
  pk_taskdeploy CHAR(20),
  taskname VARCHAR2(50), 
  taskdescription VARCHAR2(200),
  triggerstr  VARCHAR2(50),
  runnable CHAR(1), 
  vdef1 VARCHAR2(200), 
  vdef2 VARCHAR2(200),
  vdef3 VARCHAR2(200),
  vdef4 VARCHAR2(200),
  vdef5 VARCHAR2(200),
  TS CHAR(19) DEFAULT to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') ,
  DR NUMBER(10,0) DEFAULT 0
   ) ;
   ALTER TABLE task_taskdeploy ADD CONSTRAINT pk_task_taskdeploy PRIMARY key(pk_taskdeploy);
   
   
      
   CREATE TABLE task_taskparamkey 
   ( 
  pk_taskplugin CHAR(20),
  pk_taskparamkey CHAR(20),
  paramname VARCHAR2(50), 
  paramkey VARCHAR2(30),
  vdef1 VARCHAR2(200), 
  vdef2 VARCHAR2(200),
  vdef3 VARCHAR2(200),
  vdef4 VARCHAR2(200),
  vdef5 VARCHAR2(200),
  TS CHAR(19) DEFAULT to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') ,
  DR NUMBER(10,0) DEFAULT 0
   ) ;
    ALTER TABLE task_taskparamkey ADD CONSTRAINT pk_task_taskparamkey PRIMARY key(pk_taskparamkey);
   
      CREATE TABLE task_tasparamvalue 
   ( 
  pk_taskplugin CHAR(20),
  pk_taskparamkey CHAR(20),
  pk_taskparamvalue CHAR(20),
  paramname VARCHAR2(50), 
  paramkey VARCHAR2(30),
  paramvalue VARCHAR2(100),
  vdef1 VARCHAR2(200), 
  vdef2 VARCHAR2(200),
  vdef3 VARCHAR2(200),
  vdef4 VARCHAR2(200),
  vdef5 VARCHAR2(200),
  TS CHAR(19) DEFAULT to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') ,
  DR NUMBER(10,0) DEFAULT 0
   ) ;
    ALTER TABLE task_tasparamvalue ADD CONSTRAINT pk_task_tasparamvalue PRIMARY key(task_tasparamvalue);
   
   
   
    
   CREATE TABLE task_tasklog 
   ( 
  pk_tasklog  CHAR(20),
  pk_taskdeploy CHAR(20),
  returnstr  VARCHAR2(50), 
  issuccess  CHAR(1),
  runtime   VARCHAR2(20),
  runserver  VARCHAR2(1), 
  vdef1 VARCHAR2(200), 
  vdef2 VARCHAR2(200),
  vdef3 VARCHAR2(200),
  vdef4 VARCHAR2(200),
  vdef5 VARCHAR2(200),
  TS CHAR(19) DEFAULT to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') ,
  DR NUMBER(10,0) DEFAULT 0
   ) ;
   ALTER TABLE task_tasklog ADD CONSTRAINT pk_task_tasklog PRIMARY key(pk_tasklog);
   
   