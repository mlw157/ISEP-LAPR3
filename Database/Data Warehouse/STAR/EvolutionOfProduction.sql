CREATE OR REPLACE FUNCTION EvolutionOfProduction(sector IN PRODUCTION.SECTOR_ID%type, product IN PRODUCT.NAME%type, Ano IN TIME.YEAR%type, Mes IN TIME.MONTH%type) RETURN NUMBER AS

    amountCurrent          INTEGER;
    amountPast             INTEGER;
    tmpMonth          NUMBER(2, 0);
    tmpYear           NUMBER(4, 0);

BEGIN
    SELECT AMOUNT
    into amountCurrent
    FROM PRODUCTION
             JOIN PRODUCT product1 on product1.ID = PRODUCTION.PRODUCT_ID
             JOIN TIME time on time.ID = PRODUCTION.TIME_ID
    WHERE SECTOR_ID = sector
      AND product1.NAME = product
      AND time.YEAR = Ano
      AND time.MONTH = Mes;

    tmpYear := Ano;
    tmpMonth := Mes - 1;

    if (tmpMonth <= 0) THEN
        tmpMonth := 12;
        tmpYear := tmpYear - 1;
    end if;

    SELECT AMOUNT
    into amountPast
    FROM PRODUCTION
             JOIN PRODUCT product1 on product1.ID = PRODUCTION.PRODUCT_ID
             JOIN TIME time on time.ID = PRODUCTION.TIME_ID
    WHERE SECTOR_ID = sector
      AND product1.NAME = product
      AND time.YEAR = tmpYear
      AND time.MONTH = tmpMonth;

    return amountCurrent - amountPast;

EXCEPTION
    WHEN OTHERS THEN
        return NULL;
END;