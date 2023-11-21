package com.cvopa.peter.play.di

import android.content.Context
import androidx.room.Room
import com.cvopa.peter.play.db.ContactsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ContactsDatabaseModule {

    @Provides
    @Singleton
    fun provideContactsDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(context, ContactsDatabase::class.java, "contacts-database").build()

}
