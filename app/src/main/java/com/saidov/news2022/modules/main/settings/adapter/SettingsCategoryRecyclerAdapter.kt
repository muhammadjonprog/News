package com.saidov.news2022.modules.main.settings.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.saidov.news2022.R
import com.saidov.news2022.modules.main.settings.model.SettingsCategoryModel

/**
 * Created by MUHAMMADJON SAIDOV on 07,февраль,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */

class SettingsCategoryRecyclerAdapter :
    RecyclerView.Adapter<SettingsCategoryRecyclerAdapter.MyViewHolder>() {

    private var items: ArrayList<SettingsCategoryModel> = ArrayList()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var checkBox: MaterialCheckBox = itemView.findViewById(R.id.checkbox)

        fun bind(categoryModel: SettingsCategoryModel) {
            checkBox.isChecked = categoryModel.isChecked
            checkBox.text = categoryModel.name
            checkBox.setOnCheckedChangeListener{button,isCheched->
                categoryModel.isChecked = isCheched
            }
        }

        companion object {
            fun bindOnclick(
                categoryModel: SettingsCategoryModel,
                compoundButton: CompoundButton,
                checked: Boolean) {
                bindOn(categoryModel, compoundButton, checked)
            }

            private fun bindOn(categoryModel: SettingsCategoryModel, compoundButton: CompoundButton, checked: Boolean) {
                categoryModel.isChecked = checked
            }
        }
    }

    fun setItems(arrayList: ArrayList<SettingsCategoryModel>) {
        items = arrayList
        notifyItemInserted(itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.checkbox_item, parent, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind((items[position]))
    }

    override fun getItemCount(): Int {
        return items.size
    }
}