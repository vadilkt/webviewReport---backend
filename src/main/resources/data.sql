


INSERT INTO transmission_sheet (destination, provenance, date_emission, num_bordereau, nbre_courrier)
VALUES ('City A', 'City B', '2023-01-01', 'B12345', 5);


INSERT INTO courier (courrier_date, dossier_number, transmission_sheet_id, expediteur, num_courrier, objet)
VALUES ('C123', '2023-01-02', 'D123', 1, 'Expéditeur 1', 'C001', 'Document');


INSERT INTO attachment (file_name, courier_id)
VALUES ('document1.pdf', 1);

