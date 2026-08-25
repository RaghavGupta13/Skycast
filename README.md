OpenWeather API Key Setup

SkyCast uses the OpenWeather Current Weather API. The API key is not included in this repository to prevent exposing credentials in source control.

Create an account and generate an API key at OpenWeather.
Open the local.properties file in the project root.
Add the following entry:
OPEN_WEATHER_API_KEY=your_api_key_here
Sync Gradle and run the application.

The key is exposed to the application through BuildConfig.OPEN_WEATHER_API_KEY. The local.properties file is excluded from version control.
