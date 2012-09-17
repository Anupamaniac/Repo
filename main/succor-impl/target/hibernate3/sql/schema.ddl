
    create table event (
        id bigint not null auto_increment,
        name varchar(255),
        message_type_id bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table message_type (
        id bigint not null auto_increment,
        class_name varchar(255),
        message_type varchar(255),
        priority smallint,
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
        grandfathered bit,
        hours_prior_to_session int default 0,
        ignore_dnc bit,
        name varchar(255),
        solo bit,
        support_lang varchar(30),
        ticket_type varchar(255),
        update_st bit,
        version integer,
        event_id bigint,
        product_name varchar(255),
        primary key (rule_id)
    ) ENGINE=InnoDB;

    create table rule_action (
        rule_id bigint not null,
        action varchar(255),
        primary key (rule_id)
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
        body longtext,
        kid bit,
        sender varchar(255),
        subject varchar(255),
        type varchar(255),
        rule_id bigint,
        primary key (id),
        unique (rule_id, type, kid)
    ) ENGINE=InnoDB;

    create table template_variable (
        id bigint not null auto_increment,
        display_name varchar(255),
        name varchar(255),
        primary key (id)
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

    create table variable_values (
        id bigint not null auto_increment,
        value varchar(255),
        variable_id bigint,
        event_id bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    alter table event 
        add index FK5C6729A4D496F49 (message_type_id), 
        add constraint FK5C6729A4D496F49 
        foreign key (message_type_id) 
        references message_type (id);

    alter table rule 
        add index FK3596FC2A88E160 (event_id), 
        add constraint FK3596FC2A88E160 
        foreign key (event_id) 
        references event (id);

    alter table rule 
        add index FK3596FCED3EBD30 (product_name), 
        add constraint FK3596FCED3EBD30 
        foreign key (product_name) 
        references product (name);

    alter table rule_action 
        add index FKC8BD50D986753D94 (rule_id), 
        add constraint FKC8BD50D986753D94 
        foreign key (rule_id) 
        references rule (rule_id);

    alter table template 
        add index FKB13ACC7A86753D94 (rule_id), 
        add constraint FKB13ACC7A86753D94 
        foreign key (rule_id) 
        references rule (rule_id);

    alter table ticket 
        add index FKCBE86B0C86753D94 (rule_id), 
        add constraint FKCBE86B0C86753D94 
        foreign key (rule_id) 
        references rule (rule_id);

    alter table variable_values 
        add index FK463244452A88E160 (event_id), 
        add constraint FK463244452A88E160 
        foreign key (event_id) 
        references event (id);

    alter table variable_values 
        add index FK46324445E54F5094 (variable_id), 
        add constraint FK46324445E54F5094 
        foreign key (variable_id) 
        references variable (id);
