package ru.rougsig.ambercard.common.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by rougs on 21-Oct-17.
 */
@Module
class ContextModule(
        private var context: Context
) {
    @Provides
    @Singleton
    fun provideContext(): Context = context
}