INSERT INTO forms (form_time) VALUES
     ('8/9/2021, 1:45:20 PM'),
     ('8/8/2021, 3:36:27 PM');

INSERT INTO form_info (form_id, title, subtitle) VALUES
    (1, 'Get a Job Offer', 'My Contact Info'),
    (2, 'Request Your New Movie', 'Studio Information');

INSERT INTO info (form_info_id, action, icon, id, text, type) VALUES
(1, '', 'pos', 1, 'Longway 42, 1234 Oslo', 'Address'),
(1, '', 'phone', 2, '123 45 678', 'Phone'),
(1, '', 'email', 3, 'getajob@today.com', 'Email'),
(1, '', 'corpo', 4, '@job-today-facebook', 'Social'),
(2, '', 'pos', 1, 'Longway 42, 1234 Oslo', 'Address'),
(2, '', 'phone', 2, '123 45 678', 'Phone'),
(2, '', 'email', 3, 'getajob@today.com', 'Email'),
(2, '', 'corpo', 4, '@job-today-facebook', 'Social');

INSERT INTO form_items (form_id, description, error, id, needed, options, rank, title, type, validation) VALUES
(1, 'ENTER FULL NAME', 'Only letters and number, this is required', 0, 1, '', 0, 'ENTER FULL NAME', 'text', 'empty'),
(1, 'ENTER EMAIL', 'A valid email is required.', 1, 1, '', 1, 'ENTER EMAIL', 'text', 'email'),
(1, 'WHY DO YOU NEED A JOB? PLEASE DESCRIBE IN LESS THAN 250 WORDS', 'Only letters and number, this is required', 2, 1, '', 2, 'WHY DO YOU NEED A JOB? PLEASE DESCRIBE IN LESS THAN 250 WORDS', 'textarea', 'empty'),
(1, 'SUBMIT APPLICATION', '', 3, 0, '', 3, 'ENTER FULL NAME', 'button', 'button'),
(2, 'ENTER MOVIE NAME', 'Only letters and number, this is required', 0, 1, '', 0, 'ENTER FULL NAME', 'text', 'empty'),
(2, 'WHY DO YOU WANT THIS MOVIE?', 'Only letters and number', 1, 0, '', 1, 'WHY DO YOU WANT THIS MOVIE?', 'textarea', 'empty'),
(2, 'SEND MOVIE REQUEST', '', 2, 0, '', 2, 'SEND MOVIE REQUEST', 'button', 'button');