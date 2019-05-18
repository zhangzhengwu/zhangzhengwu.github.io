create database if not exists chatroom;
use chatroom;
drop table if exists tb_chat_message;  -- 先删除外键表 (注释的时候需要注意 -- 后面需要空格一下)
drop table if exists tb_user_info;   -- 再删除主键表
create table tb_user_info (
	u_id int auto_increment primary key comment '编号',
	u_name varchar(20) unique not null comment '账户',
	u_pwd varchar(20) not null comment '密码',
	u_nick varchar(20) unique comment '昵称',
	u_img varchar(256) default 'images/u_icon.png' comment '头像',
	u_email varchar(50) comment '邮箱',
	u_phone char(13) comment '电话' check(regexp_like(u_phone,'^\d{3}-\d{4}-\d{4}$|^\d{4}-\d{8}$')),
	u_card_id char(18) comment '身份证' check(len(u_card_id) in (15,18)),
	u_register_time timestamp not null comment '注册时间',
	u_state bit default 0 comment '状态',
	u_remark varchar(256) comment '备注'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8; 

create table tb_chat_message(
	c_id int auto_increment primary key comment '编号',
	c_send_content text not null comment '消息内容',
	c_send_time datetime not null comment '发送时间',
	c_send_id int not null comment '发送方',
	c_receiver_id int comment '接收方',
	c_remark varchar(256) comment'备注'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

alter table tb_chat_message add	constraint fk_msg_sendid foreign key(c_send_id) references  tb_user_info(u_id) on delete cascade;
alter table tb_chat_message add	constraint fk_msg_receiverid foreign key(c_receiver_id) references  tb_user_info(u_id) on delete set null;

select * from tb_user_info;	
select * from tb_chat_message;


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
