  drop table if exists rule;
  drop table if exists event;
  drop table if exists product;
  drop table if exists rule_action;
  drop table if exists ticket;
  drop table if exists variable;
  drop table if exists rule_history;

   create table event (
        id bigint not null auto_increment,
        name varchar(255),
        message_type_id bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table message_type (
        id bigint not null auto_increment,
        message_type varchar (255),
        class_name varchar(255),
        priority smallint default 0,
        primary key (id)
    ) ENGINE=InnoDB;

    create table product (
        name varchar(255) not null,
        long_name varchar(255),
        primary key (name)
    ) ENGINE=InnoDB;

    create table rule (
        rule_id bigint not null auto_increment,
        active bit,
        days integer,
        ignore_dnc bit,
        name varchar(255),
        support_lang varchar(30),
        ticket_type varchar(255),
        update_st bit,
        event_id bigint,
        product_name varchar(255),
        primary key (rule_id)
    ) ENGINE=InnoDB;

    create table rule_action (
        rule_id bigint not null,
        action varchar(255)
    ) ENGINE=InnoDB;

    create table ticket (
        id bigint not null auto_increment,
        summary longtext,
        type varchar(255),
        rule_id bigint,
        primary key (id),
        unique (rule_id, type)
    ) ENGINE=InnoDB;

    create table variable (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;
    
    create table rule_history (
        id bigint not null auto_increment,
        action varchar(255),
        created datetime,
        lang varchar(255),
        rule_id bigint,
        rule_name varchar(255),
        username varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

   create table template (
        id bigint not null auto_increment,
        body longtext CHARACTER SET utf8 COLLATE utf8_general_ci,
        sender varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci,
        subject varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci,
        type varchar(255),
        rule_id bigint,
        kid bit(1) DEFAULT b'0',
        primary key (id),
        unique (rule_id, type, kid)
    ) ENGINE=InnoDB;

    create table template_variable (
        id bigint not null auto_increment,
        display_name varchar(255),
        name varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table variable_values (
    	id bigint not null auto_increment,
  		event_id bigint(20) not null,
		variable_id bigint(20) NOT NULL,
  		value varchar(255) NOT NULL,
  		primary key (id),
  		unique(event_id, variable_id)
	) ENGINE=InnoDB;


    create table email_report_history (
        id bigint not null auto_increment,
        date datetime,
        primary key (id)
    ) ENGINE=InnoDB;

    create table history_entity (
        id bigint not null auto_increment,
        date datetime,
        exception longtext,
        stacktrace longtext,
        status varchar(255),
        task_id bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table message (
        id bigint not null auto_increment,
        created_at datetime,
        message longtext not null,
        message_type varchar (255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table postal_history (
        id bigint not null auto_increment,
        contact_reason varchar(255),
        created_at datetime,
        customer_name varchar(255),
        email varchar(255),
        guid varchar(255),
        language varchar(255),
        processed_at datetime,
        product_level varchar(255),
        shipping_address varchar(255),
        status varchar(255),
        support_locale varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table report_entity (
        id bigint not null auto_increment,
        date datetime,
        type varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table task (
        id bigint not null auto_increment,
        arguments longtext,
        bean_name varchar(255),
        created_at datetime,
        method_name varchar(255),
        next_run datetime,
        status varchar(255),
        type varchar(255),
        message_id bigint not null,
        priority smallint default 0,
        primary key (id)
    ) ENGINE=InnoDB;

    alter table event
        add constraint message_type_fk
        foreign key (message_type_id)
        references message_type (id);

    alter table rule
        add index rule_event_idx (event_id),
        add constraint rule_event_fk
        foreign key (event_id)
        references event (id);

    alter table rule
        add index rule_product_idx (product_name),
        add constraint product_name_fk
        foreign key (product_name)
        references product (name);

    alter table rule_action
        add index action_rule_idx (rule_id),
        add constraint action_rule_fk
        foreign key (rule_id)
        references rule (rule_id);

    alter table ticket
        add index ticket_rule_idx (rule_id),
        add constraint ticket_rule_fk
        foreign key (rule_id)
        references rule (rule_id);
        
 	alter table rule_history
 		add index rule_history_idx (rule_id);

    alter table template
        add index template_rule_idx (rule_id),
        add constraint template_rule_fk
        foreign key (rule_id)
        references rule (rule_id);

    alter table history_entity
        add index history_entity_task_idx (task_id),
        add constraint history_entity_task_fk
        foreign key (task_id)
        references task (id);

    alter table task
        add index task_message_idx (message_id),
        add index task_status_idx(status), 
    	add index task_type_idx(type), 
    	add index task_nextrun_idx(next_run),
    	add index task_created_idx(created_at),
        add constraint task_message_fk
        foreign key (message_id)
        references message (id);
        
    alter table rule
    	add column version integer default 0;
    
    alter table task
    	add column updated_at datetime;