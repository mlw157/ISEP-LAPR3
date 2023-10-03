CREATE OR REPLACE PROCEDURE us209RegisterClientOrders(c_id IN client.id%type,
delivery_address IN basketorder.delivery_address%type,
status IN basketorder.status%type,
order_date IN basketorder.order_date%type,
total_amount IN basketorder.total_amount%type,
pickup_hub_id IN DISTRIBUTIONHUB.id%type,
delivery_date IN ORDERDELIVERY.DELIVERY_DATE%type,
payment_date IN ORDERPAYMENT.PAYMENT_DATE%type)
AS
address client.DEFAULT_DELIVERY_ADDRESS%type;
o_id BASKETORDER.id%type;
BEGIN
    if(delivery_address is null) THEN
        SELECT client.DEFAULT_DELIVERY_ADDRESS into address FROM client WHERE c_id=client.ID;
        INSERT INTO basketorder(client_id,DELIVERY_ADDRESS,status,order_date,total_amount,pickup_hub_id)
        VALUES(c_id,address,status,order_date,total_amount,pickup_hub_id);
        ELSE
            INSERT INTO basketorder(client_id,DELIVERY_ADDRESS,status,order_date,total_amount,pickup_hub_id) 
            VALUES(c_id,delivery_address,status,order_date,total_amount,pickup_hub_id);
    end if;
END;

CREATE OR REPLACE PROCEDURE us209RegisterOrderPayment(payment_date IN orderpayment.payment_date%type DEFAULT CURRENT_DATE,
                                                      order_id IN BASKETORDER.id%type)
AS
BEGIN
    INSERT INTO orderpayment(PAYMENT_DATE,BASKET_ORDER_ID) VALUES(payment_date,order_id);
    UPDATE BASKETORDER SET status='Paga' WHERE BASKETORDER.id=order_id;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('It wasn´t possible to create client with the specified data');
END;

CREATE OR REPLACE PROCEDURE us209ListOrderPerStatus(c_status IN basketorder.status%type,result out SYS_REFCURSOR)
AS
BEGIN
    OPEN result for SELECT client.id,name,delivery_address,status,order_date,total_amount
                        FROM basketorder
                        INNER JOIN client
                        ON basketorder.client_id=client.id
                        WHERE status=c_status;
END;