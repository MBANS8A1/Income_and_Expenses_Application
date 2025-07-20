package com.example.income_and_expenses_application.util

import androidx.compose.ui.graphics.Color
import com.example.income_and_expenses_application.R
import com.example.income_and_expenses_application.ui.theme.businessBg
import com.example.income_and_expenses_application.ui.theme.clothBg
import com.example.income_and_expenses_application.ui.theme.electricBg
import com.example.income_and_expenses_application.ui.theme.food_drink
import com.example.income_and_expenses_application.ui.theme.gadgetBg
import com.example.income_and_expenses_application.ui.theme.giftBg
import com.example.income_and_expenses_application.ui.theme.groceryBg
import com.example.income_and_expenses_application.ui.theme.healthBg
import com.example.income_and_expenses_application.ui.theme.homeBg
import com.example.income_and_expenses_application.ui.theme.leisureBg
import com.example.income_and_expenses_application.ui.theme.miscBg
import com.example.income_and_expenses_application.ui.theme.petBg
import com.example.income_and_expenses_application.ui.theme.schBg
import com.example.income_and_expenses_application.ui.theme.snackBg
import com.example.income_and_expenses_application.ui.theme.subBg
import com.example.income_and_expenses_application.ui.theme.taxiBg
import com.example.income_and_expenses_application.ui.theme.travelBg
import com.example.income_and_expenses_application.ui.theme.vehicleBg

enum class Category(
    val title: String,
    val iconRes: Int,
    val bgRes: Color,
    val colourRes: Color = Color.White

) {
    FOOD_DRINK("Food & Drink", R.drawable.drink, food_drink, Color.Black),
    CLOTHING("Clothing", R.drawable.clothing, clothBg, Color.Black),
    HOME("Home", R.drawable.home, homeBg, Color.Black),
    HEALTH("Health", R.drawable.health, healthBg),
    SCHOOL("School", R.drawable.school, schBg),
    GROCERY("Grocery", R.drawable.grocery, groceryBg, Color.Black),
    ELECTRICITY("Electricity", R.drawable.electricity, electricBg, Color.Black),
    BUSINESS("Business", R.drawable.business, businessBg, Color.Black),
    VEHICLE("Vehicle", R.drawable.vehicle, vehicleBg),
    TAXI("Taxi", R.drawable.taxi, taxiBg),
    LEISURE("Leisure", R.drawable.leisure, leisureBg, Color.Black),
    GADGET("Gadget", R.drawable.gadget, gadgetBg),
    TRAVEL("Travel", R.drawable.travel, travelBg, Color.Black),
    SUBSCRIPTION("Subscription", R.drawable.sub, subBg),
    PET("Pet", R.drawable.pet, petBg, Color.Black),
    SNACK("Snack", R.drawable.snack, snackBg, Color.Black),
    GIFT("Gift", R.drawable.gift, giftBg, Color.Black),
    MISC("Miscellaneous", R.drawable.misc, miscBg)

}