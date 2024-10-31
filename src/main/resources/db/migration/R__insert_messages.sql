INSERT INTO messages
VALUES (1, 'Заявка успешно обновлена!', 'Қолданба сәтті жаңартылды!')
ON DUPLICATE KEY UPDATE name_ru = 'Заявка успешно обновлена!',
                        name_kz = 'Қолданба сәтті жаңартылды!';

INSERT INTO messages
VALUES (2, 'Пользователь с такой почтой уже существует!', 'Мұндай хаттары бар пайдаланушы бұрыннан бар!')
ON DUPLICATE KEY UPDATE name_ru = 'Пользователь с такой почтой уже существует!',
                        name_kz = 'Мұндай хаттары бар пайдаланушы бұрыннан бар!';

INSERT INTO messages
VALUES (3, 'Файл сохранен.', 'Файл сақталады.')
ON DUPLICATE KEY UPDATE name_ru = 'Файл сохранен.',
                        name_kz = 'Файл сақталады.';

INSERT INTO messages
VALUES (4, 'Файл не найден!', 'Файл табылмады!')
ON DUPLICATE KEY UPDATE name_ru = 'Файл не найден!',
                        name_kz = 'Файл табылмады!';

INSERT INTO messages
VALUES (5, 'Доступ к файлу запрещен.', 'Файлға кіруге тыйым салынады.')
ON DUPLICATE KEY UPDATE name_ru = 'Доступ к файлу запрещен.',
                        name_kz = 'Файлға кіруге тыйым салынады.';

INSERT INTO messages
VALUES (6, 'Права успешно установлены!', 'Рұқсаттар сәтті орнатылды!')
ON DUPLICATE KEY UPDATE name_ru = 'Права успешно установлены!',
                        name_kz = 'Рұқсаттар сәтті орнатылды!';

INSERT INTO messages
VALUES (7, 'Перезапуск всех задач в системе завершен.', 'Жүйедегі барлық тапсырмаларды қайта іске қосу аяқталды.')
ON DUPLICATE KEY UPDATE name_ru = 'Перезапуск всех задач в системе завершен.',
                        name_kz = 'Жүйедегі барлық тапсырмаларды қайта іске қосу аяқталды.';

INSERT INTO messages
VALUES (8, 'Успешно остановлены все задачи в системе.', 'Жүйедегі барлық тапсырмалар сәтті тоқтатылды.')
ON DUPLICATE KEY UPDATE name_ru = 'Успешно остановлены все задачи в системе.',
                        name_kz = 'Жүйедегі барлық тапсырмалар сәтті тоқтатылды.';

INSERT INTO messages
VALUES (9, 'Задача успешна запущена!', 'Тапсырма сәтті іске қосылды!')
ON DUPLICATE KEY UPDATE name_ru = 'Задача успешна запущена!',
                        name_kz = 'Тапсырма сәтті іске қосылды!';

INSERT INTO messages
VALUES (10, 'Не удалось найти задачу в базе данных!', 'Тапсырма дерекқордан табылмады!')
ON DUPLICATE KEY UPDATE name_ru = 'Не удалось найти задачу в базе данных!',
                        name_kz = 'Тапсырма дерекқордан табылмады!';

INSERT INTO messages
VALUES (11, 'Задача успешна остановлена!', 'Тапсырма сәтті тоқтатылды!')
ON DUPLICATE KEY UPDATE name_ru = 'Задача успешна остановлена!',
                        name_kz = 'Тапсырма сәтті тоқтатылды!';

INSERT INTO messages
VALUES (6, 'Права успешно установлены!', 'Рұқсаттар сәтті орнатылды!')
ON DUPLICATE KEY UPDATE name_ru = 'Права успешно установлены!',
                        name_kz = 'Рұқсаттар сәтті орнатылды!';

INSERT INTO messages
VALUES (6, 'Права успешно установлены!', 'Рұқсаттар сәтті орнатылды!')
ON DUPLICATE KEY UPDATE name_ru = 'Права успешно установлены!',
                        name_kz = 'Рұқсаттар сәтті орнатылды!';
