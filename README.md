The purpose of the project was which provides a simple and intuitive way for users to send money to their loved ones in Africa. The app was required the usage of jetpack compose on the UI + Maetrial Design 2/3

**Features**

* HomePage: Consist of multiple tabs
     * Transaction tab displays : the user Infos (name, current balance, currency) -  4 cards which highlights the various operations he can - at the bottom the list of his previous transactions
     * Send Money tab which is the entry point to many features of the app. Basically it is from this tab that the user initiates the flow to send money to someone
       

* Send Money Tab: it's home screen displays the list of options by which the user can send money amongst which is the SendToAfrica
* Send To Africa screen: displays the list of methods by which a user can send the money to Africa; including Mobile wallets
* Mobile Wallet screen: Consists of 2 tabs: one displays the list of previous recipients, the other displays a form to enter the details of the recipient
* Choose a mobile wallet: displys the list of supported mobile money providers
* Send Money: Form to enter the amount and all the relevant fees for the transaction

**Tech Stack**

* Kotlin Flow
* Retrofit-OkHttp-Gson
* Room
* Jetpack Compose toolkit : ComposeUI, ComposeNavigation, Compose-Coil, Compose-ConstraintLayout, Compose-Tooling, Compose-Material 3
* Dependency Injection : Dagger-Hilt
* Architecture (presentation layer): MVVM

**Project Constraints**

- We created functions to generate user data; that data is saved in ShaaredPreferences: the data consist of name - balance. Once the app's cache is cleared new values are generated for these. The balance get's updated after every successful transaction. We were required to work using € as our main curreny. Therefore this currency is used through the app in a static manner.

- API endpoints: there was some inconsistency in the endpoints for the list of countries & the list of recipients. Precisely, the country Morocco is written in French in the endpoints to get the recipients and in english for the other.
  We used those fields in our work so we had to forcefully set the value for that country to be equal in both endpoints for consistency.
  
- Design constraints: The design requires a phone number to be provided; this wasn't present in the endpoint for the recipients. It also required profilePictures which wasn't available as well.
  
- Database: Every new recipient that gets added after a transaction requires an Id. Since the endpoint has the field provided, we were required to keep some consistency in the ID's that we generated. We used the last ID from the list of recipients endpoint. That ID was incremented to a new value, and it the latter that is used after the first transaction that we perform.  Every subsequent transaction we make will rely on the last Id saved in our SharedPreference. Of course if the app's cache is cleared, everything goes away.
  
- UI : Some screens had very complex designs and considerirng the functionality associated to them we needed to be very mindful as to how we limit/define the responsabilites of our Viewmodels towards our composables.
  The composable themselves had to be extracted as small pieces to be able to properly manage their states. We realized that some colors on the design were not always consistent after installing the app. This might be device-sepcific though. Also, the bttom navigation on some devices appeared to be partially hidden at the bottom by the navigation bars; on some emulators, it appears to be displayed exactly as it is on Figma. Givent the time constriants, we couldn't investigate the cause of the issue


