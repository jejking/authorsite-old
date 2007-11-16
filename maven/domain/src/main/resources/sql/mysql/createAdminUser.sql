insert into Human (id, createdAt, createdBy_id, updatedAt, updatedBy_id, version, nameQualification, name, givenNames, DTYPE)  values (1, current_timestamp, 1, current_timestamp, 1, 0, null, 'Wurst', 'Hans', 'Individual');
insert into SystemUser (id, individual_id, createdAt, createdBy_id, updatedAt, updatedBy_id, version, userName, password, enabled) values (1, 1, current_timestamp, 1, current_timestamp, 1, 0, 'admin', 'a4a88c0872bf652bb9ed803ece5fd6e82354838a9bf59ab4babb1dab322154e1', 1);
insert into SystemUser_Authorities(SystemUser_id, element) values ( 1, 0 );


