package com.example.stepwise.screens

data class ExercisePack(
    val id: String,
    val title: String,
    val description: String,
    val workouts: List<WorkoutDetail>
)

data class Article(
    val id: String,
    val title: String,
    val summary: String,
    val content: String,
    val sourceName: String,
    val link: String
)

object ContentCatalog {
    val exercisePacks = listOf(
        ExercisePack("abs", "Abs workout", "Core strength and stability", listOf(
            WorkoutDetail("Crunches", "3 × 15", "40 kcal"), WorkoutDetail("Leg Raises", "3 × 12", "45 kcal"),
            WorkoutDetail("Plank", "3 × 30 sec", "25 kcal"), WorkoutDetail("Bicycle Crunch", "3 × 20", "35 kcal"),
            WorkoutDetail("Mountain Climbers", "3 × 20", "50 kcal"), WorkoutDetail("Heel Touches", "3 × 20", "30 kcal"),
            WorkoutDetail("Toe Touches", "3 × 15", "35 kcal"), WorkoutDetail("Russian Twists", "3 × 20", "40 kcal"),
            WorkoutDetail("Flutter Kicks", "3 × 20", "30 kcal"), WorkoutDetail("V-Ups", "3 × 12", "40 kcal")
        )),
        ExercisePack("legs", "Leg workout", "Lower-body strength and balance", listOf(
            WorkoutDetail("Squats", "3 × 15", "50 kcal"), WorkoutDetail("Lunges", "3 × 12", "40 kcal"),
            WorkoutDetail("Glute Bridges", "3 × 15", "35 kcal"), WorkoutDetail("Calf Raises", "3 × 20", "25 kcal"),
            WorkoutDetail("Wall Sit", "3 × 30 sec", "30 kcal"), WorkoutDetail("Jump Squats", "3 × 10", "45 kcal"),
            WorkoutDetail("Donkey Kicks", "3 × 15", "30 kcal"), WorkoutDetail("Side Lunges", "3 × 12", "35 kcal"),
            WorkoutDetail("Hamstring Curls", "3 × 12", "30 kcal"), WorkoutDetail("Bulgarian Split Squat", "3 × 10", "45 kcal")
        )),
        ExercisePack("arms", "Arm workout", "Upper-body strength and control", listOf(
            WorkoutDetail("Push-ups", "3 × 12", "45 kcal"), WorkoutDetail("Tricep Dips", "3 × 12", "40 kcal"),
            WorkoutDetail("Bicep Curls", "3 × 15", "35 kcal"), WorkoutDetail("Hammer Curls", "3 × 12", "30 kcal"),
            WorkoutDetail("Diamond Push-ups", "3 × 8", "40 kcal"), WorkoutDetail("Shoulder Taps", "3 × 15", "25 kcal"),
            WorkoutDetail("Plank Up-Downs", "3 × 12", "40 kcal"), WorkoutDetail("Resistance Band Pulls", "3 × 15", "30 kcal"),
            WorkoutDetail("Arm Circles", "3 × 30 sec", "20 kcal"), WorkoutDetail("Incline Push-ups", "3 × 12", "35 kcal")
        )),
        ExercisePack("chest", "Chest workout", "Pressing strength for the upper body", listOf(
            WorkoutDetail("Wide Push-ups", "3 × 12", "40 kcal"), WorkoutDetail("Incline Push-ups", "3 × 12", "35 kcal"),
            WorkoutDetail("Bench Press", "3 × 10", "60 kcal"), WorkoutDetail("Chest Fly", "3 × 12", "45 kcal"),
            WorkoutDetail("Decline Push-ups", "3 × 10", "40 kcal"), WorkoutDetail("Dumbbell Press", "3 × 12", "55 kcal"),
            WorkoutDetail("Cable Crossovers", "3 × 15", "45 kcal"), WorkoutDetail("Clap Push-ups", "3 × 8", "50 kcal"),
            WorkoutDetail("Pec Deck", "3 × 12", "40 kcal"), WorkoutDetail("Chest Dips", "3 × 10", "50 kcal")
        )),
        ExercisePack("back", "Back workout", "Posture, pulling, and posterior-chain work", listOf(
            WorkoutDetail("Superman", "3 × 15 sec", "25 kcal"), WorkoutDetail("Bent-over Rows", "3 × 12", "45 kcal"),
            WorkoutDetail("Reverse Fly", "3 × 12", "40 kcal"), WorkoutDetail("Deadlift", "3 × 10", "60 kcal"),
            WorkoutDetail("Cat-Cow", "3 × 10", "20 kcal"), WorkoutDetail("Back Extensions", "3 × 10", "35 kcal"),
            WorkoutDetail("Lat Pull-down", "3 × 12", "45 kcal"), WorkoutDetail("Good Mornings", "3 × 12", "35 kcal"),
            WorkoutDetail("T-Bar Row", "3 × 10", "50 kcal"), WorkoutDetail("Hyperextensions", "3 × 12", "40 kcal")
        )),
        ExercisePack("full-body", "Full-body workout", "A balanced cardio and strength circuit", listOf(
            WorkoutDetail("Burpees", "3 × 10", "60 kcal"), WorkoutDetail("Jumping Jacks", "3 × 30 sec", "30 kcal"),
            WorkoutDetail("High Knees", "3 × 30 sec", "35 kcal"), WorkoutDetail("Plank Jumps", "3 × 15", "40 kcal"),
            WorkoutDetail("Squat to Press", "3 × 12", "50 kcal"), WorkoutDetail("Skater Jumps", "3 × 20", "40 kcal"),
            WorkoutDetail("Mountain Climbers", "3 × 20", "45 kcal"), WorkoutDetail("Bear Crawl", "3 × 20 sec", "35 kcal"),
            WorkoutDetail("Lateral Hops", "3 × 20", "30 kcal"), WorkoutDetail("Shadow Boxing", "3 × 1 min", "55 kcal")
        ))
    )

    val articles = listOf(
        Article("healthy-eating", "Beginner’s Guide to Healthy Eating", "Balanced meals, portion control, and daily habits for steady energy.", "Start with regular meals that include vegetables or fruit, a protein source, whole grains, and healthy fats. Small, repeatable changes are more sustainable than restrictive rules.", "Healthline", "https://www.healthline.com/nutrition/healthy-eating-for-beginners"),
        Article("snacks", "Healthy Snack Ideas", "Simple nutritious snacks for busy days.", "Choose snacks that combine fibre and protein, such as fruit with yogurt, nuts, oats, or a smoothie. Preparing a few options in advance can make balanced choices easier.", "Healthline", "https://www.healthline.com/nutrition/29-healthy-snacks-for-weight-loss"),
        Article("walking", "Health Benefits of Walking", "How regular walking can support heart health and wellbeing.", "Walking is an accessible way to build daily movement. Start at a comfortable pace, add time gradually, and choose routes that make the habit enjoyable.", "Wikipedia", "https://en.wikipedia.org/wiki/Walking#Health_benefits"),
        Article("weight-loss", "The Science Behind Weight Loss", "An introduction to energy balance, metabolism, and gradual change.", "Long-term weight management is supported by consistent eating, activity, sleep, and routines. Avoid extreme changes; focus instead on habits you can repeat.", "Healthline", "https://www.healthline.com/nutrition/how-to-lose-weight-as-fast-as-possible"),
        Article("full-body", "Full-Body Exercise Guide", "Strength, cardio, and functional movements for a balanced routine.", "A well-rounded routine can include pushing, pulling, squatting, hinging, core work, and light cardio. Adjust exercise selection and intensity to your experience.", "Wikipedia", "https://en.wikipedia.org/wiki/Physical_fitness"),
        Article("sleep", "Why Sleep Matters for Fitness", "Recovery, energy, and training consistency begin with sleep.", "Quality sleep helps recovery and day-to-day energy. A regular wind-down routine, consistent sleep time, and a comfortable environment can support better rest.", "Sleep Foundation", "https://www.sleepfoundation.org/sleep-hygiene"),
        Article("immunity", "Healthy Foods That Support Immunity", "Nutrition basics that support overall health.", "Include a variety of colourful fruits and vegetables, whole grains, protein sources, and adequate fluids. No single food replaces a balanced overall pattern.", "Wikipedia", "https://en.wikipedia.org/wiki/Immune_system#Diet_and_nutrition"),
        Article("strength", "Strength Training for Beginners", "A practical introduction to safe strength training.", "Begin with simple movements, controlled form, and manageable resistance. Rest between sessions and progress slowly as movements become comfortable.", "Nerd Fitness", "https://www.nerdfitness.com/blog/strength-training-101/"),
        Article("water", "How Much Water Should You Drink Daily?", "Everyday hydration tips.", "Hydration needs vary with activity, climate, and individual health. Drink regularly, use thirst as one guide, and increase fluids around exercise or heat.", "Wikipedia", "https://en.wikipedia.org/wiki/Drinking_water"),
        Article("workout-routine", "Beginner Workout Routine", "A step-by-step way to establish an exercise habit.", "Choose a small number of simple movements, schedule a realistic number of sessions, and build consistency before increasing volume or intensity.", "wikiHow", "https://www.wikihow.com/Start-Working-Out")
    )

    fun exercisePack(id: String) = exercisePacks.firstOrNull { it.id == id }
    fun article(id: String) = articles.firstOrNull { it.id == id }
}
