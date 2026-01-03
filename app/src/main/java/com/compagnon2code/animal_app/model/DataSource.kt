package com.compagnon2code.animal_app.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblAnimal")
data class Animal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,  // valeur par défaut qui sera écrasée
    val name: String,
    val gender: String,
    val species: String,
    val description: String,
    val feeding: String,
    val note: String,
) {
}

fun getAnimalList(): List<Animal> {
    return mutableListOf(
        Animal(
            1,
            "Luna",
            "Female",
            "Cat",
            "A playful and curious kitten.",
            "Carnivore",
            "Loves chasing laser pointers."
        ),
        Animal(
            2,
            "Max",
            "Male",
            "Dog",
            "A loyal golden retriever.",
            "Omnivore",
            "Enjoys long walks in the park."
        ),
        Animal(
            3,
            "Kiki",
            "Female",
            "Parrot",
            "A talkative parrot with bright feathers.",
            "Herbivore",
            "Can mimic human speech."
        ),
        Animal(
            4,
            "Rocky",
            "Male",
            "Turtle",
            "A slow-moving but wise turtle.",
            "Herbivore",
            "Loves basking in the sun."
        ),
        Animal(
            5,
            "Bella",
            "Female",
            "Rabbit",
            "A fluffy rabbit with soft white fur.",
            "Herbivore",
            "Enjoys nibbling on carrots."
        ),
        Animal(
            6,
            "Simba",
            "Male",
            "Lion",
            "A majestic lion with a golden mane.",
            "Carnivore",
            "Prefers hunting at dawn."
        ),
        Animal(
            7,
            "Milo",
            "Male",
            "Hamster",
            "A tiny hamster who loves running on his wheel.",
            "Herbivore",
            "Very active at night."
        ),
        Animal(
            8,
            "Nala",
            "Female",
            "Elephant",
            "A gentle giant with a big heart.",
            "Herbivore",
            "Enjoys playing in the water."
        ),
        Animal(
            9,
            "Oscar",
            "Male",
            "Owl",
            "A wise owl that watches over the forest.",
            "Carnivore",
            "Has excellent night vision."
        ),
        Animal(
            10,
            "Zoe",
            "Female",
            "Dolphin",
            "An intelligent and friendly dolphin.",
            "Carnivore",
            "Loves to play with humans."
        ),Animal(
            11,
            "Nemo",
            "Male",
            "Fish",
            "An handicap and friendly fish.",
            "Herbivore",
            "Loves to play at Sidney."
        ),Animal(
            12,
            "Truche",
            "Female",
            "Ostrich",
            "An bird runs speedy.",
            "Omnivore",
            "Loves to put his head in the earth."
        ),Animal(
            13,
            "Kinder",
            "Male",
            "Penguin",
            "A bird who can't fly.",
            "Carnivore",
            "He slides on the ice !"
        ),Animal(
            14,
            "Chloe",
            "Female",
            "woodlice",
            "An intelligent and resistant insect.",
            "Omnivore",
            "Think ball !!!"
        ),Animal(
            15,
            "Zed",
            "Male",
            "Ant",
            "6 legs and 2 antennae ",
            "Omnivore",
            "An ant who loves to be with his friends."
        ),
    )
}