--INITIALISATION TABLE ROLE
INSERT INTO ROLE(ROLE_ID,ROLE_NAME) VALUES (1,'ROLE_ADMIN');
INSERT INTO ROLE(ROLE_ID,ROLE_NAME) VALUES (2,'ROLE_USER');

--INITIALISATION TABLE UTILISATEURS
INSERT INTO UTILISATEUR(USER_ID, NOM, PRENOM, LOGIN, USER_PASSWORD) values (1, 'umberto', 'de amorin', 'admin', 'admin');
INSERT INTO UTILISATEUR(USER_ID, NOM, PRENOM, LOGIN, USER_PASSWORD) values (2, 'jules', 'vernes', 'jules', 'vernes');
INSERT INTO UTILISATEUR(USER_ID, NOM, PRENOM, LOGIN, USER_PASSWORD) values (3, 'michel', 'durand', 'michel', 'durand');
INSERT INTO UTILISATEUR(USER_ID, NOM, PRENOM, LOGIN, USER_PASSWORD) values (4, 'paul', 'cholet', 'paul', 'cholet');
INSERT INTO UTILISATEUR(USER_ID, NOM, PRENOM, LOGIN, USER_PASSWORD) values (5, 'victor', 'bourel', 'victor', 'bourel');
INSERT INTO UTILISATEUR(USER_ID, NOM, PRENOM, LOGIN, USER_PASSWORD) values (6, 'bertrand', 'tavigne', 'bertrand', 'tavigne');
INSERT INTO UTILISATEUR(USER_ID, NOM, PRENOM, LOGIN, USER_PASSWORD) values (7, 'laurent', 'giraud', 'laurent', 'giraud');

INSERT INTO GERANT(USER_ID, SIREN) values (1, 123456789);
INSERT INTO CLIENT(USER_ID, GERANT_ID) values (2, 1);
INSERT INTO CLIENT(USER_ID, GERANT_ID) values (3, 1);
INSERT INTO CLIENT(USER_ID, GERANT_ID) values (4, 1);
INSERT INTO CLIENT(USER_ID, GERANT_ID) values (5, 1);
INSERT INTO CLIENT(USER_ID, GERANT_ID) values (6, 1);
INSERT INTO CLIENT(USER_ID, GERANT_ID) values (7, 1);

-- TABLE DE JOINTURE
INSERT INTO USER_ROLE(USER_ID,ROLE_ID) VALUES (1,1);
INSERT INTO USER_ROLE(USER_ID,ROLE_ID) VALUES (1,2);
INSERT INTO USER_ROLE(USER_ID,ROLE_ID) VALUES (2,2);
INSERT INTO USER_ROLE(USER_ID,ROLE_ID) VALUES (3,2);
INSERT INTO USER_ROLE(USER_ID,ROLE_ID) VALUES (4,2);
INSERT INTO USER_ROLE(USER_ID,ROLE_ID) VALUES (5,2);
INSERT INTO USER_ROLE(USER_ID,ROLE_ID) VALUES (6,2);
INSERT INTO USER_ROLE(USER_ID,ROLE_ID) VALUES (7,2);

-- INIT DES ADRESSES
INSERT INTO ADRESSE(ID, NUMERO, RUE, CP, VILLE, DETAILS) values (1, 22, 'allee leon bourgeois', 33710, 'St Jean d illac', 'Pas d observations');
INSERT INTO ADRESSE(ID, NUMERO, RUE, CP, VILLE, DETAILS) values (2, 45, 'rue marechal foch', 33000, 'Bordeaux', 'Pas d observations 2');
INSERT INTO ADRESSE(ID, NUMERO, RUE, CP, VILLE, DETAILS) values (3, 3, 'av Charles de Gaulle', 75000, 'Paris', 'Pas d observations');
INSERT INTO ADRESSE(ID, NUMERO, RUE, CP, VILLE, DETAILS) values (4, 14, 'cours Louis Pasteur', 13000, 'Marseille', 'Pas d observations');
INSERT INTO ADRESSE(ID, NUMERO, RUE, CP, VILLE, DETAILS) values (5, 98, 'av Victor Hugo', 66000, 'Perpignan', 'Pas d observations');
INSERT INTO ADRESSE(ID, NUMERO, RUE, CP, VILLE, DETAILS) values (6, 1200, 'rue Jean Jaurès', 59000, 'Lille', 'Pas d observations');
INSERT INTO ADRESSE(ID, NUMERO, RUE, CP, VILLE, DETAILS) values (7, 34, 'rue Jean Moulin', 08500, 'Revin', 'Pas d observations');
INSERT INTO ADRESSE(ID, NUMERO, RUE, CP, VILLE, DETAILS) values (8, 348, 'place Léon Gambetta', 62730, 'Marck', 'Pas d observations');
INSERT INTO ADRESSE(ID, NUMERO, RUE, CP, VILLE, DETAILS) values (9, 28, 'av Général Leclerc', 33700, 'Mérignac', 'Pas d observations');
INSERT INTO ADRESSE(ID, NUMERO, RUE, CP, VILLE, DETAILS) values (10, 110, 'cours Jules Ferry', 93300, 'Aubervilliers', 'Pas d observations');


update UTILISATEUR set address_id = 1 where USER_ID = 1;
update UTILISATEUR set address_id = 2 where USER_ID = 2;
update UTILISATEUR set address_id = 3 where USER_ID = 3;
update UTILISATEUR set address_id = 4 where USER_ID = 4;
update UTILISATEUR set address_id = 5 where USER_ID = 5;
update UTILISATEUR set address_id = 6 where USER_ID = 6;
--update UTILISATEUR set address_id = 7 where USER_ID = 7;


--INIT DEVIS (17 devis)
insert into DEVIS(REF, MONTANT_HT, TAXE, MONTANT_APPORT, APPORT_RECU, DUREE_ESTIMEE, DEVIS_ACCEPTE, DATE_DEVIS) values (1, 1200, 5.5, 586, true, 20, true, '2019-01-01');
insert into DEVIS(REF, MONTANT_HT, TAXE, MONTANT_APPORT, APPORT_RECU, DUREE_ESTIMEE, DEVIS_ACCEPTE, DATE_DEVIS) values (2, 510.25, 20, 152, false, 10, false, '2019-01-01');
insert into DEVIS(REF, MONTANT_HT, TAXE, MONTANT_APPORT, APPORT_RECU, DUREE_ESTIMEE, DEVIS_ACCEPTE, DATE_DEVIS) values (3, 1754.50, 20, 500, true, 150, false, '2019-01-01');
insert into DEVIS(REF, MONTANT_HT, TAXE, MONTANT_APPORT, APPORT_RECU, DUREE_ESTIMEE, DEVIS_ACCEPTE, DATE_DEVIS) values (4, 45.50, 20, 10, true, 8, false, '2019-01-01');
insert into DEVIS(REF, MONTANT_HT, TAXE, MONTANT_APPORT, APPORT_RECU, DUREE_ESTIMEE, DEVIS_ACCEPTE, DATE_DEVIS) values (5, 1452.50, 20, 155, true, 140, false, '2019-01-01');
insert into DEVIS(REF, MONTANT_HT, TAXE, MONTANT_APPORT, APPORT_RECU, DUREE_ESTIMEE, DEVIS_ACCEPTE, DATE_DEVIS) values (6, 25.50, 20, 10, false, 5, true, '2019-01-01');
insert into DEVIS(REF, MONTANT_HT, TAXE, MONTANT_APPORT, APPORT_RECU, DUREE_ESTIMEE, DEVIS_ACCEPTE, DATE_DEVIS) values (7, 114.50, 20, 41, true, 12, true, '2019-01-01');
insert into DEVIS(REF, MONTANT_HT, TAXE, MONTANT_APPORT, APPORT_RECU, DUREE_ESTIMEE, DEVIS_ACCEPTE, DATE_DEVIS) values (8, 981.50, 5.5, 221, false, 56, true, '2019-01-01');
insert into DEVIS(REF, MONTANT_HT, TAXE, MONTANT_APPORT, APPORT_RECU, DUREE_ESTIMEE, DEVIS_ACCEPTE, DATE_DEVIS) values (9, 910.50, 20, 115, false, 55, false, '2019-01-01');
insert into DEVIS(REF, MONTANT_HT, TAXE, MONTANT_APPORT, APPORT_RECU, DUREE_ESTIMEE, DEVIS_ACCEPTE, DATE_DEVIS) values (10, 123.50, 5.5, 10, true, 11, true, '2019-01-01');
insert into DEVIS(REF, MONTANT_HT, TAXE, MONTANT_APPORT, APPORT_RECU, DUREE_ESTIMEE, DEVIS_ACCEPTE, DATE_DEVIS) values (11, 456.50, 5.5, 55, true, 44, false, '2019-01-01');
insert into DEVIS(REF, MONTANT_HT, TAXE, MONTANT_APPORT, APPORT_RECU, DUREE_ESTIMEE, DEVIS_ACCEPTE, DATE_DEVIS) values (12, 789.50, 20, 77, false, 78, true, '2019-01-01');
insert into DEVIS(REF, MONTANT_HT, TAXE, MONTANT_APPORT, APPORT_RECU, DUREE_ESTIMEE, DEVIS_ACCEPTE, DATE_DEVIS) values (13, 8522.50, 20, 810, false, 554, false, '2019-01-01');
insert into DEVIS(REF, MONTANT_HT, TAXE, MONTANT_APPORT, APPORT_RECU, DUREE_ESTIMEE, DEVIS_ACCEPTE, DATE_DEVIS) values (14, 354.50, 20, 33, true, 33, true, '2019-01-01');
insert into DEVIS(REF, MONTANT_HT, TAXE, MONTANT_APPORT, APPORT_RECU, DUREE_ESTIMEE, DEVIS_ACCEPTE, DATE_DEVIS) values (15, 221.50, 5.5, 22, false, 21, true, '2019-01-01');
insert into DEVIS(REF, MONTANT_HT, TAXE, MONTANT_APPORT, APPORT_RECU, DUREE_ESTIMEE, DEVIS_ACCEPTE, DATE_DEVIS) values (16, 457.50, 20, 40, true, 48, true, '2019-01-01');
insert into DEVIS(REF, MONTANT_HT, TAXE, MONTANT_APPORT, APPORT_RECU, DUREE_ESTIMEE, DEVIS_ACCEPTE, DATE_DEVIS) values (17, 666.50, 5.5, 15, true, 74, true, '2019-01-01');


-- liaison client - devis
update DEVIS set CLIENT_ID = 2 where REF = 1;
update DEVIS set CLIENT_ID = 2 where REF = 2;
update DEVIS set CLIENT_ID = 2 where REF = 3;
update DEVIS set CLIENT_ID = 3 where REF = 4;
update DEVIS set CLIENT_ID = 3 where REF = 5;
update DEVIS set CLIENT_ID = 3 where REF = 6;
update DEVIS set CLIENT_ID = 3 where REF = 7;
update DEVIS set CLIENT_ID = 4 where REF = 8;
update DEVIS set CLIENT_ID = 4 where REF = 9;
update DEVIS set CLIENT_ID = 4 where REF = 10;
update DEVIS set CLIENT_ID = 4 where REF = 11;
update DEVIS set CLIENT_ID = 5 where REF = 12;
update DEVIS set CLIENT_ID = 5 where REF = 13;
update DEVIS set CLIENT_ID = 5 where REF = 14;
update DEVIS set CLIENT_ID = 5 where REF = 15;
update DEVIS set CLIENT_ID = 5 where REF = 16;
update DEVIS set CLIENT_ID = 5 where REF = 17;


-- ajout des travaux
insert into TRAVAUX(ID, ETAT, TYPE_OUVRAGE, DATE_DEBUT, TRAVAUX_PAYES, NBR_JOURS_EFFECTUES) values (1, 4, 0, '2019-01-01', false, 0);
insert into TRAVAUX(ID, ETAT, TYPE_OUVRAGE, DATE_DEBUT, TRAVAUX_PAYES, NBR_JOURS_EFFECTUES) values (2, 4, 1, '2019-01-11', false, 5);
insert into TRAVAUX(ID, ETAT, TYPE_OUVRAGE, DATE_DEBUT, TRAVAUX_PAYES, NBR_JOURS_EFFECTUES) values (3, 4, 2, '2019-01-13', true, 3);
insert into TRAVAUX(ID, ETAT, TYPE_OUVRAGE, DATE_DEBUT, TRAVAUX_PAYES, NBR_JOURS_EFFECTUES) values (4, 3, 3, '2019-02-15', true, 7);
insert into TRAVAUX(ID, ETAT, TYPE_OUVRAGE, DATE_DEBUT, TRAVAUX_PAYES, NBR_JOURS_EFFECTUES) values (5, 5, 4, '2019-03-11', true, 17);
insert into TRAVAUX(ID, ETAT, TYPE_OUVRAGE, DATE_DEBUT, TRAVAUX_PAYES, NBR_JOURS_EFFECTUES) values (6, 5, 2, '2019-05-05', true, 21);
insert into TRAVAUX(ID, ETAT, TYPE_OUVRAGE, DATE_DEBUT, TRAVAUX_PAYES, NBR_JOURS_EFFECTUES) values (7, 5, 2, '2019-05-08', false, 27);
insert into TRAVAUX(ID, ETAT, TYPE_OUVRAGE, DATE_DEBUT, TRAVAUX_PAYES, NBR_JOURS_EFFECTUES) values (8, 5, 0, '2019-05-15', true, 21);
insert into TRAVAUX(ID, ETAT, TYPE_OUVRAGE, DATE_DEBUT, TRAVAUX_PAYES, NBR_JOURS_EFFECTUES) values (9, 3, 3, '2019-05-18', true, 50);
insert into TRAVAUX(ID, ETAT, TYPE_OUVRAGE, DATE_DEBUT, TRAVAUX_PAYES, NBR_JOURS_EFFECTUES) values (10, 4, 3, '2019-06-05', false, 55);
insert into TRAVAUX(ID, ETAT, TYPE_OUVRAGE, DATE_DEBUT, TRAVAUX_PAYES, NBR_JOURS_EFFECTUES) values (11, 4, 3, '2019-07-01', true, 14);
insert into TRAVAUX(ID, ETAT, TYPE_OUVRAGE, DATE_DEBUT, TRAVAUX_PAYES, NBR_JOURS_EFFECTUES) values (12, 4, 4, '2019-07-03', true, 2);
insert into TRAVAUX(ID, ETAT, TYPE_OUVRAGE, DATE_DEBUT, TRAVAUX_PAYES, NBR_JOURS_EFFECTUES) values (13, 4, 4, '2019-08-05', false, 68);
insert into TRAVAUX(ID, ETAT, TYPE_OUVRAGE, DATE_DEBUT, TRAVAUX_PAYES, NBR_JOURS_EFFECTUES) values (14, 5, 1, '2019-09-15', true, 41);
insert into TRAVAUX(ID, ETAT, TYPE_OUVRAGE, DATE_DEBUT, TRAVAUX_PAYES, NBR_JOURS_EFFECTUES) values (15, 5, 0, '2019-10-12', false, 37);
insert into TRAVAUX(ID, ETAT, TYPE_OUVRAGE, DATE_DEBUT, TRAVAUX_PAYES, NBR_JOURS_EFFECTUES) values (16, 4, 0, '2019-11-12', true, 27);
insert into TRAVAUX(ID, ETAT, TYPE_OUVRAGE, DATE_DEBUT, TRAVAUX_PAYES, NBR_JOURS_EFFECTUES) values (17, 4, 2, '2019-11-21', false, 21);


update DEVIS set travaux_id = 1 where REF = 1;
update DEVIS set travaux_id = 2 where REF = 2;
update DEVIS set travaux_id = 3 where REF = 3;
update DEVIS set travaux_id = 4 where REF = 4;
update DEVIS set travaux_id = 5 where REF = 5;
update DEVIS set travaux_id = 6 where REF = 6;
update DEVIS set travaux_id = 7 where REF = 7;
update DEVIS set travaux_id = 8 where REF = 8;
update DEVIS set travaux_id = 9 where REF = 9;
update DEVIS set travaux_id = 10 where REF = 10;
update DEVIS set travaux_id = 11 where REF = 11;
update DEVIS set travaux_id = 12 where REF = 12;
update DEVIS set travaux_id = 13 where REF = 13;
update DEVIS set travaux_id = 14 where REF = 14;
update DEVIS set travaux_id = 15 where REF = 15;
update DEVIS set travaux_id = 16 where REF = 16;
update DEVIS set travaux_id = 17 where REF = 17;

COMMIT;