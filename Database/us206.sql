CREATE OR REPLACE PROCEDURE us206createCulture(plant_species IN culture.PLANT_SPECIES%type,
type IN CULTURETYPES.ID%type) AS

l_id culture.ID%type;

BEGIN
    INSERT INTO culture(plant_species,type_id) VALUES(plant_species,type);
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Culture Created ID:'||(l_id+1));

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('It wasn´t possible to create client with the specified data');
END;

CREATE OR REPLACE FUNCTION us206ListPlotByCulture(culture IN VARCHAR DEFAULT 'CULTURE_TYPE') RETURN SYS_REFCURSOR AS
    result Sys_Refcursor;
BEGIN
    if(culture='CULTURE_TYPE') then
        OPEN result for SELECT plot.ID,EXPLORATION_ID,DESIGNATION,AREA,PLANT_SPECIES,TYPE_DESIGNATION
                        FROM PLOT
                        INNER JOIN PLANTEDCULTURE
                        ON PLOT.ID=PLANTEDCULTURE.CULTURE_ID
                        INNER JOIN CULTURE
                        ON CULTURE.ID=PLANTEDCULTURE.CULTURE_ID
                        INNER JOIN TYPE
                        ON culture.id=type.CULTURE_TYPES_ID
                        ORDER BY TYPE_DESIGNATION;
    else
        OPEN result for SELECT plot.ID,EXPLORATION_ID,DESIGNATION,AREA,PLANT_SPECIES,PLANT_SPECIES
                        FROM PLOT
                        INNER JOIN PLANTEDCULTURE
                        ON PLOT.ID=PLANTEDCULTURE.CULTURE_ID
                        INNER JOIN CULTURE
                        ON CULTURE.ID=PLANTEDCULTURE.CULTURE_ID
                        INNER JOIN TYPE
                        ON culture.id=type.CULTURE_TYPES_ID
                        ORDER BY culture.PLANT_SPECIES;
    end if;
    return result;
END;

CREATE OR REPLACE FUNCTION us206ListPlotBySize(order_type IN VARCHAR2 DEFAULT 'ASC') RETURN SYS_REFCURSOR AS
    result Sys_Refcursor;
BEGIN
    if (order_type='DESC') then
        OPEN result for SELECT PLOT.ID,EXPLORATION_ID,DESIGNATION,AREA,PLANT_SPECIES,TYPE_DESIGNATION
            FROM PLOT
                        INNER JOIN PLANTEDCULTURE
                        ON PLOT.ID=PLANTEDCULTURE.CULTURE_ID
                        INNER JOIN CULTURE
                        ON CULTURE.ID=PLANTEDCULTURE.CULTURE_ID
                        INNER JOIN TYPE
                        ON culture.id=type.CULTURE_TYPES_ID
            ORDER BY AREA DESC;
    else
        OPEN result for SELECT PLOT.ID,EXPLORATION_ID,DESIGNATION,AREA,PLANT_SPECIES,TYPE_DESIGNATION
            FROM PLOT
                        INNER JOIN PLANTEDCULTURE
                        ON PLOT.ID=PLANTEDCULTURE.CULTURE_ID
                        INNER JOIN CULTURE
                        ON CULTURE.ID=PLANTEDCULTURE.CULTURE_ID
                        INNER JOIN TYPE
                        ON culture.id=type.CULTURE_TYPES_ID
            ORDER BY AREA DESC;
    end if;
    return result;
END;

CREATE OR REPLACE PROCEDURE us206ListPlot(result out SYS_REFCURSOR) IS
BEGIN
    OPEN result for SELECT DISTINCT PLOT.ID AS PLOT_ID,plantedculture.id AS PLANTED_CULTURE_ID,EXPLORATION_ID,DESIGNATION,AREA,PLANT_SPECIES,TYPE_DESIGNATION
                        FROM PLOT
                        INNER JOIN PLANTEDCULTURE
                        ON PLOT.ID=PLANTEDCULTURE.CULTURE_ID
                        INNER JOIN CULTURE
                        ON CULTURE.ID=PLANTEDCULTURE.CULTURE_ID
                        INNER JOIN TYPE
                        ON culture.id=type.CULTURE_TYPES_ID
                        ORDER BY DESIGNATION;
END;