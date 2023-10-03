CREATE OR REPLACE TRIGGER us211TriggerProductionFactorApplicationRegisterOperation
BEFORE INSERT ON ProductionFactorApplication
    FOR EACH ROW
    WHEN ( new.operation_id IS NULL )
BEGIN
    INSERT INTO OPERATION(STATUS) VALUES ('PENDENTE') RETURNING ID INTO :new.operation_id;
end;

CREATE OR REPLACE TRIGGER us211TriggerIrrigationRegisterOperation
BEFORE INSERT ON Irrigation
    FOR EACH ROW
    WHEN ( new.operation_id IS NULL )
BEGIN
    INSERT INTO OPERATION(STATUS) VALUES ('PENDENTE') RETURNING ID INTO :new.operation_id;
end;

CREATE OR REPLACE TRIGGER us211TriggerUpdateProductionFactorsApplication
BEFORE UPDATE ON ProductionFactorApplication
    FOR EACH ROW
DECLARE
    stat OPERATION.STATUS%TYPE;
BEGIN
    SELECT STATUS into stat FROM OPERATION WHERE ID = :new.operation_id;
    if (stat <> 'PENDENTE') THEN
        RAISE_APPLICATION_ERROR(-20007, 'Nao é possivel alterar uma operacao pendente!');
    end if;
end;

CREATE OR REPLACE TRIGGER us211TriggerUpdateIrrigation
BEFORE UPDATE ON Irrigation
    FOR EACH ROW
DECLARE
    stat OPERATION.status%TYPE;
BEGIN
    SELECT STATUS into stat FROM OPERATION WHERE ID = :new.operation_id;
    if (stat <> 'PENDENTE') THEN
        RAISE_APPLICATION_ERROR(-20007, 'Nao é possivel alterar uma operacao pendente!');
    end if;
end;
