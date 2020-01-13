package com.example.saltoapp.view.di

import com.example.saltoapp.view.navigator.NavigatorImpl
import dagger.Module
import dagger.Provides

@Module
class Modules {

    @Provides
    open fun navigator(): NavigatorImpl {
        return NavigatorImpl()
    }

}