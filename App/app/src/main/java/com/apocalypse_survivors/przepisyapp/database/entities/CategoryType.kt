package com.apocalypse_survivors.przepisyapp.database.entities

import android.content.Context

enum class CategoryType(val isMainCategory: Boolean) {
    ALL(true),
    BAKING(true),
        ROLLS(false),
        CROISSANTS(false),
        BREADS(false),
    BEVERAGES(true),
        ALKOCHOL_RFEE_DRINKS(false),
        DRINKS(false),
    CAKES(true),
        COOKIES(false),
        SHORTBREAD_FRENCH_PASTRIES(false),
        CHEESECAKES(false),
        SPONGE_CAKES(false),
        YEAST_DOUGH(false),
        CAKES_WITH_A_MASS(false),
        BIRTHDAY_CAKES(false),
        CUPCAKES_MUFFINS(false),
    DESSERTS(true),
        FRUIT_DESSERTS(false),
        JELLIES_PUDDINGS(false),
        CREAMS_MOUSSES(false),
        OTHER_DESSERTS(false),
    FISHES_SEAFOOD(true),
        FISHES(false),
        SEAFOOD(false),
    GRILLED(true),
    MEAT_DISHES(true),
        POULTRY(false),
        PORK(false),
        BEEF(false),
        VEAL(false),
        VENISON(false),
        MUTTON_LAMB(false),
        MIXED_MEATS(false),
    PASTA(true),
    SALADS(true),
    SNACKS(true),
        COLD_SNACKS(false),
        HOT_SNACKS(false),
    SOUPS(true);
//    VEGETARIAN(true),
//    CHILDRENS_DISHES(true),
//    GROATS_RICE(true),
//        GROATS(false),
//        RICE(false),
//    PANCAKES_OMELETS_TORTILLAS(true),
//        PANCAKES(false),
//        OMLETS(false),
//        TORTILLAS(false),
//    NOODLES_PIES_DUMPLINGS(true),
//        NOODLES(false),
//        PIES(false),
//        DUMPLINGS(false),
//    CASSEROLES_PIZZAS_TARTS_TOASTS(true),
//        CASSEROLES(false),
//        PIZZAS(false),
//        TARTS(false),
//        TOASTS(false),
//    PRESERVES_PICKLES(true),
//        SWEET_PRESERVES(false),
//        DRY_PRESERVES(false),
//        PICKLES(false),
//    SAUCES_PASTES(true),
//        SAUCES(false),
//        PASTES(false),


    /**
     * Returns a localized label used to represent this enumeration value.  If no label
     * has been defined, then this defaults to the result of {@link Enum#name()}.
     *
     * <p>The name of the string resource for the label must match the name of the enumeration
     * value.  For example, for enum value 'ENUM1' the resource would be defined as 'R.string.ENUM1'.
     *
     * @param context   the context that the string resource of the label is in.
     * @return      a localized label for the enum value or the result of name()
     */
    fun getLabel(context: Context): String {
        val res = context.resources
        val resId = res.getIdentifier(this.name, "string", context.packageName)
        return if (0 != resId) {
            res.getString(resId)
        } else name
    }
}