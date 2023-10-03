CREATE OR REPLACE procedure us208createProductionFactor(classification PRODUCTIONFACTOR.CLASSIFICATION%type,
comercial_name PRODUCTIONFACTOR.COMERCIAL_NAME%type,
formulation PRODUCTIONFACTOR.FORMULATION%TYPE,
type PRODUCTIONFACTOR.type%type,
provider PRODUCTIONFACTOR.PROVIDER%type,
element_designation ELEMENTTYPE.DESIGNATION%type,
element_name GENERICELEMENT.name%type,
quantity TECHNICALSHEETASSOCIATION.QUANTITY%type
)
AS
element_type_id ELEMENTTYPE.id%type;
prod_factor_id PRODUCTIONFACTOR.id%type;
gen_element_id GENERICELEMENT.id%type;
flag_etype INTEGER;
flag_genelement INTEGER;
BEGIN
    INSERT INTO ProductionFactor(classification,comercial_name,formulation,type,provider) VALUES (classification,comercial_name,formulation,type,provider)
    return id into prod_factor_id;
    select count(*) into flag_etype from ELEMENTTYPE WHERE DESIGNATION=DESIGNATION; 
    select count(*) into flag_genelement from GENERICELEMENT WHERE NAME=element_name;
    if flag_etype>0 THEN
        select id into element_type_id from ELEMENTTYPE where DESIGNATION=element_designation;
    else 
        element_type_id:=us208ElementType(element_designation);
        gen_element_id:=us208GenericElement(element_name,element_type_id);
        if flag_genelement>0 THEN   
            select id into gen_element_id from GENERICELEMENT where name=element_name;
            us208TechnicalSheetAssociation(prod_factor_id,gen_element_id,quantity);
        ELSE
            us208TechnicalSheetAssociation(prod_factor_id,gen_element_id,quantity);
        end if;
    end if;  
       
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('It was not possible to create production factor with the specified data');
END;

CREATE OR REPLACE procedure us208TechnicalSheetAssociation(prod_factor_id IN PRODUCTIONFACTOR.id%type,
gen_element_id IN GENERICELEMENT.id%type,
quantity IN TECHNICALSHEETASSOCIATION.QUANTITY%type)
AS
BEGIN
    INSERT INTO TECHNICALSHEETASSOCIATION(PRODUCTION_FACTOR_ID,GENERIC_ELEMNET_ID,QUANTITY) VALUES (prod_factor_id,gen_element_id,quantity);
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('It was not possible to create generic element with the specified data');

END;

CREATE OR REPLACE function us208ElementType(element_designation ELEMENTTYPE.DESIGNATION%type) return ELEMENTTYPE.ID%type
AS
l_id ELEMENTTYPE.id%type;
BEGIN
    INSERT INTO ELEMENTTYPE(DESIGNATION) VALUES (element_designation)
    return id into l_id;

    return l_id;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('It was not possible to create element type with the specified data');
END;

CREATE OR REPLACE function us208GenericElement(element_name IN GENERICELEMENT.NAME%type,element_type_id IN ELEMENTTYPE.ID%type) return GENERICELEMENT.ID%TYPE
AS
l_id GENERICELEMENT.id%type;
BEGIN
    INSERT INTO GENERICELEMENT(NAME,ELEMENT_TYPE_ID) VALUES (element_name,element_type_id)
    return id into l_id;
    return l_id;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('It was not possible to create generic element with the specified data');
END;