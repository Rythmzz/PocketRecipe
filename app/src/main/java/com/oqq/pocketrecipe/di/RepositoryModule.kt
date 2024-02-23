package com.oqq.pocketrecipe.di

import com.oqq.pocketrecipe.repo.Repository
import org.koin.dsl.module

val repositoryModule = module {
    single { Repository(get()) }

}