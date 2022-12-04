/*Count lessons per month regardless of type it is*/

CREATE VIEW count_lessons_month AS SELECT
	EXTRACT(month FROM datum) AS month,
	COUNT(*) FROM lesson WHERE EXTRACT(YEAR FROM datum) = '2022'
	GROUP BY EXTRACT(month FROM datum) ORDER BY EXTRACT(month FROM datum) ASC;



/*Show number of each type of lessons taught during every month*/
CREATE VIEW monthly_lesson_types AS
	SELECT type as lesson_type, COUNT(*) as number, EXTRACT(MONTH FROM datum) as month from lesson
	WHERE EXTRACT(YEAR FROM datum) = '2022'
	GROUP BY month, lesson_type;



/*Average number of ALL LESSON TYPES*/

CREATE VIEW lesons_avg_monthly AS SELECT
	COUNT(EXTRACT(YEAR FROM datum) = '2022')/12.0 AS annual_monthly_average FROM lesson;


/*Average number of EACH TYPE OF LESSON in a year*/

CREATE VIEW lesson_average_per_type AS
	SELECT type,
	COUNT(*)/12.0 AS number_of_lessons FROM lesson WHERE EXTRACT(YEAR FROM datum) = '2022'
	GROUP BY type;


/*List all instructors who have taken more than 3 lessons*/

CREATE VIEW lessons_limit_instructor AS SELECT
	instructor_id AS Instructor,
	COUNT(*) FROM lesson WHERE EXTRACT(MONTH FROM datum) = '10' AND EXTRACT(YEAR FROM datum) = '2022'
	GROUP BY Instructor HAVING COUNT(*) > 3 ORDER BY COUNT(*) DESC;

/*LAST ONE FINAL VERSION*/

CREATE MATERIALIZED VIEW next_weeks_ensembles AS
	SELECT ensemble_lesson.genre, to_char(lesson.datum, 'Day') as weekday, lesson.datum, lesson.tid,
	CASE
		WHEN current_amount_of_students = max_number_of_students THEN 'Full'
		WHEN current_amount_of_students = max_number_of_students - 1 THEN '1 seat available'
		WHEN current_amount_of_students = max_number_of_students - 2 THEN '2 seats available'
		END as seats_available
	FROM ensemble_lesson
	LEFT JOIN lesson
	ON ensemble_lesson.lesson_id = lesson.lesson_id
	WHERE date_trunc('week', lesson.datum) = date_trunc('week', now()) + interval '1 week'