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
2. For the medicines which require prescription, the app has feature for uploading prescriptions as well.
3. The customer can look into his/her current bookings and also locate the shops on map.
4. A shopkeeper can update the respective shop inventory by searching those medicines.
5. The shopkeeper can also look into the current bookings within a given deadline with the necessary details of medicine and customer. 
6. Booking history has been provided both for **shopkeeper** and **customer**.
7. Complaint Forum

*For more information, please refer to the [wiki](http://github.com/shobhi1310/MedConnect/wiki)*

Screenshots for Customer
------------------------
![Sign Up Page](Screenshots/sign_up.png "Sign Up Page")
![Search](Screenshots/search_medicine_customer.png "Search Medicine")
![Shop List](Screenshots/shop.png "Shop list")
![Upload](Screenshots/prescription.png "Prescription")
![Booking timer](Screenshots/booking_inter.png "Booking intermediate")
![Map](Screenshots/map.png "Map Page")
![Booking Success](Screenshots/booking_success.png "Booking Succesful")
![Current Booking](Screenshots/current_booking_succesful.png "Current Booking Page")

Screenshots for Shop Owners
---------------------------
![Sign Up Page](Screenshots/signup_shop.png "Sign Up Page")
![Inventory](Screenshots/inventory.png "Inventory page")
![Search](Screenshots/search_medicine.png "Search Medicine")
![Inventory Update](Screenshots/shop_medicine_add.png "Shop list")
![Booking History](Screenshots/booking_history.png "Booking History Shop")

## App info
* Target SDK 29
* Release version v 1.0.0

## Libraries Used

* [AppCompat][0] - Degrade gracefully on older versions of Android.
* [ConstraintLayout][1] - Position and size widgets in a flexible way with relative positioning.
* [Material][2] - migrate your codebase from the Design Support Library to Material Components for Android.
* [LifeCycle][3] - Create a UI that automatically responds to lifecycle events.
* [ViewPager][4] - Display Views or Fragments in a swipeable format.
* [Navigation][5] - Handle everything needed for in-app navigation.
* [RecyclerView][6] - A flexible view for providing a limited window into a large data set.
* [CardView][7] - A FrameLayout with a rounded corner background and shadow.
* [Maps][8] - With the Maps SDK for Android, you can add maps based on Google Maps data.
* [Volley][9] - HTTP library that makes networking for Android apps easier and faster.

[0]: https://developer.android.com/jetpack/androidx/releases/appcompat#version_120_3
[1]: https://developer.android.com/jetpack/androidx/releases/constraintlayout
[2]: https://material.io/develop/android/docs/getting-started
[3]: https://developer.android.com/topic/libraries/architecture/lifecycle
[4]: https://developer.android.com/jetpack/androidx/releases/viewpager
[5]: https://developer.android.com/topic/libraries/architecture/navigation/
[6]: https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView
[7]: https://developer.android.com/reference/androidx/cardview/widget/CardView
[8]: https://developers.google.com/maps/documentation/android-sdk/overview
[9]: https://developer.android.com/training/volley

## Upcoming features

These features are supposed to be released in the next update.

* Advanced Payment of Booking. 
* Cancellation of Booking.
* Rating of Medical Shops.
* Medical shop license verification.
* Feedback Forum

Interested in seeing a particular feature of the Android Framework in this
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
2. Run the following on terminal
```bash
git checkout -b my-new-feature
git commit -m 'Add some feature'
git push origin my-new-feature
```
3. Create a new Pull Request
