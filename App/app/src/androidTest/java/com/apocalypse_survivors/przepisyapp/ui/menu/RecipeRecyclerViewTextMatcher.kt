package com.apocalypse_survivors.przepisyapp.ui.menu

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

//https://stackoverflow.com/questions/37736616/espresso-how-to-find-a-specific-item-in-a-recycler-view-order-is-random
class RecipeRecyclerViewTextMatcher {
    fun withTitle(title: String): Matcher<RecyclerView.ViewHolder> {
        return object : BoundedMatcher<RecyclerView.ViewHolder, RecipeAdapter.RecipeHolder>(
            RecipeAdapter.RecipeHolder::class.java!!
        ) {
            override fun matchesSafely(item: RecipeAdapter.RecipeHolder): Boolean {
                return item.nameTextView.text.toString() == title
            }

            override fun describeTo(description: Description) {
                description.appendText("view holder with title: $title")
            }
        }
    }
}