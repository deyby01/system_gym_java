-- Fixture data for system_gym database.
-- Run this script once after creating the database and table.
-- Usage (MySQL terminal): source /path/to/sql/fixtures.sql
--   or: mysql -u gym_user -p system_gym < sql/fixtures.sql
--
-- To reset last_used for testing (makes all exercises available again):
--   UPDATE exercises SET last_used = 0;

USE system_gym;

INSERT INTO exercises (name, type, intensity_level, estimated_time, description, last_used) VALUES
-- Beginner - Strength (4)
('Bodyweight Squat',       'Strength', 'Beginner', 20.0, 'Basic squat using only body weight, focus on form and depth.', 0),
('Wall Push-Up',           'Strength', 'Beginner', 15.0, 'Push-up against a wall, great for building initial upper body strength.', 0),
('Glute Bridge',           'Strength', 'Beginner', 15.0, 'Lying hip raise to activate glutes and lower back.', 0),
('Standing Dumbbell Curl', 'Strength', 'Beginner', 15.0, 'Bicep curl with light dumbbells, standing with controlled movement.', 0),

-- Beginner - Cardio (4)
('Brisk Walk',             'Cardio',   'Beginner', 30.0, 'Steady-pace walk at a speed that slightly raises heart rate.', 0),
('Low-Impact Jumping Jacks','Cardio',  'Beginner', 20.0, 'Jumping jacks without leaving the ground, joint-friendly.', 0),
('Stationary March',       'Cardio',   'Beginner', 15.0, 'March in place lifting knees to hip height.', 0),
('Step Touch',             'Cardio',   'Beginner', 20.0, 'Side-to-side step touch, low intensity warm-up or cooldown.', 0),

-- Intermediate - Strength (4)
('Dumbbell Lunges',        'Strength', 'Intermediate', 25.0, 'Alternating forward lunges holding a dumbbell in each hand.', 0),
('Push-Up',                'Strength', 'Intermediate', 20.0, 'Standard push-up maintaining a straight body line throughout.', 0),
('Dumbbell Row',           'Strength', 'Intermediate', 25.0, 'Single-arm row with a dumbbell, targets back and biceps.', 0),
('Dumbbell Shoulder Press','Strength', 'Intermediate', 25.0, 'Overhead press with dumbbells, seated or standing.', 0),

-- Intermediate - Cardio (4)
('Cycling',                'Cardio',   'Intermediate', 40.0, 'Moderate-intensity cycling on a stationary or road bike.', 0),
('Jump Rope',              'Cardio',   'Intermediate', 20.0, 'Continuous jump rope at a steady pace.', 0),
('Swimming Laps',          'Cardio',   'Intermediate', 35.0, 'Freestyle laps in a pool maintaining consistent pace.', 0),
('Elliptical Trainer',     'Cardio',   'Intermediate', 30.0, 'Low-impact full-body cardio on elliptical machine at moderate resistance.', 0),

-- Advanced - Strength (4)
('Barbell Back Squat',     'Strength', 'Advanced', 40.0, 'Heavy squat with barbell on upper back, full range of motion.', 0),
('Weighted Pull-Up',       'Strength', 'Advanced', 30.0, 'Pull-up with added weight plate via belt.', 0),
('Romanian Deadlift',      'Strength', 'Advanced', 35.0, 'Hip-hinge movement targeting hamstrings and glutes with heavy load.', 0),
('Bench Press',            'Strength', 'Advanced', 35.0, 'Flat bench press with barbell, full range chest and tricep activation.', 0),

-- Advanced - Cardio (4)
('HIIT Sprint Intervals',  'Cardio',   'Advanced', 25.0, '30s all-out sprint followed by 30s rest, repeated 10 times.', 0),
('Rowing Machine',         'Cardio',   'Advanced', 30.0, 'Full-body cardio on rowing ergometer at high intensity.', 0),
('Stair Climbing',         'Cardio',   'Advanced', 30.0, 'Continuous stair climbing at a fast pace for sustained cardio output.', 0),
('Hill Sprints',           'Cardio',   'Advanced', 25.0, 'Repeated uphill sprints with walk-back recovery, high cardiovascular demand.', 0),

-- High Performance - Strength (4)
('Power Clean',            'Strength', 'High Performance', 45.0, 'Olympic lift from floor to front rack position, full explosive power.', 0),
('Snatch',                 'Strength', 'High Performance', 45.0, 'Olympic lift from floor to overhead in one movement.', 0),
('Barbell Hip Thrust',     'Strength', 'High Performance', 35.0, 'Heavy hip thrust with barbell across hips for maximum glute activation.', 0),
('Clean and Jerk',         'Strength', 'High Performance', 45.0, 'Two-phase Olympic lift combining a power clean with an overhead jerk.', 0),

-- High Performance - Cardio (4)
('VO2 Max Intervals',      'Cardio',   'High Performance', 30.0, 'Short max-effort intervals at 90-100% VO2 max with full recovery.', 0),
('CrossFit WOD',           'Cardio',   'High Performance', 40.0, 'High-intensity mixed-modal workout combining cardio and strength.', 0),
('Assault Bike Intervals', 'Cardio',   'High Performance', 20.0, 'All-out efforts on assault bike, full body, extremely high output.', 0),
('Triathlon Sprint',       'Cardio',   'High Performance', 60.0, 'Short triathlon simulation: swim, bike, and run at race pace.', 0);

SELECT CONCAT('Fixtures loaded correctly — ', COUNT(*), ' exercises in database.') AS '' FROM exercises;
