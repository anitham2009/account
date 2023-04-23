
drop table if exists account;
drop table if exists customer;
drop table if exists account_type;
drop table if exists branch;
drop table if exists address;
CREATE TABLE address (
  address_id INT AUTO_INCREMENT PRIMARY KEY,
  address_line VARCHAR(30) not null,
  city VARCHAR(20) NOT NULL,
  state VARCHAR(20) NOT NULL,
  country VARCHAR(30) NOT NULL,
  postal_code VARCHAR(15) NOT NULL,
  created_by VARCHAR(30) default 'System',
  created_date date default CURRENT_DATE,
  updated_by VARCHAR(30) default 'System',
  updated_date date default CURRENT_DATE
 );
 
CREATE TABLE branch (
  branch_id INT AUTO_INCREMENT PRIMARY KEY,
  branch_name VARCHAR(30) not null,
  address_id INT NOT NULL,
  is_active VARCHAR(2) not null,
  created_by VARCHAR(30) default 'System',
  created_date date default CURRENT_DATE,
  updated_by VARCHAR(30) default 'System',
  updated_date date default CURRENT_DATE,
   CONSTRAINT fk_branch_address  FOREIGN KEY (address_id) REFERENCES address(address_id)
  );

  CREATE TABLE customer (
  customer_id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(30) not null,
  surname VARCHAR(30) not null,
  gender VARCHAR(20) not null,
  date_of_birth VARCHAR(10) not null,
  contact_number VARCHAR(15) not null,
  address_id INT not null,
  is_active VARCHAR(2) not null,
  created_by VARCHAR(30) default 'System',
  created_date date default CURRENT_DATE,
  updated_by VARCHAR(30) default 'System',
  updated_date date default CURRENT_DATE,
  CONSTRAINT fk_customer_address  FOREIGN KEY (address_id) REFERENCES address(address_id)
  );
  
  CREATE TABLE account_type (
  type_id INT AUTO_INCREMENT PRIMARY KEY,
  type VARCHAR(30) not null,
  is_active VARCHAR(2) not null,
  created_by VARCHAR(30) default 'System',
  created_date date default CURRENT_DATE,
  updated_by VARCHAR(30) default 'System',
  updated_date date default CURRENT_DATE
 );
  
  CREATE TABLE account (
  account_id INT AUTO_INCREMENT PRIMARY KEY,
  account_number VARCHAR(30) not null,
  branch_id INT not null,
  type_id INT not null,
  customer_id INT not null,
  balance numeric null,
  account_status VARCHAR(10) not null,
  created_by VARCHAR(30) default 'System',
  open_date date default CURRENT_DATE,
  updated_by VARCHAR(30) default 'System',
  updated_date date default CURRENT_DATE,
  CONSTRAINT fk_branch  FOREIGN KEY (branch_id) REFERENCES branch(branch_id),
  CONSTRAINT fk_account_type  FOREIGN KEY (type_id) REFERENCES account_type(type_id),
  CONSTRAINT fk_customer  FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
  );
  