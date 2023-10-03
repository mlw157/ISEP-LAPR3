CREATE OR REPLACE FUNCTION fncUS212GetTheNthSensorReading(readNumber IN INTEGER)
RETURN VARCHAR
AS
    result   VARCHAR(25);
    tmp      VARCHAR(25);
    readings INTEGER;
    line      SYS_REFCURSOR;
    tmpCount     INTEGER;
BEGIN
    result := NULL;
    tmpCount := 1;
    SELECT count(*) into readings FROM input_sensor;
    if (readNumber > readings ) THEN
        RAISE_APPLICATION_ERROR(-20005, 'There is no entry for the ' || readNumber || ' position! There are only ' ||
                                        readings || ' entries!');
    end if;
    if (readNumber < 0 ) THEN
        RAISE_APPLICATION_ERROR(-20004, 'There is no entry for the negative numbers like ' || readNumber );
    end if;
    OPEN line FOR SELECT input_string from input_sensor;
    LOOP
        FETCH line INTO tmp;
        if (tmpCount =readNumber) THEN
            result := tmp;
        end if;
        tmpCount := tmpCount + 1;
        EXIT WHEN line%notfound;
    end loop;
    close line;
    return result;
end;

CREATE OR REPLACE FUNCTION fncUS212IsValidReading(reading IN varchar,
                                                  id OUT VARCHAR2,
                                                  sensorType OUT VARCHAR2,
                                                  value OUT NUMBER,
                                                  uniqueNum OUT NUMBER,
                                                  readingDate OUT DATE
                                                  ) RETURN boolean AS

    iden       VARCHAR2(5);
    senType    VARCHAR2(2);
    val        VARCHAR2(3);             
    idNum      VARCHAR2(2);
    charDate   VARCHAR2(100);
    flag       BOOLEAN      := TRUE;

BEGIN

    iden := SUBSTR(reading, 0, 5);
    senType := SUBSTR(reading, 6, 2);
    val := SUBSTR(reading, 8, 3);
    idNum := SUBSTR(reading, 11, 2);
    charDate := SUBSTR(reading, 13);
    dbms_output.put_line(reading);
    if (iden is null OR senType is null OR val is null OR idNum is null OR charDate is null) then
        flag := FALSE;
    end if;
    id := iden;
    if (NOT (senType = 'HS' OR senType = 'PL' OR senType = 'TS' OR senType = 'VV' OR senType = 'TA'
        OR senType = 'HA' OR senType = 'PA')) THEN
        flag := false;
    end if;
    sensorType := senType;
    if (TO_NUMBER(val, '999') > 100 OR TO_NUMBER(val, '999') < 0) THEN
        flag := false;
    end if;
    value := TO_NUMBER(val, '999');
    uniqueNum := TO_NUMBER(idNum, '99');
    readingDate := TO_DATE(charDate, 'DDMMYYYYHH24MI');
    return flag;
EXCEPTION
    WHEN OTHERS THEN
        return false;
end;

CREATE OR REPLACE PROCEDURE prcUS212TransferInputsToSensorReadings(numberValid OUT NUMBER,
                                                                   numberInvalid OUT NUMBER) AS
    numValid    INTEGER;
    numInvalid  INTEGER;
    numCounter  INTEGER;
    CUR         SYS_REFCURSOR;
    reading     VARCHAR2(25);
    rid         input_sensor.ID%type;
    idSen       VARCHAR2(5);
    senType     VARCHAR2(2);
    value       NUMBER(3);
    uniqueNum   NUMBER(2);
    readingDate date;
    counterValid    INTEGER;
    counterInvalid    INTEGER;
    curDate     DATE;
    aux INTEGER;
    regtimes INTEGER;
    referrors INTEGER;

BEGIN
    counterValid:=0;
    counterInvalid:=0;
    numValid:=0;
    numInvalid:=0;
    numCounter:=0;
    aux:=0;
    regtimes:=0;
    referrors:=1;
    open CUR for SELECT * FROM input_sensor;
    select CURRENT_DATE into curDate from dual;
    LOOP
        FETCH CUR into rid,reading;
        EXIT WHEN CUR%NOTFOUND;
        if (FNCUS212ISVALIDREADING(reading, idSen, senType, value, uniqueNum, readingDate)) then
            numValid := numValid + 1;
            SELECT count(*) into counterValid FROM SENSOR WHERE SENSOR.ID = idSen;
            if (counterValid = 0) THEN
                INSERT INTO SENSOR(id,type, unique_Number) VALUES (idSen,senType, uniqueNum);
            end if;
            INSERT INTO SENSORDATA(RECORD_DATE,sensor_id, VALUE) VALUES (readingDate, idSen, value);

            DELETE INPUT_SENSOR WHERE ID = rid;
        else
        SELECT count(*) into counterInvalid FROM SENSORERROR WHERE SENSOR_ID = idSen;
         if (counterInvalid = 0) THEN
                INSERT INTO SENSORERROR(SENSOR_ID,total_errors) VALUES (idSen, 0);
            end if;
            SELECT total_errors into aux FROM SENSORERROR Where SENSOR_ID=idSen;
            UPDATE SENSORERROR
            SET total_errors=aux+1
            WHERE SENSOR_ID=idsen;
            numInvalid := numInvalid + 1;
        end if;
         numCounter := numCounter + 1;
    end LOOP;
    INSERT INTO SENSORREGIST(read_date,read_reg,ins_reg,not_read_reg) VALUES (curDate,numValid,numCounter,numInvalid);
    numberValid := numValid;
    numberInvalid := numInvalid;
end;