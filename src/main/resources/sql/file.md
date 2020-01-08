queryUserFilePath
===
    
    SELECT * FROM FILE_PATH  where user_Name = #userName# and file_path like #parentPathSearch# and FILE_PATH <> #parentPath# and FILE_PATH <> #parentPath1#
    
insertUserFilePath
===
    
    insert into FILE_PATH(FILE_PATH,FILE_TYPE,USER_NAME,PARENT_PATH,FILE_NAME ) 
    values( #filePath.filePath#,
            #fileType#,
            #userName#,
            #parentPath#,
            #fileName#)