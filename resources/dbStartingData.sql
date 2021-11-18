INSERT INTO SAFETY.ACCOUNTS (id,version,last_modified,TYPE, LOGIN, PASSWORD, NAME, SURNAME, EMAIL, PHONE, VERIFIEDBY, question, answer, isactive) VALUES (-1,1,'2021-06-27 10:18:14','Administrator', 'administrator1', '32af2872c54cfb4d6adb2a9e41bbc8ee0d1e456cc46d7f10371b1d03ac8ee6b3', 'Jan', 'Kowalski','jan.kowalski@mail.com', '123456789', null, 'question', 'answer', 1);
INSERT INTO SAFETY.ADMINISTRATORS(id, adminstamp, version,last_modified, account_Id) VALUES (-1, 'J. Kowalski', 1,'2021-06-27 10:18:14', -1);

INSERT INTO SAFETY.ACCOUNTS (id,version,last_modified,TYPE, LOGIN, PASSWORD, NAME, SURNAME, EMAIL, PHONE, VERIFIEDBY, question, answer, isactive) VALUES (-2,1,'2021-06-27 10:18:14','Administrator', 'administrator2', '578abac9137a4267c49a616b7e400de916a20efc7c192d76f03d5ecdf0e29a71', 'Magdalena ', 'Jabłczyńska','magdalena.jablczynska@mail.com', '710456789', -1, 'question', 'answer', 1);
INSERT INTO SAFETY.ADMINISTRATORS(id, adminstamp, version,last_modified, account_Id) VALUES (-2, 'M. Jabłczyńska', 1,'2021-06-27 10:18:14', -2);

UPDATE SAFETY.ACCOUNTS SET VERIFIEDBY =-2 WHERE ID =-1;

INSERT INTO SAFETY.ACCOUNTS (id,version,last_modified,TYPE, LOGIN, PASSWORD, NAME, SURNAME, EMAIL, PHONE, VERIFIEDBY, question, answer, isactive) VALUES (-3,1,'2021-06-27 10:18:14','Assessor', 'assessor1', '6c64cecb92f6d5ee698a63f2dd132d8140b5bd019e9aeee05433bd2d49e8373b', 'Tomasz', 'Malinowski','tomasz.malinowski@mail.com', '123412989', -1, 'question', 'answer', 1);
INSERT INTO SAFETY.ASSESSORS(id, assessorstamp, version,last_modified, account_Id) VALUES (-1, 'T. Malinowski', 1,'2021-06-27 10:18:14', -3);

INSERT INTO SAFETY.ACCOUNTS (id,version,last_modified,TYPE, LOGIN, PASSWORD, NAME, SURNAME, EMAIL, PHONE, VERIFIEDBY, question, answer, isactive) VALUES (-6,1,'2021-06-27 10:18:14', null, 'assessor2', '53925b78ecd8a6f69921ed46ab9eae4a953eeb8adc3f40430e9886da2938c623', 'Paweł', 'Rybicki','pawel.rybicki@mail.com', '471412989', null, 'question', 'answer', 0);


INSERT INTO SAFETY.ACCOUNTS (id,version,last_modified,TYPE, LOGIN, PASSWORD, NAME, SURNAME, EMAIL, PHONE, VERIFIEDBY, question, answer, isactive) VALUES (-4,1,'2021-06-27 10:18:14','Sales', 'sales1', '855dbcf882298e0d2086979cd7e29c0afec57eb72598b55e2cb101f7b63edc99', 'Anna', 'Nowak','anna.nowak@mail.com', '129216789', -1, 'question', 'answer', 1);
INSERT INTO SAFETY.SALES(id, salesstamp, version,last_modified, account_Id) VALUES (-1, 'A. Nowak', 1,'2021-06-27 10:18:14', -4);

INSERT INTO SAFETY.ACCOUNTS (id,version,last_modified,TYPE, LOGIN, PASSWORD, NAME, SURNAME, EMAIL, PHONE, VERIFIEDBY, question, answer, isactive) VALUES (-5,1,'2021-06-27 10:18:14','LabTechnician', 'laboratory1', '0eb865b915c5dcfc7b4debf8ba6c303ef775ecf2ef90d4043f81321a116ac296', 'Katarzyna', 'Brzozowska','katarzyna.brzozowska@mail.com', '721456789', -1, 'question', 'answer', 1);
INSERT INTO SAFETY.LABTECHNICIANS(id, labstamp, version,last_modified, account_Id) VALUES (-1, 'K. Brzozowska', 1,'2021-06-27 10:18:14', -5);

INSERT INTO SAFETY.SUBSTRATES (substrate_id,version,last_modified,SUBSTRATENAME, SUBSTRATE_DESCRIPTION) VALUES (-1,1,'2021-06-27 10:18:14','Aqua', 'rozpuszczalnik');
INSERT INTO SAFETY.SUBSTRATES (substrate_id,version,last_modified,SUBSTRATENAME, SUBSTRATE_DESCRIPTION) VALUES (-2,1,'2021-06-28 10:18:14','Glycerin', 'rozpuszczalnik');
INSERT INTO SAFETY.SUBSTRATES (substrate_id,version,last_modified,SUBSTRATENAME, SUBSTRATE_DESCRIPTION) VALUES (-3,1,'2021-06-29 10:18:14','AlcoholDenat', 'rozpuszczalnik');
INSERT INTO SAFETY.SUBSTRATES (substrate_id,version,last_modified,SUBSTRATENAME, SUBSTRATE_DESCRIPTION) VALUES (-4,1,'2021-06-22 10:18:14','Isopropyl Myristate', 'rozpuszczalnik');
INSERT INTO SAFETY.SUBSTRATES (substrate_id,version,last_modified,SUBSTRATENAME, SUBSTRATE_DESCRIPTION) VALUES (-5,1,'2021-06-21 10:18:14','Cetearyl Alcohol', 'rozpuszczalnik');
INSERT INTO SAFETY.SUBSTRATES (substrate_id,version,last_modified,SUBSTRATENAME, SUBSTRATE_DESCRIPTION) VALUES (-6,1,'2021-06-20 10:18:14','Xantan Gum', 'emulgator');


INSERT INTO TOXICOLOGY(toxicology_id, last_modified, version, name) VALUES (-1, '2021-06-28 10:18:14', 1, 'ToxicologyFor Krem A');

INSERT INTO DESCRIBED_BY(substrate_id, toxicology_id) VALUES (-1, -1);
INSERT INTO DESCRIBED_BY(substrate_id, toxicology_id) VALUES (-2, -1);
INSERT INTO DESCRIBED_BY(substrate_id, toxicology_id) VALUES (-5, -1);


INSERT INTO SAFETY.CATEGORIES (category_id,version,last_modified,CATEGORYNAME) VALUES (-1,1,'2021-06-27 10:18:14','Krem');
INSERT INTO SAFETY.CATEGORIES (category_id,version,last_modified,CATEGORYNAME) VALUES (-2,1,'2021-06-27 10:18:14','Tonik');
INSERT INTO SAFETY.CATEGORIES (category_id,version,last_modified,CATEGORYNAME) VALUES (-3,1,'2021-06-27 10:18:14','Serum');
INSERT INTO SAFETY.CATEGORIES (category_id,version,last_modified,CATEGORYNAME) VALUES (-4,1,'2021-06-27 10:18:14','Płyn micelarny');

INSERT INTO SAFETY.ANALYSIS (analysis_id,version,last_modified, name, minimum, maximum, standard) VALUES (-1,1,'2021-06-27 10:18:14','HPLC', 1, 100, 'ASTM1');
INSERT INTO SAFETY.ANALYSIS (analysis_id,version,last_modified, name, minimum, maximum, standard) VALUES (-2,1,'2021-06-27 10:18:14','Dermatologia', 1, 100, 'ASTM2');
INSERT INTO SAFETY.ANALYSIS (analysis_id,version,last_modified, name, minimum, maximum, standard) VALUES (-3,1,'2021-06-27 10:18:14','Mikrobiologia', 1, 100, 'ASTM3');
INSERT INTO SAFETY.ANALYSIS (analysis_id,version,last_modified, name, minimum, maximum, standard) VALUES (-4,1,'2021-06-27 10:18:14','GC-MS', 1, 100, 'ASTM4');

INSERT INTO SAFETY.ANALYSIS_DEMANDS (analysis_id, category_id) VALUES (-1, -1);
INSERT INTO SAFETY.ANALYSIS_DEMANDS (analysis_id, category_id) VALUES (-3, -1);
INSERT INTO SAFETY.ANALYSIS_DEMANDS (analysis_id, category_id) VALUES (-4, -1);

INSERT INTO SAFETY.ANALYSIS_DEMANDS (analysis_id, category_id) VALUES (-2, -2);
INSERT INTO SAFETY.ANALYSIS_DEMANDS (analysis_id, category_id) VALUES (-3, -2);

INSERT INTO SAFETY.ANALYSIS_DEMANDS (analysis_id, category_id) VALUES (-1, -3);
INSERT INTO SAFETY.ANALYSIS_DEMANDS (analysis_id, category_id) VALUES (-3, -3);

INSERT INTO SAFETY.ANALYSIS_DEMANDS (analysis_id, category_id) VALUES (-1, -4);
INSERT INTO SAFETY.ANALYSIS_DEMANDS (analysis_id, category_id) VALUES (-2, -4);
INSERT INTO SAFETY.ANALYSIS_DEMANDS (analysis_id, category_id) VALUES (-3, -4);
INSERT INTO SAFETY.ANALYSIS_DEMANDS (analysis_id, category_id) VALUES (-4, -4);


INSERT INTO COSMETICS (cosmetic_id, assessed_by, composition, created_by, last_modified, name, order_nb, version, category_id, toxicology_id) VALUES (-1, -1, 'Aqua, Glycerin, Cetearyl Alcohol', -1, '2021-06-20 10:18:14', 'Krem A', 123556, 1, -1, -1);
INSERT INTO COSMETICS (cosmetic_id, assessed_by, composition, created_by, last_modified, name, order_nb, version, category_id) VALUES (-2, null, 'Aqua, Cetearyl Alcohol', -1, '2021-06-20 10:18:14', 'Tonik A', 123556, 1, -2);  


