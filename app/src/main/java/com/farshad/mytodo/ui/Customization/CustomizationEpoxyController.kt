package com.farshad.mytodo.ui.Customization

import android.app.AlertDialog
import android.content.res.ColorStateList
import com.airbnb.epoxy.EpoxyController
import com.farshad.mytodo.R
import com.farshad.mytodo.SharedPrefUtil
import com.farshad.mytodo.addHeaderModel
import com.farshad.mytodo.database.entity.CategoryEntity
import com.farshad.mytodo.databinding.ModelCategoryBinding
import com.farshad.mytodo.databinding.ModelEmptyButtonBinding
import com.farshad.mytodo.databinding.ModelPriorityColorItemBinding
import com.farshad.mytodo.ui.epoxy.ViewBindingKotlinModel

class CustomizationEpoxyController(
    private val customizationInterface: CustomizationInterface
) : EpoxyController() {

    var categories: List<CategoryEntity> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {

        // Categories section
        addHeaderModel("Categories")

        categories.forEach {
            CategoryEpoxyModel(it, customizationInterface).id(it.id).addTo(this)
        }

        EmptyButtonEpoxyModel("Add Category", customizationInterface)
            .id("add_category")
            .addTo(this)

        // Priority customization section
        addHeaderModel("Priorities")

        val highPriorityColor = SharedPrefUtil.getHighPriorityColor()
        val mediumPriorityColor = SharedPrefUtil.getMediumPriorityColor()
        val lowPriorityColor = SharedPrefUtil.getLowPriorityColor()

        PriorityColorItemEpoxyModel("High", highPriorityColor, customizationInterface)
            .id("priority_high")
            .addTo(this)
        PriorityColorItemEpoxyModel("Medium", mediumPriorityColor, customizationInterface)
            .id("priority_medium")
            .addTo(this)
        PriorityColorItemEpoxyModel("Low", lowPriorityColor, customizationInterface)
            .id("priority_low")
            .addTo(this)
    }

    // region EpoxyModels
    data class CategoryEpoxyModel(
        val categoryEntity: CategoryEntity,
        val customizationInterface: CustomizationInterface
    ) : ViewBindingKotlinModel<ModelCategoryBinding>(R.layout.model_category) {

        override fun ModelCategoryBinding.bind() {
            tv.text = categoryEntity.name

            root.setOnClickListener {
                customizationInterface.onCategorySelected(categoryEntity)
            }

            root.setOnLongClickListener {
                AlertDialog.Builder(it.context)
                    .setTitle("Delete ${categoryEntity.name}?")
                    .setPositiveButton("Yes") { _, _ ->
                        customizationInterface.onDeleteCategory(categoryEntity)
                    }
                    .setNegativeButton("Cancel") { _, _ ->
                    }
                    .show()
                return@setOnLongClickListener true
            }
        }
    }

    data class EmptyButtonEpoxyModel(
        val buttonText: String,
        val customizationInterface: CustomizationInterface
    ) : ViewBindingKotlinModel<ModelEmptyButtonBinding>(R.layout.model_empty_button) {

        override fun ModelEmptyButtonBinding.bind() {
            button.text = buttonText
            button.setOnClickListener { customizationInterface.onCategoryEmptyStateClicked() }
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }

    data class PriorityColorItemEpoxyModel(
        val displayText: String,
        val displayColor: Int,
        val customizationInterface: CustomizationInterface
    ) : ViewBindingKotlinModel<ModelPriorityColorItemBinding>(R.layout.model_priority_color_item) {

        override fun ModelPriorityColorItemBinding.bind() {
            textView.text = displayText
            root.setStrokeColor(ColorStateList.valueOf(displayColor))
            imageView.setBackgroundColor(displayColor)
            imageView.setOnClickListener { customizationInterface.onPrioritySelected(displayText) }
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }
    // endregion EpoxyModels
}