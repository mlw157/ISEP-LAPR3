CREATE OR REPLACE PROCEDURE CreateIrrigation(
irrigation_minutes IN irrigation.IRRIGATION_MINUTES%type,
type IN irrigation.TYPE%type,
operation_id IN operation.id%type
)
AS
BEGIN
    INSERT INTO irrigation(irrigation_minutes,type,operation_id) VALUES(irrigation_minutes,type,operation_id);
END;

CREATE OR REPLACE PROCEDURE CreateProductionFactorApplication(
production_factor_id IN PRODUCTIONFACTOR.ID%type,
quantity IN PRODUCTIONFACTORAPPLICATION.QUANTITY_APPLIED%type,
type IN productionfactorapplication.TYPE%type,
operation_id IN operation.id%type
)
AS
BEGIN
    INSERT INTO PRODUCTIONFACTORAPPLICATION(production_factor_id,type,quantity_applied,operation_id)
    VALUES(production_factor_id,type,quantity,operation_id);
END;

CREATE OR REPLACE PROCEDURE us210ExecuteOperation (
    id_op operation.ID%TYPE)
AS
    l_status operation.STATUS%TYPE;
BEGIN

    SELECT status
    INTO l_status
    FROM operation
    WHERE id = id_op;

    IF (l_status = 'PENDENTE') THEN
        UPDATE operation SET status = 'COMPLETO' WHERE id = id_op;
        UPDATE operation_calendar SET end_date=SYSDATE WHERE id = id_op;
        DBMS_OUTPUT.PUT_LINE('Operacao concluida');
    END IF;
    IF (l_status = 'COMPLETO') THEN
        DBMS_OUTPUT.PUT_LINE('Operacao ja foi concluida');
    END IF;
    IF (l_status = 'CANCELADO') THEN
        DBMS_OUTPUT.PUT_LINE('Operacao ja foi cancelada');
    END IF;
END;

CREATE OR REPLACE PROCEDURE us210ListOperations(id_plot IN plot.id%TYPE,
s_date IN OPERATION_CALENDAR.START_DATE%type,
e_date IN OPERATION_CALENDAR.END_DATE%type,
result OUT SYS_REFCURSOR)
AS
BEGIN

    open result for SELECT operation.id,operation.status,operation_calendar.START_DATE,OPERATION_CALENDAR.END_DATE FROM OPERATION
                    INNER JOIN OPERATION_CALENDAR
                        ON OPERATION.ID=OPERATION_CALENDAR.OPERATION_ID
                    WHERE PLOT_ID=id_plot  and s_date<=operation_calendar.START_DATE and e_date>=OPERATION_CALENDAR.END_DATE;
END;

CREATE OR REPLACE PROCEDURE us210ListRestrictions(id_plot IN plot.id%TYPE,
r_date IN PLOTRESTRICTIONS.RESTRICTION_BEGINING_DATE%type,
result OUT SYS_REFCURSOR)
AS
BEGIN

    open result for SELECT * FROM PLOTRESTRICTIONS
                    WHERE PLOT_ID=id_plot
                    and r_date>=RESTRICTION_BEGINING_DATE
                    and r_date<=RESTRICTION_END_DATE;
END;

CREATE OR REPLACE PROCEDURE us210RegisterOperation(status IN operation.status%type,
start_date IN operation_calendar.start_date%type,
end_date IN operation_calendar.end_date%type,
plot_id IN plot.id%type,
production_factor_id IN PRODUCTIONFACTOR.ID%type,
operation_type IN VARCHAR)
AS
o_id operation.id%type;
o_type VARCHAR(255);
BEGIN
    o_type:=UPPER(operation_type);
    if(status is not null) THEN
        INSERT INTO operation(status) VALUES(status)
        return id into o_id;
        dbms_output.PUT_LINE(o_id);
        CASE o_type
            WHEN 'IRRIGATION' THEN
                CreateIrrigation(10,1,o_id);
            WHEN 'PRODUCTION FACTOR APPLICATION' THEN
                CreateProductionFactorApplication(1,1,10,o_id);
        ELSE
            RAISE_APPLICATION_ERROR(-20002,'Opearation type does not exist');
        END CASE;
        if(start_date is null) THEN 
            if(us210checkRestrictions(plot_id,production_factor_id,start_date,end_date))then
                INSERT INTO operation_calendar(operation_id,start_date,end_date) VALUES(o_id,CURRENT_DATE,end_date);
                ELSE
                INSERT INTO operation_calendar(operation_id,start_date,end_date) VALUES(o_id,start_date,end_date);
                END IF;
        END IF;
    ELSE
        RAISE_APPLICATION_ERROR(-20001,'Values introduced are not valid');
    END IF;
END;

CREATE OR REPLACE function us210checkRestrictions(id_plot IN plot.id%type,
id_production_factor IN PRODUCTIONFACTOR.ID%type,
operation_start_date IN OPERATION_CALENDAR.START_DATE%type,
operation_end_date IN OPERATION_CALENDAR.END_DATE%type)RETURN BOOLEAN
AS
restriction_begining_date PLOTRESTRICTIONS.RESTRICTION_BEGINING_DATE%type;
restriction_end_date PLOTRESTRICTIONS.RESTRICTION_END_DATE%type;
flag BOOLEAN:=true;
CUR SYS_REFCURSOR;

BEGIN

    open CUR for select RESTRICTION_BEGINING_DATE,RESTRICTION_END_DATE from PLOTRESTRICTIONS WHERE PLOT_ID=id_plot and PRODUCTION_FACTOR_ID=id_production_factor;
    LOOP
        FETCH CUR into restriction_begining_date,restriction_end_date;
        EXIT WHEN CUR%notfound;
        
        if(restriction_begining_date<operation_start_date or restriction_end_date>operation_end_date)then
            flag:=false;
        ELSE
            flag:=true;
        end if;
        return flag;
    end loop;
end;