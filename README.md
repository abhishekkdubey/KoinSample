# KoinSample
This Project contains Sample Android Application on Koin Dependency Injection(DI) with Architecture Component like RoomDB.
It's completly MVVM Based project.

### List of third party dependecy used in this project
```.groovy
    //For Network Communication
    implementation 'com.squareup.okhttp3:okhttp:3.12.7'
    implementation 'com.squareup.okhttp3:logging-interceptor:3.12.7'
    implementation 'com.squareup.retrofit2:retrofit:2.6.4'
    implementation 'com.squareup.retrofit2:converter-gson:2.6.4'

    //DI
    implementation 'org.koin:koin-android-scope:2.0.0'
    implementation 'org.koin:koin-android-viewmodel:2.0.0'

    implementation 'com.github.bumptech.glide:glide:4.11.0'
    kapt 'com.github.bumptech.glide:compiler:4.11.0'

    //For Unit Testing
    testImplementation 'junit:junit:4.13'
    testImplementation 'org.mockito:mockito-core:2.24.5'
    testImplementation 'io.mockk:mockk:1.10.0'
    testImplementation  'androidx.test.ext:junit:1.1.1'

    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'

```
