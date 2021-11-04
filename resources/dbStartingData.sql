
INSERT INTO SAFETY.ACCOUNTS (id,version,last_modified,TYPE, LOGIN, PASSWORD, NAME, SURNAME, EMAIL, PHONE, VERIFIEDBY, question, answer, isactive) VALUES (-1,1,'2021-06-27 10:18:14','Administrator', 'administrator1', '0f6b31b40e622d92344d3c3c5112b32816ac9a0fcc8993a8277ffeb95db297c8', 'Jan', 'Kowalski','jan.kowalski@mail.com', '123456789', -2, 'question', 'answer', 1);
INSERT INTO SAFETY.ADMINISTRATORS(id, adminstamp, version,last_modified, account_Id) VALUES (-1, 'J. Kowalski', 1,'2021-06-27 10:18:14', -1);

INSERT INTO SAFETY.ACCOUNTS (id,version,last_modified,TYPE, LOGIN, PASSWORD, NAME, SURNAME, EMAIL, PHONE, VERIFIEDBY, question, answer, isactive) VALUES (-2,1,'2021-06-27 10:18:14','Administrator', 'administrator2', '1c142b2d01aa34e9a36bde480645a57fd69e14155dacfab5a3f9257b77fdc8d8', 'Magdalena ', 'Jabłczyńska','magdalena.jabłczyńska@mail.com', '710456789', -1, 'question', 'answer', 1);
INSERT INTO SAFETY.ADMINISTRATORS(id, adminstamp, version,last_modified, account_Id) VALUES (-2, 'M. Jabłczyńska', 1,'2021-06-27 10:18:14', -2);

INSERT INTO SAFETY.ACCOUNTS (id,version,last_modified,TYPE, LOGIN, PASSWORD, NAME, SURNAME, EMAIL, PHONE, VERIFIEDBY, question, answer, isactive) VALUES (-3,1,'2021-06-27 10:18:14','Assessor', 'assessor1', '0edbe3c1e1d075320dc70cd8c0115cb8dc617ec076e131efa9f7d920eb79c5fe', 'Tomasz', 'Malinowski','tomasz.malinowski@mail.com', '123412989', -1, 'question', 'answer', 1);
INSERT INTO SAFETY.ASSESSORS(id, assessorstamp, version,last_modified, account_Id) VALUES (-1, 'T. Malinowski', 1,'2021-06-27 10:18:14', -3);

INSERT INTO SAFETY.ACCOUNTS (id,version,last_modified,TYPE, LOGIN, PASSWORD, NAME, SURNAME, EMAIL, PHONE, VERIFIEDBY, question, answer, isactive) VALUES (-6,1,'2021-06-27 10:18:14', null, 'assessor2', 'd36415030bdaabcffa5d3419a720aeebb05bdd2cd317237b4c1c325e77dc4914', 'Paweł', 'Rybicki','pawel.rybicki@mail.com', '471412989', null, 'question', 'answer', 0);


INSERT INTO SAFETY.ACCOUNTS (id,version,last_modified,TYPE, LOGIN, PASSWORD, NAME, SURNAME, EMAIL, PHONE, VERIFIEDBY, question, answer, isactive) VALUES (-4,1,'2021-06-27 10:18:14','Sales', 'sales1', '188d79be2c98f9df67c9a6386a445c844a2ca70fe6da3710350fd84a04cd975e', 'Anna', 'Nowak','anna.nowak@mail.com', '129216789', -1, 'question', 'answer', 1);
INSERT INTO SAFETY.SALES(id, salesstamp, version,last_modified, account_Id) VALUES (-1, 'A. Nowak', 1,'2021-06-27 10:18:14', -4);

INSERT INTO SAFETY.ACCOUNTS (id,version,last_modified,TYPE, LOGIN, PASSWORD, NAME, SURNAME, EMAIL, PHONE, VERIFIEDBY, question, answer, isactive) VALUES (-5,1,'2021-06-27 10:18:14','LabTechnician', 'laboratory1', 'eaef7e302d5003065997eb6d1c1811c555206f214999d40d232510c6c1a1e01f', 'Katarzyna', 'Brzozowska','katarzyna.Brzozowska@mail.com', '721456789', -1, 'question', 'answer', 1);
INSERT INTO SAFETY.LABTECHNICIANS(id, labstamp, version,last_modified, account_Id) VALUES (-1, 'K. Brzozowska', 1,'2021-06-27 10:18:14', -5);


INSERT INTO SAFETY.SUBSTRATES (substrate_id,version,last_modified,SUBSTRATE_NAME, SUBSTRATE_DESCRIPTION) VALUES (-1,1,'2021-06-27 10:18:14','Aqua', 'rozpuszczalnik');
INSERT INTO SAFETY.SUBSTRATES (substrate_id,version,last_modified,SUBSTRATE_NAME, SUBSTRATE_DESCRIPTION) VALUES (-2,1,'2021-06-28 10:18:14','Glycerin', 'rozpuszczalnik');
INSERT INTO SAFETY.SUBSTRATES (substrate_id,version,last_modified,SUBSTRATE_NAME, SUBSTRATE_DESCRIPTION) VALUES (-3,1,'2021-06-29 10:18:14','AlcoholDenat', 'rozpuszczalnik');
INSERT INTO SAFETY.SUBSTRATES (substrate_id,version,last_modified,SUBSTRATE_NAME, SUBSTRATE_DESCRIPTION) VALUES (-4,1,'2021-06-22 10:18:14','Isopropyl Myristate', 'rozpuszczalnik');
INSERT INTO SAFETY.SUBSTRATES (substrate_id,version,last_modified,SUBSTRATE_NAME, SUBSTRATE_DESCRIPTION) VALUES (-5,1,'2021-06-21 10:18:14','Cetearyl Alcohol', 'rozpuszczalnik');
INSERT INTO SAFETY.SUBSTRATES (substrate_id,version,last_modified,SUBSTRATE_NAME, SUBSTRATE_DESCRIPTION) VALUES (-6,1,'2021-06-20 10:18:14','Xantan Gum', 'emulgator');


INSERT INTO TOXICOLOGY(toxicology_id, last_modified, version, name) VALUES (-1, '2021-06-28 10:18:14', 1, 'ToxicologyFor Krem A');

INSERT INTO DESCRIBED_BY(substrate_id, toxicology_id) VALUES (-1, -1);
INSERT INTO DESCRIBED_BY(substrate_id, toxicology_id) VALUES (-2, -1);
INSERT INTO DESCRIBED_BY(substrate_id, toxicology_id) VALUES (-5, -1);

INSERT INTO COSMETICS (cosmetic_id, assessed_by, composition, created_by, last_modified, name, order_nb, version, category_id) VALUES (-1, -1, 'Aqua, Glycerin, Cetearyl Alcohol', -1, '2021-06-20 10:18:14', 'Krem A', 123556, 1, -1); 

INSERT INTO SAFETY.CATEGORIES (category_id,version,last_modified,CATEGORY_NAME) VALUES (-1,1,'2021-06-27 10:18:14','Krem');
INSERT INTO SAFETY.CATEGORIES (category_id,version,last_modified,CATEGORY_NAME) VALUES (-2,1,'2021-06-27 10:18:14','Tonik');
INSERT INTO SAFETY.CATEGORIES (category_id,version,last_modified,CATEGORY_NAME) VALUES (-3,1,'2021-06-27 10:18:14','Serum');
INSERT INTO SAFETY.CATEGORIES (category_id,version,last_modified,CATEGORY_NAME) VALUES (-4,1,'2021-06-27 10:18:14','Płyn micelarny');

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


