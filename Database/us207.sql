CREATE OR REPLACE PROCEDURE us207ListExplorationProductionQuantity(id_plot IN plot.id%type DEFAULT 1,result out SYS_REFCURSOR) IS
BEGIN
    OPEN result for  SELECT plot.id,culture.id,culture.plant_species, SUM(STOREDCULTURE.QUANTITY) AS quantidade_producao FROM plot
    INNER JOIN PLANTEDCULTURE
        ON PLANTEDCULTURE.PLOT_ID=plot.id
    INNER JOIN CULTURE
        ON CULTURE.TYPE_ID=PLANTEDCULTURE.CULTURE_ID
    INNER JOIN STOREDCULTURE
        ON CULTURE.ID=STOREDCULTURE.CULTURE_ID
    WHERE plot.id=id_plot
    GROUP BY plot.id,culture.plant_species,STOREDCULTURE.QUANTITY,culture.id
    ORDER BY quantidade_producao DESC;
END;

CREATE OR REPLACE PROCEDURE us207ListExplorationProfit(id_plot IN plot.id%type,result out SYS_REFCURSOR) 
AS
    total_gasto INTEGER;
BEGIN
    total_gasto:=100;
    OPEN result for SELECT plot.ID, (SUM(BASKETORDER.total_amount)-total_gasto) AS quantidade_lucro FROM plot
                    INNER JOIN exploration
                        ON plot.EXPLORATION_ID=exploration.id
                    INNER JOIN CLIENT
                        ON client.EXPLORATION_ID=EXPLORATION.ID
                    INNER JOIN BASKETORDER
                        ON client.id=basketorder.client_id
                    WHERE plot.id=id_plot
                    GROUP BY BASKETORDER.CLIENT_ID,client.EXPLORATION_ID,plot.EXPLORATION_ID,plot.id
                    ORDER BY quantidade_lucro DESC;
END;