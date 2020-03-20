# News Application

## 简介

这是一个MVVM-Based的项目，是我个人对MVVM的一些理解

由于使用了AndroidX中最新的API（有些尚不稳定），可能不适用于生产环境，仅仅作为参考

## 使用到的技术

* 导航 - AndroidX Navigation

  采用单Activity + Fragment的架构，使用Navigation作为导航组件。

  Activity的启动涉及进程通信，而Fragment切换不需要，使用这样的架构，有助于我们打造画面间切换更加顺畅的App。

* 依赖注入 - insertKoin

  Koin 是一个相对小巧的依赖注入框架。

  虽然官网叫做依赖注入（Dependency Injection），但是其本质更像是Service Locator。

  对于Android来说，其实没有严格的DI，因为所有的Activity对象由系统管理，自动注入几乎是不可能的，即便是Dagger这样的框架，也需要手动在Activity的onCreate()生命周期中触发注入。

* 网络请求 - Retrofit

  Retrofit是一个很流行的网络请求工具，在Github上已有35k的star。

  其使用也非常简单，只需声明Service接口，然后通过注解标记请求方法即可。

  Retrofit使用动态代理方式生成Service的实现，在编译期间不会产生大量的类，同时支持Kotlin协程，使用体验良好。

* 异步操作 - Kotlin Coroutine

* UI架构 - MVVM（ViewModel + LiveData）

* 其他

  * AndroidX Paging - 分页加载库
  * WorkManager - 后台任务库