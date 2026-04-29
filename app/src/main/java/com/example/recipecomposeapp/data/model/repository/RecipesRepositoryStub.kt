package com.example.recipecomposeapp.data.model.repository

import com.example.recipecomposeapp.data.model.CategoryDto
import com.example.recipecomposeapp.data.model.IngredientDto
import com.example.recipecomposeapp.data.model.RecipeDto

object RecipesRepositoryStub {
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
                    0.5f,
                    "кг",
                    "Говяжий фарш"
                ),
                IngredientDto(
                    1.0f,
                    "шт",
                    "Луковица, мелко нарезанная"
                ),
                IngredientDto(
                    2.0f,
                    "зубч",
                    "Чеснок, измельченный"
                ),
                IngredientDto(
                    4.0f,
                    "шт",
                    "Булочки для бургера"
                ),
                IngredientDto(
                    4.0f,
                    "шт",
                    "Помидор, нарезанный кольцами"
                ),
                IngredientDto(
                    2.0f,
                    "ст. л.",
                    "Горчица"
                ),
                IngredientDto(
                    2.0f,
                    "ст. л.",
                    "Кетчуп"
                ),
                IngredientDto(
                    0f,
                    "",
                    "Соль и черный перец"
                )
            ),
            "\n В глубокой миске смешайте говяжий фарш, лук, чеснок, соль и перец. Разделите фарш на 4 равные части и сформируйте котлеты." +
                    "\n Разогрейте сковороду на среднем огне. Обжаривайте котлеты с каждой стороны в течение 4-5 минут или до желаемой степени прожарки." +
                    "\n В то время как котлеты готовятся, подготовьте булочки. Разрежьте их пополам и обжарьте на сковороде до золотистой корочки." +
                    "\n Смазать нижние половинки булочек горчицей и кетчупом, затем положите лист салата, котлету, кольца помидора и закройте верхней половинкой булочки." +
                    "\n Подавайте бургеры горячими с картофельными чипсами или картофельным пюре.",
            "burger_hamburger.png"
        ),
        RecipeDto(
            1,
            "Чизбургер с беконом",
            listOf(
                IngredientDto(
                    0.4f,
                    "кг",
                    "Говяжий фарш"
                ),
                IngredientDto(
                    4.0f,
                    "шт",
                    "Ломтика бекона"
                ),
                IngredientDto(
                    4.0f,
                    "шт",
                    "Ломтика сыра чеддер"
                ),
                IngredientDto(
                    4.0f,
                    "шт",
                    "Булочки для бургера"
                ),
                IngredientDto(
                    1.0f,
                    "шт",
                    "Помидор, нарезанный"
                ),
                IngredientDto(
                    0f,
                    "",
                    "Майонез и кетчуп"
                )
            ),
            "\n Обжарьте бекон на сковороде до хрустящей корочки, отложите на бумажное полотенце." +
                    "\n Сформируйте из фарша 4 котлеты, обжарьте с каждой стороны по 4 минуты." +
                    "\n За минуту до готовности положите на каждую котлету по ломтику сыра, чтобы он расплавился." +
                    "\n Соберите бургер: булочка, майонез, котлета с сыром, бекон, помидор, кетчуп." +
                    "\n Подавайте горячими.",
            "burger_cheeseburger.png"
        )
    )
    private val allRecipes = burgerRecipesList

    fun getCategories(): List<CategoryDto> {
        return categoryList
    }

    fun getRecipesByCategoryId(categoryId: Int): List<RecipeDto> {
        return when (categoryId) {
            0 -> burgerRecipesList
            else -> emptyList()
        }
    }

    fun getRecipeById(recipeId: Int): RecipeDto? {
        return allRecipes.find { it.id == recipeId }
    }
}