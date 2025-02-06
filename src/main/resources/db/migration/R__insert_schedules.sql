INSERT INTO schedules
VALUES (1, 'monthly-report', 1, '0 0 0 1 * *')
ON DUPLICATE KEY UPDATE schedule_name   = 'monthly-report',
                        enable          = 1,
                        cron_expression = '0 0 0 1 * *';

INSERT INTO schedules
VALUES (2, 'exchange-rates', 1, '0 0 0 * * *')
ON DUPLICATE KEY UPDATE schedule_name   = 'exchange-rates',
                        enable          = 1,
                        cron_expression = '0 0 0 * * *';
