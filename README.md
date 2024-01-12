# Forevely

### Getting started

#### Prerequisites

1. Install Docker:

```Bash
brew install docker
```

2. Install Java 17

#### Running the local database

Run MongoDb in Docker:

```Bash
docker run -d -p 27017:27017 --name mongodb-forevely mongo:latest
```

#### Running the backend

Run the backend locally with command or simply press Run from your IDE

 ```Bash
 ./gradlew :run
 ```

#### Running the Android/iOS app

You'll need to create a `local.properties` file in the root of the project and add the following properties:

##### Properties
`graphql.server.url=YOUR_BACKEND_URL`
`openai.key=YOUR_OPEN_AI_KEY`
`db.name=YOUR_DB_NAME` By default: MaiBUDDY.db
`org.name=AJLabs`

To run Android app simply press run `androidApp` from your IDE.

To run iOS app, press Run `Forevely` from the Xcode, or hit tun in Android Studio with KMP plugin installed.
