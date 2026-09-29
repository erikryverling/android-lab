# Android Lab
This is where I play around with the latest Android stuff

## Gradle module execution order
<img width="1355" alt="Screenshot 2022-11-16 at 10 45 15" src="https://user-images.githubusercontent.com/1917608/202146304-4b734396-b071-4dc8-9a1b-350396561b5e.png">

## Running
You need to create a `local.properties` file with the following keys:
* `openWeatherMapApiKey` that contains an API key for [Open Weather Map](https://openweathermap.org)

You also need to add a `google-services.json` file with support for [Firebase AI](https://firebase.google.com/docs/ai-logic/get-started).

## Screenshot testing

Screenshot tests use Compose Preview Screenshot Testing with AGP test suites.

### Generate or update baseline images

Render composables and record golden reference images:

```bash
./gradlew updateScreenshotTestDefaultDebugTestSuite
```

Or for a specific module:

```bash
./gradlew :mobile:common:ui:updateScreenshotTestDefaultDebugTestSuite
```

Reference images are saved under `{module}/src/screenshotTestDefaultDebug/reference/`.

### Run and verify tests

Compare current previews against reference images:

```bash
./gradlew testScreenshotTestDefaultDebugTestSuite
```

Or for a specific module:

```bash
./gradlew :mobile:common:ui:testScreenshotTestDefaultDebugTestSuite
```

Test reports are generated at `{module}/build/reports/tests/{taskName}/index.html`.

