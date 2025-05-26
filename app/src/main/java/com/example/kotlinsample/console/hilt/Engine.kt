package com.example.kotlinsample.console.hilt

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Qualifier

interface Engine {
    fun start()
    fun shutdown()
}

class GasEngine @Inject constructor() : Engine {
    override fun start() {
        println("Gas engine start.")
    }

    override fun shutdown() {
        println("Gas engine shutdown.")
    }
}

class ElectricEngine @Inject constructor() : Engine {
    override fun start() {
        println("Electric engine start.")
    }

    override fun shutdown() {
        println("Electric engine shutdown.")
    }
}

class Driver @Inject constructor(val name: String) {

    override fun toString(): String {
        return "Driver(name=$name)"
    }
}

// 方式1：通过入参来确认实际注入的接口对象，GasEngine
@Module
@InstallIn(ActivityComponent::class)
abstract class EngineModule1 {

    @Binds
    abstract fun bindEngine(gasEngine: GasEngine): Engine
}

// 方式2：通过注释来确定具体注入哪个接口对象
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BindGasEngine
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BindElectricEngine
@Module
@InstallIn(ActivityComponent::class)
abstract class EngineModule2 {

    @BindGasEngine
    @Binds
    abstract fun bindGasEngine(gasEngine: GasEngine): Engine

    @BindElectricEngine
    @Binds
    abstract fun bindElectricEngine(electricEngine: ElectricEngine): Engine
}

// 或使用 @Provides
//@Module
//@InstallIn(ActivityComponent::class)
//object EngineModule {
//    @Provides
//    fun provideGasEngine(): Engine = GasEngine()
//}


@Module
@InstallIn(ActivityComponent::class)
object AppModule {
    @Provides
    fun provideDriverName(): String = "Hua" // 提供具体的 String 值
}



class Truck @Inject constructor(private val driver: Driver) {

    // 方式1
    @Inject
    lateinit var engine: Engine

    // 方式2：
    @BindGasEngine
    @Inject
    lateinit var gasEngine: Engine

    @BindElectricEngine
    @Inject
    lateinit var electricEngine: Engine

    fun deliver() {
        // 方式1
        engine.start()
        println("Truck is delivering cargo. Driven by $driver")
        engine.shutdown()

        // 方式2
        gasEngine.start()
        electricEngine.start()
        println("Truck is delivering cargo. Driven by $driver")
        gasEngine.shutdown()
        electricEngine.shutdown()
    }
}


