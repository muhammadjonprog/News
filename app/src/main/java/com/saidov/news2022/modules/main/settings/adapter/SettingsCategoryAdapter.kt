package com.saidov.news2022.modules.main.settings.adapter

import android.widget.CompoundButton
import com.saidov.news2022.modules.main.settings.model.SettingsCategoryModel

/**
 * Created by MUHAMMADJON SAIDOV on 06,февраль,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */

class SettingsCategoryAdapter(private val settingsCategoryModel: SettingsCategoryModel) :
    CompoundButton.OnCheckedChangeListener {

    override fun onCheckedChanged(compoundButton: CompoundButton?, checked: Boolean) {
        SettingsCategoryRecyclerAdapter.MyViewHolder.bindOnclick(settingsCategoryModel,
            compoundButton!!,checked)
    }
}