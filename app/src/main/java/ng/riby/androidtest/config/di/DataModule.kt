package ng.riby.androidtest.config.di

import androidx.room.Room
import ng.riby.androidtest.data.local.db.AppDatabase
import ng.riby.androidtest.data.repository.ILocationRepository
import ng.riby.androidtest.data.repository.LocationRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "android_test"
        ).build()
    }

    single { get<AppDatabase>().locationDao() }

    single<ILocationRepository> { LocationRepository(get()) }
}