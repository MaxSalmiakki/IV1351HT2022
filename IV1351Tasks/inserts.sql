/*Students*/
INSERT INTO person VALUES('200204049552', 'Maj-Britt', 'Karlsson', 'Maj-Britt.Karlsson@gmail.com', '+4629343081', '2002', 'Sandviken', '88797', 'Kvarnvägen');
INSERT INTO person VALUES('200105289750', 'Lilly', 'Andersson', 'Lilly.Andersson@gmail.com', '+4623043315', '2001', 'Mölndal', '74293', 'Åkertorget');
INSERT INTO person VALUES('200110243090', 'Ingegerd', 'Andersson', 'Ingegerd.Andersson@gmail.com', '+4698700424', '2001', 'Skellefteå', '46968', 'Skogsstigen');
INSERT INTO person VALUES('199909207595', 'Gustaf', 'Andersson', 'Gustaf.Andersson@gmail.com', '+4623614919', '1999', 'Luleå', '80223', 'Ängsvägen');
INSERT INTO person VALUES('200303097750', 'Karl', 'Lindahl', 'Karl.Lindahl@gmail.com', '+4659175334', '2003', 'Mölndal', '24393', 'Furutorget');
INSERT INTO person VALUES('200110117750', 'Sten', 'Gustafsson', 'Sten.Gustafsson@gmail.com', '+4698201077', '2001', 'Nyköping', '55504', 'Trädgårdsgatan');
INSERT INTO person VALUES('199808116134', 'Åke', 'Jansson', 'Åke.Jansson@gmail.com', '+4618543268', '1998', 'Sandviken', '80524', 'Skogsstigen');

/*Paretns* people parts*/
INSERT INTO person VALUES('196304178347', 'Linnéa', 'Andersson', 'Linnéa.Andersson@gmail.com', '+4634452124', '1963', 'Trollhättan', '33077', 'Kvarnvägen');
INSERT INTO person VALUES('195152083334', 'Birgitta', 'Sandberg', 'Birgitta.Sandberg@gmail.com', '+4697021318', '1951', 'Trollhättan', '85329', 'Kyrkvägen');
INSERT INTO person VALUES('196311105711', 'Folke', 'Gustafsson', 'Folke.Gustafsson@gmail.com', '+4686621246', '1963', 'Stockholm', '60743', 'Villavägen');

/*Teachers*/
INSERT INTO person VALUES('195912148234', 'Matilda', 'Blomqvist', 'Matilda.Blomqvist@gmail.com', '+4665153159', '1959', 'Motala', '42346', 'Stationsvägen');
INSERT INTO person VALUES('196713039129', 'Anna', 'Ekman', 'Anna.Ekman@gmail.com', '+4643625584', '1967', 'Landskrona', '76014', 'Skolgatan');


/*The Anderssons*/
INSERT INTO student VALUES('200110243090ST', '199909207595ST', '196304178347', '200110243090');
INSERT INTO student VALUES('199909207595ST', '200105289750ST', '196304178347', '199909207595');
INSERT INTO student VALUES('200105289750ST', '199909207595ST', '196304178347', '200105289750');

/*MAJ-BRIT*/
INSERT INTO student VALUES('200204049552ST', '199808116134ST', '195152083334', '200204049552');

/*Åke*/
INSERT INTO student VALUES('199808116134ST', '200204049552ST', '195152083334', '199808116134');

/*Karl*/
INSERT INTO student VALUES('200303097750ST', '200110117750ST', '196311105711', '200303097750');

/*Sten*/
INSERT INTO student VALUES('200110117750ST', '200303097750ST', '196311105711', '200110117750');



/*Insert into teachers' table*/
INSERT INTO instructor VALUES('195912148234TR', '1', '195912148234');
INSERT INTO instructor VALUES('196713039129TR', '0', '196713039129');


/*Lesson */
INSERT INTO lesson VALUES('20221022143034', '300', 'Ensemble', '195912148234TR', '2022-10-22', '14:30');
INSERT INTO lesson VALUES('20221023150034', '280', 'Ensemble', '195912148234TR', '2022-10-23', '15:00');
INSERT INTO lesson VALUES('20221024140029', '300', 'Ensemble', '196713039129TR', '2022-10-24', '14:00');

/*Seminar 5 inserts*/

INSERT INTO lesson VALUES('20230118150034', 285, 'Ensemble', '195912148234TR', '2023-01-18', '15:00:00');
INSERT INTO ensemble_lesson VALUES('jazz', 'ens003', 9, 4, '20230118150034', 8);

INSERT INTO lesson VALUES('20230119150034', 300, 'Ensemble', '195912148234TR', '2023-01-19', '15:00:00');
INSERT INTO ensemble_lesson VALUES('blues', 'ens003', 8, 2, '20230119150034', 6);

INSERT INTO lesson VALUES('20230120140029', 295, 'Ensemble', '196713039129TR', '2023-01-20', '14:00:00');
INSERT INTO ensemble_lesson VALUES('blues', 'ens002', 6, 3, '20230120140029', 6);

INSERT INTO lesson VALUES('20230112140029', 285, 'Ensemble', '196713039129TR', '2023-01-12', '14:00:00');
INSERT INTO ensemble_lesson VALUES('blues', 'ens002', 6, 3, '20230112140029', 6);



/*New lessons for testing purpose*/
INSERT INTO lesson VALUES('20221101123034', '310', 'Ensemble', '195912148234TR', '2022-11-01', '12:30');
INSERT INTO lesson VALUES('20221102110034', '290', 'Ensemble', '195912148234TR', '2022-11-02', '11:00');
INSERT INTO lesson VALUES('20221031130029', '285', 'Ensemble', '196713039129TR', '2022-10-31', '13:00');


INSERT INTO lesson VALUES('20221021130029', '300', 'Group', '196713039129TR', '2022-10-21', '13:00');
INSERT INTO lesson VALUES('20221023110029', '320', 'Group', '196713039129TR', '2022-10-23', '11:00');
INSERT INTO lesson VALUES('20221024130034', '350', 'Group', '195912148234TR', '2022-10-24', '13:00');
INSERT INTO lesson VALUES('20221124130034', '350', 'Group', '195912148234TR', '2022-11-24', '13:00');


INSERT INTO lesson VALUES('20221020123034', '600', 'Individual', '195912148234TR', '2022-10-20', '12:30');
INSERT INTO lesson VALUES('20221021113034', '500', 'Individual', '195912148234TR', '2022-10-21', '11:30');
INSERT INTO lesson VALUES('20221025123029', '520', 'Individual', '196713039129TR', '2022-10-25', '12:30');

INSERT INTO lesson VALUES('20221207150034', 290, 'Ensemble', '195912148234TR', '2022-12-07', '15:00:00');
INSERT INTO ensemble_lesson VALUES('jazz', 'ens001', 8, 3, '20221207150034', 6);

INSERT INTO lesson VALUES('20221208150034', 285, 'Ensemble', '195912148234TR', '2022-12-08', '15:00:00');
INSERT INTO ensemble_lesson VALUES('blues', 'ens003', 9, 4, '20221208150034', 8);

INSERT INTO lesson VALUES('20221209140029', 295, 'Ensemble', '196713039129TR', '2022-12-09', '14:00:00');
INSERT INTO ensemble_lesson VALUES('blues', 'ens002', 7, 3, '20221209140029', 7);

INSERT INTO lesson VALUES('20221214150034', 290, 'Ensemble', '195912148234TR', '2022-12-14', '15:00:00');
INSERT INTO ensemble_lesson VALUES('jazz', 'ens001', 9, 3, '20221214150034', 7);

INSERT INTO lesson VALUES('20221215140029', 300, 'Ensemble', '196713039129TR', '2022-12-15', '14:00:00');
INSERT INTO ensemble_lesson VALUES('blues', 'ens003', 6, 3, '20221215140029', 5);

INSERT INTO lesson VALUES('20221216140029', 295, 'Ensemble', '196713039129TR', '2022-12-16', '14:00:00');
INSERT INTO ensemble_lesson VALUES('blues', 'ens002', 7, 3, '20221216140029', 7);

INSERT INTO lesson VALUES('20221201140029', 285, 'Ensemble', '196713039129TR', '2022-12-01', '14:00:00');
INSERT INTO ensemble_lesson VALUES('jazz', 'ens002', 7, 3, '20221201140029', 7);




/*Individual lessons*/
INSERT INTO individual_lesson VALUES('beginner', 'trambone', '20221020123034');
INSERT INTO individual_lesson VALUES('intermediate', 'synth-piano', '20221021113034');
INSERT INTO individual_lesson VALUES('advanced', 'saxophone', '20221025123029');

/*Ensemble lessons*/
INSERT INTO ensemble_lesson VALUES('jazz', 'ens001', '9', '2', '20221022143034' , '0');
INSERT INTO ensemble_lesson VALUES('blues', 'ens002', '7', '2', '20221102110034' , '5');
INSERT INTO ensemble_lesson VALUES('rock', 'ens003', '5', '1', '20221031130029' , '5');

/*New enesmbles for testing purpose*/
INSERT INTO ensemble_lesson VALUES('jazz', 'ens001', '9', '2', '20221101123034' , '7');
INSERT INTO ensemble_lesson VALUES('blues', 'ens002', '6', '2', '20221023150034' , '5');
INSERT INTO ensemble_lesson VALUES('rock', 'ens003', '5', '1', '20221024140029' , '5');

/*Group lessons*/
INSERT INTO group_lesson VALUES('intermediate', 'synth-piano', '6', '2', '20221021130029' , '3');
INSERT INTO group_lesson VALUES('advanced', 'saxophone', '4', '2', '20221023110029' , '1');
INSERT INTO group_lesson VALUES('beinner', 'synth-piano', '8', '2', '20221024130034' , '7');
INSERT INTO group_lesson VALUES('beinner', 'synth-piano', '10', '2', '20221124130034' , '7');

/*Conceptual instrument*/
INSERT INTO conceptual_instrument VALUES('trambone');
INSERT INTO conceptual_instrument VALUES('synth-piano');
INSERT INTO conceptual_instrument VALUES('saxophone');
INSERT INTO conceptual_instrument VALUES('guitar');

/*Instrument*/
INSERT INTO instrument VALUES('SNP01', 'Yamaha', 'synth-piano', '1', '200');
INSERT INTO instrument VALUES('SNP02', 'Yamaha', 'synth-piano', '1', '200');
INSERT INTO instrument VALUES('SNP03', 'Roland', 'synth-piano', '1', '250');

INSERT INTO instrument VALUES('GTR01', 'Yamaha', 'guitar', '1', '300');
INSERT INTO instrument VALUES('GTR02', 'Yamaha', 'guitar', '1', '300');
INSERT INTO instrument VALUES('GTR03', 'Roland', 'guitar', '1', '350');

INSERT INTO instrument VALUES('SPH01', 'Taylor', 'saxophone', '1', '500');
INSERT INTO instrument VALUES('SPH02', 'Taylor', 'saxophone', '1', '500');
INSERT INTO instrument VALUES('SPH03', 'Roland', 'saxophone', '1', '450');

INSERT INTO instrument VALUES('TBN01', 'Roland', 'trambone', '1', '450');
INSERT INTO instrument VALUES('TBN02', 'Taylor', 'trambone', '1', '550');
INSERT INTO instrument VALUES('TBN03', 'Taylor', 'trambone', '1', '550');

INSERT INTO student_payment VALUES('200110243090ST20221025', '200110243090ST', '10');
INSERT INTO student_payment VALUES('199909207595ST20221025', '199909207595ST', '15');
INSERT INTO student_payment VALUES('200105289750ST20221025', '200105289750ST', '15');
INSERT INTO student_payment VALUES('200204049552ST20221025', '200204049552ST', '15');
INSERT INTO student_payment VALUES('199808116134ST20221025', '199808116134ST', '15');
INSERT INTO student_payment VALUES('200303097750ST20221025', '200303097750ST', '15');
INSERT INTO student_payment VALUES('200110117750ST20221025', '200110117750ST', '10');