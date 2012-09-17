
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
        message_type varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table parature_task_history (
        id bigint not null auto_increment,
        paratureErrorCode varchar(255),
        paratureException longtext,
        request longtext,
        created_at datetime,
        response longtext,
        recieved_at datetime,
        tracker_id bigint not null,
        primary key (id)
    ) ENGINE=InnoDB;

    create table parature_task_tracker (
        id bigint not null auto_increment,
        paratureAction varchar(255),
        retryCount int default 0 not null,
        status varchar(255),
        ticketId varchar(255),
        task_id bigint not null,
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
        priority smallint not null,
        status varchar(255),
        type varchar(255),
        updated_at datetime,
        message_id bigint not null,
        primary key (id)
    ) ENGINE=InnoDB;

    alter table history_entity 
        add index FKE2136DEE9A0C32DF (task_id), 
        add constraint FKE2136DEE9A0C32DF 
        foreign key (task_id) 
        references task (id);

    alter table parature_task_history 
        add index FK6A89E55895129C (tracker_id), 
        add constraint FK6A89E55895129C 
        foreign key (tracker_id) 
        references parature_task_tracker (id);

    alter table parature_task_tracker 
        add index FK898B7C899A0C32DF (task_id), 
        add constraint FK898B7C899A0C32DF 
        foreign key (task_id) 
        references task (id);

    alter table task 
        add index FK36358593B1B2F5 (message_id), 
        add constraint FK36358593B1B2F5 
        foreign key (message_id) 
        references message (id);
