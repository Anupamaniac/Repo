alter table POSTALHISTORY add column SUPPORTLOCALE VARCHAR(255);
update TASK set STATUS = 'CANCELLED' where STATUS='ACTIVE';
update POSTALHISTORY set SUPPORTLOCALE = 'en';
