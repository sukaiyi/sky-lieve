use mysql;
select host, user from user;
create user docker identified by 'hello,20150417,c';
grant all on lieve.* to docker@'%' identified by 'hello,20150417,c' with grant option;
flush privileges;