CREATE OR REPLACE FUNCTION us205createClient(expl_id IN client.exploration_id%type,
name IN client.name%type,
fiscal_number IN client.fiscal_number%type,
email IN client.email%type,
plafond IN client.plafond%type,
level IN client.client_level%type,
inc_num IN client.incident_number%type,
inc_date IN client.last_incident_date%type,
total_orders IN client.total_orders_value_last_year%type,
amount_of_orders IN client.amount_of_orders_last_year%type,
lev_id IN client.level_id%type,
c_address IN client.correspondence_address%type,
d_address IN client.default_delivery_address%type,
p_code IN client.postal_code%type,
def_hub IN client.default_hub_id%type
)RETURN client.id%type AS
l_id client.id%type;
BEGIN
    INSERT INTO Client(exploration_id,name, fiscal_number, email, plafond, client_level, incident_number, last_incident_date, amount_of_orders_last_year, total_orders_value_last_year, level_id, correspondence_address, default_delivery_address, postal_code,default_hub_id) VALUES (expl_id,name,fiscal_number,email,plafond,level,inc_num,inc_date,amount_of_orders,total_orders,lev_id,c_address,d_address,p_code,def_hub);
    DBMS_OUTPUT.PUT_LINE('Client Created ID:'||(l_id));
    return l_id;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Nao foi possivel criar o cliente!');
END;


CREATE OR REPLACE FUNCTION us205RiskFactor(id_client IN CLIENT.id%TYPE) RETURN INTEGER AS
    result     INTEGER;
    tmp        INTEGER;
    cur        Sys_Refcursor;
    basketId   BASKETORDER.ID%type;
    amount     BASKETORDER.total_amount%type;
    inc_num    INTEGER;
    c_id       client.id%type;
BEGIN
    OPEN cur FOR SELECT Bo.ID, Bo.TOTAL_AMOUNT
                 FROM BASKETORDER Bo
                          JOIN CLIENT C2 on C2.id = Bo.client_id
                 WHERE order_date >= COALESCE(last_incident_date, TO_DATE('01/01/0001', 'DD/MM/YYYY'))
                   AND status = 'Registada'
                   AND client_id = id_client;
    result := 0;

    LOOP
        FETCH cur INTO c_id, amount;
        EXIT WHEN cur%notfound;
        result := result + amount;
    end loop;

    SELECT COUNT(*) into inc_num FROM BASKETORDER
    WHERE status = 'Registada'
      AND client_id = id_client
      AND order_date >= SYSDATE - 365;
    return result / inc_num;
EXCEPTION
    WHEN ZERO_DIVIDE THEN
        return 0;

END;


CREATE OR REPLACE FUNCTION us206ListPlotByCulture(culture IN VARCHAR DEFAULT 'CULTURE_TYPE') RETURN SYS_REFCURSOR AS
    result Sys_Refcursor;
BEGIN
    if(culture='CULTURE_TYPE') then
        OPEN result for SELECT ID_PLOT,EXPLORATION_ID,DESIGNATION,AREA,PLANT_SPECIES,CULTURE_TYPE
                        FROM PLOT
                        INNER JOIN CULTURE
                        ON CULTURE.ID_CULTURE=PLOT.CULTURE_ID
                        ORDER BY CULTURE_TYPE;
    else
        OPEN result for SELECT ID_PLOT,EXPLORATION_ID,DESIGNATION,AREA,PLANT_SPECIES,CULTURE_TYPE
                        FROM PLOT
                        INNER JOIN CULTURE
                        ON CULTURE.ID_CULTURE=PLOT.CULTURE_ID
                        ORDER BY PLANT_SPECIES;
    end if;
    return result;
END;


CREATE OR REPLACE FUNCTION us206ListPlotBySize(order_type IN VARCHAR2 DEFAULT 'ASC') RETURN SYS_REFCURSOR AS
    result Sys_Refcursor;
BEGIN
    if (order_type='DESC') then
        OPEN result for SELECT ID_PLOT,EXPLORATION_ID,DESIGNATION,AREA,PLANT_SPECIES,CULTURE_TYPE
            FROM PLOT
            INNER JOIN CULTURE
            ON CULTURE.ID_CULTURE=PLOT.CULTURE_ID
            ORDER BY AREA DESC;
    else
        OPEN result for SELECT ID_PLOT,EXPLORATION_ID,DESIGNATION,AREA,PLANT_SPECIES,CULTURE_TYPE
            FROM PLOT
            INNER JOIN CULTURE
            ON CULTURE.ID_CULTURE=PLOT.CULTURE_ID
            ORDER BY AREA ASC;
    end if;
    return result;
END;

