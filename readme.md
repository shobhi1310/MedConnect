# MedConnect

A medicine search app for providing an ease of locating nearby medicine shops and medicine availibility.

MedConnect is currently under heavy development. Note that some changes (such as database schema modifications) are not backwards compatible and may cause the app to crash. In this case, please uninstall and re-install the app.

## Installation
Clone this repository and import into **Android Studio**
```bash
git clone https://github.com/shobhi1310/MedConnect.git
```
The App is communicating with a custom made [*API*](http://glacial-caverns-39108.herokuapp.com/), the documentation of which is available [*here*](https://github.com/shobhi1310/medcon_server)

## App features
As of now the app performs the following features:
1. A customer can search for the medicine and get the corresponding nearby medicine stores in his/her city, see the location in map and also book it for a specified period of time.
2. The customer can look into his/her current bookings and also locate the shops on map.
3. A shopkeeper can update the respective shop inventory by searching those medicines.
4. The shopkeeper can also look into the current bookings within a given deadline with the necessary details of medicine and customer. 
5. Booking history has been provided both for **shopkeeper** and **customer**.

*For more information, please refer to the [wiki](http://github.com/shobhi1310/MedConnect/wiki)*

Screenshots
-----------
![Sign Up Page](Screenshots/sign_up.png "Sign Up Page")
![Current Booking](Screenshots/current_booking.png "Current Booking Page")
![Inventory](Screenshots/inventory.png "Shop inventory Page")
![Search](Screenshots/search_medicine.png "Search Medicine")
![Shop](Screenshots/shop.png "Shop page")
![Map](Screenshots/map.png "Map Page")

## App info
* target SDK 29
* release version v 1.0.0
Libraries Used
--------------
* [Foundation][0] - Components for core system capabilities, Kotlin extensions and support for
  multidex and automated testing.
  * [AppCompat][1] - Degrade gracefully on older versions of Android.
  * [Android KTX][2] - Write more concise, idiomatic Kotlin code.
  * [Test][4] - An Android testing framework for unit and runtime UI tests.
* [Architecture][10] - A collection of libraries that help you design robust, testable, and
  maintainable apps. Start with classes for managing your UI component lifecycle and handling data
  persistence.
  * [Data Binding][11] - Declaratively bind observable data to UI elements.
  * [Lifecycles][12] - Create a UI that automatically responds to lifecycle events.
  * [LiveData][13] - Build data objects that notify views when the underlying database changes.
  * [Navigation][14] - Handle everything needed for in-app navigation.
  * [Room][16] - Access your app's SQLite database with in-app objects and compile-time checks.
  * [ViewModel][17] - Store UI-related data that isn't destroyed on app rotations. Easily schedule
     asynchronous tasks for optimal execution.
  * [WorkManager][18] - Manage your Android background jobs.
* [UI][30] - Details on why and how to use UI Components in your apps - together or separate
  * [Animations & Transitions][31] - Move widgets and transition between screens.
  * [Fragment][34] - A basic unit of composable UI.
  * [Layout][35] - Lay out widgets using different algorithms.
* Third party
  * [Glide][90] for image loading
  * [Kotlin Coroutines][91] for managing background threads with simplified code and reducing needs for callbacks

[0]: https://developer.android.com/jetpack/components
[1]: https://developer.android.com/topic/libraries/support-library/packages#v7-appcompat
[2]: https://developer.android.com/kotlin/ktx
[4]: https://developer.android.com/training/testing/
[10]: https://developer.android.com/jetpack/arch/
[11]: https://developer.android.com/topic/libraries/data-binding/
[12]: https://developer.android.com/topic/libraries/architecture/lifecycle
[13]: https://developer.android.com/topic/libraries/architecture/livedata
[14]: https://developer.android.com/topic/libraries/architecture/navigation/
[16]: https://developer.android.com/topic/libraries/architecture/room
[17]: https://developer.android.com/topic/libraries/architecture/viewmodel
[18]: https://developer.android.com/topic/libraries/architecture/workmanager
[30]: https://developer.android.com/guide/topics/ui
[31]: https://developer.android.com/training/animation/
[34]: https://developer.android.com/guide/components/fragments
[35]: https://developer.android.com/guide/topics/ui/declaring-layout
[90]: https://bumptech.github.io/glide/
[91]: https://kotlinlang.org/docs/reference/coroutines-overview.html

Upcoming features
-----------------
Updates will include incorporating additional Jetpack components and updating existing components
as the component libraries evolve.

Interested in seeing a particular feature of the Android Framework or Jetpack implemented in this
app? Please open a new [issue](https://github.com/shobhi1310/MedConnect/issues).

## Maintainers
This project is mantained by:
* [Shubhankar Bhadra](http://github.com/shobhi1310)
* [Tapish Ojha](http://github.com/tapish2000)
* [Mir Sameed Ali](http://github.com/mir-sam-ali)
* [Rohit Shakya](http://github.com/rohit-cs18b029)
* [Chirag Gupta](http://github.com/chirag2706)

## Contributing

1. Fork it
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -m 'Add some feature')
4. Push your branch (git push origin my-new-feature)
5. Create a new Pull Request
