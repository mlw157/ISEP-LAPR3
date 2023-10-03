CREATE TABLE SystemUser (
  id            int(10) NOT NULL AUTO_INCREMENT, 
  email         varchar(255) NOT NULL UNIQUE, 
  password      int(10) NOT NULL, 
  role_function varchar(255) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Building (
  id             int(10) NOT NULL AUTO_INCREMENT, 
  exploration_id int(10) NOT NULL, 
  type           varchar(255) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Exploration (
  id      int(10) NOT NULL AUTO_INCREMENT, 
  address varchar(255) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Plot (
  id             int(10) NOT NULL AUTO_INCREMENT, 
  exploration_id int(10) NOT NULL, 
  designation    varchar(255) NOT NULL, 
  area           int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Culture (
  id            int(10) NOT NULL AUTO_INCREMENT, 
  plant_species varchar(255) NOT NULL, 
  type          varchar(255) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE StoredProductionFactor (
  id                   int(10) NOT NULL AUTO_INCREMENT, 
  quantity             int(10) NOT NULL, 
  production_factor_id int(10) NOT NULL, 
  building_id          int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE ProductionFactor (
  id                 int(10) NOT NULL AUTO_INCREMENT, 
  classification     varchar(255) NOT NULL, 
  comercial_name     varchar(255) NOT NULL, 
  formulation        varchar(255) NOT NULL, 
  technical_sheet_id int(10) NOT NULL, 
  type               varchar(255) NOT NULL, 
  provider           varchar(255) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE TechnicalSheet (
  id int(10) NOT NULL AUTO_INCREMENT, 
  PRIMARY KEY (id));
CREATE TABLE Element (
  id                 int(10) NOT NULL AUTO_INCREMENT, 
  name               varchar(255) NOT NULL, 
  quantity           int(10) NOT NULL, 
  technical_sheet_id int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Substance (
  id                 int(10) NOT NULL AUTO_INCREMENT, 
  name               varchar(255) NOT NULL, 
  quantity           int(10) NOT NULL, 
  techincal_sheet_id int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE AnimalRation (
  id     int(10) NOT NULL AUTO_INCREMENT, 
  animal varchar(255) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE StoredAnimalRation (
  id               int(10) NOT NULL AUTO_INCREMENT, 
  quantity         int(10) NOT NULL, 
  animal_ration_id int(10) NOT NULL, 
  building_id      int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE StoredCulture (
  id          int(10) NOT NULL AUTO_INCREMENT, 
  quantity    int(10) NOT NULL, 
  culture_id  int(10) NOT NULL, 
  building_id int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE IrrigationFertilizationSystem (
  id             int(10) NOT NULL AUTO_INCREMENT, 
  exploration_id int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE MetereologicStation (
  id             int(10) NOT NULL AUTO_INCREMENT, 
  exploration_id int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE ThermicalSensor (
  id                     int(10) NOT NULL AUTO_INCREMENT, 
  metereologic_sensor_id int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Client (
  id                           int(10) NOT NULL AUTO_INCREMENT, 
  exploration_id               int(10) NOT NULL, 
  name                         varchar(255) NOT NULL, 
  fiscal_number                int(10) NOT NULL, 
  email                        varchar(255) NOT NULL, 
  plafond                      int(10) NOT NULL, 
  incident_number              int(10) NOT NULL, 
  last_incident_date           date NOT NULL, 
  amount_of_orders_last_year   int(10) NOT NULL, 
  total_orders_value_last_year int(10) NOT NULL, 
  level                        int(10) NOT NULL, 
  correspondence_address       varchar(255) NOT NULL, 
  default_delivery_address     varchar(255) NOT NULL, 
  postal_code                  varchar(255) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE ClientIncident (
  id            int(10) NOT NULL AUTO_INCREMENT, 
  client_id     int(10) NOT NULL, 
  value         int(10) NOT NULL, 
  `date`        date NOT NULL, 
  remedied_date date NOT NULL, 
  status        varchar(255) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE BasketOrder (
  id               int(10) NOT NULL AUTO_INCREMENT, 
  client_id        int(10) NOT NULL, 
  delivery_address varchar(255), 
  status           int(10) NOT NULL, 
  order_date       date NOT NULL, 
  total_amount     int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE OrderDelivery (
  id              int(10) NOT NULL AUTO_INCREMENT, 
  delivery_date   date NOT NULL, 
  basket_order_id int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE OrderPayment (
  id           int(10) NOT NULL AUTO_INCREMENT, 
  payment_date date NOT NULL, 
  order_id     int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE RainfallSensor (
  id                     int(10) NOT NULL AUTO_INCREMENT, 
  metereologic_sensor_id int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE WindSensor (
  id                     int(10) NOT NULL AUTO_INCREMENT, 
  metereologic_sensor_id int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE HumiditySensor (
  id                     int(10) NOT NULL AUTO_INCREMENT, 
  metereologic_sensor_id int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE AtmPressureSensor (
  id                     int(10) NOT NULL AUTO_INCREMENT, 
  metereologic_sensor_id int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE SoilThermicalSensor (
  id             int(10) NOT NULL AUTO_INCREMENT, 
  soil_sensor_id int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE SoilHumiditySesnor (
  id             int(10) NOT NULL AUTO_INCREMENT, 
  soil_sensor_id int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE FieldBook (
  id             int(10) NOT NULL AUTO_INCREMENT, 
  exploration_id int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Exploration2 (
  id   int(10) NOT NULL AUTO_INCREMENT, 
  area int(10), 
  PRIMARY KEY (id));
CREATE TABLE IrrigationFertilizationCalendar (
  id                   int(10) NOT NULL AUTO_INCREMENT, 
  irrigation_system_id int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE DayPlan (
  id                     int(10) NOT NULL AUTO_INCREMENT, 
  irrigation_calendar_id int(10) NOT NULL, 
  `date`                 date, 
  PRIMARY KEY (id));
CREATE TABLE Hour (
  id          int(10) NOT NULL AUTO_INCREMENT, 
  day_plan_id int(10) NOT NULL, 
  hour        int(10) NOT NULL, 
  minute      int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE PlotPlan (
  id      int(10) NOT NULL AUTO_INCREMENT, 
  hour_id int(10) NOT NULL, 
  plot_id int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Record (
  id            int(10) NOT NULL AUTO_INCREMENT, 
  field_book_id int(10) NOT NULL, 
  operation_id  int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE ProductionFactorApplication (
  id                   int(10) NOT NULL AUTO_INCREMENT, 
  plot_plan_id         int(10) NOT NULL, 
  production_factor_id int(10) NOT NULL, 
  type                 int(10), 
  PRIMARY KEY (id));
CREATE TABLE Irrigation (
  id                 int(10) NOT NULL AUTO_INCREMENT, 
  plot_plan_id       int(10) NOT NULL, 
  irrigation_minutes int(10), 
  type               int(10), 
  PRIMARY KEY (id));
CREATE TABLE PlantedCulture (
  id         int(10) NOT NULL AUTO_INCREMENT, 
  plot_id    int(10) NOT NULL, 
  culture_id int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Harvest (
  id           int(10) NOT NULL AUTO_INCREMENT, 
  plot_id      int(10) NOT NULL, 
  culture_id   int(10) NOT NULL, 
  harvest_date date NOT NULL, 
  quantity     int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE MetereologicSensor (
  id                      int(10) NOT NULL AUTO_INCREMENT, 
  metereologic_station_id int(10) NOT NULL, 
  data_type               varchar(255), 
  PRIMARY KEY (id));
CREATE TABLE SoilSensor (
  id        int(10) NOT NULL AUTO_INCREMENT, 
  plot_id   int(10) NOT NULL, 
  data_type varchar(255), 
  PRIMARY KEY (id));
CREATE TABLE SensorData (
  id          int(10) NOT NULL AUTO_INCREMENT, 
  sensor_id   int(10) NOT NULL, 
  record_date date NOT NULL, 
  value       int(10), 
  PRIMARY KEY (id));
CREATE TABLE Basket (
  id                  int(10) NOT NULL AUTO_INCREMENT, 
  basket_order_id     int(10) NOT NULL, 
  distribution_hub_id int(10) NOT NULL, 
  creation_date       date NOT NULL, 
  price               int(10), 
  PRIMARY KEY (id));
CREATE TABLE Product (
  id        int(10) NOT NULL AUTO_INCREMENT, 
  price     int(10) NOT NULL, 
  name      varchar(255) NOT NULL, 
  type      varchar(255) NOT NULL, 
  basket_id int(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE DistributionHub (
  id      int(10) NOT NULL AUTO_INCREMENT, 
  address int(10), 
  PRIMARY KEY (id));
ALTER TABLE Building ADD CONSTRAINT FKBuilding98808 FOREIGN KEY (exploration_id) REFERENCES Exploration (id);
ALTER TABLE Plot ADD CONSTRAINT FKPlot395149 FOREIGN KEY (exploration_id) REFERENCES Exploration (id);
ALTER TABLE ProductionFactor ADD CONSTRAINT FKProduction645935 FOREIGN KEY (technical_sheet_id) REFERENCES TechnicalSheet (id);
ALTER TABLE Substance ADD CONSTRAINT FKSubstance325293 FOREIGN KEY (techincal_sheet_id) REFERENCES TechnicalSheet (id);
ALTER TABLE Element ADD CONSTRAINT FKElement323341 FOREIGN KEY (technical_sheet_id) REFERENCES TechnicalSheet (id);
ALTER TABLE StoredProductionFactor ADD CONSTRAINT FKStoredProd989029 FOREIGN KEY (production_factor_id) REFERENCES ProductionFactor (id);
ALTER TABLE StoredAnimalRation ADD CONSTRAINT FKStoredAnim722184 FOREIGN KEY (animal_ration_id) REFERENCES AnimalRation (id);
ALTER TABLE StoredCulture ADD CONSTRAINT FKStoredCult19001 FOREIGN KEY (culture_id) REFERENCES Culture (id);
ALTER TABLE IrrigationFertilizationSystem ADD CONSTRAINT FKIrrigation909202 FOREIGN KEY (exploration_id) REFERENCES Exploration (id);
ALTER TABLE MetereologicStation ADD CONSTRAINT FKMetereolog22823 FOREIGN KEY (exploration_id) REFERENCES Exploration (id);
ALTER TABLE ClientIncident ADD CONSTRAINT FKClientInci426524 FOREIGN KEY (client_id) REFERENCES Client (id);
ALTER TABLE BasketOrder ADD CONSTRAINT FKBasketOrde306327 FOREIGN KEY (client_id) REFERENCES Client (id);
ALTER TABLE OrderPayment ADD CONSTRAINT FKOrderPayme358084 FOREIGN KEY (order_id) REFERENCES BasketOrder (id);
ALTER TABLE OrderDelivery ADD CONSTRAINT FKOrderDeliv967783 FOREIGN KEY (basket_order_id) REFERENCES BasketOrder (id);
ALTER TABLE Client ADD CONSTRAINT FKClient943020 FOREIGN KEY (exploration_id) REFERENCES Exploration (id);
ALTER TABLE StoredAnimalRation ADD CONSTRAINT FKStoredAnim402095 FOREIGN KEY (building_id) REFERENCES Building (id);
ALTER TABLE StoredCulture ADD CONSTRAINT FKStoredCult282446 FOREIGN KEY (building_id) REFERENCES Building (id);
ALTER TABLE StoredProductionFactor ADD CONSTRAINT FKStoredProd149195 FOREIGN KEY (building_id) REFERENCES Building (id);
ALTER TABLE SoilHumiditySesnor ADD CONSTRAINT FKSoilHumidi860525 FOREIGN KEY (soil_sensor_id) REFERENCES Plot (id);
ALTER TABLE SoilThermicalSensor ADD CONSTRAINT FKSoilThermi8148 FOREIGN KEY (soil_sensor_id) REFERENCES Plot (id);
ALTER TABLE FieldBook ADD CONSTRAINT FKFieldBook430485 FOREIGN KEY (exploration_id) REFERENCES Exploration (id);
ALTER TABLE IrrigationFertilizationCalendar ADD CONSTRAINT FKIrrigation442733 FOREIGN KEY (irrigation_system_id) REFERENCES IrrigationFertilizationSystem (id);
ALTER TABLE DayPlan ADD CONSTRAINT FKDayPlan269013 FOREIGN KEY (irrigation_calendar_id) REFERENCES IrrigationFertilizationCalendar (id);
ALTER TABLE Hour ADD CONSTRAINT FKHour521649 FOREIGN KEY (day_plan_id) REFERENCES DayPlan (id);
ALTER TABLE PlotPlan ADD CONSTRAINT FKPlotPlan751125 FOREIGN KEY (hour_id) REFERENCES Hour (id);
ALTER TABLE PlotPlan ADD CONSTRAINT FKPlotPlan180004 FOREIGN KEY (plot_id) REFERENCES Plot (id);
ALTER TABLE ProductionFactorApplication ADD CONSTRAINT FKProduction786465 FOREIGN KEY (plot_plan_id) REFERENCES PlotPlan (id);
ALTER TABLE Irrigation ADD CONSTRAINT FKIrrigation470 FOREIGN KEY (plot_plan_id) REFERENCES PlotPlan (id);
ALTER TABLE ProductionFactorApplication ADD CONSTRAINT FKProduction34777 FOREIGN KEY (production_factor_id) REFERENCES ProductionFactor (id);
ALTER TABLE Record ADD CONSTRAINT FKRecord926973 FOREIGN KEY (field_book_id) REFERENCES FieldBook (id);
ALTER TABLE Record ADD CONSTRAINT FKRecord101428 FOREIGN KEY (operation_id) REFERENCES ProductionFactorApplication (id);
ALTER TABLE Record ADD CONSTRAINT FKRecord685507 FOREIGN KEY (operation_id) REFERENCES Irrigation (id);
ALTER TABLE PlantedCulture ADD CONSTRAINT FKPlantedCul364152 FOREIGN KEY (plot_id) REFERENCES Plot (id);
ALTER TABLE PlantedCulture ADD CONSTRAINT FKPlantedCul754813 FOREIGN KEY (culture_id) REFERENCES Culture (id);
ALTER TABLE Harvest ADD CONSTRAINT FKHarvest383289 FOREIGN KEY (plot_id) REFERENCES Plot (id);
ALTER TABLE Harvest ADD CONSTRAINT FKHarvest264323 FOREIGN KEY (culture_id) REFERENCES Culture (id);
ALTER TABLE Record ADD CONSTRAINT FKRecord525748 FOREIGN KEY (operation_id) REFERENCES Harvest (id);
ALTER TABLE MetereologicSensor ADD CONSTRAINT FKMetereolog665179 FOREIGN KEY (metereologic_station_id) REFERENCES MetereologicStation (id);
ALTER TABLE ThermicalSensor ADD CONSTRAINT FKThermicalS737284 FOREIGN KEY (metereologic_sensor_id) REFERENCES MetereologicSensor (id);
ALTER TABLE RainfallSensor ADD CONSTRAINT FKRainfallSe736108 FOREIGN KEY (metereologic_sensor_id) REFERENCES MetereologicSensor (id);
ALTER TABLE WindSensor ADD CONSTRAINT FKWindSensor586305 FOREIGN KEY (metereologic_sensor_id) REFERENCES MetereologicSensor (id);
ALTER TABLE AtmPressureSensor ADD CONSTRAINT FKAtmPressur409350 FOREIGN KEY (metereologic_sensor_id) REFERENCES MetereologicSensor (id);
ALTER TABLE HumiditySensor ADD CONSTRAINT FKHumiditySe433249 FOREIGN KEY (metereologic_sensor_id) REFERENCES MetereologicSensor (id);
ALTER TABLE SoilSensor ADD CONSTRAINT FKSoilSensor506620 FOREIGN KEY (plot_id) REFERENCES Plot (id);
ALTER TABLE SoilHumiditySesnor ADD CONSTRAINT FKSoilHumidi557988 FOREIGN KEY (soil_sensor_id) REFERENCES SoilSensor (id);
ALTER TABLE SoilThermicalSensor ADD CONSTRAINT FKSoilThermi705610 FOREIGN KEY (soil_sensor_id) REFERENCES SoilSensor (id);
ALTER TABLE SensorData ADD CONSTRAINT FKSensorData867419 FOREIGN KEY (sensor_id) REFERENCES ThermicalSensor (id);
ALTER TABLE SensorData ADD CONSTRAINT FKSensorData195354 FOREIGN KEY (sensor_id) REFERENCES AtmPressureSensor (id);
ALTER TABLE Record ADD CONSTRAINT FKRecord344206 FOREIGN KEY (operation_id) REFERENCES SensorData (id);
ALTER TABLE Basket ADD CONSTRAINT FKBasket917325 FOREIGN KEY (basket_order_id) REFERENCES BasketOrder (id);
ALTER TABLE Product ADD CONSTRAINT FKProduct707622 FOREIGN KEY (basket_id) REFERENCES Basket (id);
ALTER TABLE Basket ADD CONSTRAINT FKBasket435848 FOREIGN KEY (distribution_hub_id) REFERENCES DistributionHub (id);
