
-- exploration
INSERT INTO exploration(area) VALUES(50);
INSERT INTO exploration(area) VALUES(150);
INSERT INTO exploration(area) VALUES(500);
INSERT INTO exploration(area) VALUES(250);
INSERT INTO exploration(area) VALUES(350);
COMMIT;

--building
INSERT INTO building(exploration_id,type) VALUES(1,'stables');
INSERT INTO building(exploration_id,type) VALUES(1,'warehouse');
INSERT INTO building(exploration_id,type) VALUES(1,'garage');
INSERT INTO building(exploration_id,type) VALUES(2,'garage');
INSERT INTO building(exploration_id,type) VALUES(2,'garage');
INSERT INTO building(exploration_id,type) VALUES(2,'garage');
INSERT INTO building(exploration_id,type) VALUES(3,'garage');
INSERT INTO building(exploration_id,type) VALUES(4,'garage');
INSERT INTO building(exploration_id,type) VALUES(5,'garage');
COMMIT;

--CultureTypes
INSERT INTO CultureTypes(last_modification_date) VALUES(TO_DATE('19/12/2021', 'DD/MM/YYYY'));
INSERT INTO CultureTypes(last_modification_date) VALUES(TO_DATE('14/08/2022', 'DD/MM/YYYY'));
INSERT INTO CultureTypes(last_modification_date) VALUES(TO_DATE('25/05/2019', 'DD/MM/YYYY'));
INSERT INTO CultureTypes(last_modification_date) VALUES(TO_DATE('05/02/2018', 'DD/MM/YYYY'));
INSERT INTO CultureTypes(last_modification_date) VALUES(TO_DATE('30/01/2022', 'DD/MM/YYYY'));
COMMIT;

--Type
INSERT INTO Type(type_designation,culture_types_id) VALUES('tipo1',1);
INSERT INTO Type(type_designation,culture_types_id) VALUES('tipo2',1);
INSERT INTO Type(type_designation,culture_types_id) VALUES('tipo1',2);
INSERT INTO Type(type_designation,culture_types_id) VALUES('tipo2',2);


--culture
INSERT INTO culture(plant_species,type_id) VALUES('flores',1);
INSERT INTO culture(plant_species,type_id) VALUES('frutos',2);
INSERT INTO culture(plant_species,type_id) VALUES('cereais',3);
INSERT INTO culture(plant_species,type_id) VALUES('hortÃ­colas',3);
INSERT INTO culture(plant_species,type_id) VALUES('forragens',1);
COMMIT;

--plot
INSERT INTO plot(exploration_id,designation,area) VALUES(1,'sector',200);
INSERT INTO plot(exploration_id,designation,area) VALUES(1,'campo',1000);
INSERT INTO plot(exploration_id,designation,area) VALUES(2,'campo',750);
INSERT INTO plot(exploration_id,designation,area) VALUES(2,'sector',600);
INSERT INTO plot(exploration_id,designation,area) VALUES(3,'sector',500);
COMMIT;

--animal ration
INSERT INTO AnimalRation(animal) VALUES('Galinha');
INSERT INTO AnimalRation(animal) VALUES('Porco');
INSERT INTO AnimalRation(animal) VALUES('Vaca');
INSERT INTO AnimalRation(animal) VALUES('Ovelha');
INSERT INTO AnimalRation(animal) VALUES('Coelho');
COMMIT;

--stored culture
INSERT INTO StoredCulture(quantity,building_id,culture_id) VALUES(1000,1,1);
INSERT INTO StoredCulture(quantity,building_id,culture_id) VALUES(1500,1,3);
INSERT INTO StoredCulture(quantity,building_id,culture_id) VALUES(3000,2,2);
INSERT INTO StoredCulture(quantity,building_id,culture_id) VALUES(500,3,5);
INSERT INTO StoredCulture(quantity,building_id,culture_id) VALUES(800,3,4);
COMMIT;

--stored animal ration
INSERT INTO StoredAnimalRation(quantity,building_id,animal_ration_id) VALUES(200,1,1);
INSERT INTO StoredAnimalRation(quantity,building_id,animal_ration_id) VALUES(1500,2,2);
INSERT INTO StoredAnimalRation(quantity,building_id,animal_ration_id) VALUES(2000,2,3);
INSERT INTO StoredAnimalRation(quantity,building_id,animal_ration_id) VALUES(1000,3,4);
INSERT INTO StoredAnimalRation(quantity,building_id,animal_ration_id) VALUES(150,1,5);
COMMIT;

--production factor
INSERT INTO ProductionFactor(classification,comercial_name,formulation,type,provider) VALUES('Fertilizante','Fertilizante-A','PÃ³','Marca A', 'ADP-Fertilizantes');
INSERT INTO ProductionFactor(classification,comercial_name,formulation,type,provider) VALUES('Fertilizante','Fertilizante-B','PÃ³','Marca B', 'ADP-Fertilizantes');
INSERT INTO ProductionFactor(classification,comercial_name,formulation,type,provider) VALUES('Adubo','Adubo-A','Liquido','Marca A', 'ADP-Fertilizantes');
INSERT INTO ProductionFactor(classification,comercial_name,formulation,type,provider) VALUES('Correctivo','Correctivo-A','Granulado','Marca A', 'ADP-Fertilizantes');
INSERT INTO ProductionFactor(classification,comercial_name,formulation,type,provider) VALUES('Produto FitofÃ¡rmaco','Produto FitofÃ¡rmaco-C','PÃ³','Marca C', 'ADP-Fertilizantes');
COMMIT;

--stored production factor
INSERT INTO StoredProductionFactor(quantity,building_id,production_factor_id) VALUES(50,1,1);
INSERT INTO StoredProductionFactor(quantity,building_id,production_factor_id) VALUES(150,1,2);
INSERT INTO StoredProductionFactor(quantity,building_id,production_factor_id) VALUES(500,2,3);
INSERT INTO StoredProductionFactor(quantity,building_id,production_factor_id) VALUES(25,3,4);
INSERT INTO StoredProductionFactor(quantity,building_id,production_factor_id) VALUES(100,1,5);
COMMIT;

--irrigation fertilization system
INSERT INTO IrrigationFertilizationSystem(exploration_id) VALUES(1);
INSERT INTO IrrigationFertilizationSystem(exploration_id) VALUES(2);
INSERT INTO IrrigationFertilizationSystem(exploration_id) VALUES(3);
INSERT INTO IrrigationFertilizationSystem(exploration_id) VALUES(4);
INSERT INTO IrrigationFertilizationSystem(exploration_id) VALUES(5);
COMMIT;

--metereologic station
INSERT INTO MetereologicStation(exploration_id) VALUES(1);
INSERT INTO MetereologicStation(exploration_id) VALUES(2);
INSERT INTO MetereologicStation(exploration_id) VALUES(3);
INSERT INTO MetereologicStation(exploration_id) VALUES(4);
INSERT INTO MetereologicStation(exploration_id) VALUES(5);
COMMIT;

--metereologic sensor
INSERT INTO MetereologicSensor(metereologic_station_id,data_type) VALUES(1,'km/h');
INSERT INTO MetereologicSensor(metereologic_station_id,data_type) VALUES(1,'km/h');
INSERT INTO MetereologicSensor(metereologic_station_id,data_type) VALUES(1,'ºC');
INSERT INTO MetereologicSensor(metereologic_station_id,data_type) VALUES(1,'ºC');
INSERT INTO MetereologicSensor(metereologic_station_id,data_type) VALUES(1,'mm');
INSERT INTO MetereologicSensor(metereologic_station_id,data_type) VALUES(1,'mm');
INSERT INTO MetereologicSensor(metereologic_station_id,data_type) VALUES(1,'%');
INSERT INTO MetereologicSensor(metereologic_station_id,data_type) VALUES(1,'pa');
COMMIT;

--thermical sensor
INSERT INTO ThermicalSensor(metereologic_sensor_id) VALUES(1);
INSERT INTO ThermicalSensor(metereologic_sensor_id) VALUES(2);
INSERT INTO ThermicalSensor(metereologic_sensor_id) VALUES(3);
INSERT INTO ThermicalSensor(metereologic_sensor_id) VALUES(4);
INSERT INTO ThermicalSensor(metereologic_sensor_id) VALUES(5);
COMMIT;

--rainfall sensor
INSERT INTO RainfallSensor(metereologic_sensor_id) VALUES(1);
INSERT INTO RainfallSensor(metereologic_sensor_id) VALUES(2);
INSERT INTO RainfallSensor(metereologic_sensor_id) VALUES(3);
INSERT INTO RainfallSensor(metereologic_sensor_id) VALUES(4);
INSERT INTO RainfallSensor(metereologic_sensor_id) VALUES(5);
COMMIT;

--wind sensor
INSERT INTO WindSensor(metereologic_sensor_id) VALUES(1);
INSERT INTO WindSensor(metereologic_sensor_id) VALUES(2);
INSERT INTO WindSensor(metereologic_sensor_id) VALUES(3);
INSERT INTO WindSensor(metereologic_sensor_id) VALUES(4);
INSERT INTO WindSensor(metereologic_sensor_id) VALUES(5);
COMMIT;

--humidity sensor
INSERT INTO HumiditySensor(metereologic_sensor_id) VALUES(1);
INSERT INTO HumiditySensor(metereologic_sensor_id) VALUES(2);
INSERT INTO HumiditySensor(metereologic_sensor_id) VALUES(3);
INSERT INTO HumiditySensor(metereologic_sensor_id) VALUES(4);
INSERT INTO HumiditySensor(metereologic_sensor_id) VALUES(5);
COMMIT;

--atm pressure sensor
INSERT INTO AtmPressureSensor(metereologic_sensor_id) VALUES(1);
INSERT INTO AtmPressureSensor(metereologic_sensor_id) VALUES(2);
INSERT INTO AtmPressureSensor(metereologic_sensor_id) VALUES(3);
INSERT INTO AtmPressureSensor(metereologic_sensor_id) VALUES(4);
INSERT INTO AtmPressureSensor(metereologic_sensor_id) VALUES(5);
COMMIT;


--soil sensor
INSERT INTO SoilSensor(plot_id,data_type) VALUES(1,'%');
INSERT INTO SoilSensor(plot_id,data_type) VALUES(1,'%');
INSERT INTO SoilSensor(plot_id,data_type) VALUES(2,'ÂºC');
INSERT INTO SoilSensor(plot_id,data_type) VALUES(2,'ÂºC');
INSERT INTO SoilSensor(plot_id,data_type) VALUES(3,'ÂºC');
COMMIT;

--soil thermical sensor
INSERT INTO SoilThermicalSensor(soil_sensor_id) VALUES(1);
INSERT INTO SoilThermicalSensor(soil_sensor_id) VALUES(2);
INSERT INTO SoilThermicalSensor(soil_sensor_id) VALUES(3);
INSERT INTO SoilThermicalSensor(soil_sensor_id) VALUES(4);
INSERT INTO SoilThermicalSensor(soil_sensor_id) VALUES(5);
COMMIT;

--soil humidity sensor
INSERT INTO SoilHumiditySesnor(soil_sensor_id) VALUES(1);
INSERT INTO SoilHumiditySesnor(soil_sensor_id) VALUES(2);
INSERT INTO SoilHumiditySesnor(soil_sensor_id) VALUES(3);
INSERT INTO SoilHumiditySesnor(soil_sensor_id) VALUES(4);
INSERT INTO SoilHumiditySesnor(soil_sensor_id) VALUES(5);
COMMIT;

--sensor data
INSERT INTO SensorData(sensor_id,record_date,value) VALUES (1,TO_DATE('10/08/2022', 'DD/MM/YYYY'),20);
INSERT INTO SensorData(sensor_id,record_date,value) VALUES (1,TO_DATE('11/08/2022', 'DD/MM/YYYY'),30);
INSERT INTO SensorData(sensor_id,record_date,value) VALUES (3,TO_DATE('12/08/2022', 'DD/MM/YYYY'),40);
INSERT INTO SensorData(sensor_id,record_date,value) VALUES (4,TO_DATE('13/08/2022', 'DD/MM/YYYY'),50);
INSERT INTO SensorData(sensor_id,record_date,value) VALUES (5,TO_DATE('14/08/2022', 'DD/MM/YYYY'),60);
COMMIT;

--planted culture
INSERT INTO PlantedCulture(plot_id,culture_id,begin_date, end_date) VALUES (1,1,TO_DATE('11/09/2022', 'DD/MM/YYYY'),TO_DATE('12/10/2022', 'DD/MM/YYYY'));
INSERT INTO PlantedCulture(plot_id,culture_id,begin_date, end_date) VALUES (1,3,TO_DATE('12/09/2022', 'DD/MM/YYYY'),TO_DATE('13/10/2022', 'DD/MM/YYYY'));
INSERT INTO PlantedCulture(plot_id,culture_id,begin_date, end_date) VALUES (1,2,TO_DATE('13/09/2022', 'DD/MM/YYYY'),TO_DATE('14/10/2022', 'DD/MM/YYYY'));
INSERT INTO PlantedCulture(plot_id,culture_id,begin_date, end_date) VALUES (2,2,TO_DATE('14/09/2022', 'DD/MM/YYYY'),TO_DATE('15/10/2022', 'DD/MM/YYYY'));
INSERT INTO PlantedCulture(plot_id,culture_id,begin_date, end_date) VALUES (2,1,TO_DATE('15/09/2022', 'DD/MM/YYYY'),TO_DATE('16/10/2022', 'DD/MM/YYYY'));
COMMIT;

--field book
INSERT INTO FieldBook(exploration_id,creation_date, end_date, writer) VALUES (1,TO_DATE('10/08/2022', 'DD/MM/YYYY'), TO_DATE('11/08/2022', 'DD/MM/YYYY'), 'Caneta');
INSERT INTO FieldBook(exploration_id,creation_date, end_date, writer) VALUES (2,TO_DATE('11/08/2022', 'DD/MM/YYYY'), TO_DATE('12/08/2022', 'DD/MM/YYYY'), 'Caneta');
INSERT INTO FieldBook(exploration_id,creation_date, end_date, writer) VALUES (2,TO_DATE('12/08/2022', 'DD/MM/YYYY'), TO_DATE('13/08/2022', 'DD/MM/YYYY'), 'Caneta');
INSERT INTO FieldBook(exploration_id,creation_date, end_date, writer) VALUES (3,TO_DATE('13/08/2022', 'DD/MM/YYYY'), TO_DATE('14/08/2022', 'DD/MM/YYYY'), 'Caneta');
COMMIT;

--product
INSERT INTO Product(price,name,type,culture_id) VALUES (10,'ananas',1,2);
INSERT INTO Product(price,name,type,culture_id) VALUES (2,'pao',2,1);
INSERT INTO Product(price,name,type,culture_id) VALUES (100,'morango',2,3);
INSERT INTO Product(price,name,type,culture_id) VALUES (5,'milho',1,4);
COMMIT;

--harvest
INSERT INTO Harvest(plot_id,product_id,field_book_id,harvest_date, quantity) VALUES (2,1,1,TO_DATE('10/08/2022', 'DD/MM/YYYY'),20);
INSERT INTO Harvest(plot_id,product_id,field_book_id,harvest_date, quantity) VALUES (1,2,2,TO_DATE('11/08/2022', 'DD/MM/YYYY'),30);
INSERT INTO Harvest(plot_id,product_id,field_book_id,harvest_date, quantity) VALUES (1,1,1,TO_DATE('12/08/2022', 'DD/MM/YYYY'),40);
INSERT INTO Harvest(plot_id,product_id,field_book_id,harvest_date, quantity) VALUES (3,2,2,TO_DATE('13/08/2022', 'DD/MM/YYYY'),50);
INSERT INTO Harvest(plot_id,product_id,field_book_id,harvest_date, quantity) VALUES (1,2,3,TO_DATE('14/08/2022', 'DD/MM/YYYY'),60);
COMMIT;

--DistributionHub
INSERT INTO DistributionHub(address,hub_code) VALUES ('Rua da rua', 'CT1');
INSERT INTO DistributionHub(address,hub_code) VALUES ('Rua da rua2', 'CT5');
INSERT INTO DistributionHub(address,hub_code) VALUES ('Rua da rua3', 'CT8');
INSERT INTO DistributionHub(address,hub_code) VALUES ('Rua da rua4', 'CT12');
COMMIT;

--Input_hub
INSERT INTO Input_hub(loc_id,lon,lat,clients_productors) VALUES ('CT1',-54.5548,62.2215,'E1');
INSERT INTO Input_hub(loc_id,lon,lat,clients_productors) VALUES ('CT5',-1.5120,-51.3332,'P1');
INSERT INTO Input_hub(loc_id,lon,lat,clients_productors) VALUES ('CT8',89.3331,-60.0021,'P2');
INSERT INTO Input_hub(loc_id,lon,lat,clients_productors) VALUES ('CT12',-53.9986,15.2222,'P3');
COMMIT;

--ClientLevels
INSERT INTO ClientLevels(last_modification_date) VALUES(TO_DATE('19/12/2021', 'DD/MM/YYYY'));
INSERT INTO ClientLevels(last_modification_date) VALUES(TO_DATE('14/08/2022', 'DD/MM/YYYY'));
INSERT INTO ClientLevels(last_modification_date) VALUES(TO_DATE('25/05/2019', 'DD/MM/YYYY'));
INSERT INTO ClientLevels(last_modification_date) VALUES(TO_DATE('05/02/2018', 'DD/MM/YYYY'));
INSERT INTO ClientLevels(last_modification_date) VALUES(TO_DATE('30/01/2022', 'DD/MM/YYYY'));
COMMIT;

--ClientLevel
INSERT INTO ClientLevel(client_level_id, designation) VALUES(1, 'A');
INSERT INTO ClientLevel(client_level_id, designation) VALUES(2, 'B');
INSERT INTO ClientLevel(client_level_id, designation) VALUES(3, 'C');
COMMIT;

--client
INSERT INTO Client(exploration_id,name, fiscal_number, email, plafond, client_level, incident_number, last_incident_date, amount_of_orders_last_year, total_orders_value_last_year, level_id, correspondence_address, default_delivery_address, postal_code,default_hub_id) VALUES (1,'Diogo',123456789,'diogo@mail.com',1000,'A',0,NULL,4,4000, 1, 'Rua A','Rua A','4500-210',4);
INSERT INTO Client(exploration_id,name, fiscal_number, email, plafond, client_level, incident_number, last_incident_date, amount_of_orders_last_year, total_orders_value_last_year, level_id, correspondence_address, default_delivery_address, postal_code,default_hub_id) VALUES (2,'Paulo',987654321,'Paulo@gmail.com',5,'C',1,TO_DATE('14/08/2022', 'DD/MM/YYYY'),3,3000,3,'Rua brazil','Rua loureiro','3880-800',1);
INSERT INTO Client(exploration_id,name, fiscal_number, email, plafond, client_level, incident_number, last_incident_date, amount_of_orders_last_year, total_orders_value_last_year, level_id, correspondence_address, default_delivery_address, postal_code,default_hub_id) VALUES (3,'Pedro',123547698,'Pedro@gmail.com',0,'C',1,TO_DATE('14/04/2020', 'DD/MM/YYYY'),0,0,3,'Rua lizil','Rua oureiro','1380-820',2);
INSERT INTO Client(exploration_id,name, fiscal_number, email, plafond, client_level, incident_number, last_incident_date, amount_of_orders_last_year, total_orders_value_last_year, level_id, correspondence_address, default_delivery_address, postal_code,default_hub_id) VALUES (4,'Joao',213243546,'Joao@gmail.com',10,'B',3,TO_DATE('20/09/2019', 'DD/MM/YYYY'),1,25,2,'Rua il','Rua leitao','3999-230',2);
INSERT INTO Client(exploration_id,name, fiscal_number, email, plafond, client_level, incident_number, last_incident_date, amount_of_orders_last_year, total_orders_value_last_year, level_id, correspondence_address, default_delivery_address, postal_code,default_hub_id) VALUES (5,'Manuel',309876543,'Manuel@gmail.com',68,'A',0,NULL,3,3000,1,'Rua brazil','Rua loureiro','3880-800',3);
COMMIT;

--client incident
INSERT INTO ClientIncident(client_id,value, date_incident, status, remedied_date) VALUES (1,20,TO_DATE('14/08/2022', 'DD/MM/YYYY'), 'Pendente', TO_DATE('15/08/2022', 'DD/MM/YYYY'));
INSERT INTO ClientIncident(client_id,value, date_incident, status, remedied_date) VALUES (2,40,TO_DATE('20/08/2021', 'DD/MM/YYYY'), 'Paga', TO_DATE('25/08/2021', 'DD/MM/YYYY'));
INSERT INTO ClientIncident(client_id,value, date_incident, status, remedied_date) VALUES (3,60,TO_DATE('01/01/2018', 'DD/MM/YYYY'), 'Pendente', TO_DATE('20/01/2018', 'DD/MM/YYYY'));
COMMIT;

--SystemUser
INSERT INTO SYSTEMUSER(email, password, exploration_id) VALUES ('1211511@isep.ipp.pt', '1234', 1);
INSERT INTO SYSTEMUSER(email, password, exploration_id) VALUES ('1211512@isep.ipp.pt', '1234', 1);
INSERT INTO SYSTEMUSER(email, password, exploration_id) VALUES ('1211513@isep.ipp.pt', '1234', 1);
COMMIT;

--AuditLog
INSERT INTO AuditLog(operation_date, type,plot_id,system_user_id) VALUES (TO_DATE('20/01/2018', 'DD/MM/YYYY'), 'type',1,1);
INSERT INTO AuditLog(operation_date, type,plot_id,system_user_id) VALUES (TO_DATE('22/01/2018', 'DD/MM/YYYY'), 'type',3,1);
INSERT INTO AuditLog(operation_date, type,plot_id,system_user_id) VALUES (TO_DATE('24/01/2018', 'DD/MM/YYYY'), 'type',2,2);
COMMIT;

--Operation
INSERT INTO Operation(status) VALUES ('PENDENTE');
INSERT INTO Operation(status) VALUES ('COMPLETO');
INSERT INTO Operation(status) VALUES ('CANCELADO');
INSERT INTO Operation(status) VALUES ('PENDENTE');
COMMIT;

--Plot Restrictions
INSERT INTO PLOTRESTRICTIONS(plot_id, production_factor_id, restriction_begining_date, restriction_end_date) VALUES(1, 1, TO_DATE('24/05/2019', 'DD/MM/YYYY'), TO_DATE('30/05/2020', 'DD/MM/YYYY'));
INSERT INTO PLOTRESTRICTIONS(plot_id, production_factor_id, restriction_begining_date, restriction_end_date) VALUES(1, 2, TO_DATE('10/04/2019', 'DD/MM/YYYY'), TO_DATE('10/05/2019', 'DD/MM/YYYY'));
INSERT INTO PLOTRESTRICTIONS(plot_id, production_factor_id, restriction_begining_date, restriction_end_date) VALUES(2, 2, TO_DATE('30/05/2019', 'DD/MM/YYYY'), TO_DATE('30/05/2021', 'DD/MM/YYYY'));
INSERT INTO PLOTRESTRICTIONS(plot_id, production_factor_id, restriction_begining_date, restriction_end_date) VALUES(2, 3, TO_DATE('29/05/2018', 'DD/MM/YYYY'), TO_DATE('30/05/2020', 'DD/MM/YYYY'));
COMMIT;

--ElementType
INSERT INTO ElementType(designation) VALUES ('Fertilizante');
INSERT INTO ElementType(designation) VALUES ('Agua');
INSERT INTO ElementType(designation) VALUES ('Óleo');
INSERT INTO ElementType(designation) VALUES ('Oxigénio');
INSERT INTO ElementType(designation) VALUES ('Herbicida');
COMMIT;

--GenericElement
INSERT INTO GenericElement(name, element_type_id) VALUES('Fertilizante', 1);
INSERT INTO GenericElement(name, element_type_id) VALUES('Agua', 2);
INSERT INTO GenericElement(name, element_type_id) VALUES('Óleo', 3);
INSERT INTO GenericElement(name, element_type_id) VALUES('Oxigénio', 4);
INSERT INTO GenericElement(name, element_type_id) VALUES('Herbicida', 5);
COMMIT;

--TechnicalSheetAssociation
INSERT INTO TechnicalSheetAssociation(production_factor_id, generic_elemnet_id, quantity) VALUES(1, 1, 10);
INSERT INTO TechnicalSheetAssociation(production_factor_id, generic_elemnet_id, quantity) VALUES(2, 1, 30);
INSERT INTO TechnicalSheetAssociation(production_factor_id, generic_elemnet_id, quantity) VALUES(3, 2, 40);
INSERT INTO TechnicalSheetAssociation(production_factor_id, generic_elemnet_id, quantity) VALUES(4, 3, 100);
INSERT INTO TechnicalSheetAssociation(production_factor_id, generic_elemnet_id, quantity) VALUES(5, 1, 20);
COMMIT;

--GenericElement
INSERT INTO GenericElement(name, element_type_id) VALUES('Fertilizante', 1);
INSERT INTO GenericElement(name, element_type_id) VALUES('Agua', 2);
INSERT INTO GenericElement(name, element_type_id) VALUES('Óleo', 3);
INSERT INTO GenericElement(name, element_type_id) VALUES('Oxigénio', 4);
INSERT INTO GenericElement(name, element_type_id) VALUES('Herbicida', 5);
COMMIT;

--ElementType
INSERT INTO ElementType(designation) VALUES('Fertilizante');
INSERT INTO ElementType(designation) VALUES('Agua');
INSERT INTO ElementType(designation) VALUES('Óleo');
INSERT INTO ElementType(designation) VALUES('Oxigénio');
INSERT INTO ElementType(designation) VALUES('Herbicida');
COMMIT;


--IrrigationFertilizationCalendar
INSERT INTO IrrigationFertilizationCalendar(irrigation_system_id) VALUES (1);
INSERT INTO IrrigationFertilizationCalendar(irrigation_system_id) VALUES (2);
INSERT INTO IrrigationFertilizationCalendar(irrigation_system_id) VALUES (2);
INSERT INTO IrrigationFertilizationCalendar(irrigation_system_id) VALUES (3);
COMMIT;

--DayPlan
INSERT INTO DayPlan(irrigation_calendar_id,day_plan_date) VALUES (1,TO_DATE('12/02/2022', 'DD/MM/YYYY'));
INSERT INTO DayPlan(irrigation_calendar_id,day_plan_date) VALUES (2,TO_DATE('02/01/2022', 'DD/MM/YYYY'));
INSERT INTO DayPlan(irrigation_calendar_id,day_plan_date) VALUES (2,TO_DATE('21/05/2022', 'DD/MM/YYYY'));
INSERT INTO DayPlan(irrigation_calendar_id,day_plan_date) VALUES (3,TO_DATE('15/11/2021', 'DD/MM/YYYY'));
COMMIT;

--Hour
INSERT INTO Hour(day_plan_id,hour,minute) VALUES (1,20,12);
INSERT INTO Hour(day_plan_id,hour,minute) VALUES (2,10,52);
INSERT INTO Hour(day_plan_id,hour,minute) VALUES (3,12,00);
INSERT INTO Hour(day_plan_id,hour,minute) VALUES (1,21,20);
COMMIT;

--PlotPlan
INSERT INTO PlotPlan(hour_id,plot_id) VALUES (1,1);
INSERT INTO PlotPlan(hour_id,plot_id) VALUES (2,2);
INSERT INTO PlotPlan(hour_id,plot_id) VALUES (3,1);
INSERT INTO PlotPlan(hour_id,plot_id) VALUES (4,1);
INSERT INTO PlotPlan(hour_id,plot_id) VALUES (3,3);
COMMIT;

--Irrigation 
INSERT INTO Irrigation(plot_plan_id,irrigation_minutes,type,field_book_id,operation_id) VALUES (1,20,1,1,1); 
INSERT INTO Irrigation(plot_plan_id,irrigation_minutes,type,field_book_id,operation_id) VALUES (2,10,1,3,2); 
INSERT INTO Irrigation(plot_plan_id,irrigation_minutes,type,field_book_id,operation_id) VALUES (3,15,2,2,1); 
COMMIT;

--ProductionFactorApplication
INSERT INTO ProductionFactorApplication(plot_plan_id,production_factor_id,type,field_book_id,quantity_applied,operation_id) VALUES(1,1,1,2,10,1);
INSERT INTO ProductionFactorApplication(plot_plan_id,production_factor_id,type,field_book_id,quantity_applied,operation_id) VALUES(2,3,2,1,20,2);
INSERT INTO ProductionFactorApplication(plot_plan_id,production_factor_id,type,field_book_id,quantity_applied,operation_id) VALUES(3,2,1,3,5,1);
INSERT INTO ProductionFactorApplication(plot_plan_id,production_factor_id,type,field_book_id,quantity_applied,operation_id) VALUES(4,3,2,1,10,3);
COMMIT;

--Sensor
INSERT INTO SENSOR(metereologic_sensor_id) VALUES(1);
INSERT INTO SENSOR(metereologic_sensor_id) VALUES(2);
INSERT INTO SENSOR(metereologic_sensor_id) VALUES(3);
INSERT INTO SENSOR(metereologic_sensor_id) VALUES(4);
INSERT INTO SENSOR(metereologic_sensor_id) VALUES(5);
COMMIT;

--BasketOrder
INSERT INTO BasketOrder(client_id, delivery_address, status, order_date, total_amount, pickup_hub_id) VALUES(1, 'Rua Dão Afonso 301', 'Paga',  TO_DATE('19/12/2021', 'DD/MM/YYYY'), 10, 1);
INSERT INTO BasketOrder(client_id, delivery_address, status, order_date, total_amount, pickup_hub_id) VALUES(2, 'Rua Família Fonseca 101', 'Registada',  TO_DATE('14/08/2022', 'DD/MM/YYYY'), 20, 2);
INSERT INTO BasketOrder(client_id, delivery_address, status, order_date, total_amount, pickup_hub_id) VALUES(3, 'Rua da Alameda 205', 'Entregue',  TO_DATE('25/05/2019', 'DD/MM/YYYY'), 30, 3);
INSERT INTO BasketOrder(client_id, delivery_address, status, order_date, total_amount, pickup_hub_id) VALUES(4, 'Rua da Circunvalação 90', 'Paga',  TO_DATE('05/02/2018', 'DD/MM/YYYY'), 40, 4);
INSERT INTO BasketOrder(client_id, delivery_address, status, order_date, total_amount, pickup_hub_id) VALUES(2, 'Rua das Chinas 31', 'Entregue',  TO_DATE('30/01/2022', 'DD/MM/YYYY'), 50, 1);
COMMIT;	

--BasketProduct
INSERT INTO BasketProduct(basket_order_id, product_id, quantity) VALUES(1, 1, 1);
INSERT INTO BasketProduct(basket_order_id, product_id, quantity) VALUES(2, 2, 2);
INSERT INTO BasketProduct(basket_order_id, product_id, quantity) VALUES(3, 3, 3);
INSERT INTO BasketProduct(basket_order_id, product_id, quantity) VALUES(4, 4, 4);
INSERT INTO BasketProduct(basket_order_id, product_id, quantity) VALUES(5, 3, 5);
COMMIT;

--OrderDelivery
INSERT INTO OrderDelivery(delivery_date, basket_order_id) VALUES(TO_DATE('19/12/2021', 'DD/MM/YYYY'), 1);
INSERT INTO OrderDelivery(delivery_date, basket_order_id) VALUES(TO_DATE('15/01/2022', 'DD/MM/YYYY'), 2);
INSERT INTO OrderDelivery(delivery_date, basket_order_id) VALUES(TO_DATE('23/02/2019', 'DD/MM/YYYY'), 3);
INSERT INTO OrderDelivery(delivery_date, basket_order_id) VALUES(TO_DATE('01/09/2018', 'DD/MM/YYYY'), 4);
INSERT INTO OrderDelivery(delivery_date, basket_order_id) VALUES(TO_DATE('30/07/2022', 'DD/MM/YYYY'), 5);
COMMIT;

--OrderPayment
INSERT INTO OrderPayment(payment_date, basket_order_id) VALUES(TO_DATE('13/02/2021', 'DD/MM/YYYY'), 1);
INSERT INTO OrderPayment(payment_date, basket_order_id) VALUES(TO_DATE('24/11/2022', 'DD/MM/YYYY'), 2);
INSERT INTO OrderPayment(payment_date, basket_order_id) VALUES(TO_DATE('15/06/2019', 'DD/MM/YYYY'), 3);
INSERT INTO OrderPayment(payment_date, basket_order_id) VALUES(TO_DATE('25/10/2018', 'DD/MM/YYYY'), 4);
INSERT INTO OrderPayment(payment_date, basket_order_id) VALUES(TO_DATE('12/04/2022', 'DD/MM/YYYY'), 5);
COMMIT;