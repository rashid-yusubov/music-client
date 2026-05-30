# 🎵 music-client

Android-приложение для прослушивания музыки, разработанное с использованием современных технологий Android-разработки.

## ✨ Основные возможности

- **Воспроизведение треков:** Полнофункциональный аудиоплеер на базе Media3 ExoPlayer с управлением воспроизведением и прогресс-баром.
- **Управление плейлистами:** Возможность просмотра списка плейлистов и добавления в них любимых треков.
- **Избранное:** Добавление треков в список избранного для быстрого доступа.
- **Информация об исполнителях:** Просмотр подробной информации об артистах и их альбомах.
- **Современный интерфейс:** Полностью декларативный UI на Jetpack Compose с поддержкой Material Design 3.

## 🛠 Технологический стек

| Категория | Технология |
| :--- | :--- |
| **UI** | [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3) |
| **Архитектура** | Clean Architecture + MVVM |
| **Инъекция зависимостей** | [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) |
| **Сеть** | [Ktor Client](https://ktor.io/docs/client-dependencies.html) |
| **Аудио** | [Media3 ExoPlayer](https://developer.android.com/guide/topics/media/exoplayer) |
| **Локальное хранилище** | [Room](https://developer.android.com/training/data-storage/room) и [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) |
| **Загрузка изображений** | [Coil](https://coil-kt.github.io/coil/) |
| **Асинхронность** | Kotlin Coroutines & Flow |
| **Сериализация** | Kotlinx Serialization |

## 📂 Структура проекта

```text
music-client/
├── app/                        # Основной Android-модуль
│   ├── src/main/java/com/rashidyusubov/musicapp/
│   │   ├── core/               # Общие утилиты, расширения и DI
│   │   ├── data/               # Слой данных (Repositories, API, DB)
│   │   │   ├── remote/         # Ktor клиенты, DTO и Firebase Auth
│   │   │   ├── local/          # Room DAO, базы данных и DataStore
│   │   │   ├── repository/     # Реализация интерфейсов репозиториев
│   │   │   └── mapper/         # Мапперы (DTO <-> Domain <-> Entity)
│   │   ├── domain/             # Слой бизнес-логики (чистый Kotlin)
│   │   │   ├── model/          # Доменные модели данных
│   │   │   ├── repository/     # Интерфейсы репозиториев
│   │   │   └── usecase/        # Сценарии использования
│   │   └── presentation/       # Слой UI (Jetpack Compose)
│   │       ├── [feature]/      # Фичи: home, player, search, profile и др.
│   │       ├── components/     # Переиспользуемые UI компоненты
│   │       ├── navigation/     # Конфигурация навигации
│   │       └── theme/          # Тема приложения (Цвета, Типографика)
│   └── src/main/res/           # Android ресурсы
├── gradle/                     # Gradle конфигурация и Version Catalog
├── build.gradle.kts            # Скрипт сборки проекта
├── settings.gradle.kts         # Настройки проекта
└── README.md
```

## 🚀 Установка и запуск

1. Склонируйте репозиторий.
```bash
git clone https://github.com/rashid-yusubov/music-client.git
```
2. Откройте проект в Android Studio.
3. Дождитесь завершения синхронизации Gradle.
4. Запустите приложение на эмуляторе или физическом устройстве.

---