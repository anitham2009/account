 INSERT INTO address(address_line,city,state,country,postal_code) 
 VALUES ('Stadsplein 6','Amstelveen','North Holland','Netherlands','1176 KV'),
 ('Gelderlandplein 6','Amsterdam','North Holland','Netherlands','1086 HZ'),
 ('Nieuwendijk 160','Amstelveen','North Holland','Netherlands','1186 BU');
 
INSERT INTO branch(branch_name,address_id,is_active)
 VALUES ('ABC Bank',1,'Y');

 INSERT INTO account_type(type,is_active)
 VALUES ('Savings Account','Y'),
 ('Current Account','Y'),
 ('Fixed Deposit','Y');
 
 INSERT INTO customer(first_name,surname,gender,contact_number,date_of_birth,address_id,is_active)
  VALUES ('Nina','John','F','0678987543','01-10-1986',2,'Y'),
  ('Peter','Paul','M','0698423476','01-10-1990',3,'Y');

INSERT INTO account(account_number,branch_id,type_id,customer_id,account_status)
 VALUES ('NL91 ABCB 12345981833',1,1,1,'Active'),
 ('NL91 ABCB 29876589431',1,1,1,'Active');
