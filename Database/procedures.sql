CREATE OR REPLACE PROCEDURE us205UpdateClient(client_id IN CLIENT.id%type, numero_de_encomendas IN CLIENT.amount_of_orders_last_year%type DEFAULT NULL, gasto IN CLIENT.total_orders_value_last_year%type DEFAULT NULL) as
   	novoNumero CLIENT.amount_of_orders_last_year%type;
   	novoGasto  CLIENT.total_orders_value_last_year%type;
BEGIN

    	SELECT total_orders_value_last_year, amount_of_orders_last_year INTO novoGasto,novoNumero FROM CLIENT WHERE ID = clientId;

    	if (numero_de_encomendas IS NOT NULL) THEN
     	   novoNumero := numero_de_encomendas;
    	end if;

    	if (gasto IS NOT NULL) THEN
    	    novoGasto := gasto;
    	end if;

	UPDATE CLIENT SET total_orders_value_last_year = novoGasto, amount_of_orders_last_year = novoNumero WHERE Client.id = client_id;
end;

CREATE OR REPLACE PROCEDURE us206createCulture(plant_species IN culture.PLANT_SPECIES%type,
type IN CULTURE.CULTURE_TYPE%type) AS

l_id culture.id_culture%type;

BEGIN
    SELECT MAX(id_culture) into l_id FROM culture;
    if l_id<1 THEN
        l_id:=0;
    end if;
    INSERT INTO culture VALUES(l_id+1,plant_species,type);
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Culture Created ID:'||(l_id+1));

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('It wasn´t possible to create client with the specified data');
END;

CREATE OR REPLACE PROCEDURE us206ListPlot(result out SYS_REFCURSOR) IS
BEGIN
    OPEN result for SELECT ID_PLOT,EXPLORATION_ID,DESIGNATION,AREA,PLANT_SPECIES,CULTURE_TYPE
                        FROM PLOT
                        INNER JOIN CULTURE
                        ON CULTURE.ID_CULTURE=PLOT.CULTURE_ID
                        ORDER BY DESIGNATION;
END;

CREATE OR REPLACE PROCEDURE us207ListExplorationProductionQuantity(result out SYS_REFCURSOR) IS
BEGIN
    OPEN result for  SELECT exploration.id_exploration, SUM(stored_culture.quantity) AS quantidade_producao FROM exploration
    INNER JOIN plot
        ON exploration.ID_EXPLORATION=plot.EXPLORATION_ID
    INNER JOIN CULTURE
        ON CULTURE.ID_CULTURE=plot.CULTURE_ID
    INNER JOIN STORED_CULTURE
        ON CULTURE.ID_CULTURE=STORED_CULTURE.CULTURE_ID
    GROUP BY exploration.id_exploration
    ORDER BY quantidade_producao DESC;
END;

CREATE OR REPLACE PROCEDURE us207ListExplorationProfit(result out SYS_REFCURSOR) IS
BEGIN
    OPEN result for SELECT exploration.id_exploration, SUM(client_order.total_amount) AS quantidade_lucro FROM exploration
                    INNER JOIN client
                        ON client.exploration_id=exploration.id_exploration
                    INNER JOIN client_order
                        ON client.id_client=client_order.client_id
                    GROUP BY exploration.id_exploration
                    ORDER BY quantidade_lucro DESC;
END;
 COMMIT;
    DBMS_OUTPUT.PUT_LINE('Element Created ID:'||(l_id+1));

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('It wasn´t possible to create client with the specified data');
END;

CREATE OR REPLACE PROCEDURE us205UpdateClient(ClientID IN client.id_client%type,
number_of_orders IN client.amount_of_orders%type,
value_of_orders IN client.total_orders_value%type)AS

new_number_orders client.amount_of_orders%type;
new_value_orders client.total_orders_value%type;

BEGIN
    if(number_of_orders IS NOT NULL)THEN
        new_number_orders:=number_of_orders;
    end if;
    if(value_of_orders IS NOT NULL)THEN
        new_value_orders:=value_of_orders;
    end if;

    UPDATE client SET total_orders_value=new_value_orders,amount_of_orders=new_number_orders WHERE client.id_client=ClientID;

END;



CREATE OR REPLACE PROCEDURE us206createPlot(exploration IN exploration.id_exploration%type,
designation IN plot.designation%type,
area IN plot.area%type,
culture IN culture.id_culture%type) AS

l_id plot.id_plot%type;

BEGIN
    SELECT MAX(id_plot) into l_id FROM plot;
    if l_id<1 THEN
        l_id:=0;
    end if;
    INSERT INTO plot VALUES(l_id+1,exploration,designation,area,culture);
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Plot Created ID:'||(l_id+1));

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('It wasn´t possible to create plot with the specified data');
END;


CREATE OR REPLACE PROCEDURE us207ListExplorationProductionQuantity(result out SYS_REFCURSOR) IS
BEGIN
    OPEN result for  SELECT exploration.id_exploration, SUM(stored_culture.quantity) AS quantidade_producao FROM exploration
    INNER JOIN plot
        ON exploration.ID_EXPLORATION=plot.EXPLORATION_ID
    INNER JOIN CULTURE
        ON CULTURE.ID_CULTURE=plot.CULTURE_ID
    INNER JOIN STORED_CULTURE
        ON CULTURE.ID_CULTURE=STORED_CULTURE.CULTURE_ID
    GROUP BY exploration.id_exploration
    ORDER BY quantidade_producao DESC;
END;

CREATE OR REPLACE PROCEDURE us207ListExplorationProfit(result out SYS_REFCURSOR) IS
BEGIN
    OPEN result for SELECT exploration.id_exploration, SUM(client_order.total_amount) AS quantidade_lucro FROM exploration
                    INNER JOIN client
                        ON client.exploration_id=exploration.id_exploration
                    INNER JOIN client_order
                        ON client.id_client=client_order.client_id
                    GROUP BY exploration.id_exploration
                    ORDER BY quantidade_lucro DESC;
END;
 COMMIT;
    DBMS_OUTPUT.PUT_LINE('Element Created ID:'||(l_id+1));

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('It wasn´t possible to create client with the specified data');
END;

    CREATE OR REPLACE PROCEDURE us209RegisterOrder(delivery_date IN order_delivery.delivery_date%type,
                                               order_id IN client_order.id_order%type) 
AS
l_id order_delivery.id_order_delivery%type;
BEGIN
    SELECT MAX(id_order_delivery) into l_id FROM order_delivery;
    if l_id<1 THEN
        l_id:=0;
    end if;
    INSERT INTO order_delivery VALUES(l_id+1,delivery_date,order_id);
    UPDATE client_order SET status='Entregue' WHERE client_order.id_order=order_id;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Order Delivery Created ID:'(l_id+1));

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('It wasn´t possible to create client with the specified data');
END;

CREATE OR REPLACE PROCEDURE us209RegisterOrderPayment(payment_date IN order_payment.payment_date%type,
                                                      order_id IN client_order.id_order%type)
AS
l_id order_payment.id_order_payment%type;
BEGIN
    SELECT MAX(id_order_payment) into l_id FROM order_payment;
    if l_id<1 THEN
        l_id:=1;
    end if;
    INSERT INTO order_payment VALUES(l_id+1,payment_date,order_id);
    UPDATE client_order SET status='Paga' WHERE client_order.id_order=order_id;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Order Delivery Created ID:'(l_id+1));

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('It wasn´t possible to create client with the specified data');
END;

CREATE OR REPLACE PROCEDURE us209ListOrderPerStatus(c_status IN client_order.status%type,result out SYS_REFCURSOR)
AS
BEGIN
    OPEN result for SELECT id_order,client_name,delivery_address,status,order_date,total_amount
                        FROM client_order
                        INNER JOIN client 
                        ON client_order.client_id=client.id_client
                        WHERE status=c_status;  
END;

CREATE OR REPLACE PROCEDURE us209RegisterClientOrders(c_id IN client.id_client%type,
delivery_address IN client_order.delivery_address%type,
status IN client_order.status%type,
order_date IN client_order.order_date%type,
total_amount IN client_order.total_amount%type)
AS
l_id client_order.id_order%type;
address client.DEFAULT_DELIVERY_ADDRESS%type;
BEGIN
    SELECT MAX(id_order) into l_id FROM client_order;
    if l_id<1 THEN
        l_id:=1;
    end if;
    if(delivery_address is null) THEN
        SELECT client.DEFAULT_DELIVERY_ADDRESS into address FROM client WHERE c_id=client.ID_CLIENT;
        INSERT INTO client_order VALUES(l_id+1,c_id,address,status,order_date,total_amount);
    end if;
    INSERT INTO client_order VALUES(l_id+1,c_id,delivery_address,status,order_date,total_amount);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Order of Client Created ID:'||(l_id+1));

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('It wasn´t possible to create client with the specified data');
END;

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
