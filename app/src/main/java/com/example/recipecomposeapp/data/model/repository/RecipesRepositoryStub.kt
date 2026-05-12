package com.example.recipecomposeapp.data.model.repository

import com.example.recipecomposeapp.data.model.CategoryDto
import com.example.recipecomposeapp.data.model.IngredientDto
import com.example.recipecomposeapp.data.model.RecipeDto

object RecipesRepositoryStub : RecipesRepository {
    private val categoryList = listOf(
        CategoryDto(
            0,
            "Бургеры",
            "Рецепты всех популярных видов бургеров",
            "burger.png"
        ),
        CategoryDto(
            1,
            "Десерты",
            "Самые вкусные рецепты десертов специально для вас",
            "dessert.png"
        ),
        CategoryDto(
            2,
            "Пицца",
            "Пицца на любой вкус и цвет. Лучшая подборка для тебя",
            "pizza.png"
        ),
        CategoryDto(
            3,
            "Рыба",
            "Печеная, жареная, сушеная, любая рыба на твой вкус",
            "fish.png"
        ),
        CategoryDto(
            4,
            "Супы",
            "От классики до экзотики: мир в одной тарелке",
            "soup.png"
        ),
        CategoryDto(
            5,
            "Салаты",
            "Хрустящий калейдоскоп под соусом вдохновения",
            "salad.png"
        )
    )
    private val burgerRecipesList = listOf(
        RecipeDto(
            0,
            "Классический бургер с говядиной",
            listOf(
                IngredientDto(
                    "0.5",
                    "кг",
                    "Говяжий фарш"
                ),
                IngredientDto(
                    "1",
                    "шт",
                    "Луковица, мелко нарезанная"
                ),
                IngredientDto(
                    "2",
                    "зубч",
                    "Чеснок, измельченный"
                ),
                IngredientDto(
                    "4",
                    "шт",
                    "Булочки для бургера"
                ),
                IngredientDto(
                    "4",
                    "шт",
                    "Помидор, нарезанный кольцами"
                ),
                IngredientDto(
                    "2",
                    "ст. л.",
                    "Горчица"
                ),
                IngredientDto(
                    "2",
                    "ст. л.",
                    "Кетчуп"
                ),
                IngredientDto(
                    "0",
                    "",
                    "Соль и черный перец"
                )
            ),
            listOf(
                "В глубокой миске смешайте говяжий фарш, лук, чеснок, соль и перец. Разделите фарш на 4 равные части и сформируйте котлеты.",
                "Разогрейте сковороду на среднем огне. Обжаривайте котлеты с каждой стороны в течение 4-5 минут или до желаемой степени прожарки.",
                "В то время как котлеты готовятся, подготовьте булочки. Разрежьте их пополам и обжарьте на сковороде до золотистой корочки.",
                "Смазать нижние половинки булочек горчицей и кетчупом, затем положите лист салата, котлету, кольца помидора и закройте верхней половинкой булочки.",
                "Подавайте бургеры горячими с картофельными чипсами или картофельным пюре."
            ),
            "burger_hamburger.png"
        ),
        RecipeDto(
            1,
            "Чизбургер с беконом",
            listOf(
                IngredientDto(
                    "0.4",
                    "кг",
                    "Говяжий фарш"
                ),
                IngredientDto(
                    "4",
                    "шт",
                    "Ломтика бекона"
                ),
                IngredientDto(
                    "4",
                    "шт",
                    "Ломтика сыра чеддер"
                ),
                IngredientDto(
                    "4",
                    "шт",
                    "Булочки для бургера"
                ),
                IngredientDto(
                    "1",
                    "шт",
                    "Помидор, нарезанный"
                ),
                IngredientDto(
                    "0",
                    "",
                    "Майонез и кетчуп"
                )
            ),
            listOf(
                "Обжарьте бекон на сковороде до хрустящей корочки, отложите на бумажное полотенце.",
                "Сформируйте из фарша 4 котлеты, обжарьте с каждой стороны по 4 минуты.",
                "За минуту до готовности положите на каждую котлету по ломтику сыра, чтобы он расплавился.",
                "Соберите бургер: булочка, майонез, котлета с сыром, бекон, помидор, кетчуп.",
                "Подавайте горячими."
            ),
            "burger_cheeseburger.png"
        )
    )
    private val allRecipes = burgerRecipesList

    override suspend fun getCategories(): List<CategoryDto> {
        return categoryList
    }

    override suspend fun getRecipesByCategory(categoryId: Int): List<RecipeDto> {
        return when (categoryId) {
            0 -> burgerRecipesList
            else -> emptyList()
        }
    }

    override suspend fun getRecipe(recipeId: Int): RecipeDto? {
        return allRecipes.find { it.id == recipeId }
    }
}