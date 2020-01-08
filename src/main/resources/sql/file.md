queryUserFilePath
===
    
    SELECT * FROM FILE_PATH  where user_Name = #userName# and PARENT_PATH = #parentPath# 
    
insertUserFilePath
===
    
    insert into FILE_PATH(FILE_PATH,FILE_TYPE,USER_NAME,PARENT_PATH,FILE_NAME ) 
    values( #filePath.filePath#,
            #fileType#,
            #userName#,
            #parentPath#,
            #fileName#)