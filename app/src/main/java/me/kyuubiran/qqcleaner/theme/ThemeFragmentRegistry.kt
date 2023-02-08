package me.kyuubiran.qqcleaner.theme

interface ThemeFragmentRegistry {
    fun initFragment() {
        initColor()
        initDrawable()
        initLayout()
        initListener()
    }

    fun initColor()

    fun initDrawable() {}

    fun initLayout() {}

    fun initListener()
}