
insert into t_order(user_id, status) values(1,"OK"),(1,"FAIL");
insert into t_order(user_id, status) values(2,"OK"),(2,"FAIL");
insert into t_order(user_id, status) values(3,"OK"),(3,"FAIL");
insert into t_order(user_id, status) values(4,"OK"),(4,"FAIL");
insert into t_order(user_id, status) values(5,"OK"),(5,"FAIL");
insert into t_order(user_id, status) values(6,"OK"),(6,"FAIL");
insert into t_order(user_id, status) values(7,"OK"),(7,"FAIL");
insert into t_order(user_id, status) values(8,"OK"),(8,"FAIL");
insert into t_order(user_id, status) values(9,"OK"),(9,"FAIL");
insert into t_order(user_id, status) values(10,"OK"),(10,"FAIL");
insert into t_order(user_id, status) values(11,"OK"),(11,"FAIL");
insert into t_order(user_id, status) values(12,"OK"),(12,"FAIL");
insert into t_order(user_id, status) values(13,"OK"),(13,"FAIL");
insert into t_order(user_id, status) values(14,"OK"),(14,"FAIL");
insert into t_order(user_id, status) values(15,"OK"),(15,"FAIL");
insert into t_order(user_id, status) values(16,"OK"),(16,"FAIL");
insert into t_order(user_id, status) values(1,"FAIL1"),(1,"FAIL2"),(1,"FAIL3"),(1,"FAIL4"),(1,"FAIL5"),(1,"FAIL6"),(1,"FAIL7"),(1,"FAIL8"),(1,"FAIL9"),(1,"FAIL10"),(1,"FAIL11"),(1,"FAIL12"),(1,"FAIL13"),(1,"FAIL14"),(1,"FAIL15"),(1,"FAIL16");
select * from t_order where user_id=1 and status='FAIL';
select * from t_order where user_id=1 and order_id=648621847038373889;
delete from t_order where user_id=1 and order_id=648621847038373889;
update t_order set status='FINISH' where user_id=1 and order_id=648621847038373888;
delete from t_order;
drop table t_order;
CREATE TABLE IF NOT EXISTS t_order (order_id BIGINT NOT NULL AUTO_INCREMENT, user_id INT NOT NULL, status VARCHAR(50), PRIMARY KEY (order_id));
insert into t_order(user_id, status) values(1,"FAIL1"),(1,"FAIL2"),(1,"FAIL3"),(1,"FAIL4"),(1,"FAIL5"),(1,"FAIL6"),(1,"FAIL7"),(1,"FAIL8"),(1,"FAIL9"),(1,"FAIL10"),(1,"FAIL11"),(1,"FAIL12"),(1,"FAIL13"),(1,"FAIL14"),(1,"FAIL15"),(1,"FAIL16"),(1,"FAIL17"),(1,"FAIL18"),(1,"FAIL19"),(1,"FAIL20");
insert into t_order(user_id, status) values(2,"FAIL1"),(2,"FAIL2"),(2,"FAIL3"),(2,"FAIL4"),(2,"FAIL5"),(2,"FAIL6"),(2,"FAIL7"),(2,"FAIL8"),(2,"FAIL9"),(2,"FAIL10"),(2,"FAIL11"),(2,"FAIL12"),(2,"FAIL13"),(2,"FAIL14"),(2,"FAIL15"),(2,"FAIL16"),(2,"FAIL17"),(2,"FAIL18"),(2,"FAIL19"),(2,"FAIL20");
