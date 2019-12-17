sample
===
   * 注释

    select #use("cols")# from user where #use("condition")#

cols
===

    id,name,age,create_date

updateSample
===

    `id`=#id#,`name`=#name#,`age`=#age#,`create_date`=#date#

condition
===

    1 = 1
    @if(!isEmpty(name)){
     and `name`=#name#
    @}
    @if(!isEmpty(age)){
     and `age`=#age#
    @}
    
select
===
    select * from user where 1=1
    @if(!isEmpty(age)){
    and age = #age#
    @}
    @if(!isEmpty(name)){
    and name = #name#
    @}
    
createTable1
===
    
    CREATE TABLE `user` (
          `id` int(11) NOT NULL AUTO_INCREMENT,
          `name` varchar(64) DEFAULT NULL,
          `age` int(4) DEFAULT NULL,
          `create_date` datetime NULL DEFAULT NULL,
          PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
createTable
===
    
    SELECT orgid as id,key as name FROM T_INFO_API_ORGKEY 
    