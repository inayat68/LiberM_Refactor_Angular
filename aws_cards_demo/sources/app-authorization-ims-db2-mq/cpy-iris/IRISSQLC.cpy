      ******************************************************************
      * Author : mLogica
      * Product: IRIS-DB - v. 5.5.0
      ******************************************************************
      *
      *   DESCRIPTION: IRIS DATA DICTIONARY CONSTANTS
      *
      ******************************************************************
      *
       01 IRIS-DB-SQLCODE                  PIC S9(9) COMP.
         88 IRIS-SQL-OK                              VALUE 0.
         88 IRIS-SQL-UNIQ-CONSTR-VIOLATED            VALUE -803.
         88 IRIS-SQL-DEADLOCK                        VALUE -911.
         88 IRIS-SQL-INTE-CONSTR-VIOLATED            VALUE -530.
         88 IRIS-SQL-NOT-FOUND                       VALUE +100.
         88 IRIS-SQL-END-DB-REACHED                  VALUE +999.
         88 IRIS-SQL-CHG-SEG                         VALUE +998.
         88 IRIS-SQL-CHG-LEV                         VALUE +997.
         88 IRIS-SQL-DUAL-PCB-MISMATCH               VALUE +996.
         88 IRIS-SQL-DUAL-IOAREA-MISMATCH            VALUE +995.
         88 IRIS-SQL-CURSOR-ALREADY-OPEN             VALUE -502.
         88 IRIS-SQL-NOT-VALID-LENGTH                VALUE +994.
         88 IRIS-SQL-DELETE-RULE-VIOLATED            VALUE +993.
         88 IRIS-SQL-UPDATE-RULE-VIOLATED            VALUE +992.
      *
      * OBJECT TYPES
      *
       01 DATA-DICT-OBJECT-TYPE            PIC S9(4) COMP-5.
         88 DATA-DICT-DBD                            VALUE 101.
         88 DATA-DICT-PSB                            VALUE 102.
         88 DATA-DICT-COPYBOOK                       VALUE 103.
         88 DATA-DICT-XML                            VALUE 104.
         88 DATA-DICT-SQL                            VALUE 105.
         88 DATA-DICT-BRIDGE                         VALUE 106.
         88 DATA-DICT-LOG                            VALUE 107.
         88 DATA-DICT-LOADER                         VALUE 108.
      *
      * OBJECT SUB-TYPES
      *
       01 DATA-DICT-OBJECT-SUBTYPE         PIC S9(4) COMP-5.
         88 DATA-DICT-DBD-PHYSICAL                   VALUE 201.
         88 DATA-DICT-DBD-INDEX                      VALUE 202.
         88 DATA-DICT-DBD-LOGICAL                    VALUE 203.
      *
      * OBJECT SUB-TYPES
      *
       01 BRIDGES-INTERNAL-TYPES           PIC S9(4) COMP-5.
         88 FLD-TYPE-STANDARD                        VALUE 2000.
         88 FLD-TYPE-PRIMARY-KEY                     VALUE 2001.
         88 FLD-TYPE-PRIMARY-KEY-SUBSET              VALUE 2002.
         88 FLD-TYPE-PARENT-KEY                      VALUE 2003.
         88 FLD-TYPE-PARENT-KEY-SUBSET               VALUE 2004.
         88 FLD-TYPE-NOKEY-PROGRESSIVE               VALUE 2005.
         88 FLD-TYPE-MULTIKEY-PROGRESSIVE            VALUE 2006.
         88 FLD-TYPE-CONCAT-KEY-FIELD                VALUE 2007.
         88 FLD-TYPE-SX-SYSTEM-FIELD                 VALUE 2008.
         88 FLD-TYPE-XDFLD-KEY                       VALUE 2009.
         88 FLD-TYPE-XDFLD-KEY-SUBSET                VALUE 2010.
         88 FLD-TYPE-XDFLD-KEY-OVER-COPY             VALUE 2011.
      *
      * FIELD SEQUENCE TYPE
      *
       01 SEGMENT-FIELD-SEQUENCE           PIC X.
         88 FIELD-SEQUENCE-NONE                      VALUE '0'.
         88 FIELD-SEQUENCE-UNIQUE                    VALUE '1'.
         88 FIELD-SEQUENCE-MULTI                     VALUE '2'.

       01 WS-SQLSTMT                       PIC X(256).
