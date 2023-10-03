CREATE OR REPLACE VIEW us205Client AS
SELECT id, name, client_level, COALESCE(TO_CHAR(last_incident_date), 'Sem incidentes') as Incidentes,
    (SELECT COUNT() FROM BasketOrder Bo 
    WHERE c.id = Bo.client_id
      AND Bo.order_date > SYSDATE - 365
    AND Bo.status = 'Paga' ) AS Numero_de_encomendas_pagas,
       (SELECT COUNT() FROM BasketOrder Bo
        WHERE c.id = Bo.client_id 
          AND Bo.status = 'Registada' ) AS Numero_de_encomendas_pendentes_de_pagamento
FROM CLIENT c;

