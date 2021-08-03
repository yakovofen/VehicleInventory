
SET FOREIGN_KEY_CHECKS=0;

drop table if exists vehicle_inv_test.vtype_vpart_relations;
drop table if exists vehicle_inv_test.vehicle_type;
drop table if exists vehicle_inv_test.vehicle;
drop table if exists vehicle_inv_test.vehicle_part;


create table vehicle_inv_test.vehicle (id bigint not null auto_increment, plate_number varchar(10) not null, registration_date date not null, vin varchar(20) not null, vehicle_type_id bigint not null, primary key (id)) engine=InnoDB;
create table vehicle_inv_test.vehicle_part (id bigint not null auto_increment, name varchar(100) not null, primary key (id)) engine=InnoDB;
create table vehicle_inv_test.vehicle_type (id bigint not null auto_increment, type_name varchar(100) not null, primary key (id)) engine=InnoDB;
create table vehicle_inv_test.vtype_vpart_relations (vehicle_type_id bigint not null, vehicle_part_id bigint not null, primary key (vehicle_part_id, vehicle_type_id)) engine=InnoDB;

alter table vehicle_inv_test.vehicle add constraint FKddtxlc05rojc56bprvek17hnk foreign key (vehicle_type_id) references vehicle_type (id);
alter table vehicle_inv_test.vtype_vpart_relations add constraint FKa6jyospu1w37suxr2po46qvm1 foreign key (vehicle_part_id) references vehicle_part (id);
alter table vehicle_inv_test.vtype_vpart_relations add constraint FKe4xpcw0bj3242ag21uwrv5cxd foreign key (vehicle_type_id) references vehicle_type (id);

SET FOREIGN_KEY_CHECKS=1;
