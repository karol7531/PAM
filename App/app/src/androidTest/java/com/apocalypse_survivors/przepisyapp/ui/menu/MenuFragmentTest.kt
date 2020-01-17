package com.apocalypse_survivors.przepisyapp.ui.menu

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnHolderItem
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToHolder
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.apocalypse_survivors.przepisyapp.R
import com.apocalypse_survivors.przepisyapp.ui.activity.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MenuFragmentTest {

    private val recipeName = "name test"
    private val recipeIngredients = "ingredients description"
    private val steps = listOf(
//        "first step test",
//        "testing second step",
//        "third step",
//        "fourth step just to be sure",
        "and the last is the fifth step")
    private var itemsCount = 1

    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        activityRule.activity.supportFragmentManager.beginTransaction()
    }

    @Test
    fun testRecipe(){
        //MenuFragment
        //click fab to add new recipe
        onView(withId(R.id.menu_fab_modify))
            .perform(click())

        //ModifyFragment
        //setting name
        onView(withId(R.id.modify_text_name))
            .perform(typeText(recipeName))
            .perform(closeSoftKeyboard())

        //setting ingredients
        onView(withId(R.id.modify_text_ingredients))
            .perform(typeText(recipeIngredients))
            .perform(closeSoftKeyboard())

        //setting steps
        for(step in steps){
            //check if recyclerview has itemsCount of items
            onView(withId(R.id.modify_steps_recyclerview))
                .check( RecyclerViewItemCountAssertion(itemsCount))

            //click on item at itemsCount-1 position
            onView(withId(R.id.modify_steps_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(itemsCount - 1, click()))

            //type text on item at itemCount-1 position
            onView(RecyclerViewPositionMatcher(R.id.modify_steps_recyclerview)
                .atPositionOnView(itemsCount - 1, R.id.step_item_desc))
                .perform(typeText(step))
                .perform(closeSoftKeyboard())

            //new item should be added after typing something in the last item
            itemsCount++
        }

        //click fab to save new recipe
        onView(withId(R.id.modify_fab_done))
            .perform(click())

        //MenuFragment
        //click on added recipe item
        var matcher = RecipeRecyclerViewTextMatcher().withTitle(recipeName)
        onView(withId(R.id.menu_recycler_view))
            .perform(scrollToHolder(matcher))
            .perform(actionOnHolderItem(matcher, click()))

        //RecipeFragment
        //check if ingredients are ok
        onView(withId(R.id.recipe_description))
            .check(matches(withText(recipeIngredients)))


        //check if recyclerview has steps.size of items
        onView(withId(R.id.recipe_steps_recyclerview))
            .check( RecyclerViewItemCountAssertion(steps.size))


        //check if steps are ok
        for((position, step) in steps.withIndex()){
            //type text on item at itemCount-1 position
            onView(RecyclerViewPositionMatcher(R.id.recipe_steps_recyclerview)
                .atPositionOnView(position, R.id.recipe_step_item_desc))
                .check(matches(withText(step)))
        }

        //go back to MenuFragment
        pressBack()

        //MenuFragment
        //swipe right added recipe item
//        matcher = RecipeRecyclerViewTextMatcher().withTitle(recipeName)
//        onView(withId(R.id.menu_recycler_view))
//            .perform(scrollToHolder(matcher))
//            .perform(actionOnHolderItem(matcher, swipeRight()))
//
//
//        Thread.sleep(2000)
    }

    @Test
    fun testSwipe(){
        val matcher = RecipeRecyclerViewTextMatcher().withTitle(recipeName)
        onView(withId(R.id.menu_recycler_view))
            .perform(scrollToHolder(matcher))
            .perform(actionOnHolderItem(matcher, swipeRight()))
    }

    @After
    fun tearDown() {
    }
}