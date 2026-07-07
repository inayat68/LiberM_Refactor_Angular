       IDENTIFICATION DIVISION.                                         
      *-----------------------                                          
       PROGRAM-ID.    DSN8BC3.                                          
                                                                        
      ****** DSN8BC3 - DB2 SAMPLE PHONE APPLICATION - COBOL - BATCH *** 
      *                                                               * 
      *   MODULE NAME = DSN8BC3                                       * 
      *                                                               * 
      *   DESCRIPTIVE NAME = DB2  SAMPLE APPLICATION                  * 
      *                      PHONE APPLICATION                        * 
      *                      BATCH                                    * 
      *                      COBOL                                    * 
      *                                                               * 
      *LICENSED MATERIALS - PROPERTY OF IBM                           * 
      *5605-DB2                                                       * 
      *(C) COPYRIGHT 1982, 2010 IBM CORP.  ALL RIGHTS RESERVED.       * 
      *                                                               * 
      *STATUS = VERSION 10                                            * 
      *                                                               * 
      *   FUNCTION = THIS MODULE LISTS EMPLOYEE PHONE NUMBERS AND     * 
      *              UPDATES THEM IF DESIRED.                         * 
      *                                                               * 
      *   NOTES = NONE                                                * 
      *                                                               * 
      *   MODULE TYPE = COBOL PROGRAM                                 * 
      *      PROCESSOR   = DB2  PRECOMPILER, VS COBOL                 * 
      *      MODULE SIZE = SEE LINK EDIT                              * 
      *      ATTRIBUTES  = NOT REENTRANT OR REUSABLE                  * 
      *                                                               * 
      *                                                               * 
      *   ENTRY POINT = DSN8BC3                                       * 
      *      PURPOSE = SEE FUNCTION                                   * 
      *      LINKAGE = INVOKED FROM DSN RUN                           * 
      *      INPUT   =                                                * 
      *                                                               * 
      *                SYMBOLIC LABEL/NAME =  CARDIN                  * 
      *                DESCRIPTION         =  INPUT REQUEST FILE      * 
      *                                                               * 
      *                SYMBOLIC LABEL/NAME =  VPHONE                  * 
      *                DESCRIPTION         =  VIEW OF TELEPHONE       * 
      *                                       INFORMATION             * 
      *                                                               * 
      *                                                               * 
      *      OUTPUT  =                                                * 
      *                                                               * 
      *                SYMBOLIC LABEL/NAME =  REPORT                  * 
      *                DESCRIPTION         =  REPORT OF EMPLOYEE      * 
      *                                       PHONE NUMBERS           * 
      *                                                               * 
      *                SYMBOLIC LABEL/NAME =  VEMPLP                  * 
      *                DESCRIPTION         =  VIEW OF EMPLOYEE        * 
      *                                       INFORMATION             * 
      *                                                               * 
      *   EXIT-NORMAL = RETURN CODE 0 NORMAL COMPLETION               * 
      *                                                               * 
      *   EXIT-ERROR =                                                * 
      *                                                               * 
      *      RETURN CODE    =  NONE                                   * 
      *                                                               * 
      *      ABEND CODES    =  NONE                                   * 
      *                                                               * 
      *      ERROR-MESSAGES =                                         * 
      *            DSN8004I - EMPLOYEE SUCCESSFULLY UPDATED           * 
      *            DSN8007E - EMPLOYEE DOES NOT EXIST, UPDATE NOT DONE* 
      *            DSN8008I - NO EMPLOYEE FOUND IN TABLE              * 
      *            DSN8053I - ROLLBACK SUCCESSFUL, ALL UPDATES REMOVED* 
      *            DSN8060E - SQL ERROR, RETURN CODE IS:              * 
      *            DSN8061E - ROLLBACK FAILED, RETURN CODE IS:        * 
      *            DSN8068E - INVALID REQUEST, SHOULD BE 'L' OR 'U'   * 
      *            DSN8075E - MESSAGE FORMAT ROUTINE ERROR,           * 
      *                        RETURN CODE IS:                        * 
      *                                                               * 
      *   EXTERNAL REFERENCES =                                       * 
      *      ROUTINES/SERVICES =                                      * 
      *            DSNTIAR  -     TRANSLATE SQLCA INTO MESSAGES       * 
      *            DSN8MCG -      ERROR MESSAGE ROUTINE               * 
      *                                                               * 
      *      DATA-AREAS        =  NONE                                * 
      *                                                               * 
      *      CONTROL-BLOCKS    =                                      * 
      *            SQLCA    -     SQL COMMUNICATION AREA              * 
      *                                                               * 
      *   TABLES =  NONE                                              * 
      *                                                               * 
      *                                                               * 
      *   CHANGE-ACTIVITY = NONE                                      * 
      *                                                               * 
      *                                                               * 
      *  *PSEUDOCODE*                                                 * 
      *                                                               * 
      *  PROCEDURE                                                    * 
      *    GET FIRST INPUT                                            * 
      *    DO WHILE MORE INPUT                                        * 
      *      CREATE REPORT HEADING                                    * 
      *                                                               * 
      *      CASE (ACTION)                                            * 
      *                                                               * 
      *        SUBCASE ('L')                                          * 
      *          IF LASTNAME IS '*' THEN                              * 
      *            LIST ALL EMPLOYEES                                 * 
      *          ELSE                                                 * 
      *            IF LASTNAME CONTAINS '%' THEN                      * 
      *              LIST EMPLOYEES GENERIC                           * 
      *            ELSE                                               * 
      *              LIST EMPLOYEES SPECIFIC                          * 
      *        ENDSUB                                                 * 
      *                                                               * 
      *        SUBCASE ('U')                                          * 
      *          UPDATE PHONENUMBER FOR EMPLOYEE                      * 
      *          WRITE CONFIRMATION MESSAGE                           * 
      *        OTHERWISE                                              * 
      *          INVALID REQUEST                                      * 
      *        ENDSUB                                                 * 
      *                                                               * 
      *      ENDCASE                                                  * 
      *      GET NEXT INPUT                                           * 
      *    END                                                        * 
      *                                                               * 
      *    IF SQL ERROR OCCURS THEN                                   * 
      *       DO                                                      * 
      *        FORMAT ERROR MESSAGE                                   * 
      *        ROLLBACK                                               * 
      *       END                                                     * 
      *  END.                                                         * 
      *---------------------------------------------------------------* 
                                                                        
                                                                        
      /                                                                 
       ENVIRONMENT DIVISION.                                            
      *--------------------                                             
       CONFIGURATION SECTION.                                           
       SPECIAL-NAMES.      C01 IS TO-TOP-OF-PAGE.                       
       INPUT-OUTPUT SECTION.                                            
       FILE-CONTROL.                                                    
           SELECT CARDIN                                                
                  ASSIGN TO DA-S-CARDIN.                                
           SELECT REPOUT                                                
                  ASSIGN TO UT-S-REPORT.                                
                                                                        
       DATA DIVISION.                                                   
      *-------------                                                    
       FILE SECTION.                                                    
       FD      CARDIN                                                   
               RECORD CONTAINS 80 CHARACTERS                            
               BLOCK CONTAINS 0 RECORDS                                 
               LABEL RECORDS ARE OMITTED.                               
       01  CARDREC                    PIC X(80).                        
                                                                        
       FD  REPOUT                                                       
               RECORD CONTAINS 120 CHARACTERS                           
               LABEL RECORDS ARE OMITTED                                
               DATA RECORD IS REPREC.                                   
       01  REPREC                     PIC X(120).                       
      /                                                                 
       WORKING-STORAGE SECTION.                                         
                                                                        
      *****************************************************             
      * STRUCTURE FOR INPUT                               *             
      *****************************************************             
       01  IOAREA.                                                      
               02  ACTION             PIC X(01).                        
               02  LNAME              PIC X(15).                        
               02  FNAME              PIC X(12).                        
               02  ENO                PIC X(06).                        
               02  NEWNO              PIC X(04).                        
               02  FILLER             PIC X(42).                        
                                                                        
      *****************************************************             
      * REPORT HEADER STRUCTURE                           *             
      *****************************************************             
       01  REPHDR1.                                                     
               02  FILLER PIC X(29)                                     
                   VALUE '-----------------------------'.               
               02  FILLER PIC X(21)                                     
                   VALUE ' TELEPHONE DIRECTORY '.                       
               02  FILLER PIC X(29)                                     
                   VALUE '-----------------------------'.               
       01  REPHDR2.                                                     
               02  FILLER PIC X(09) VALUE 'LAST NAME'.                  
               02  FILLER PIC X(07) VALUE SPACES.                       
               02  FILLER PIC X(10) VALUE 'FIRST NAME'.                 
               02  FILLER PIC X(03) VALUE SPACES.                       
               02  FILLER PIC X(08) VALUE 'INITIAL'.                    
               02  FILLER PIC X(07) VALUE 'PHONE'.                      
               02  FILLER PIC X(09) VALUE 'EMPLOYEE'.                   
               02  FILLER PIC X(05) VALUE 'WORK'.                       
               02  FILLER PIC X(04) VALUE 'WORK'.                       
       01  REPHDR3.                                                     
               02  FILLER PIC X(37) VALUE SPACES.                       
               02  FILLER PIC X(07) VALUE 'NUMBER'.                     
               02  FILLER PIC X(09) VALUE 'NUMBER'.                     
               02  FILLER PIC X(05) VALUE 'DEPT'.                       
               02  FILLER PIC X(05) VALUE 'DEPT'.                       
               02  FILLER PIC X(04) VALUE 'NAME'.                       
                                                                        
      *****************************************************             
      * REPORT STRUCTURE                                  *             
      *****************************************************             
       01  REPDATA.                                                     
               02  RLNAME    PIC X(15).                                 
               02  FILLER    PIC X(01) VALUE SPACES.                    
               02  RFNAME    PIC X(12).                                 
               02  FILLER    PIC X(04) VALUE SPACES.                    
               02  RMIDINIT  PIC X(01).                                 
               02  FILLER    PIC X(04) VALUE SPACES.                    
               02  RPHONE    PIC X(04).                                 
               02  FILLER    PIC X(03) VALUE SPACES.                    
               02  REMPNO    PIC X(06).                                 
               02  FILLER    PIC X(03) VALUE SPACES.                    
               02  RDEPTNO   PIC X(03).                                 
               02  FILLER    PIC X(02) VALUE SPACES.                    
               02  RDEPTNAME PIC X(36).                                 
                                                                        
      *****************************************************             
      * WORKAREAS                                         *             
      *****************************************************             
       01  LNAME-WORK.                                                  
            49 LNAME-WORKL     PIC S9(4)  COMP.                         
            49 LNAME-WORKC     PIC X(15).                               
       01  FNAME-WORK.                                                  
            49 FNAME-WORKL     PIC S9(4)  COMP.                         
            49 FNAME-WORKC     PIC X(12).                               
       77  INPUT-SWITCH        PIC X          VALUE  'Y'.               
               88  NOMORE-INPUT               VALUE  'N'.               
       77  NOT-FOUND           PIC S9(9) COMP VALUE  +100.              
                                                                        
      *****************************************************             
      * VARIABLES FOR ERROR-HANDLING                      *             
      *****************************************************             
       01  ERROR-MESSAGE.                                               
               02  ERROR-LEN   PIC S9(4)  COMP VALUE +960.              
               02  ERROR-TEXT  PIC X(120) OCCURS 10 TIMES               
                                          INDEXED BY ERROR-INDEX.       
       77  ERROR-TEXT-LEN      PIC S9(9)  COMP VALUE +120.              
                                                                        
      /****************************************************             
      * SQL INCLUDE FOR SQLCA                             *             
      *****************************************************             
                EXEC SQL INCLUDE SQLCA  END-EXEC.                       
                                                                        
      *****************************************************             
      * SQL DECLARATION FOR VIEW VPHONE                   *             
      *****************************************************             
                EXEC SQL DECLARE VPHONE TABLE                           
                        (LASTNAME       VARCHAR(15)  NOT NULL,          
                         FIRSTNAME      VARCHAR(12)  NOT NULL,          
                         MIDDLEINITIAL     CHAR(01)  NOT NULL,          
                         PHONENUMBER       CHAR(04)          ,          
                         EMPLOYEENUMBER    CHAR(06)  NOT NULL,          
                         DEPTNUMBER        CHAR(03)  NOT NULL,          
                         DEPTNAME       VARCHAR(36)  NOT NULL)          
                      END-EXEC.                                         
                                                                        
      *****************************************************             
      * STRUCTURE FOR PPHONE RECORD                       *             
      *****************************************************             
       01  PPHONE.                                                      
              02  LASTNAME.                                             
                    49  LASTNAMEL      PIC S9(4)  COMP.                 
                    49  LASTNAMEC      PIC X(15)  VALUE SPACES.         
              02  FIRSTNAME.                                            
                    49  FIRSTNAMEL     PIC S9(4)  COMP.                 
                    49  FIRSTNAMEC     PIC X(12)  VALUE SPACES.         
              02  MIDDLEINITIAL        PIC X(01).                       
              02  PHONENUMBER          PIC X(04).                       
              02  EMPLOYEENUMBER       PIC X(06).                       
              02  DEPTNUMBER           PIC X(03).                       
              02  DEPTNAME.                                             
                    49  DEPTNAMEL      PIC S9(4)  COMP.                 
                    49  DEPTNAMEC      PIC X(36)  VALUE SPACES.         
      *                                                                 
       77  PERCENT-COUNTER             PIC S9(4)  COMP.                 
                                                                        
      *****************************************************             
      * SQL DECLARATION FOR VIEW VEMPLP                   *             
      *****************************************************             
                 EXEC SQL DECLARE VEMPLP TABLE                          
                         (EMPLOYEENUMBER   CHAR(06)  NOT NULL,          
                          PHONENUMBER      CHAR(04)          )          
                     END-EXEC.                                          
                                                                        
      *****************************************************             
      * SQL CURSORS                                       *             
      *****************************************************             
      *** CURSOR LISTS ALL EMPLOYEE NAMES                               
                                                                        
                EXEC SQL DECLARE TELE1 CURSOR FOR                       
                         SELECT *                                       
                         FROM   VPHONE                                  
                     END-EXEC.                                          
                                                                        
      *** CURSOR LISTS ALL EMPLOYEE NAMES WITH A PATTERN (%) OR (_)     
      *** FOR LAST NAME                                                 
                                                                        
                EXEC SQL DECLARE TELE2 CURSOR FOR                       
                         SELECT *                                       
                         FROM   VPHONE                                  
                         WHERE  LASTNAME  LIKE :LNAME-WORK              
                           AND  FIRSTNAME LIKE :FNAME-WORK              
                     END-EXEC.                                          
                                                                        
      *** CURSOR LISTS ALL EMPLOYEES WITH A SPECIFIC                    
      *** LAST NAME                                                     
                                                                        
                EXEC SQL DECLARE TELE3 CURSOR FOR                       
                         SELECT *                                       
                         FROM   VPHONE                                  
                         WHERE  LASTNAME   =   :LNAME                   
                           AND  FIRSTNAME LIKE :FNAME-WORK              
                      END-EXEC.                                         
      /                                                                 
      /****************************************************             
      * FIELDS SENT TO MESSAGE ROUTINE                    *             
      *****************************************************             
       01  MAJOR                     PIC X(07) VALUE 'DSN8BC3'.         
                                                                        
       01  MSGCODE                   PIC X(4).                          
                                                                        
       01  OUTMSG                    PIC X(69).                         
                                                                        
       01  MSG-REC1.                                                    
               02 OUTMSG1            PIC X(69).                         
               02 RETCODE            PIC S9(9).                         
                                                                        
       01  MSG-REC2.                                                    
               02 OUTMSG2            PIC X(69).                         
                                                                        
                                                                        
       PROCEDURE DIVISION.                                              
      *------------------                                               
                                                                        
      *****************************************************             
      * SQL RETURN CODE HANDLING                          *             
      *****************************************************             
      * 
                EXEC SQL WHENEVER SQLERROR   GOTO DBERROR END-EXEC.     
                EXEC SQL WHENEVER SQLWARNING GOTO DBERROR END-EXEC.     
                EXEC SQL WHENEVER NOT FOUND  CONTINUE     END-EXEC.     
                                                                        
      *****************************************************             
      * MAIN PROGRAM ROUTINE                              *             
      *****************************************************             
       PROG-START.                                                      
      *                                 **OPEN FILES                    
                OPEN INPUT  CARDIN                                      
                     OUTPUT REPOUT.                                     
                                                                        
      *                                 **GET FIRST INPUT               
                READ CARDIN RECORD INTO IOAREA                          
                   AT END MOVE 'N' TO INPUT-SWITCH.                     
                                                                        
      *                                 **MAIN ROUTINE                  
                PERFORM PROCESS-INPUT                                   
                   UNTIL NOMORE-INPUT.                                  
       PROG-END.                                                        
      *                                 **CLOSE FILES                   
                CLOSE CARDIN                                            
                      REPOUT.                                           
                GOBACK.                                                 
                                                                        
      *****************************************************             
      * CREATE REPORT HEADING                             *             
      * SELECT ACTION                                     *             
      *****************************************************             
       PROCESS-INPUT.                                                   
      *                                             **PRINT HEADING     
                WRITE REPREC FROM REPHDR1                               
                   AFTER ADVANCING TO-TOP-OF-PAGE.                      
                WRITE REPREC FROM REPHDR2                               
                   AFTER ADVANCING 2 LINES.                             
                WRITE REPREC FROM REPHDR3.                              
                                                                        
      *                                             **SELECT ACTION     
                IF ACTION = 'L'                                         
                   PERFORM LIST-FUNCTION                                
                ELSE                                                    
                   IF ACTION = 'U'                                      
                      PERFORM TELEPHONE-UPDATE                          
                                                                        
                   ELSE                                                 
      *                                           **INVALID REQUEST     
      *                                           **PRINT ERROR MESSAGE 
                MOVE '068E' TO MSGCODE                                  
                CALL 'DSN8MCG' USING MAJOR MSGCODE OUTMSG               
                MOVE OUTMSG TO OUTMSG2                                  
                WRITE REPREC FROM MSG-REC2                              
                         AFTER ADVANCING 2 LINES.                       
                READ CARDIN RECORD INTO IOAREA                          
                   AT END MOVE 'N' TO INPUT-SWITCH.                     
      /                                                                 
      *****************************************************             
      * DETERMINE FORM OF NAME USED TO LIST EMPLOYEES     *             
      *****************************************************             
       LIST-FUNCTION.                                                   
      *                                           **NO LAST NAME GIVEN  
                IF LNAME = SPACES                                       
                   MOVE '%' TO LNAME.                                   
      *                                           **NO FIRST NAME GIVEN 
                IF FNAME = SPACES                                       
                   MOVE '%' TO FNAME.                                   
      *                                           **LIST ALL EMPLOYEES  
                IF LNAME = '*'                                          
                   PERFORM LIST-ALL                                     
                ELSE                                                    
      *                                           **UNSTRING LAST NAME  
                   UNSTRING LNAME                                       
                      DELIMITED BY SPACE                                
                      INTO     LNAME-WORKC                              
                      COUNT IN LNAME-WORKL                              
      *                                           **UNSTRING FIRST NAME 
                   UNSTRING FNAME                                       
                      DELIMITED BY SPACE                                
                      INTO     FNAME-WORKC                              
                      COUNT IN FNAME-WORKL                              
      *                                            **COUNT %'S          
                   MOVE ZERO TO PERCENT-COUNTER                         
                   INSPECT LNAME                                        
                      TALLYING PERCENT-COUNTER FOR ALL '%'              
                   IF PERCENT-COUNTER > ZERO                            
      *                                         **IF NO %'S THEN        
      *                                         **LIST SPECIFIC NAME(S) 
      *                                         **ELSE                  
      *                                         **LIST GENERIC NAME(S)  
                      PERFORM LIST-GENERIC                              
                   ELSE                                                 
                      PERFORM LIST-SPECIFIC.                            
      /                                                                 
      *****************************************************             
      * LIST ALL EMPLOYEES                                *             
      *****************************************************             
       LIST-ALL.                                                        
      *                                           **OPEN CURSOR         
                EXEC SQL OPEN TELE1 END-EXEC.                           
                                                                        
      *                                           **GET EMPLOYEES       
                EXEC SQL FETCH TELE1 INTO :PPHONE END-EXEC.             
                                                                        
                IF SQLCODE = NOT-FOUND                                  
      *                                           **NO EMPLOYEE FOUND   
      *                                           **PRINT ERROR MESSAGE 
                  MOVE '008I' TO MSGCODE                                
                  CALL 'DSN8MCG' USING MAJOR MSGCODE OUTMSG             
                  MOVE OUTMSG TO OUTMSG2                                
                  WRITE REPREC FROM MSG-REC2                            
                      AFTER ADVANCING 2 LINES                           
                ELSE                                                    
      *                                           **LIST ALL EMPLOYEES  
                   PERFORM PRINT-AND-GET1                               
                      UNTIL SQLCODE IS NOT EQUAL TO ZERO.               
                                                                        
      *                                           **CLOSE CURSOR        
                EXEC SQL CLOSE TELE1 END-EXEC.                          
                                                                        
       PRINT-AND-GET1.                                                  
                PERFORM PRINT-A-LINE.                                   
                EXEC SQL FETCH TELE1 INTO :PPHONE END-EXEC.             
      /                                                                 
      *****************************************************             
      * LIST GENERIC EMPLOYEES                            *             
      *****************************************************             
       LIST-GENERIC.                                                    
      *                                           **OPEN CURSOR         
                EXEC SQL OPEN  TELE2 END-EXEC.                          
                                                                        
      *                                           **GET EMPLOYEES       
                EXEC SQL FETCH TELE2 INTO :PPHONE END-EXEC.             
                                                                        
                IF SQLCODE = NOT-FOUND                                  
      *                                           **NO EMPLOYEE FOUND   
      *                                           **PRINT ERROR MESSAGE 
                  MOVE '008I' TO MSGCODE                                
                  CALL 'DSN8MCG' USING MAJOR MSGCODE OUTMSG             
                  MOVE OUTMSG TO OUTMSG2                                
                  WRITE REPREC FROM MSG-REC2                            
                      AFTER ADVANCING 2 LINES                           
                ELSE                                                    
      *                                      **LIST GENERIC EMPLOYEE(S) 
                   PERFORM PRINT-AND-GET2                               
                      UNTIL SQLCODE IS NOT EQUAL TO ZERO.               
                                                                        
      *                                           **CLOSE CURSOR        
                EXEC SQL CLOSE TELE2 END-EXEC.                          
                                                                        
       PRINT-AND-GET2.                                                  
                PERFORM PRINT-A-LINE.                                   
                EXEC SQL FETCH TELE2 INTO :PPHONE END-EXEC.             
      /                                                                 
      *****************************************************             
      * LIST SPECIFIC EMPLOYEES                           *             
      *****************************************************             
       LIST-SPECIFIC.                                                   
      *                                           **OPEN CURSOR         
                EXEC SQL OPEN  TELE3 END-EXEC.                          
                                                                        
      *                                           **GET EMPLOYEES       
                EXEC SQL FETCH TELE3 INTO :PPHONE END-EXEC.             
                                                                        
                IF SQLCODE = NOT-FOUND                                  
      *                                           **NO EMPLOYEE FOUND   
      *                                           **PRINT ERROR MESSAGE 
                  MOVE '008I' TO MSGCODE                                
                  CALL 'DSN8MCG' USING MAJOR MSGCODE OUTMSG             
                  MOVE OUTMSG TO OUTMSG2                                
                  WRITE REPREC FROM MSG-REC2                            
                      AFTER ADVANCING 2 LINES                           
                ELSE                                                    
      *                                     **LIST SPECIFIC EMPLOYEE(S) 
                   PERFORM PRINT-AND-GET3                               
                      UNTIL SQLCODE IS NOT EQUAL TO ZERO.               
                                                                        
      *                                           **CLOSE CURSOR        
                EXEC SQL CLOSE TELE3 END-EXEC.                          
                                                                        
       PRINT-AND-GET3.                                                  
                PERFORM PRINT-A-LINE.                                   
                EXEC SQL FETCH TELE3 INTO :PPHONE END-EXEC.             
      /                                                                 
      *****************************************************             
      * PRINT A LINE OF INFORMATION FROM DIRECTORY        *             
      *****************************************************             
       PRINT-A-LINE.                                                    
      *                                           **GET INFORMATION     
                MOVE LASTNAMEC TO RLNAME.                               
                MOVE FIRSTNAMEC TO RFNAME.                              
                MOVE MIDDLEINITIAL TO RMIDINIT.                         
                MOVE PHONENUMBER OF PPHONE TO RPHONE.                   
                MOVE EMPLOYEENUMBER OF PPHONE TO REMPNO.                
                MOVE DEPTNUMBER TO RDEPTNO.                             
                MOVE DEPTNAMEC TO RDEPTNAME.                            
      *                                           **PRINT INFORMATION   
                WRITE REPREC FROM REPDATA                               
                   AFTER ADVANCING 2 LINES.                             
                                                                        
                MOVE SPACES TO LASTNAMEC                                
                               FIRSTNAMEC                               
                               DEPTNAMEC.                               
      /                                                                 
      *****************************************************             
      * UPDATES PHONE NUMBERS FOR EMPLOYEES               *             
      *****************************************************             
       TELEPHONE-UPDATE.                                                
                EXEC SQL UPDATE VEMPLP                                  
                            SET   PHONENUMBER    = :NEWNO               
                            WHERE EMPLOYEENUMBER = :ENO END-EXEC.       
                IF SQLCODE = ZERO                                       
      *                                         **EMPLOYEE FOUND        
      *                                         **UPDATE SUCCESSFUL     
      *                                         **PRINT CONFIRMATION    
      *                                         **MESSAGE               
                   MOVE '004I' TO MSGCODE                               
                ELSE                                                    
      *                                           **NO EMPLOYEE FOUND   
      *                                           **UPDATE FAILED       
      *                                           **PRINT ERROR MESSAGE 
                   MOVE '007E' TO MSGCODE.                              
                CALL 'DSN8MCG' USING MAJOR MSGCODE OUTMSG.              
                MOVE OUTMSG TO OUTMSG2.                                 
                WRITE REPREC FROM MSG-REC2                              
                      AFTER ADVANCING 2 LINES.                          
      /                                                                 
      *****************************************************             
      * SQL ERROR OCCURRED - GET ERROR MESSAGE            *             
      *****************************************************             
       DBERROR.                                                         
      *                                           **SQL ERROR           
      *                                           **PRINT ERROR MESSAGE 
                MOVE '060E' TO MSGCODE                                  
                CALL 'DSN8MCG' USING MAJOR MSGCODE OUTMSG.              
                MOVE OUTMSG TO OUTMSG1 OF  MSG-REC1.                    
                MOVE SQLCODE TO RETCODE OF MSG-REC1.                    
                WRITE REPREC FROM MSG-REC1                              
                   AFTER ADVANCING 2 LINES.                             
      * 
                CALL 'DSNTIAR' USING SQLCA ERROR-MESSAGE ERROR-TEXT-LEN.
                IF RETURN-CODE = ZERO                                   
                   PERFORM ERROR-PRINT VARYING ERROR-INDEX              
                      FROM 1 BY 1 UNTIL ERROR-INDEX GREATER THAN 10     
                ELSE                                                    
                                                                        
      *                                           **MESSAGE FORMAT      
      *                                           **ROUTINE ERROR       
      *                                           **PRINT ERROR MESSAGE 
                   MOVE '075E' TO MSGCODE                               
                   CALL 'DSN8MCG' USING MAJOR MSGCODE OUTMSG            
                   MOVE OUTMSG TO OUTMSG1 OF MSG-REC1                   
                   MOVE RETURN-CODE TO RETCODE OF MSG-REC1              
                   WRITE REPREC FROM MSG-REC1                           
                      AFTER ADVANCING 2 LINES.                          
                                                                        
      ***********************************************************       
      * SQL RETURN CODE HANDLING WHEN PROCESSING CANNOT PROCEED *       
      ***********************************************************       
      * 
                EXEC SQL WHENEVER SQLERROR   CONTINUE     END-EXEC.     
                EXEC SQL WHENEVER SQLWARNING CONTINUE     END-EXEC.     
                EXEC SQL WHENEVER NOT FOUND  CONTINUE     END-EXEC.     
                                                                        
      *                                            **PERFORM ROLLBACK   
                EXEC SQL ROLLBACK END-EXEC.                             
                                                                        
                IF SQLCODE = ZERO                                       
                                                                        
      *                                           **ROLLBACK SUCCESSFUL 
      *                                           **PRINT CONFIRMATION  
      *                                           **MESSAGE             
                   MOVE '053I' TO MSGCODE                               
                ELSE                                                    
                                                                        
      *                                            **ROLLBACK FAILED    
      *                                           **PRINT ERROR MESSAGE 
                   MOVE '061E' TO MSGCODE.                              
                CALL 'DSN8MCG' USING MAJOR MSGCODE OUTMSG.              
                MOVE OUTMSG TO OUTMSG1 OF MSG-REC1.                     
                MOVE SQLCODE TO RETCODE OF MSG-REC1.                    
                WRITE REPREC FROM MSG-REC1                              
                      AFTER ADVANCING 2 LINES.                          
                GO TO PROG-END.                                         
                                                                        
      *****************************************************             
      *  PRINT MESSAGE TEXT                               *             
      *****************************************************             
       ERROR-PRINT.                                                     
                WRITE REPREC FROM ERROR-TEXT (ERROR-INDEX)              
                   AFTER ADVANCING 1 LINE.      