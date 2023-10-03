CREATE OR REPLACE PROCEDURE us215UpdateHub
AS
    	cur     SYS_REFCURSOR;
	str     VARCHAR2(255);
      code    VARCHAR(255);
      lat    VARCHAR2(255);
      lon    VARCHAR2(255);
      cli_pro VARCHAR2(255);
BEGIN
      OPEN cur FOR SELECT input_string FROM INPUT_HUB;
    	LOOP
      	FETCH CUR INTO str;
        	EXIT WHEN cur%NOTFOUND;
        	code:=REGEXP_SUBSTR(str,'[^;]+',1,1);
        	lat:=REGEXP_SUBSTR(str,'[^;]+',1,2);
        	lon:=REGEXP_SUBSTR(str,'[^;]+',1,3);
        	cli_pro:=REGEXP_SUBSTR(str,'[^;]+',1,4);

        	DBMS_OUTPUT.PUT_LINE(code);
        	DBMS_OUTPUT.PUT_LINE(lat);
        	DBMS_OUTPUT.PUT_LINE(lon);
        	DBMS_OUTPUT.PUT_LINE(cli_pro);
        	if cli_pro LIKE 'E%' or cli_pro LIKE 'P%' THEN
            	INSERT INTO DistributionHub(loc_id, lat, lon, clients_productors) VALUES (code, lat, lon, cli_pro);
        	end if;
    	end loop;
end;


CREATE OR REPLACE PROCEDURE us215AlterDefaultHub (client_id IN client.ID%TYPE, hub IN DISTRIBUTIONHUB.ID%type) AS
	hubs integer;
BEGIN
  	SELECT COUNT(*) INTO hubs FROM DistributionHub WHERE id = hub;
  	IF hubs = 0 THEN
    		RAISE_APPLICATION_ERROR(-20001, 'Hub não existe!');
  	END IF;
  	UPDATE Client c SET c.default_hub_id = hub
  	WHERE c.id = client_id;
END;


CREATE OR REPLACE TRIGGER us215AlterBasketOrder
BEFORE INSERT ON BasketOrder
FOR EACH ROW
DECLARE
    hubs INTEGER;
    default_hub Client.default_hub_id%TYPE;
BEGIN
    IF :new.pickup_hub_id IS NULL THEN
        SELECT c.default_hub_id INTO default_hub FROM client c 
        WHERE c.default_hub_id = :new.client_id;

        :new.pickup_hub_id := default_hub;
    END IF;
    SELECT COUNT(*) INTO hubs FROM DistributionHub WHERE id = :new.pickup_hub_id;
    IF hubs = 0 THEN
        RAISE_APPLICATION_ERROR(-20999, 'Nao foi possivel alterar o hub por defeito para esta encomenda!');
    END IF;

END;