addUser
===
    
    INSERT INTO User_Pojo (user_Name,password,nickname) VALUES (#userName#,#password#,#nickname#)
    
addRole
===
    
    INSERT INTO USER_ROLE (id,ROLE_NAME,USER_NAME) VALUES ((SELECT case  when max(ID) IS NULL then 0 else max(ID)+1 end FROM USER_ROLE),#roleName#,#userName#)
    
addRoleList
===
    
    INSERT INTO ROLE_LIST (id,ROLE_NAME) VALUES ((SELECT case  when max(ID) IS NULL then 0 else max(ID)+1 end FROM ROLE_LIST),#roleName#)
    
addPermissions
===
    
    INSERT INTO Permissions (id,permissions_Name) VALUES ((SELECT case  when max(ID) IS NULL then 0 else max(ID)+1 end FROM Permissions),#permissionsName#)
    
addRolePermissions
===
    
    INSERT INTO Role_Permissions (id,role_Name,permissions_Name) VALUES ((SELECT case  when max(ID) IS NULL then 0 else max(ID)+1 end FROM Role_Permissions),#roleName#,#permissionsName#)
    
    
addSystemConf
===
    
    INSERT INTO System_Config (conf_Name,conf_Value) VALUES (#confName#,#confValue#)