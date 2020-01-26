queryUserFilePath
===
    
    SELECT * FROM FILE_PATH  where user_Name = #userName# and PARENT_FILE_RELATIVE_PATH = #parentPath# and FILE_TYPE like 'folder%'
    union all
    SELECT * FROM FILE_PATH  where user_Name = #userName# and PARENT_FILE_RELATIVE_PATH = #parentPath# and FILE_TYPE not like 'folder%'
    
insertUserFilePath
===
    
    insert into FILE_PATH(FILE_PATH,FILE_TYPE,USER_NAME,PARENT_PATH,FILE_NAME,FILE_RELATIVE_PATH,PARENT_FILE_RELATIVE_PATH ) 
    values( #filePath.filePath#,
            #fileType#,
            #userName#,
            #parentPath#,
            #fileName#,
            #fileRelativePath#,
            #parentFileRelativePath#
            )

deleteUserFilePath
===
    
    DELETE FROM FILE_PATH WHERE FILE_PATH = #filePath.filePath#