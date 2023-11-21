package com.cvopa.peter.play.db

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DbModule {
    @Binds
    abstract fun provideContactsRepositoryImpl(contactsRepositoryImpl: ContactsRepositoryImpl): ContactsRepository
}