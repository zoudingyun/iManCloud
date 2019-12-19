queryAllPermission
===
    
    SELECT * FROM PERMISSIONS 
    
queryRolesByUserName
===
    
    SELECT r.id, 
           r.role_name 
    FROM   role_list r, 
           user_role i 
    WHERE  i.user_name = #userName# 
           AND i.role_name = r.role_name  
           
queryPermissionsByRoleName
=== 
    
    SELECT p.id, 
           p.permissions_name 
    FROM   permissions p, 
           role_permissions rp 
    WHERE  rp.permissions_name = p.permissions_name 
           AND rp.role_name =  #roleName#
           
queryUserInfoByUserName
=== 
    
    SELECT user_pojo.user_name, 
           user_pojo.password,
           role_list.role_name, 
           permissions.permissions_name 
    FROM   user_pojo, 
           user_role, 
           role_list, 
           role_permissions, 
           permissions 
    WHERE  user_pojo.user_name = user_role.user_name 
           AND user_role.role_name = role_list.role_name 
           AND role_list.role_name = role_permissions .role_name 
           AND role_permissions .permissions_name = permissions .permissions_name 
           AND user_pojo.user_name = #userName#