

INSERT INTO vehicle_inv_test.vehicle_type (type_name) VALUES('Passenger car');
SET @pass_id = LAST_INSERT_ID();

INSERT INTO vehicle_inv_test.vehicle_type (type_name) VALUES('Pickup');
SET @pick_id = LAST_INSERT_ID();

INSERT INTO vehicle_inv_test.vehicle_type (type_name) VALUES('Truck');
SET @truck_id = LAST_INSERT_ID();

INSERT INTO vehicle_inv_test.vehicle_type (type_name) VALUES('Bus');
SET @bus_id = LAST_INSERT_ID();


INSERT INTO vehicle_inv_test.vehicle_part (name) VALUES('Engine');
SET @engine_id = LAST_INSERT_ID();
INSERT INTO vehicle_inv_test.vtype_vpart_relations
(vehicle_type_id, vehicle_part_id) VALUES(@pass_id , @engine_id);

INSERT INTO vehicle_inv_test.vehicle_part (name) VALUES('Gearbox');
SET @gear_id = LAST_INSERT_ID();
INSERT INTO vehicle_inv_test.vtype_vpart_relations
(vehicle_type_id, vehicle_part_id) VALUES(@pass_id , @gear_id);

INSERT INTO vehicle_inv_test.vehicle_part (name) VALUES('Brakes');
SET @break_id = LAST_INSERT_ID();
INSERT INTO vehicle_inv_test.vtype_vpart_relations
(vehicle_type_id, vehicle_part_id) VALUES(@pass_id , @break_id);

INSERT INTO vehicle_inv_test.vehicle
(plate_number, registration_date, vin, vehicle_type_id)
VALUES('101-85-901', '2018-01-05', 'ghasd85d4sd6a4sw1', @pass_id);

INSERT INTO vehicle_inv_test.vehicle_part (name) VALUES('Tyres');
SET @tire_id = LAST_INSERT_ID();
INSERT INTO vehicle_inv_test.vtype_vpart_relations
(vehicle_type_id, vehicle_part_id) VALUES(@pick_id, @tire_id);

INSERT INTO vehicle_inv_test.vehicle_part (name) VALUES('Mirrors');
SET @mirror_id = LAST_INSERT_ID();
INSERT INTO vehicle_inv_test.vtype_vpart_relations
(vehicle_type_id, vehicle_part_id) VALUES(@pick_id, @mirror_id);

INSERT INTO vehicle_inv_test.vehicle
(plate_number, registration_date, vin, vehicle_type_id)
VALUES('102-86-902', '2019-03-06', '11asddf445d6a4syu', @pick_id);

INSERT INTO vehicle_inv_test.vehicle
(plate_number, registration_date, vin, vehicle_type_id)
VALUES('103-87-903', '2020-07-07', '87jkh85d4sd6ahh67', @pick_id);

INSERT INTO vehicle_inv_test.vehicle_part (name) VALUES('Headlights');
SET @light_id = LAST_INSERT_ID();
INSERT INTO vehicle_inv_test.vtype_vpart_relations
(vehicle_type_id, vehicle_part_id) VALUES(@truck_id , @light_id);

INSERT INTO vehicle_inv_test.vehicle_part (name) VALUES('Horn');
SET @horn_id = LAST_INSERT_ID();
INSERT INTO vehicle_inv_test.vtype_vpart_relations
(vehicle_type_id, vehicle_part_id) VALUES(@truck_id , @horn_id);

INSERT INTO vehicle_inv_test.vehicle_part (name) VALUES('Dump body');
SET @dump_id = LAST_INSERT_ID();
INSERT INTO vehicle_inv_test.vtype_vpart_relations
(vehicle_type_id, vehicle_part_id) VALUES(@truck_id , @dump_id);

INSERT INTO vehicle_inv_test.vehicle
(plate_number, registration_date, vin, vehicle_type_id)
VALUES('104-88-904', '2021-12-08', '145g48sm2316a4sw1', @truck_id );

INSERT INTO vehicle_inv_test.vehicle_part (name) VALUES('Doors');
SET @door_id = LAST_INSERT_ID();
INSERT INTO vehicle_inv_test.vtype_vpart_relations
(vehicle_type_id, vehicle_part_id) VALUES(@bus_id, @door_id);

INSERT INTO vehicle_inv_test.vehicle_part (name) VALUES('Steps');
SET @step_id = LAST_INSERT_ID();
INSERT INTO vehicle_inv_test.vtype_vpart_relations
(vehicle_type_id, vehicle_part_id) VALUES(@bus_id, @step_id);

INSERT INTO vehicle_inv_test.vehicle
(plate_number, registration_date, vin, vehicle_type_id)
VALUES('001-99-007', '2021-01-01', 'llhasd85dj760004j', @bus_id);

