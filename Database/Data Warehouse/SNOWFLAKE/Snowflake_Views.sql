CREATE OR REPLACE VIEW EvolutionInLastFiveYears AS

SELECT Prd.ID, Tm.YEAR, Tm.MONTH, SECTOR_ID, product.NAME, AMOUNT,
       COALESCE(TO_CHAR(EvolutionOfProduction(SECTOR_ID, product.NAME, Tm.YEAR, Tm.MONTH)), 'No data available to make a comparison with the last month!') as EVOLUTION
FROM PRODUCTION Prd
         JOIN TIME Tm on Tm.ID = Prd.TIME_ID
         JOIN PRODUCT product on product.ID = prd.PRODUCT_ID
WHERE Tm.YEAR >= TO_NUMBER(TO_CHAR(SYSDATE, 'YYYY'), '9999') - 5;




CREATE OR REPLACE VIEW SalesComparison AS

SELECT Time1.MONTH,
       Product.NAME                 as Product_Name,
       Time1.YEAR                   as First_Year,
       Sale1.QUANTITY               as First_Year_Sales,
       Time2.YEAR                   as Second_Year,
       Sale2.QUANTITY               as Second_Year_Sales,
       Sale1.QUANTITY - Sale2.QUANTITY as Years_Comparison
FROM SALE Sale1
         JOIN TIME Time1 on Sale1.TIME_ID = Time1.ID
         JOIN PRODUCT Product on Product.ID = Sale1.PRODUCT_ID,
     SALE Sale2
         JOIN TIME Time2 on Time2.ID = Sale2.TIME_ID
WHERE Time1.MONTH = Time2.MONTH
  AND Sale1.PRODUCT_ID = Sale2.PRODUCT_ID
  AND Sale1.CLIENT_ID = Sale2.CLIENT_ID;




CREATE OR REPLACE VIEW MonthlySalesEvolutionByCultureType AS

SELECT DISTINCT TM.YEAR, TM.MONTH, TYPE, sum(QUANTITY) as Quantity, COALESCE(TO_CHAR(sum(QUANTITY) - (SELECT DISTINCT SUM(QUANTITY)
                                 FROM PRODUCT Previous
                                          JOIN SALE Sale3 on Previous.ID = Sale3.PRODUCT_ID
                                          JOIN TIME Time2 on Time2.ID = Sale3.TIME_ID
                                 WHERE Previous.TYPE = Actual.TYPE
                                   AND Time2.ID = (SELECT ID
                                                    FROM TIME Time3
                                                    WHERE (Time3.MONTH = TM.MONTH - 1 AND Time3.YEAR = TM.YEAR)
                                                       OR (Time3.YEAR = TM.YEAR - 1 AND Time3.MONTH = 12)
                                                    ORDER BY YEAR DESC, MONTH FETCH FIRST ROW ONLY))), 'No values to compare!') as EVOLUTION
FROM PRODUCT Actual
         JOIN SALE Sale2 on Actual.ID = Sale2.PRODUCT_ID
         JOIN TIME TM on TM.ID = Sale2.TIME_ID
GROUP BY TM.YEAR, TM.MONTH, TYPE
ORDER BY TM.YEAR, TM.MONTH;




CREATE OR REPLACE VIEW MonthlySalesEvolutionByCultureTypeAndHub AS

SELECT DISTINCT TM.YEAR, TM.MONTH, TYPE, HUB_NAME, sum(QUANTITY) as Quantity, COALESCE(TO_CHAR(sum(QUANTITY) - (SELECT DISTINCT SUM(QUANTITY)
                                 FROM PRODUCT Previous
                                          JOIN SALE Sale3 on Previous.ID = Sale3.PRODUCT_ID
                                          JOIN TIME Time2 on Time2.ID = Sale3.TIME_ID
                                          JOIN HUB Parent on Parent.ID = Sale3.HUB_ID
                                 WHERE Previous.TYPE = Actual.TYPE
                                   AND Parent.HUB_NAME = CHILD.HUB_NAME
                                   AND Time2.ID = (SELECT ID
                                                    FROM TIME Time3
                                                    WHERE (Time3.MONTH = TM.MONTH - 1 AND Time3.YEAR = TM.YEAR)
                                                       OR (Time3.YEAR = TM.YEAR - 1 AND Time3.MONTH = 12)
                                                    ORDER BY YEAR DESC, MONTH FETCH FIRST ROW ONLY))), 'No values to compare!') as EVOLUTION
FROM PRODUCT Actual
         JOIN SALE Sale2 on Actual.ID = Sale2.PRODUCT_ID
         JOIN TIME TM on TM.ID = Sale2.TIME_ID
         JOIN HUB CHILD on CHILD.ID = Sale2.HUB_ID
GROUP BY TM.YEAR, TM.MONTH, TYPE, HUB_NAME
ORDER BY TM.YEAR, TM.MONTH;